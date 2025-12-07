package com.mrbysco.weightedinventory.datagen.provider;

import com.google.common.collect.ImmutableList;
import com.mrbysco.weightedinventory.WeightedInventoryMod;
import com.mrbysco.weightedinventory.registry.ArmorSlot;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.WithConditions;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public abstract class ArmorSlotProvider implements DataProvider {
	protected final CompletableFuture<HolderLookup.Provider> registries;

	protected final PackOutput.PathProvider armorSlotPathProvider;
	private final String modID;
	private final Map<String, WithConditions<ArmorSlot>> toSerialize = new HashMap<>();

	public ArmorSlotProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries, String modID) {
		this.armorSlotPathProvider = packOutput.createRegistryElementsPathProvider(ArmorSlot.REGISTRY_KEY);
		this.registries = registries;
		this.modID = modID;
	}

	@Override
	public final CompletableFuture<?> run(CachedOutput cache) {
		return this.registries.thenCompose(registries -> this.run(cache, registries));
	}

	public CompletableFuture<?> run(CachedOutput cache, HolderLookup.Provider registries) {
		this.defineSlots(registries);
		ImmutableList.Builder<CompletableFuture<?>> futuresBuilder = new ImmutableList.Builder<>();

		for (var entry : toSerialize.entrySet()) {
			var name = entry.getKey();
			var modifier = entry.getValue();
			Path modifierPath = armorSlotPathProvider.json(ResourceLocation.fromNamespaceAndPath(modID, name));
			futuresBuilder.add(
					DataProvider.saveStable(cache, registries, ArmorSlot.CONDITIONAL_CODEC, Optional.of(modifier), modifierPath)
			);
		}

		return CompletableFuture.allOf(futuresBuilder.build().toArray(CompletableFuture[]::new));
	}

	public void defineSlots(HolderLookup.Provider registries) {

	}

	public void addSlot(String slotId, ArmorSlotBuilder builder, List<ICondition> conditions) {
		this.toSerialize.put(slotId, new WithConditions<>(conditions, builder.createArmorSlot()));
	}

	public void addSlot(String slotId, ArmorSlotBuilder builder, ICondition... conditions) {
		addSlot(slotId, builder, Arrays.asList(conditions));
	}

	@Override
	public String getName() {
		return "Armor Slot Definitions: " + modID;
	}
}
