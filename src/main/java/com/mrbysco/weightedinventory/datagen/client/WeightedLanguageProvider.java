package com.mrbysco.weightedinventory.datagen.client;

import com.mrbysco.weightedinventory.WeightedInventoryMod;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.jetbrains.annotations.Nullable;

public class WeightedLanguageProvider extends LanguageProvider {
	public WeightedLanguageProvider(PackOutput packOutput) {
		super(packOutput, WeightedInventoryMod.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		this.add("attribute.name.unlocked.slot_count", "Inventory Slots");
		this.add("weightedinventory.message.item_dropped", "Item dropped, too heavy!");

		this.addConfig("general", "General", "General Settings");
		this.addConfig("defaultSlotCount", "Default Slot Count", "The amount of slots given by armor that has not been setup with a slot count");
		this.addConfig("disableInCreative", "Disable in Creative", "Disable weighted inventory in creative mode");
	}

	/**
	 * Add the translation for a config entry
	 *
	 * @param path        The path of the config entry
	 * @param name        The name of the config entry
	 * @param description The description of the config entry (optional in case of targeting "title" or similar entries that have no tooltip)
	 */
	private void addConfig(String path, String name, @Nullable String description) {
		this.add(WeightedInventoryMod.MOD_ID + ".configuration." + path, name);
		if (description != null && !description.isEmpty())
			this.add(WeightedInventoryMod.MOD_ID + ".configuration." + path + ".tooltip", description);
	}
}
