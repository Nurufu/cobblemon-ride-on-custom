package net.starliteheart.cobbleride.common.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = LivingEntity.class)
public abstract class LivingEntityMixin {
    /**
     * Minecraft is weird in that all entities seem(?) to move at the same speed in water, because movement speed isn't really factored into the way they move in water. The below inject makes sure that a Ride Pokemon's speed in liquid will be properly applied.
     */
    @Redirect(
            method = "travel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;updateVelocity(FLnet/minecraft/util/math/Vec3d;)V"
            )
    )
    private void modifyFluidSpeed(LivingEntity instance, float v, Vec3d vec3) {
        if (instance instanceof RideablePokemonEntity) {
            // Since fluid movement is likely balanced around players, base player movement speed is used as a basis
            float speedRatio = instance.getMovementSpeed() / 0.1F;
            instance.updateVelocity(v * speedRatio, vec3);
        } else {
            instance.updateVelocity(v, vec3);
        }
    }
}