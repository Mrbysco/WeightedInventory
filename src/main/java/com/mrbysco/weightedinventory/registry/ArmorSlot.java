package com.mrbysco.weightedinventory.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrbysco.weightedinventory.WeightedInventoryMod;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.conditions.ConditionalOps;
import net.neoforged.neoforge.common.conditions.WithConditions;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class ArmorSlot {
	public static final ResourceKey<Registry<ArmorSlot>> REGISTRY_KEY = ResourceKey.createRegistryKey(
			WeightedInventoryMod.modLoc("armor_slot"));

	public static final Codec<ArmorSlot> DIRECT_CODEC = ExtraCodecs.catchDecoderException(
			RecordCodecBuilder.create(
					apply -> apply.group(
									ResourceLocation.CODEC.listOf().fieldOf("armorItems").forGetter(ArmorSlot::armorIds),
									Codec.FLOAT.fieldOf("slotsPerPiece").forGetter(ArmorSlot::slotsPerPiece)
							)
							.apply(apply, ArmorSlot::new)
			)
	);
	public static final Codec<Optional<WithConditions<ArmorSlot>>> CONDITIONAL_CODEC = ConditionalOps.createConditionalCodecWithConditions(DIRECT_CODEC);

	private final List<ResourceLocation> armorIds;
	private final List<Holder.Reference<Item>> armorItems;
	private final float slotsPerPiece;

	public ArmorSlot(List<ResourceLocation> armorIds, List<Holder.Reference<Item>> armorItems, float slotsPerPiece) {
		this.armorIds = armorIds;
		this.armorItems = armorItems;
		this.slotsPerPiece = slotsPerPiece;
	}

	public ArmorSlot(List<ResourceLocation> armorIds, float slotsPerPiece) {
		this(armorIds, armorIds.stream().map(BuiltInRegistries.ITEM::getHolder).filter(Optional::isPresent).map(Optional::get).toList(), slotsPerPiece);
	}

	public static ArmorSlot createSlot(List<Holder.Reference<Item>> armorItems, float slotsPerPiece) {
		return new ArmorSlot(armorItems.stream().map(reference -> reference.key().location()).toList(), slotsPerPiece);
	}

	public List<ResourceLocation> armorIds() {
		return armorIds;
	}

	public List<Holder.Reference<Item>> armorItems() {
		return armorItems;
	}

	public float slotsPerPiece() {
		return slotsPerPiece;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (ArmorSlot) obj;
		return Objects.equals(this.armorIds, that.armorIds) &&
				Float.floatToIntBits(this.slotsPerPiece) == Float.floatToIntBits(that.slotsPerPiece);
	}

	@Override
	public int hashCode() {
		return Objects.hash(armorIds, slotsPerPiece);
	}

	@Override
	public String toString() {
		return "ArmorSlot[" +
				"armorItems=" + armorIds + ", " +
				"slotsPerPiece=" + slotsPerPiece + ']';
	}
}
