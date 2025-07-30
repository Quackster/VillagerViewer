package org.oldskooler.villagerviewer.gui;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class VillagerInventoryScreen extends HandledScreen<VillagerInventoryScreenHandler> {
	private static final Identifier CONTAINER_TEXTURE = Identifier.of("textures/gui/container/horse.png");
	private static final Identifier SLOT_TEXTURE = Identifier.of("textures/gui/sprites/recipe_book/slot_craftable.png");

	private final MerchantEntity villager;
	private float mouseX;
	private float mouseY;

	public VillagerInventoryScreen(VillagerInventoryScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
		this.villager = handler.villager;
	}

	@Override
	protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
		int screenX = (this.width - this.backgroundWidth) / 2;
		int screenY = (this.height - this.backgroundHeight) / 2;

		context.drawTexture(
				RenderPipelines.GUI_TEXTURED,
				CONTAINER_TEXTURE,
				screenX, screenY,
				0, 0,
				this.backgroundWidth, this.backgroundHeight,
				256, 256
		);

		int inventorySize = this.handler.villagerInventory.size();
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

			context.drawTexture(
					RenderPipelines.GUI_TEXTURED,
					SLOT_TEXTURE,
					slotX, slotY,
					0, 0,
					slotWidth, slotHeight,
					slotWidth, slotHeight
			);
		}

		InventoryScreen.drawEntity(
				context,
				screenX + 26, screenY + 18,
				screenX + 78, screenY + 70,
				17, 0.25F,
				this.mouseX, this.mouseY,
				this.villager
		);
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		super.render(context, mouseX, mouseY, delta);
		this.drawMouseoverTooltip(context, mouseX, mouseY);
	}
}