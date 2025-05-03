package net.starliteheart.cobbleride.common.mixin.client;

import com.cobblemon.mod.common.api.pokedex.entry.PokedexEntry;
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.client.gui.pokedex.PokedexGUIConstants;
import com.cobblemon.mod.common.client.gui.pokedex.widgets.PokemonInfoWidget;
import com.cobblemon.mod.common.pokemon.Species;
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

@Mixin(value = PokemonInfoWidget.class)
public abstract class PokemonInfoWidgetMixin {
    /**
     * This inject makes sure that Ride Pokemon have a neat little icon in the Pokedex to show that you can ride them!
     */
    @Inject(
            method = "renderWidget", at = @At(
            value = "INVOKE",
            target = "Lcom/cobblemon/mod/common/client/gui/pokedex/ScaledButton;render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V",
            ordinal = 6, shift = At.Shift.AFTER
    )
    )
    private void displayRideDexIcon(GuiGraphics context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        PoseStack matrices = context.pose();
        PokemonInfoWidget pokeInfo = (PokemonInfoWidget) (Object) this;
        final float SCALE = PokedexGUIConstants.SCALE;

        PokedexEntry entry = pokeInfo.getCurrentEntry();
        if (entry != null) {
            Species species = PokemonSpecies.INSTANCE.getByIdentifier(entry.getSpeciesId());
            if (species != null && !pokeInfo.getVisibleForms().isEmpty()) {
                String formName = pokeInfo.getVisibleForms().get(pokeInfo.getSelectedFormIndex()).getDisplayForm();
                RideableSpecies rideableSpecies = RideablePokemonSpecies.INSTANCE.getByName(species.showdownId());
                if (rideableSpecies != null && rideableSpecies.getForm(formName).getEnabled()) blitRideIcon(
                        matrices,
                        rideableResource("textures/gui/pokedex/ride-dex-icon.png"),
                        (pokeInfo.getPX() + 114) / SCALE,
                        (pokeInfo.getPY() + 69) / SCALE,
                        44,
                        20,
                        SCALE
                );
            }
        }
    }
}