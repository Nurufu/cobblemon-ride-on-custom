package net.starliteheart.cobbleride.common.client.settings

import net.starliteheart.cobbleride.common.CobbleRideMod

object ClientSettings {
    var canDismountInMidair: Boolean = CobbleRideMod.config.client.canDismountInMidair
    var useCameraNavigation: Boolean = CobbleRideMod.config.client.useCameraNavigation
}