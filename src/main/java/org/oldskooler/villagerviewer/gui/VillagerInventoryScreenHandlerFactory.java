package org.oldskooler.villagerviewer.gui;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class VillagerInventoryScreenHandlerFactory implements ExtendedScreenHandlerFactory<Integer> {
	private final MerchantEntity villager;

	public VillagerInventoryScreenHandlerFactory(MerchantEntity villager) {
		this.villager = villager;
	}

	@Override
	public Text getDisplayName() {
		return this.villager.getDisplayName();
	}

	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
		return new VillagerInventoryScreenHandler(syncId, playerInventory, this.villager.getId());
	}

	@Override
	public Integer getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
		return this.villager.getId();
	}
}
