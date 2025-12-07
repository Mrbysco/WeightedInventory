package com.mrbysco.weightedinventory.datagen.client;

import com.mrbysco.weightedinventory.WeightedInventoryMod;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class WeightedLanguageProvider extends LanguageProvider {
	public WeightedLanguageProvider(PackOutput packOutput) {
		super(packOutput, WeightedInventoryMod.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		this.add("attribute.name.unlocked.slot_count", "Inventory Slots");
		this.add("weightedinventory.message.item_dropped", "Item dropped, too heavy!");
	}
}
