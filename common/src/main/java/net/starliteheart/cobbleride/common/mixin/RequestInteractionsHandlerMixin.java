// ?

//package net.starliteheart.cobbleride.common.mixin;
//
//import com.cobblemon.mod.common.net.serverhandling.RequestInteractionsHandler;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.entity.Entity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.RaycastContext;
//import net.starliteheart.cobbleride.common.util.CobbleRideUtilsKt;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Redirect;
//
//@Mixin(value = RequestInteractionsHandler.class)
//public abstract class RequestInteractionsHandlerMixin {
//    /**
//     * We need to check here to make sure that there isn't an entity hidden inside a mount's hitbox while riding it, and we return the entity instead if it is there.
//     */
//    @Redirect(
//            method = "handle(Lcom/cobblemon/mod/common/net/messages/server/RequestPlayerInteractionsPacket;Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/server/network/ServerPlayerEntity;)V",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lcom/cobblemon/mod/common/net/messages/server/RequestPlayerInteractionsPacket;getTargetId()Ljava/util/UUID;"
//            )
//    )
//    private Entity getRiderIfFirstHitIsMount(PlayerEntity player, float maxDistance, float stepDistance, Class<Entity> entityClass, Entity ignoreEntity, RaycastContext.FluidHandling collideBlock, int i, Object o) {
//        float newStep = (stepDistance > 0) ? stepDistance : 0.05F;
//        return CobbleRideUtilsKt.traceEntityCollisionAndReturnRider(player, maxDistance, newStep, entityClass, ignoreEntity, collideBlock);
//    }
//}
