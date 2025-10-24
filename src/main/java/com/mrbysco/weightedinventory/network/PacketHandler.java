package com.mrbysco.weightedinventory.network;

import com.mrbysco.weightedinventory.WeightedInventoryMod;
import com.mrbysco.weightedinventory.network.packet.UpdateSlotReferences;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(WeightedInventoryMod.MOD_ID, "main"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
	);

	private static int id = 0;

	public static void init() {
		CHANNEL.registerMessage(id++, UpdateSlotReferences.class, UpdateSlotReferences::encode, UpdateSlotReferences::decode, UpdateSlotReferences::handle);
	}
}
