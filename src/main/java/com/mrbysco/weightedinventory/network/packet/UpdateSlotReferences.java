package com.mrbysco.weightedinventory.network.packet;

import com.mrbysco.weightedinventory.registry.ArmorSlot;
import com.mrbysco.weightedinventory.registry.ArmorSlotRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.DistExecutor.SafeRunnable;
import net.minecraftforge.network.NetworkEvent.Context;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class UpdateSlotReferences {
	private final Map<ResourceLocation, ArmorSlot> slotMap;

	public UpdateSlotReferences(Map<ResourceLocation, ArmorSlot> slotMap) {
		this.slotMap = new HashMap<>(slotMap);
	}

	public void encode(FriendlyByteBuf buffer) {
		buffer.writeMap(this.slotMap, FriendlyByteBuf::writeResourceLocation, (byteBuf, slot) -> {
			slot.serializeToNetwork(byteBuf);
		});
	}

	public static UpdateSlotReferences decode(final FriendlyByteBuf buffer) {
		Map<ResourceLocation, ArmorSlot> slotMap = buffer.readMap(FriendlyByteBuf::readResourceLocation, ArmorSlot::fromNetwork);
		return new UpdateSlotReferences(slotMap);
	}

	public void handle(Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			if (ctx.getDirection().getReceptionSide().isClient()) {
				UpdateReferences.update(this.slotMap).run();
			}
		});
		ctx.setPacketHandled(true);
	}

	private static class UpdateReferences {
		private static SafeRunnable update(Map<ResourceLocation, ArmorSlot> slotMap) {
			return new SafeRunnable() {
				@Serial
				private static final long serialVersionUID = 1L;

				@Override
				public void run() {
					ArmorSlotRegistry.updateMap(slotMap);
				}
			};
		}
	}
}
