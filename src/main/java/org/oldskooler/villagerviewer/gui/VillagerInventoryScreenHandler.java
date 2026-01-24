package org.oldskooler.villagerviewer.gui;

import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.oldskooler.villagerviewer.VillagerViewer;

public class VillagerInventoryScreenHandler extends AbstractContainerMenu {
	public final AbstractVillager villager;
	public final Container villagerInventory;

	public VillagerInventoryScreenHandler(int syncId, Inventory playerInventory, int villagerId) {
		super(VillagerViewer.VILLAGER_SCREEN_HANDLER, syncId);

		// Find and initialize villager and inventory
		Entity entity = playerInventory.player.level().getEntity(villagerId);

		if (entity instanceof AbstractVillager merchant) {
			this.villager = merchant;
			this.villagerInventory = merchant.getInventory();
			setupVillagerSlots();
		} else {
			this.villager = null;
			this.villagerInventory = null;
		}

		setupPlayerInventory(playerInventory);
	}

	private void setupVillagerSlots() {
		int inventorySize = this.villagerInventory.getContainerSize();
		int rows = inventorySize / 2;
		int columns = inventorySize / rows;

		int textureWidth = 18;
		int textureHeight = 18;

		int startX = 80 + (textureWidth / 2);
		int startY = 18 + (textureHeight / 2);

		for (int slot = 0; slot < inventorySize && slot < rows * columns; slot++) {
			int slotSpacing = 17;

			int slotX = startX + (slot / columns) * slotSpacing;
			int slotY = startY + (slot % columns) * slotSpacing;

			this.addSlot(new Slot(this.villagerInventory, slot, slotX, slotY));
		}
	}

	private void setupPlayerInventory(Inventory playerInventory) {
		// Player main inventory (3 rows)
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 9; col++) {
				int slotIndex = col + row * 9 + 9;
				int x = 8 + col * 18;
				int y = 84 + row * 18;
				this.addSlot(new Slot(playerInventory, slotIndex, x, y));
			}
		}

		// Player hotbar
		for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
			int x = 8 + hotbarSlot * 18;
			int y = 142;
			this.addSlot(new Slot(playerInventory, hotbarSlot, x, y));
		}
	}

	@Override
	public boolean stillValid(Player player) {
		return this.villager != null && this.villagerInventory != null;
	}

	@Override
	public ItemStack quickMoveStack(Player player, int slotIndex) {
		ItemStack transferredStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(slotIndex);

		if (slot != null && slot.hasItem()) {
			ItemStack originalStack = slot.getItem();
			transferredStack = originalStack.copy();

			int villagerSlotCount = this.villagerInventory != null ? this.villagerInventory.getContainerSize() : 0;

			if (slotIndex < villagerSlotCount) {
				// Moving from villager inventory to player inventory
				if (!this.moveItemStackTo(originalStack, villagerSlotCount, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else {
				// Moving from player inventory to villager inventory
				if (!this.moveItemStackTo(originalStack, 0, villagerSlotCount, false)) {
					return ItemStack.EMPTY;
				}
			}

			if (originalStack.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
		}

		return transferredStack;
	}
}