package net.starliteheart.cobbleride.common.mixin.client;

import com.cobblemon.mod.common.client.gui.pokedex.PokedexGUIConstants;
import com.cobblemon.mod.common.client.gui.summary.Summary;
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

@Mixin(value = Summary.class)
public abstract class SummaryMixin {
    /**
     * This inject makes sure that Ride Pokemon have a neat little icon in the summary screens to show that you can ride them!
     */
    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/Screen;render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V"
            )
    )
    private void displayRideIcon(GuiGraphics context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        Summary summary = (Summary) (Object) this;
        final float SCALE = PokedexGUIConstants.SCALE;
        int x = (summary.width - Summary.BASE_WIDTH) / 2;
        int y = (summary.height - Summary.BASE_HEIGHT) / 2;
        PoseStack matrices = context.pose();

        Pokemon pokemon = summary.getSelectedPokemon$common();
        RideableSpecies species = RideablePokemonSpecies.INSTANCE.getByName(pokemon.getSpecies().showdownId());
        if (species != null && species.getForm(pokemon.getForm().getName()).getEnabled()) blitRideIcon(
                matrices,
                rideableResource("textures/gui/summary/ride-icon.png"),
                (x + 56) / SCALE,
                (y + 101) / SCALE,
                32,
                16,
                SCALE
        );
    }
}