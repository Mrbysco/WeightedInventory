package com.mrbysco.weightedinventory.registry;

import com.mrbysco.weightedinventory.config.WeightedConfig;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DataPackRegistryEvent;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
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
				if (stack.getItem().builtInRegistryHolder().equals(armorItem)) {
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

	public static void updateMap(Map<ResourceLocation, ArmorSlot> newMap) {
		armorSlotMap.clear();
		itemSlotCache.clear();
		armorSlotMap.putAll(newMap);
	}
}
