package com.mrbysco.weightedinventory.handler;

import com.mrbysco.weightedinventory.menu.slot.WeightedSlot;
import com.mrbysco.weightedinventory.util.UnlockHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber
public class InventoryHandler {
	@SubscribeEvent
	public static void onInventoryChange(PlayerTickEvent.Pre event) {
		Player player = event.getEntity();
		Level level = player.level();
		if (level.getGameTime() % 10 != 0) return; // Only run once per second
		int unlockedSlots = UnlockHelper.getUnlockedSlots(player);
		for (Slot slot : player.containerMenu.slots) {
			if (slot instanceof WeightedSlot weightedSlot) {
				int slotIndex = weightedSlot.getSlotIndex();
				boolean isUnlocked = UnlockHelper.isUnlocked(slotIndex, unlockedSlots);
				weightedSlot.setUnlocked(isUnlocked);
				if (!isUnlocked && !weightedSlot.getItem().isEmpty() && !level.isClientSide) {
					player.drop(weightedSlot.getItem(), true);
					weightedSlot.set(ItemStack.EMPTY);
					player.displayClientMessage(Component.translatable("weightedinventory.message.item_dropped").withStyle(ChatFormatting.RED), true);
					break;
				}
			}
		}
	}
}
