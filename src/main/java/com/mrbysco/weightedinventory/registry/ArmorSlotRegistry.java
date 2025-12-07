package com.mrbysco.weightedinventory.registry;

import com.mrbysco.weightedinventory.WeightedInventoryMod;
import com.mrbysco.weightedinventory.config.WeightedConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("removal")
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ArmorSlotRegistry {
	private static final Map<Item, Float> itemSlotCache = new HashMap<>();
	private static final Map<ResourceLocation, ArmorSlot> armorSlotMap = new HashMap<>();

	public static float getSlots(ItemStack stack) {
		if (stack.isEmpty()) return 0.0F;
		if (itemSlotCache.containsKey(stack.getItem())) {
			return itemSlotCache.get(stack.getItem());
		}
		for (ArmorSlot armorSlot : armorSlotMap.values()) {
			for (Holder.Reference<Item> armorItem : armorSlot.armorItems()) {
				if (armorItem.is(stack.getItem().builtInRegistryHolder())) {
					float slots = armorSlot.slotsPerPiece();
					itemSlotCache.put(stack.getItem(), slots);
					return slots;
				}
			}
		}
		return WeightedConfig.COMMON.defaultSlotCount.get().floatValue();
	}

	@SubscribeEvent
	public static void onNewRegistry(DataPackRegistryEvent.NewRegistry event) {
		event.dataPackRegistry(ArmorSlot.REGISTRY_KEY, ArmorSlot.DIRECT_CODEC, ArmorSlot.DIRECT_CODEC);
	}

	public static void onTagsUpdated(OnDatapackSyncEvent event) {
		final RegistryAccess registryAccess = event.getPlayerList().getServer().registryAccess();

		itemSlotCache.clear();
		armorSlotMap.clear();
		final Registry<ArmorSlot> biomePlaceRegistry = registryAccess.registryOrThrow(ArmorSlot.REGISTRY_KEY);
		biomePlaceRegistry.entrySet().forEach((key) -> armorSlotMap.put(key.getKey().location(), key.getValue()));
		WeightedInventoryMod.LOGGER.info("Loaded unlocked armor slots: {} ", armorSlotMap.size());
	}
}
