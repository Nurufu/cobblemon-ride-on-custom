package net.starliteheart.cobbleride.fabric;

import net.fabricmc.api.ModInitializer;
import net.starliteheart.cobbleride.common.CobbleRideMod;

public class CobbleRideModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        CobbleRideMod.INSTANCE.initialize(new CobbleRideModFabricImpl());
    }
}
