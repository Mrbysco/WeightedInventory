package com.mrbysco.weightedinventory.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class WeightedConfig {
	public static class Common {
		public final ForgeConfigSpec.DoubleValue defaultSlotCount;

		Common(ForgeConfigSpec.Builder builder) {
			builder.comment("General settings")
					.push("general");

			defaultSlotCount = builder
					.comment("The amount of slots given by armor that has not been setup with a slot count [Default: 2.25]")
					.defineInRange("defaultSlotCount", 2.25f, 0, 26);

			builder.pop();
		}
	}

	public static final ForgeConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}
}
