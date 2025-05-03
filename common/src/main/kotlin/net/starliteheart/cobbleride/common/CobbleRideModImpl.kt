package net.starliteheart.cobbleride.common

import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.player.Player
import net.starliteheart.cobbleride.common.api.tags.CobbleRideTags
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity

abstract class CobbleRideModImpl {
    /**
     * Implemented here, where it can be overridden or augmented as needed for compatibility reasons
     */
    open fun canInteractToMount(player: Player, hand: InteractionHand, entity: RideablePokemonEntity): Boolean {
        return !player.isShiftKeyDown && hand == InteractionHand.MAIN_HAND
                && !player.getItemInHand(hand).`is`(CobbleRideTags.NO_MOUNT_ITEMS)
                && !(entity.isBattling && player.getItemInHand(hand).`is`(CobbleRideTags.NO_MOUNT_BATTLE_ITEMS))
    }

    open fun shouldRenderStaminaBar(player: Player): Boolean {
        return if (player.vehicle is RideablePokemonEntity) {
            val mount = (player.vehicle as RideablePokemonEntity)
            mount.canSprint && mount.canExhaust && mount.isControlledByLocalInstance
        } else false
    }
}