package net.starliteheart.cobbleride.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.starliteheart.cobbleride.common.client.CobbleRideClient;
import net.starliteheart.cobbleride.common.client.gui.RideStaminaOverlay;

public final class CobbleRideModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        HudRenderCallback.EVENT.register((matrixStack, delta) -> RideStaminaOverlay.render(matrixStack));

        CobbleRideClient.INSTANCE.initialize();
    }
}