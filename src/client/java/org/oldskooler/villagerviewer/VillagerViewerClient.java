package org.oldskooler.villagerviewer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import org.oldskooler.villagerviewer.gui.VillagerInventoryScreen;
import org.oldskooler.villagerviewer.gui.VillagerInventoryScreenHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public class VillagerViewerClient implements ClientModInitializer {
	public static final String MOD_NAME = "VillagerViewer";
	public static final String MOD_ID = MOD_NAME.toLowerCase();
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	/*
	public static final ScreenHandlerType<VillagerInventoryScreenHandler> VILLAGER_SCREEN_HANDLER =
			new ExtendedScreenHandlerType<>(VillagerInventoryScreenHandler::new, PacketCodecs.INTEGER.cast());
	 */
	public static final ScreenHandlerType<VillagerInventoryScreenHandler> VILLAGER_SCREEN_HANDLER =
			new ExtendedScreenHandlerType<>(VillagerInventoryScreenHandler::new, PacketCodecs.INTEGER.cast());
			//Registry.register(Registries.SCREEN_HANDLER, Identifier.of(MOD_ID, "villager_screen"),
			//		new ExtendedScreenHandlerType<>(VillagerInventoryScreenHandler::new, PacketCodecs.INTEGER.cast()));

	@Override
		public void onInitializeClient() {
		/*Registry.register(
				Registries.SCREEN_HANDLER,
				Identifier.of(MOD_ID.toLowerCase(), "villager_screen"),
				VILLAGER_SCREEN_HANDLER
		);*/

		HandledScreens.register(VILLAGER_SCREEN_HANDLER, VillagerInventoryScreen::new);

	}
}