package org.oldskooler.villagerviewer.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.player.Inventory;

public class VillagerInventoryScreen extends AbstractContainerScreen<VillagerInventoryScreenHandler> {
	private static final Identifier CONTAINER_TEXTURE = Identifier.withDefaultNamespace("textures/gui/container/horse.png");
	private static final Identifier SLOT_TEXTURE = Identifier.withDefaultNamespace("textures/gui/sprites/recipe_book/slot_craftable.png");

	private final AbstractVillager villager;
	private float mouseX;
	private float mouseY;

	public VillagerInventoryScreen(VillagerInventoryScreenHandler handler, Inventory inventory, Component title) {
		super(handler, inventory, title);
		this.villager = handler.villager;
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float delta, int mouseX, int mouseY) {
		int screenX = (this.width - this.imageWidth) / 2;
		int screenY = (this.height - this.imageHeight) / 2;

		guiGraphics.blit(
				CONTAINER_TEXTURE,
				screenX, screenY,
				0, 0,
				this.imageWidth, this.imageHeight,
				256, 256
		);

		int inventorySize = this.menu.villagerInventory.getContainerSize();
		int rows = inventorySize / 2;
		int columns = inventorySize / rows;

		int slotWidth = 18;
		int slotHeight = 18;

		int startX = 80 + (slotWidth / 2);
		int startY = 18 + (slotWidth / 2);

		for (int slot = 0; slot < inventorySize && slot < rows * columns; slot++) {
			int slotSpacing = 17;

			int slotX = screenX + startX + (slot / columns) * slotSpacing;
			int slotY = screenY + startY + (slot % columns) * slotSpacing;

			guiGraphics.blit(
					SLOT_TEXTURE,
					slotX, slotY,
					0, 0,
					slotWidth, slotHeight,
					slotWidth, slotHeight
			);
		}

		InventoryScreen.renderEntityInInventoryFollowsMouse(
				guiGraphics,
				screenX + 26, screenY + 18,
				screenX + 78, screenY + 70,
				17, 0.25F,
				this.mouseX, this.mouseY,
				this.villager
		);
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		super.render(guiGraphics, mouseX, mouseY, delta);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}
}