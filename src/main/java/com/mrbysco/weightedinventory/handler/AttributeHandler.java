package com.mrbysco.weightedinventory.handler;

import com.mrbysco.weightedinventory.registry.ArmorAttributeRegistry;
import com.mrbysco.weightedinventory.registry.ArmorSlotRegistry;
import com.mrbysco.weightedinventory.util.UnlockHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;

public class AttributeHandler {

	/**
	 * Add the base unlocked attribute to players
	 *
	 * @param event The entity attribute modification event
	 */
	public static void addEntityAttributes(EntityAttributeModificationEvent event) {
		event.add(EntityType.PLAYER, ArmorAttributeRegistry.UNLOCKED, 0.0D);
	}

	/**
	 * Add item attribute modifiers for armor pieces
	 *
	 * @param event The item attribute modifier event
	 */
	public static void addItemAttributes(ItemAttributeModifierEvent event) {
		ItemStack armorStack = event.getItemStack();
		EquipmentSlot slot = UnlockHelper.getEquipmentSlotForItem(armorStack);
		ItemAttributeModifiers defaultModifiers = event.getDefaultModifiers();
		if (defaultModifiers.modifiers().stream().anyMatch(entry -> entry.attribute().is(ArmorAttributeRegistry.UNLOCKED.getKey()))) {
			return;
		}
		if (slot != null && slot != EquipmentSlot.OFFHAND && slot != EquipmentSlot.MAINHAND) {
			float slots = ArmorSlotRegistry.getSlots(armorStack);
			EquipmentSlotGroup equipmentslotgroup = EquipmentSlotGroup.bySlot(slot);
			event.addModifier(ArmorAttributeRegistry.UNLOCKED, UnlockHelper.createModifier(slot, slots), equipmentslotgroup);
		}
	}
}
