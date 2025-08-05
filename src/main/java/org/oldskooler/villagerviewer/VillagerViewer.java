package org.oldskooler.villagerviewer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.screen.ScreenHandlerType;
import org.oldskooler.villagerviewer.config.VillagerEditConfig;
import org.oldskooler.villagerviewer.gui.VillagerInventoryScreenHandler;
import org.oldskooler.villagerviewer.gui.VillagerInventoryScreenHandlerFactory;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.item.Items;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VillagerViewer implements ModInitializer {
	public static final String MOD_NAME = "VillagerViewer";
	public static final String MOD_ID = MOD_NAME.toLowerCase();
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static VillagerEditConfig CONFIG;

	public static final ScreenHandlerType<VillagerInventoryScreenHandler> VILLAGER_SCREEN_HANDLER =
			Registry.register(Registries.SCREEN_HANDLER, Identifier.of(MOD_ID, "villager_screen"),
					new ExtendedScreenHandlerType<>(VillagerInventoryScreenHandler::new, PacketCodecs.INTEGER.cast()));

	@Override
	public void onInitialize() {
		CONFIG = VillagerEditConfig.load();

		LOGGER.info("(server) Loading " + MOD_NAME);
		LOGGER.info("(server) Config loaded: requireStick = {}", CONFIG.requireStick);

		UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if (world.isClient() || player.isSpectator()) {
				return ActionResult.PASS;
			}

			if (entity instanceof MerchantEntity villager
					&& player.isSneaking()
					&& player instanceof ServerPlayerEntity serverPlayer
					&& (!CONFIG.requireStick || player.getStackInHand(hand).isOf(Items.STICK))) {

				serverPlayer.openHandledScreen(new VillagerInventoryScreenHandlerFactory(villager));
				return ActionResult.SUCCESS;
			}

			return ActionResult.PASS;
		});

		LOGGER.info("(server) Registered UseEntityCallback for Merchant interaction");
	}
}
