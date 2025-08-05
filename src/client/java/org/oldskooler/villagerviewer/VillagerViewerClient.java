package org.oldskooler.villagerviewer;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import org.oldskooler.villagerviewer.gui.VillagerInventoryScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VillagerViewerClient implements ClientModInitializer {
	public static final String MOD_NAME = "VillagerViewer";
	public static final String MOD_ID = MOD_NAME.toLowerCase();
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	@Override
	public void onInitializeClient() {
		LOGGER.info("(client) Loading " + MOD_NAME);
		HandledScreens.register(VillagerViewer.VILLAGER_SCREEN_HANDLER, VillagerInventoryScreen::new);
		LOGGER.info("(client) Registered inventory screen for villagers");
	}
}