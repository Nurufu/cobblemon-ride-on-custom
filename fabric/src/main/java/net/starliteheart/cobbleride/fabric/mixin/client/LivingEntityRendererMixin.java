package net.starliteheart.cobbleride.fabric.mixin.client;

import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = LivingEntityRenderer.class)
public class LivingEntityRendererMixin {
    /**
     * We need to redirect this method in order to add a Ride Pokemon check, so that we can adjust the passenger's render state if they need to be standing
     */
    @Redirect(
            method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isPassenger()Z")
    )
    private boolean setIfPassengerIsSitting(LivingEntity livingEntity) {
        return livingEntity.isPassenger() &&
                (!(livingEntity.getVehicle() instanceof RideablePokemonEntity pokemon) || pokemon.shouldRiderSit());
    }
}
