package net.starliteheart.cobbleride.neoforge;

import net.neoforged.fml.common.Mod;
import net.starliteheart.cobbleride.common.CobbleRideMod;

@Mod(CobbleRideMod.MOD_ID)
public class CobbleRideModNeoForge {
    public CobbleRideModNeoForge() {
        CobbleRideMod.INSTANCE.initialize(new CobbleRideModNeoForgeImpl());
    }
}
