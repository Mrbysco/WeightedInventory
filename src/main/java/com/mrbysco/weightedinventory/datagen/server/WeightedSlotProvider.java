package com.mrbysco.weightedinventory.datagen.server;

import com.mrbysco.weightedinventory.WeightedInventoryMod;
import com.mrbysco.weightedinventory.datagen.provider.ArmorSlotBuilder;
import com.mrbysco.weightedinventory.datagen.provider.ArmorSlotProvider;
import com.mrbysco.weightedinventory.datagen.provider.WrappedSlot;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class WeightedSlotProvider extends ArmorSlotProvider {

	public WeightedSlotProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(packOutput, lookupProvider, WeightedInventoryMod.MOD_ID);
	}

	@Override
	public void defineSlots(Consumer<WrappedSlot> consumer) {
		this.addSlot(consumer, "leather", new ArmorSlotBuilder()
				.setArmorItems(Items.LEATHER_BOOTS, Items.LEATHER_LEGGINGS, Items.LEATHER_CHESTPLATE, Items.LEATHER_HELMET)
				.setSlotsPerPiece(2.25f));
		this.addSlot(consumer, "iron", new ArmorSlotBuilder()
				.setArmorItems(Items.IRON_BOOTS, Items.IRON_LEGGINGS, Items.IRON_CHESTPLATE, Items.IRON_HELMET)
				.setSlotsPerPiece(4.5f));
		this.addSlot(consumer, "chainmail", new ArmorSlotBuilder()
				.setArmorItems(Items.CHAINMAIL_BOOTS, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_HELMET)
				.setSlotsPerPiece(4.5f));
		this.addSlot(consumer, "gold", new ArmorSlotBuilder()
				.setArmorItems(Items.GOLDEN_BOOTS, Items.GOLDEN_LEGGINGS, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_HELMET)
				.setSlotsPerPiece(6.75f));
		this.addSlot(consumer, "diamond", new ArmorSlotBuilder()
				.setArmorItems(Items.DIAMOND_BOOTS, Items.DIAMOND_LEGGINGS, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_HELMET)
				.setSlotsPerPiece(6.75f));
		this.addSlot(consumer, "netherite", new ArmorSlotBuilder()
				.setArmorItems(Items.NETHERITE_BOOTS, Items.NETHERITE_LEGGINGS, Items.NETHERITE_CHESTPLATE, Items.NETHERITE_HELMET)
				.setSlotsPerPiece(6.75f));
	}
}
