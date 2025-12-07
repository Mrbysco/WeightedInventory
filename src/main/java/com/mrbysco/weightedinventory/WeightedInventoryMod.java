package com.mrbysco.weightedinventory;

import com.mojang.logging.LogUtils;
import com.mrbysco.weightedinventory.config.WeightedConfig;
import com.mrbysco.weightedinventory.handler.AttributeHandler;
import com.mrbysco.weightedinventory.registry.ArmorAttributeRegistry;
import com.mrbysco.weightedinventory.registry.ArmorSlotRegistry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(WeightedInventoryMod.MOD_ID)
public class WeightedInventoryMod {
	public static final String MOD_ID = "weightedinventory";
	public static final Logger LOGGER = LogUtils.getLogger();


	public WeightedInventoryMod(IEventBus eventBus, ModContainer container, Dist dist) {
		container.registerConfig(ModConfig.Type.COMMON, WeightedConfig.commonSpec);

		ArmorAttributeRegistry.ATTRIBUTES.register(eventBus);
		eventBus.addListener(AttributeHandler::addEntityAttributes);
		NeoForge.EVENT_BUS.addListener(AttributeHandler::addItemAttributes);
		NeoForge.EVENT_BUS.addListener(ArmorSlotRegistry::onTagsUpdated);

		if (dist.isClient()) {
			container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
		}
	}

	public static ResourceLocation modLoc(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
}
