package com.mrbysco.weightedinventory.util;

import com.mrbysco.weightedinventory.registry.ArmorSlotRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class UnlockHelper {
	public static boolean isUnlocked(int index, int unlockedSlots) {
		if (unlockedSlots > 0) {
			return (index - 9) < unlockedSlots;
		} else {
			return false;
		}
	}

	public static int getUnlockedSlots(Player player) {
		float slots = 0f;
		for (ItemStack armorStack : player.getArmorSlots()) {
			if (armorStack.isEmpty()) continue;
			slots += ArmorSlotRegistry.getSlots(armorStack);
		}
		return Mth.clamp(Mth.floor(slots), 0, 26);
	}

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
