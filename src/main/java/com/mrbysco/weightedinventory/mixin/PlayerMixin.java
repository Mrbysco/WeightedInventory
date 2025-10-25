package com.mrbysco.weightedinventory.mixin;

import com.mojang.authlib.GameProfile;
import com.mrbysco.weightedinventory.menu.WeightedInventoryMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
	@Shadow
	@Final
	@Mutable
	public InventoryMenu inventoryMenu;

	@Shadow
	public AbstractContainerMenu containerMenu;

	@Shadow
	@Final
	Inventory inventory;

	protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
		super(entityType, level);
	}

	@Inject(
			method = "<init>",
			at = @At("RETURN")
	)
	public void weightedinventory$init(Level level, BlockPos pos, float yRot, GameProfile gameProfile, CallbackInfo ci) {
		Player player = (Player) (Object) this;
		this.inventoryMenu = new WeightedInventoryMenu(this.inventory, !level.isClientSide, player);
		this.containerMenu = this.inventoryMenu;
	}
}
