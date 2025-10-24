package com.mrbysco.weightedinventory.menu;

import com.mrbysco.weightedinventory.menu.slot.WeightedSlot;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;

public class WeightedInventoryMenu extends InventoryMenu {
	public WeightedInventoryMenu(Inventory playerInventory, boolean active, Player owner) {
		super(playerInventory, active, owner);
	}

	@Override
	public void slotsChanged(Container inventory) {
		super.slotsChanged(inventory);
	}

	@Override
	protected Slot addSlot(Slot slot) {
		int slotIndex = slot.getSlotIndex();
		if (slotIndex >= 9 && slotIndex < 36) {
			return super.addSlot(new WeightedSlot(slot.container, slotIndex, slot.x, slot.y));
		}
		return super.addSlot(slot);
	}
}
