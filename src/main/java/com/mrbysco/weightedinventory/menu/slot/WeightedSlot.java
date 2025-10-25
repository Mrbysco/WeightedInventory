package com.mrbysco.weightedinventory.menu.slot;

import com.mojang.datafixers.util.Pair;
import com.mrbysco.weightedinventory.WeightedInventoryMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class WeightedSlot extends Slot {
	private boolean isUnlocked = false;

	public WeightedSlot(Container container, int slot, int x, int y) {
		super(container, slot, x, y);
	}

	public void setUnlocked(boolean unlocked) {
		this.isUnlocked = unlocked;
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return super.mayPlace(stack) && this.isUnlocked;
	}

	@Override
	public boolean mayPickup(Player player) {
		return super.mayPickup(player) && this.isUnlocked;
	}

	@Override
	public boolean allowModification(Player player) {
		return this.isUnlocked;
	}

	@Override
	public boolean isHighlightable() {
		return this.isUnlocked;
	}

	@Nullable
	@Override
	public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
		if (this.isUnlocked) {
			return null;
		}
		return Pair.of(InventoryMenu.BLOCK_ATLAS, WeightedInventoryMod.modLoc("item/locked_slot"));
	}
}
