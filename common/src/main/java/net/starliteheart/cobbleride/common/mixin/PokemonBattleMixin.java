package net.starliteheart.cobbleride.common.mixin;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.pokemon.Pokemon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = PokemonBattle.class)
public abstract class PokemonBattleMixin {
    /**
     * In 1.6, Cobblemon forces Pokemon to recall after a battle if they have a raft, because they didn't have the clientside moving behaviour. I fixed this for Ride Pokemon (and by extension, all owned Pokemon), so I suppress this recall since it is unnecessary.
     */
    @Redirect(
            method = "end",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/cobblemon/mod/common/pokemon/Pokemon;tryRecallWithAnimation()V"
            ),
            remap = false
    )
    private void preventRecalling(Pokemon instance) {
    }
}