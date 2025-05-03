package net.starliteheart.cobbleride.neoforge;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.starliteheart.cobbleride.common.CobbleRideMod;
import net.starliteheart.cobbleride.common.CobbleRideModImpl;
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity;
import net.starliteheart.cobbleride.neoforge.compat.PetYourCobblemonCompat;
import org.jetbrains.annotations.NotNull;

@Mod(CobbleRideMod.MOD_ID)
public class CobbleRideModNeoForgeImpl extends CobbleRideModImpl {
    @Override
    public boolean canInteractToMount(@NotNull Player player, @NotNull InteractionHand hand, @NotNull RideablePokemonEntity entity) {
        return super.canInteractToMount(player, hand, entity)
                && !(ModList.get().isLoaded("petyourcobblemon") && PetYourCobblemonCompat.isInteractionModeEnabled(player));
    }
}
