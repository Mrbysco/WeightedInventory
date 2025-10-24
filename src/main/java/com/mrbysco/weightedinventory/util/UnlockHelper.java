package com.mrbysco.weightedinventory.util;

import com.mrbysco.weightedinventory.registry.ArmorSlotRegistry;
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
}
