package net.starliteheart.cobbleride.common.mixin;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.entity.pokemon.PokemonServerDelegate;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = PokemonServerDelegate.class)
public abstract class PokemonServerDelegateMixin<T> {
    /**
     * Animations for Ride Pokemon don't work unless we make sure their MOVING state is being updated correctly.
     */
    @SuppressWarnings("unchecked")
    @Redirect(
            method = "updateTrackedValues", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/data/DataTracker;set(Lnet/minecraft/entity/data/TrackedData;Ljava/lang/Object;)V"
    )
    )
    private void setIfRideableIsMoving(DataTracker instance, TrackedData<T> arg, T object) {
        if (arg.equals(PokemonEntity.Companion.getMOVING()) && ((PokemonServerDelegate) (Object) this).entity instanceof RideablePokemonEntity rideable && rideable.getControllingPassenger() != null && rideable.getControllingPassenger() instanceof PlayerEntity player) {
            float x = player.sidewaysSpeed * 0.5f;
            float z = player.forwardSpeed;
            if (z <= 0.0f) {
                z *= 0.25f;
            }
            Vec3d input = new Vec3d(x, 0.0, z);
            boolean isRideableMoving = input.length() > 0.005F;
//            boolean hasPlatform = rideable.getPlatform() != PlatformType.NONE;
            instance.set((TrackedData<Boolean>) arg, ((Boolean) object)); /*|| (!hasPlatform && isRideableMoving))*/
        } else {
            instance.set(arg, object);
        }
    }
}