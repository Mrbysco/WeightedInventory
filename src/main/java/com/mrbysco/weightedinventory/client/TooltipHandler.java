package com.mrbysco.weightedinventory.client;

import com.mrbysco.weightedinventory.registry.ArmorSlotRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class TooltipHandler {
	@SubscribeEvent
	public static void onTooltipEvent(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		EquipmentSlot slot = LivingEntity.getEquipmentSlotForItem(stack);
		if (slot != EquipmentSlot.MAINHAND && slot != EquipmentSlot.OFFHAND) {
			float slots = ArmorSlotRegistry.getSlots(stack);
			if (slots > 0) {
				event.getToolTip().add(Component.translatable("weightedinventory.tooltip.slot_count", slots).withStyle(ChatFormatting.BLUE));
			}
		}
	}
}
