package com.mrbysco.weightedinventory.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrbysco.weightedinventory.WeightedInventoryMod;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Objects;

public final class ArmorSlot {
	public static final ResourceKey<Registry<ArmorSlot>> REGISTRY_KEY = ResourceKey.createRegistryKey(
			new ResourceLocation(WeightedInventoryMod.MOD_ID, "armor_slot"));

	public static final Codec<ArmorSlot> DIRECT_CODEC = ExtraCodecs.catchDecoderException(
			RecordCodecBuilder.create(
					apply -> apply.group(
									ResourceLocation.CODEC.listOf().fieldOf("armorItems").forGetter(ArmorSlot::armorIds),
									Codec.FLOAT.fieldOf("slotsPerPiece").forGetter(ArmorSlot::slotsPerPiece)
							)
							.apply(apply, ArmorSlot::new)
			)
	);
	private final List<ResourceLocation> armorIds;
	private final List<Holder.Reference<Item>> armorItems;
	private final float slotsPerPiece;

	public ArmorSlot(List<ResourceLocation> armorIds, List<Holder.Reference<Item>> armorItems, float slotsPerPiece) {
		this.armorIds = armorIds;
		this.armorItems = armorItems;
		this.slotsPerPiece = slotsPerPiece;
	}

	public ArmorSlot(List<ResourceLocation> armorIds, float slotsPerPiece) {
		this(armorIds, armorIds.stream().map(ForgeRegistries.ITEMS::getValue).filter(Objects::nonNull).map(Item::builtInRegistryHolder).toList(), slotsPerPiece);
	}

	public static ArmorSlot createSlot(List<Holder.Reference<Item>> armorItems, float slotsPerPiece) {
		return new ArmorSlot(armorItems.stream().map(test -> test.key().location()).toList(), slotsPerPiece);
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

	public void serializeToNetwork(FriendlyByteBuf byteBuf) {
		byteBuf.writeCollection(this.armorIds, FriendlyByteBuf::writeResourceLocation);
		byteBuf.writeFloat(this.slotsPerPiece);
	}

	public static ArmorSlot fromNetwork(FriendlyByteBuf byteBuf) {
		List<ResourceLocation> armorIds = byteBuf.readList(FriendlyByteBuf::readResourceLocation);
		float slotsPerPiece = byteBuf.readFloat();
		return new ArmorSlot(armorIds, slotsPerPiece);
	}
}
