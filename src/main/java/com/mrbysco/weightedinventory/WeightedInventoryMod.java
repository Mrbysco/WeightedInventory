package com.mrbysco.weightedinventory;

import com.mojang.logging.LogUtils;
import com.mrbysco.weightedinventory.config.WeightedConfig;
import com.mrbysco.weightedinventory.network.PacketHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(WeightedInventoryMod.MOD_ID)
public class WeightedInventoryMod {
	public static final String MOD_ID = "weightedinventory";
	public static final Logger LOGGER = LogUtils.getLogger();

	public WeightedInventoryMod() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, WeightedConfig.commonSpec);

		eventBus.addListener(this::setup);
	}

	private void setup(final FMLCommonSetupEvent event) {
		PacketHandler.init();
	}
}
