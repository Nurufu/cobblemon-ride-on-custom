//package net.starliteheart.cobbleride.common.mixin;
//
//import com.cobblemon.mod.common.util.EntityTraceResult;
//import com.cobblemon.mod.common.util.PlayerExtensionsKt;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.world.RaycastContext;
//import net.starliteheart.cobbleride.common.util.CobbleRideUtilsKt;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Redirect;
//
//@Mixin(value = PlayerExtensionsKt.class)
//public abstract class PlayerExtensionsMixin {
//    /**
//     * This inject ensures that any mounted entity will not interfere with the player's ability to interact with an entity in front of them.
//     */
//    @Redirect(
//            method = "traceFirstEntityCollision",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lcom/cobblemon/mod/common/util/PlayerExtensionsKt;traceEntityCollision(Lnet/minecraft/entity/player/PlayerEntity;FFLjava/lang/Class;Lnet/minecraft/entity/Entity;)Lcom/cobblemon/mod/common/util/EntityTraceResult;"
//            )
//    )
//    private static EntityTraceResult<Entity> ignoreMountForTrace(PlayerEntity playerEntity, float x, float v, Class aClass, Entity entity) {
//        return CobbleRideUtilsKt.resolveTraceEntityCollision(playerEntity, x, v, aClass, entity);
//    }
//
//    /**
//     * We need to check here to make sure that there isn't an entity hidden inside a mount's hitbox while riding it, and we return the entity instead if it is there.
//     */
//    @Redirect(
//            method = "canInteractWith",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lcom/cobblemon/mod/common/util/PlayerExtensionsKt;traceFirstEntityCollision$default(Lnet/minecraft/world/entity/player/Player;FFLjava/lang/Class;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/level/ClipContext$Fluid;ILjava/lang/Object;)Lnet/minecraft/world/entity/Entity;"
//            )
//    )
//    private static Entity getRiderIfFirstHitIsMount(Player player, float maxDistance, float stepDistance, Class<Entity> entityClass, Entity ignoreEntity, ClipContext.Fluid collideBlock, int i, Object o) {
//        float newStep = (stepDistance > 0) ? stepDistance : 0.05F;
//        return CobbleRideUtilsKt.traceEntityCollisionAndReturnRider(player, maxDistance, newStep, entityClass, ignoreEntity, collideBlock);
//    }
//}
