package com.mrbysco.weightedinventory.util;

import com.mrbysco.weightedinventory.config.WeightedConfig;
import com.mrbysco.weightedinventory.registry.ArmorSlotRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class UnlockHelper {
	/**
	 * Check if a slot index is unlocked based on the number of unlocked slots
	 *
	 * @param index         The slot index
	 * @param unlockedSlots The number of unlocked slots
	 * @return True if the slot is unlocked, false otherwise
	 */
	public static boolean isUnlocked(int index, int unlockedSlots) {
		if (unlockedSlots > 0) {
			return (index - 9) < unlockedSlots;
		} else {
			return false;
		}
	}

	/**
	 * Get the number of unlocked slots for a player based on their armor
	 *
	 * @param player The player
	 * @return The number of unlocked slots
	 */
	public static int getUnlockedSlots(Player player) {
		if (WeightedConfig.COMMON.disableInCreative.getAsBoolean() && player.isCreative()) {
			return 27;
		}
		float slots = 0f;
		for (ItemStack armorStack : player.getArmorSlots()) {
			if (armorStack.isEmpty()) continue;
			slots += ArmorSlotRegistry.getSlots(armorStack);
		}
		return Mth.clamp(Mth.floor(slots), 0, 27);
	}

	/**
	 * Get the first unlocked free slot in the inventory
	 *
	 * @param player The player
	 * @param items  The inventory items
	 * @return The index of the first unlocked free slot, or -1 if none are available
	 */
	public static int getUnlockedFreeSlot(Player player, NonNullList<ItemStack> items) {
		int unlockedCount = UnlockHelper.getUnlockedSlots(player) + 9;
		int slots = Mth.clamp(unlockedCount, 0, items.size());
		if (slots == 0) {
			return -1; // Should never happen, but just in case
		}
		for (int i = 0; i < slots; ++i) {
			if (items.get(i).isEmpty()) {
				return i;
			}
		}
		return -1;
	}
}
