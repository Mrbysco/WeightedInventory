package com.mrbysco.weightedinventory.mixin;

import com.mrbysco.weightedinventory.util.UnlockHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Inventory.class)
public abstract class InventoryMixin implements Container {

	@Shadow
	@Final
	public Player player;

	@Shadow
	@Final
	public NonNullList<ItemStack> items;

	@Inject(
			method = "getFreeSlot()I",
			at = @At(value = "HEAD"),
			cancellable = true
	)
	public void weightedinventory$getFreeSlot(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(UnlockHelper.getUnlockedFreeSlot(player, this.items));
	}
}
