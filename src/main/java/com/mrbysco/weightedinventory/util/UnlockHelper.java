package com.mrbysco.weightedinventory.util;

import com.mrbysco.weightedinventory.WeightedInventoryMod;
import com.mrbysco.weightedinventory.config.WeightedConfig;
import com.mrbysco.weightedinventory.registry.ArmorAttributeRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Locale;

public class UnlockHelper {
	private static final int[] ARMOR_SLOTS = new int[]{36, 37, 38, 39};
	private static final ResourceLocation BASE_UNLOCKED_ID = WeightedInventoryMod.modLoc("base_unlocked");

	public static boolean isIndexLockable(int index) {
		if (WeightedConfig.COMMON.allowOffhandLock.getAsBoolean() && index == Inventory.SLOT_OFFHAND) {
			return true;
		}
		if (WeightedConfig.COMMON.allowArmorLock.getAsBoolean() && ArrayUtils.contains(ARMOR_SLOTS, index)) {
			return true;
		}
		return index >= 1 && index < 36;
	}

	/**
	 * Check if a slot index is unlocked based on the number of unlocked slots
	 *
	 * @param index         The slot index
	 * @param unlockedSlots The number of unlocked slots
	 * @return True if the slot is unlocked, false otherwise
	 */
	public static boolean isUnlocked(int index, int unlockedSlots) {
		if (unlockedSlots > 0) {
			return index < unlockedSlots;
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
		AttributeInstance modifier = player.getAttribute(ArmorAttributeRegistry.UNLOCKED);
		if (modifier != null) {
			return Mth.clamp(Mth.floor(modifier.getValue()), 0, 27);
		}
		return 0;
	}

	/**
	 * Get the first unlocked free slot in the inventory
	 *
	 * @param player The player
	 * @param items  The inventory items
	 * @return The index of the first unlocked free slot, or -1 if none are available
	 */
	public static int getUnlockedFreeSlot(Player player, NonNullList<ItemStack> items) {
		int unlockedCount = UnlockHelper.getUnlockedSlots(player);
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
				BASE_UNLOCKED_ID.withSuffix("_" + slot.getName().toLowerCase(Locale.ROOT)),
				slots, AttributeModifier.Operation.ADD_VALUE
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
