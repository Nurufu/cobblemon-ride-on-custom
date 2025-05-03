package net.starliteheart.cobbleride.common.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.player.Player;
import net.starliteheart.cobbleride.common.CobbleRideMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Gui.class)
public abstract class GuiMixin {
    /**
     * We hide the experience bar while rendering the stamina bar, to avoid funny overlapping sprites.
     */
    @Inject(
            method = "isExperienceBarVisible",
            at = @At("RETURN"),
            cancellable = true
    )
    private void hideExperienceBarForRidePokemon(CallbackInfoReturnable<Boolean> cir) {
        Player player = Minecraft.getInstance().player;
        cir.setReturnValue(cir.getReturnValue() &&
                !(player != null && CobbleRideMod.implementation.shouldRenderStaminaBar(player))
        );
    }
}