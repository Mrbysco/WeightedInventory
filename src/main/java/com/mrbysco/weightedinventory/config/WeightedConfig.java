package com.mrbysco.weightedinventory.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class WeightedConfig {
	public static class Common {
		public final ModConfigSpec.DoubleValue defaultSlotCount;
		public final ModConfigSpec.BooleanValue disableInCreative;

		Common(ModConfigSpec.Builder builder) {
			builder.comment("General settings")
					.push("general");

			defaultSlotCount = builder
					.comment("The amount of slots given by armor that has not been setup with a slot count [Default: 2.25]")
					.defineInRange("defaultSlotCount", 2.25f, 0, 26);

			disableInCreative = builder
					.comment("Disable weighted inventory in creative mode [Default: true]")
					.define("disableInCreative", true);

			builder.pop();
		}
	}

	public static final ModConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}
}
