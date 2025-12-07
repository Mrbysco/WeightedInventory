package com.mrbysco.weightedinventory.handler;

import com.mrbysco.weightedinventory.registry.ArmorAttributeRegistry;
import com.mrbysco.weightedinventory.registry.ArmorSlotRegistry;
import com.mrbysco.weightedinventory.util.UnlockHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;

public class AttributeHandler {

	/**
	 * Add the base unlocked attribute to players
	 *
	 * @param event The entity attribute modification event
	 */
	public static void addEntityAttributes(EntityAttributeModificationEvent event) {
		event.add(EntityType.PLAYER, ArmorAttributeRegistry.UNLOCKED.get(), 0.0D);
	}

	/**
	 * Add item attribute modifiers for armor pieces
	 *
	 * @param event The item attribute modifier event
	 */
	public static void addItemAttributes(ItemAttributeModifierEvent event) {
		ItemStack armorStack = event.getItemStack();
		EquipmentSlot slot = UnlockHelper.getEquipmentSlotForItem(armorStack);
		if (slot != null && slot != EquipmentSlot.OFFHAND && slot != EquipmentSlot.MAINHAND) {
			float slots = ArmorSlotRegistry.getSlots(armorStack);
			event.addModifier(ArmorAttributeRegistry.UNLOCKED.get(), UnlockHelper.createModifier(slot, slots));
		}
	}
}
