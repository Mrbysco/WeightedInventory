package com.mrbysco.weightedinventory.datagen.provider;

import com.mrbysco.weightedinventory.registry.ArmorSlot;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArmorSlotBuilder {
	private final List<Holder.Reference<Item>> armorItems = new ArrayList<>();
	private float slotsPerPiece;

	public ArmorSlotBuilder setArmorStacks(List<ItemStack> armorItems) {
		this.armorItems.addAll(armorItems.stream().map(stack -> stack.getItem().builtInRegistryHolder()).toList());
		return this;
	}

	public ArmorSlotBuilder setArmorItems(List<Item> armorItems) {
		this.armorItems.addAll(armorItems.stream().map(Item::builtInRegistryHolder).toList());
		return this;
	}

	public ArmorSlotBuilder setArmorItems(Item... armorItems) {
		this.armorItems.addAll(Arrays.stream(armorItems).map(Item::builtInRegistryHolder).toList());
		return this;
	}

	public ArmorSlotBuilder setSlotsPerPiece(float slotsPerPiece) {
		this.slotsPerPiece = slotsPerPiece;
		return this;
	}

	public ArmorSlot createArmorSlot() {
		if (armorItems.isEmpty()) {
			throw new IllegalStateException("ArmorSlot must have at least one armor item defined.");
		}
		return ArmorSlot.createSlot(armorItems, slotsPerPiece);
	}
}