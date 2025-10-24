package com.mrbysco.weightedinventory.datagen.provider;

import com.mrbysco.weightedinventory.registry.ArmorSlot;
import net.minecraft.resources.ResourceLocation;

/**
 * Just a wrapper class to hold both the ArmorSlot and its ResourceLocation ID for data generation.
 */
public class WrappedSlot {
	private final ArmorSlot slot;
	private final ResourceLocation id;

	public WrappedSlot(ArmorSlot slot, ResourceLocation id) {
		this.slot = slot;
		this.id = id;
	}

	public ResourceLocation getId() {
		return id;
	}

	public ArmorSlot getSlot() {
		return slot;
	}
}
