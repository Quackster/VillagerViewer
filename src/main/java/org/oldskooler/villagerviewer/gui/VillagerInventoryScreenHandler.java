package org.oldskooler.villagerviewer.gui;

import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.oldskooler.villagerviewer.VillagerViewer;

public class VillagerInventoryScreenHandler extends ScreenHandler {
	public final MerchantEntity villager;
	public final Inventory villagerInventory;

	public VillagerInventoryScreenHandler(int syncId, PlayerInventory playerInventory, int villagerId) {
		super(VillagerViewer.VILLAGER_SCREEN_HANDLER, syncId);

		MerchantEntity foundVillager = null;
		Inventory foundInventory = null;

		if (playerInventory.player.getWorld().getEntityById(villagerId) instanceof MerchantEntity merchant) {
			foundVillager = merchant;
			foundInventory = merchant.getInventory();
		}

		this.villager = foundVillager;
		this.villagerInventory = foundInventory;

		if (this.villagerInventory != null) {
			setupVillagerSlots();
		}

		setupPlayerInventory(playerInventory);
	}

	private void setupVillagerSlots() {
		int inventorySize = this.villagerInventory.size();
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

	private void setupPlayerInventory(PlayerInventory playerInventory) {
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
	public boolean canUse(PlayerEntity player) {
		return this.villager != null && this.villagerInventory != null;
	}

	@Override
	public ItemStack quickMove(PlayerEntity player, int slotIndex) {
		ItemStack transferredStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(slotIndex);

		if (slot != null && slot.hasStack()) {
			ItemStack originalStack = slot.getStack();
			transferredStack = originalStack.copy();

			int villagerSlotCount = this.villagerInventory != null ? this.villagerInventory.size() : 0;

			if (slotIndex < villagerSlotCount) {
				// Moving from villager inventory to player inventory
				if (!this.insertItem(originalStack, villagerSlotCount, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else {
				// Moving from player inventory to villager inventory
				if (!this.insertItem(originalStack, 0, villagerSlotCount, false)) {
					return ItemStack.EMPTY;
				}
			}

			if (originalStack.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
		}

		return transferredStack;
	}
}