package net.starliteheart.cobbleride.common.mixin.client;

import com.cobblemon.mod.common.CobblemonNetwork;
import com.cobblemon.mod.common.client.CobblemonClient;
import com.cobblemon.mod.common.client.keybind.keybinds.PartySendBinding;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.starliteheart.cobbleride.common.client.settings.ClientSettings;
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity;
import net.starliteheart.cobbleride.common.mixin.accessor.KeyMappingAccessor;
import net.starliteheart.cobbleride.common.net.messages.server.pokemon.update.DismountPokemonPacket;
import net.starliteheart.cobbleride.common.util.CobbleRideUtilsKt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PartySendBinding.class)
public abstract class PartySendBindingMixin {

    @Unique
    private boolean cobbleRide$wasShiftDown = false;

    /**
     * A dismount handler for ensuring that any attempts to dismount in midair are blocked if the feature is enabled.
     * We also apply this before checking anything else, including battle triggers. This is due to a problem that arises where we might try to enter a battle or perform other actions, but the Ride Pokemon might block the raytrace. Or, it doesn't, but then we have a problem where we cannot dismount while a battle is ongoing. The best solution that was decided upon was preventing any other actions with the R key until dismounted.
     */
    @Inject(
            method = "onRelease",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/cobblemon/mod/common/client/CobblemonClient;getBattle()Lcom/cobblemon/mod/common/client/battle/ClientBattle;"
            ),
            remap = false,
            cancellable = true
    )
    private void prioritizeDismountActionWithMidairLogic(CallbackInfo ci, @Local LocalPlayer player) {
        int selectedSlot = CobblemonClient.INSTANCE.getStorage().getSelectedSlot();
        if (selectedSlot != -1 && Minecraft.getInstance().screen == null) {
            Pokemon pokemon = CobblemonClient.INSTANCE.getStorage().getMyParty().get(selectedSlot);
            if (
                    cobbleRide$wasShiftDown &&
                            player.getVehicle() instanceof RideablePokemonEntity mount && mount.isAlive()
                            && (!mount.isOwnedBy(player)
                            || (pokemon != null && mount.is(pokemon.getEntity()))
                    )
            ) {
                // If we aren't canceling, send a packet to queue the expected dismount action
                if (mount.onGround() || !mount.isFlying() || ClientSettings.INSTANCE.getCanDismountInMidair()) {
                    CobblemonNetwork.INSTANCE.sendToServer(new DismountPokemonPacket(selectedSlot));
                }
                ci.cancel();
            }
        }
    }

    /**
     * We need to check here to make sure that there isn't an entity hidden inside a mount's hitbox while riding it, and we return the entity instead if it is there.
     */
    @Redirect(
            method = "onRelease",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/cobblemon/mod/common/util/PlayerExtensionsKt;traceFirstEntityCollision$default(Lnet/minecraft/world/entity/player/Player;FFLjava/lang/Class;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/level/ClipContext$Fluid;ILjava/lang/Object;)Lnet/minecraft/world/entity/Entity;"
            )
    )
    private Entity getRiderIfFirstHitIsMount(Player player, float maxDistance, float stepDistance, Class<Entity> entityClass, Entity ignoreEntity, ClipContext.Fluid collideBlock, int i, Object o) {
        float newStep = (stepDistance > 0) ? stepDistance : 0.05F;
        return CobbleRideUtilsKt.traceEntityCollisionAndReturnRider(player, maxDistance, newStep, entityClass, ignoreEntity, collideBlock);
    }

    @Inject(method = "onPress", at = @At("TAIL"), remap = false)
    private void setShiftFalse(CallbackInfo ci) {
        cobbleRide$wasShiftDown = false;
    }

    @Inject(method = "onTick", at = @At("TAIL"), remap = false)
    private void setShiftTrue(CallbackInfo ci) {
        if (((KeyMappingAccessor) this).getIsDown() && !cobbleRide$wasShiftDown) {
            LocalPlayer player = Minecraft.getInstance().player;
            cobbleRide$wasShiftDown = (player != null && player.isShiftKeyDown());
        }
    }
}
