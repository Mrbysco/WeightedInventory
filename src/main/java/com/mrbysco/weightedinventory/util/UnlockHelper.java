package com.mrbysco.weightedinventory.util;

import com.mrbysco.weightedinventory.registry.ArmorAttributeRegistry;
import net.minecraft.Util;
import net.minecraft.core.NonNullList;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;

import java.util.EnumMap;
import java.util.UUID;

public class UnlockHelper {

	private static final EnumMap<EquipmentSlot, UUID> MODIFIER_PER_SLOT = Util.make(new EnumMap<>(EquipmentSlot.class), (map) -> {
		map.put(EquipmentSlot.FEET, UUID.fromString("9E7A3709-84F7-46F7-9AEE-F38BE8D4BDFE"));
		map.put(EquipmentSlot.LEGS, UUID.fromString("A1E7F0F6-A94D-4D9F-BEA2-D41E93A16602"));
		map.put(EquipmentSlot.CHEST, UUID.fromString("97E368D8-2513-44D8-9EB9-4E120C10DB82"));
		map.put(EquipmentSlot.HEAD, UUID.fromString("E7FBD6E7-FF63-484D-B0BA-A06E21D30BDC"));
	});

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

	public static int getUnlockedSlots(Player player) {
		AttributeInstance modifier = player.getAttribute(ArmorAttributeRegistry.UNLOCKED.get());
		if (modifier != null) {
			return Mth.clamp(Mth.floor(modifier.getValue()), 0, 27);
		}
		return 0;
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

	/**
	 * Create an AttributeModifier for unlocked slots
	 *
	 * @param slots The number of slots to unlock
	 * @return The AttributeModifier
	 */
	public static AttributeModifier createModifier(EquipmentSlot slot, float slots) {
		return new AttributeModifier(
				MODIFIER_PER_SLOT.get(slot), "Unlocked Slots",
				slots, AttributeModifier.Operation.ADDITION
		);
	}

	/**
	 * Get the equipment slot for the given item stack.
	 *
	 * @param stack The item stack to check.
	 * @return The equipment slot.
	 */
	public static EquipmentSlot getEquipmentSlotForItem(ItemStack stack) {
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
