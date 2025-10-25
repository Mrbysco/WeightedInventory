package com.mrbysco.weightedinventory.client;

import com.mrbysco.weightedinventory.registry.ArmorSlotRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber
public class TooltipHandler {
	@SubscribeEvent
	public static void onTooltipEvent(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		EquipmentSlot slot = getEquipmentSlotForItem(stack);
		if (slot != EquipmentSlot.MAINHAND && slot != EquipmentSlot.OFFHAND && slot != EquipmentSlot.BODY) {
			float slots = ArmorSlotRegistry.getSlots(stack);
			if (slots > 0) {
				event.getToolTip().add(Component.translatable("weightedinventory.tooltip.slot_count", slots).withStyle(ChatFormatting.BLUE));
			}
		}
	}

	/**
	 * Get the equipment slot for the given item stack.
	 *
	 * @param stack The item stack to check.
	 * @return The equipment slot.
	 */
	private static EquipmentSlot getEquipmentSlotForItem(ItemStack stack) {
		EquipmentSlot slot = stack.getEquipmentSlot();
		if (slot != null) {
			return slot;
		} else {
			Equipable equipable = Equipable.get(stack);
			if (equipable != null) {
				return equipable.getEquipmentSlot();
			}

			return EquipmentSlot.MAINHAND;
		}
	}
}
