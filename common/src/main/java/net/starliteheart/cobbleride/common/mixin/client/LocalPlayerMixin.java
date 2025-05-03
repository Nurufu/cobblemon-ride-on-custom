package net.starliteheart.cobbleride.common.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LocalPlayer.class)
public abstract class LocalPlayerMixin {
    @Shadow
    @Final
    protected Minecraft minecraft;

    @Shadow
    public Input input;

    /**
     * This inject is necessary to pass client inputs to the Ride Pokemon. This ensures that we can operate the Pokemon on a mostly client-side level. Only isRideAscending is not set here, but we can get this from the player's jumping state.
     */
    @Inject(
            method = "aiStep",
            at = @At(value = "TAIL")
    )
    private void setRidePokemonInputs(CallbackInfo ci) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        if (player.getVehicle() instanceof RideablePokemonEntity pokemon && pokemon.isControlledByLocalInstance()) {
            pokemon.setRideDescending(this.input.shiftKeyDown);
            pokemon.setRideSprinting(this.minecraft.options.keySprint.isDown());
        }
    }
}