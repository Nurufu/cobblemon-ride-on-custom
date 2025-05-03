package net.starliteheart.cobbleride.neoforge.compat;

import net.minecraft.world.entity.player.Player;
import petyourcobblemon.network.PetyourcobblemonModVariables;

public class PetYourCobblemonCompat {
    public static boolean isInteractionModeEnabled(Player player) {
        return player.getData(PetyourcobblemonModVariables.PLAYER_VARIABLES).isInteractionmode;
    }
}
