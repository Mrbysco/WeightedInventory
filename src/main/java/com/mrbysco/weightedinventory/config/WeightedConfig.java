package com.mrbysco.weightedinventory.config;

import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class WeightedConfig {
	public static class Common {
		public final ModConfigSpec.DoubleValue defaultSlotCount;
		public final ModConfigSpec.BooleanValue disableInCreative;

		public final ModConfigSpec.IntValue defaultUnlockedSlots;
		public final ModConfigSpec.BooleanValue allowOffhandLock;
		public final ModConfigSpec.BooleanValue allowArmorLock;

		Common(ModConfigSpec.Builder builder) {
			builder.comment("General settings")
					.push("general");

			defaultSlotCount = builder
					.comment("The amount of slots given by armor that has not been setup with a slot count (Requires rejoin) [Default: 2.25]")
					.defineInRange("defaultSlotCount", 2.25f, 0, Inventory.INVENTORY_SIZE);

			disableInCreative = builder
					.comment("Disable weighted inventory in creative mode [Default: true]")
					.define("disableInCreative", true);

			builder.pop();
			builder.comment("Unlock settings")
					.push("unlock");

			defaultUnlockedSlots = builder
					.comment("The amount of unlocked slots given to players by default [Default: 9]")
					.defineInRange("defaultUnlockedSlots", 9, 1, Inventory.INVENTORY_SIZE);

			allowOffhandLock = builder
					.comment("Allow locking of the offhand slot [Default: false]")
					.define("allowOffhandLock", false);

			allowArmorLock = builder
					.comment("Allow locking of the armor slots [Default: false]")
					.define("allowArmorLock", false);

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
