package com.mrbysco.weightedinventory.registry;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;
import com.mrbysco.weightedinventory.WeightedInventoryMod;
import com.mrbysco.weightedinventory.network.PacketHandler;
import com.mrbysco.weightedinventory.network.packet.UpdateSlotReferences;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.slf4j.Logger;

import java.util.Map;

@Mod.EventBusSubscriber(modid = WeightedInventoryMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ArmorSlotReloadListener extends SimpleJsonResourceReloadListener {
	public static final String FOLDER_NAME = "armor_slot";
	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
	public static final Logger LOGGER = LogUtils.getLogger();

	public static final ArmorSlotReloadListener INSTANCE = new ArmorSlotReloadListener();

	private Map<ResourceLocation, ArmorSlot> byName = ImmutableMap.of();

	public ArmorSlotReloadListener() {
		super(GSON, FOLDER_NAME);
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
		ImmutableMap.Builder<ResourceLocation, ArmorSlot> mapBuilder = ImmutableMap.builder();
		for (Map.Entry<ResourceLocation, JsonElement> entry : object.entrySet()) {
			ArmorSlot.DIRECT_CODEC.parse(JsonOps.INSTANCE, entry.getValue())
					.resultOrPartial(message -> LOGGER.error("Couldn't parse armor slot definition {}: {}", entry.getKey(), message))
					.ifPresent(test -> mapBuilder.put(entry.getKey(), test));
		}
		byName = mapBuilder.build();

		ArmorSlotRegistry.updateMap(byName);
	}

	@SubscribeEvent
	public static void addReloadListener(AddReloadListenerEvent event) {
		event.addListener(INSTANCE);
	}

	@SubscribeEvent
	public static void syncPlaces(PlayerEvent.PlayerLoggedInEvent event) {
		if (!event.getEntity().level().isClientSide)
			INSTANCE.syncToPlayer((ServerPlayer) event.getEntity());

		if (ServerLifecycleHooks.getCurrentServer() != null)
			INSTANCE.syncToAll();
	}

	private void syncToAll() {
		PacketHandler.CHANNEL.send(PacketDistributor.ALL.noArg(), new UpdateSlotReferences(this.byName));
	}

	private void syncToPlayer(ServerPlayer player) {
		PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new UpdateSlotReferences(this.byName));
	}
}
