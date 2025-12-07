package com.mrbysco.weightedinventory.registry;

import com.mrbysco.weightedinventory.WeightedInventoryMod;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ArmorAttributeRegistry {
	public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, WeightedInventoryMod.MOD_ID);

	public static final RegistryObject<Attribute> UNLOCKED = ATTRIBUTES.register("unlocked", () ->
			new RangedAttribute("attribute.name.unlocked.slot_count", 0, 0, 1024)
					.setSyncable(true));
}
