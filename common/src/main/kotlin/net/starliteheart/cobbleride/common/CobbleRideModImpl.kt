package net.starliteheart.cobbleride.common

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.Hand
import net.starliteheart.cobbleride.common.api.tags.CobbleRideTags
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity

abstract class CobbleRideModImpl {
    /**
     * Implemented here, where it can be overridden or augmented as needed for compatibility reasons
     */
    open fun canInteractToMount(player: PlayerEntity, hand: Hand, entity: RideablePokemonEntity): Boolean {
        return !player.isSneaking && hand == Hand.MAIN_HAND
                && !player.getStackInHand(hand).isIn(CobbleRideTags.NO_MOUNT_ITEMS)
                && !(entity.isBattling && player.getStackInHand(hand).isIn(CobbleRideTags.NO_MOUNT_BATTLE_ITEMS))
    }

    open fun shouldRenderStaminaBar(player: PlayerEntity): Boolean {
        return if (player.vehicle is RideablePokemonEntity) {
            val mount = (player.vehicle as RideablePokemonEntity)
            mount.canSprint && mount.canExhaust && mount.isLogicalSideForUpdatingMovement
        } else false
    }
}