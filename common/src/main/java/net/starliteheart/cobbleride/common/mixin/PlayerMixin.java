package net.starliteheart.cobbleride.common.mixin;

import net.minecraft.world.entity.player.Player;
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Player.class)
public abstract class PlayerMixin {
    /**
     * This inject makes sure that sneaking won't dismount you when you're riding a Ride Pokemon.
     */
    @Inject(
            method = "wantsToStopRiding",
            at = @At(value = "RETURN"),
            cancellable = true
    )
    private void doNotDismountRidePokemon(CallbackInfoReturnable<Boolean> cir) {
        Player player = (Player) (Object) this;
        cir.setReturnValue(!(player.getVehicle() instanceof RideablePokemonEntity) && cir.getReturnValue());
    }
}