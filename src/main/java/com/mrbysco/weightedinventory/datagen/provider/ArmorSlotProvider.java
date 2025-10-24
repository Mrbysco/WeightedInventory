package com.mrbysco.weightedinventory.datagen.provider;

import com.google.common.collect.Sets;
import com.mojang.serialization.JsonOps;
import com.mrbysco.weightedinventory.registry.ArmorSlot;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public abstract class ArmorSlotProvider implements DataProvider {
	protected final CompletableFuture<HolderLookup.Provider> lookupProvider;

	protected final PackOutput.PathProvider armorSlotPathProvider;
	private final String modID;

	public ArmorSlotProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, String modID) {
		this.armorSlotPathProvider = packOutput.createPathProvider(PackOutput.Target.DATA_PACK, "armor_slot");
		this.lookupProvider = lookupProvider;
		this.modID = modID;
	}

	@Override
	public CompletableFuture<?> run(CachedOutput cache) {
		return this.lookupProvider.thenCompose((registries) -> {
			List<CompletableFuture<?>> list = new ArrayList<>();
			Set<ResourceLocation> set = Sets.newHashSet();
			var ops = RegistryOps.create(JsonOps.INSTANCE, registries);
			this.defineSlots((wrappedSlot) -> {
				if (!set.add(wrappedSlot.getId())) {
					throw new IllegalStateException("Duplicate Places " + wrappedSlot.getId());
				} else {
					list.add(DataProvider.saveStable(cache,
							ArmorSlot.DIRECT_CODEC.encodeStart(
									ops, wrappedSlot.getSlot()
							).getOrThrow(false, LOGGER::error),
							this.armorSlotPathProvider.json(wrappedSlot.getId()))
					);
				}
			});

			return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
		});
	}

	public void defineSlots(Consumer<WrappedSlot> consumer) {

	}

	protected void addSlot(Consumer<WrappedSlot> consumer, String name, ArmorSlotBuilder builder) {
		consumer.accept(new WrappedSlot(builder.createArmorSlot(), new ResourceLocation(modID, name)));
	}

	@Override
	public String getName() {
		return "Armor Slots: " + modID;
	}
}
