package net.starliteheart.cobbleride.common.mixin;

import com.cobblemon.mod.common.battles.interpreter.instructions.SwitchInstruction;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.world.entity.player.Player;
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.concurrent.CompletableFuture;

@Mixin(value = SwitchInstruction.Companion.class)
public abstract class SwitchInstructionMixin {
    /**
     * It feels bad to recall the Pokemon you're actively riding if it isn't knocked out, so we prevent that here.
     */
    @Redirect(
            method = "createEntitySwitch",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;recallWithAnimation()Ljava/util/concurrent/CompletableFuture;"
            )
    )
    private CompletableFuture<Pokemon> interruptRecallForRidePokemon(PokemonEntity pokemon) {
        if (!(pokemon instanceof RideablePokemonEntity mount && mount.getOwner() instanceof Player player && player.getVehicle() == mount)) {
            return pokemon.recallWithAnimation();
        }
        return null;
    }
}