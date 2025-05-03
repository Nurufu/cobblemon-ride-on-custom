package net.starliteheart.cobbleride.common.mixin.client;

import com.cobblemon.mod.common.client.gui.pc.PCGUI;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.starliteheart.cobbleride.common.api.pokemon.RideablePokemonSpecies;
import net.starliteheart.cobbleride.common.pokemon.RideableSpecies;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.starliteheart.cobbleride.common.client.CobbleRideClientUtilsKt.blitRideIcon;
import static net.starliteheart.cobbleride.common.util.CobbleRideUtilsKt.rideableResource;

@Mixin(value = PCGUI.class)
public abstract class PCGUIMixin {
    /**
     * This inject makes sure that Ride Pokemon have a neat little icon in the PC screens to show that you can ride them!
     */
    @Inject(
            method = "render", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screens/Screen;render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V"
    )
    )
    private void displayRideIcon(GuiGraphics context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        PCGUI pcgui = ((PCGUI) (Object) this);
        int x = (pcgui.width - PCGUI.BASE_WIDTH) / 2;
        int y = (pcgui.height - PCGUI.BASE_HEIGHT) / 2;
        PoseStack matrices = context.pose();

        Pokemon pokemon = pcgui.getPreviewPokemon$common();
        if (pcgui.getPreviewPokemon$common() != null) {
            RideableSpecies species = RideablePokemonSpecies.INSTANCE.getByName(pokemon.getSpecies().showdownId());
            if (species != null && species.getForm(pokemon.getForm().getName()).getEnabled()) {
                blitRideIcon(
                        matrices,
                        rideableResource("textures/gui/summary/ride-icon.png"),
                        (x + 56) / PCGUI.SCALE,
                        (y + 95.5) / PCGUI.SCALE,
                        32,
                        16,
                        PCGUI.SCALE
                );
            }
        }
    }
}