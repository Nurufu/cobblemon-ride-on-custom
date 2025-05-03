package net.starliteheart.cobbleride.common.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
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
                    target = "Lnet/minecraft/world/entity/LivingEntity;moveRelative(FLnet/minecraft/world/phys/Vec3;)V"
            )
    )
    private void modifyFluidSpeed(LivingEntity instance, float v, Vec3 vec3) {
        if (instance instanceof RideablePokemonEntity) {
            // Since fluid movement is likely balanced around players, base player movement speed is used as a basis
            float speedRatio = instance.getSpeed() / 0.1F;
            instance.moveRelative(v * speedRatio, vec3);
        } else {
            instance.moveRelative(v, vec3);
        }
    }
}