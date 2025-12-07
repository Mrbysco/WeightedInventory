package com.mrbysco.weightedinventory.registry;

import com.mrbysco.weightedinventory.WeightedInventoryMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ArmorAttributeRegistry {
	public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, WeightedInventoryMod.MOD_ID);

	public static final DeferredHolder<Attribute, Attribute> UNLOCKED = ATTRIBUTES.register("unlocked", () ->
			new RangedAttribute("attribute.name.unlocked.slot_count", 0, 0, 1024)
					.setSentiment(Attribute.Sentiment.POSITIVE).setSyncable(true));
}
