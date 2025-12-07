package com.mrbysco.weightedinventory.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mrbysco.weightedinventory.WeightedInventoryMod;
import com.mrbysco.weightedinventory.registry.ArmorAttributeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.level.GameType;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ClientHandler {
	private static final ResourceLocation LOCKED_TEXTURE = WeightedInventoryMod.modLoc("textures/gui/locked_hotbar.png");

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void renderHotbar(RenderGuiLayerEvent.Pre event) {
		if (event.getName().equals(VanillaGuiLayers.HOTBAR)) {
			Minecraft mc = Minecraft.getInstance();
			if (mc.player == null || mc.gameMode.getPlayerMode() == GameType.SPECTATOR) return;
			final AttributeInstance instance = mc.player.getAttribute(ArmorAttributeRegistry.UNLOCKED);
			if (instance == null) return;
			final int unlockedSlots = Mth.clamp(Mth.floor(instance.getValue()), 0, 27);
			if (unlockedSlots >= 9) return;
			final GuiGraphics guiGraphics = event.getGuiGraphics();
			int lockedSlots = 9 - unlockedSlots;

			int center = guiGraphics.guiWidth() / 2;
			int xEnd = center + 72;

			RenderSystem.enableBlend();
			for (int i = 0; i < lockedSlots; i++) {
				int xOffset = (i * 20);
				guiGraphics.blit(
						LOCKED_TEXTURE,
						xEnd - xOffset, guiGraphics.guiHeight() - 19,
						0, 0,
						16, 16,
						16, 16
				);
			}
			RenderSystem.disableBlend();
		}
	}
}
