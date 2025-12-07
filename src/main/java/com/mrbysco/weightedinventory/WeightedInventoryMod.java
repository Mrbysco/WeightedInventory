package com.mrbysco.weightedinventory;

import com.mojang.logging.LogUtils;
import com.mrbysco.weightedinventory.config.WeightedConfig;
import com.mrbysco.weightedinventory.handler.AttributeHandler;
import com.mrbysco.weightedinventory.network.PacketHandler;
import com.mrbysco.weightedinventory.registry.ArmorAttributeRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(WeightedInventoryMod.MOD_ID)
public class WeightedInventoryMod {
	public static final String MOD_ID = "weightedinventory";
	public static final Logger LOGGER = LogUtils.getLogger();

	public WeightedInventoryMod() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, WeightedConfig.commonSpec);

		eventBus.addListener(this::setup);

		ArmorAttributeRegistry.ATTRIBUTES.register(eventBus);
		eventBus.addListener(AttributeHandler::addEntityAttributes);
		NeoForge.EVENT_BUS.addListener(AttributeHandler::addItemAttributes);
	}

	private void setup(final FMLCommonSetupEvent event) {
		PacketHandler.init();
	}
}
