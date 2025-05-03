package net.starliteheart.cobbleride.common.mixin;

import com.cobblemon.mod.common.net.messages.server.SendOutPokemonPacket;
import com.cobblemon.mod.common.net.serverhandling.storage.SendOutPokemonHandler;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.activestate.ActivePokemonState;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SendOutPokemonHandler.class)
public abstract class SendOutPokemonHandlerMixin {
    /**
     * We make sure that dismounting the active Pokemon is prioritized if the right keys were pressed! We also interrupt recalling the Ride Pokemon if Sneak isn't held, so normal functions are maintained if dismounting is not explicitly desired.
     */
    @Inject(
            method = "handle(Lcom/cobblemon/mod/common/net/messages/server/SendOutPokemonPacket;Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/server/level/ServerPlayer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/cobblemon/mod/common/pokemon/Pokemon;getState()Lcom/cobblemon/mod/common/pokemon/activestate/PokemonState;",
                    remap = false
            ),
            cancellable = true
    )
    private void dismountIfRidingActivePokemon(SendOutPokemonPacket packet, MinecraftServer server, ServerPlayer player, CallbackInfo ci, @Local Pokemon pokemon) {
        if (
                player.getVehicle() instanceof RideablePokemonEntity mount && (
                        !mount.isOwnedBy(player) || (
                                pokemon.getState() instanceof ActivePokemonState active && mount.is(active.getEntity())
                        )
                )
        ) {
            ci.cancel();
        }
    }
}