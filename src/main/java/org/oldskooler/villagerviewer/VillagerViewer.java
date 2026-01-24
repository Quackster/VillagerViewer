package org.oldskooler.villagerviewer;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Items;
import org.oldskooler.villagerviewer.config.VillagerEditConfig;
import org.oldskooler.villagerviewer.gui.VillagerInventoryScreenHandler;
import org.oldskooler.villagerviewer.gui.VillagerInventoryScreenHandlerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VillagerViewer implements ModInitializer {
	public static final String MOD_NAME = "VillagerViewer";
	public static final String MOD_ID = MOD_NAME.toLowerCase();
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static VillagerEditConfig CONFIG;

	public static final MenuType<VillagerInventoryScreenHandler> VILLAGER_SCREEN_HANDLER =
			Registry.register(BuiltInRegistries.MENU, Identifier.fromNamespaceAndPath(MOD_ID, "villager_screen"),
					new ExtendedScreenHandlerType<>(VillagerInventoryScreenHandler::new, ByteBufCodecs.INT));

	@Override
	public void onInitialize() {
		CONFIG = VillagerEditConfig.load();

		LOGGER.info("(server) Loading " + MOD_NAME);
		LOGGER.info("(server) Config loaded: requireStick = {}", CONFIG.requireStick);

		UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if (world.isClientSide() || player.isSpectator()) {
				return InteractionResult.PASS;
			}

			if (entity instanceof AbstractVillager villager
					&& player.isShiftKeyDown()
					&& player instanceof ServerPlayer serverPlayer
					&& (!CONFIG.requireStick || player.getItemInHand(hand).is(Items.STICK))) {

				serverPlayer.openMenu(new VillagerInventoryScreenHandlerFactory(villager));
				return InteractionResult.SUCCESS;
			}

			return InteractionResult.PASS;
		});

		LOGGER.info("(server) Registered UseEntityCallback for Merchant interaction");
	}
}
