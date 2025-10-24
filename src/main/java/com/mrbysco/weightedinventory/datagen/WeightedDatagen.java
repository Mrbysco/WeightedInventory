package com.mrbysco.weightedinventory.datagen;


import com.mrbysco.weightedinventory.datagen.client.WeightedLanguageProvider;
import com.mrbysco.weightedinventory.datagen.server.WeightedSlotProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class WeightedDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

		if (event.includeClient()) {
			generator.addProvider(true, new WeightedLanguageProvider(packOutput));
		}
		if (event.includeServer()) {
			generator.addProvider(true, new WeightedSlotProvider(packOutput, lookupProvider));
		}
	}
}
