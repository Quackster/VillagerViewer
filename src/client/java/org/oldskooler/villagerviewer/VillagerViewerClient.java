package org.oldskooler.villagerviewer;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.screen.ScreenHandler;
import org.oldskooler.villagerviewer.gui.VillagerInventoryScreen;

public class VillagerViewerClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		HandledScreens.register(VillagerViewer.VILLAGER_SCREEN_HANDLER, VillagerInventoryScreen::new);
	}
}