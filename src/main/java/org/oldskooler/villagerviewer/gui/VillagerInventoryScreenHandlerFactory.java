package org.oldskooler.villagerviewer.gui;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class VillagerInventoryScreenHandlerFactory implements ExtendedScreenHandlerFactory<Integer> {
	private final AbstractVillager villager;

	public VillagerInventoryScreenHandlerFactory(AbstractVillager villager) {
		this.villager = villager;
	}

	@Override
	public Component getDisplayName() {
		return this.villager.getDisplayName();
	}

	@Override
	public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
		return new VillagerInventoryScreenHandler(syncId, playerInventory, this.villager.getId());
	}

	@Override
	public Integer getScreenOpeningData(ServerPlayer serverPlayer) {
		return this.villager.getId();
	}
}
