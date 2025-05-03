package net.starliteheart.cobbleride.common.mixin;

import com.cobblemon.mod.common.api.pokemon.evolution.Evolution;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.evolution.controller.ServerEvolutionController;
import com.cobblemon.mod.common.util.MiscUtilsKt;
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ServerEvolutionController.class)
public abstract class ServerEvolutionControllerMixin {
    /**
     * It would be silly to allow a player to evolve their Pokemon while someone is riding it, so we prevent that here.
     */
    @Inject(
            method = "start(Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;)V",
            at = @At("HEAD"),
            remap = false,
            cancellable = true
    )
    private void preventEvolvingIfMounted(Evolution evolution, CallbackInfo ci) {
        Pokemon pokemon = ((ServerEvolutionController) (Object) this).pokemon();
        if (pokemon.getEntity() instanceof RideablePokemonEntity mount && mount.isVehicle()) {
            if (pokemon.getOwnerPlayer() != null) {
                pokemon.getOwnerPlayer().sendSystemMessage(MiscUtilsKt.asTranslated("cobbleride.ui.mount.evolve", pokemon.getDisplayName()));
            }
            ci.cancel();
        }
    }
}