package com.mrbysco.weightedinventory.mixin;

import com.mrbysco.weightedinventory.menu.slot.WeightedSlot;
import com.mrbysco.weightedinventory.util.UnlockHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractContainerMenu.class)
public abstract class AbstractContainerMenuMixin {

	@Shadow
	@Final
	public NonNullList<Slot> slots;

	@Shadow
	@Final
	private NonNullList<ItemStack> lastSlots;

	@Shadow
	@Final
	private NonNullList<ItemStack> remoteSlots;

	@Inject(
			method = "addSlot(Lnet/minecraft/world/inventory/Slot;)Lnet/minecraft/world/inventory/Slot;",
			at = @At(value = "HEAD"),
			cancellable = true)
	public void weightedinventory$addSlot(Slot slot, CallbackInfoReturnable<Slot> cir) {
		if (slot.container instanceof Inventory inventory) {
			int slotIndex = slot.getSlotIndex();
			if (UnlockHelper.isIndexLockable(slotIndex)) {
				WeightedSlot weightedSlot = new WeightedSlot(inventory, slotIndex, slot.x, slot.y);
				weightedSlot.index = this.slots.size();
				this.slots.add(weightedSlot);
				this.lastSlots.add(ItemStack.EMPTY);
				this.remoteSlots.add(ItemStack.EMPTY);
				cir.setReturnValue(weightedSlot);
			}
		}
	}
}
