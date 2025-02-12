package net.funkystudios.funkycore;

import net.fabricmc.api.ClientModInitializer;
import net.funkystudios.funkycore.init.FCScreenHandlerTypeInit;
import net.funkystudios.funkycore.screen.FoundryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class FunkyCoreClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(FCScreenHandlerTypeInit.FOUNDRY_SCREEN_HANDLER, FoundryScreen::new);
    }
}
