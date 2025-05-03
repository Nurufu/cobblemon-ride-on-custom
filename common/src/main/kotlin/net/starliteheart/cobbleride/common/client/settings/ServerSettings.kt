package net.starliteheart.cobbleride.common.client.settings

import net.starliteheart.cobbleride.common.CobbleRideMod

object ServerSettings {
    var general = General
    var restrictions = Restrictions
    var speedStat = SpeedStat
    var sprinting = Sprinting

    object General {
        var globalBaseSpeedModifier: Double = CobbleRideMod.config.general.globalBaseSpeedModifier
        var globalLandSpeedModifier: Double = CobbleRideMod.config.general.globalLandSpeedModifier
        var globalWaterSpeedModifier: Double = CobbleRideMod.config.general.globalWaterSpeedModifier
        var globalAirSpeedModifier: Double = CobbleRideMod.config.general.globalAirSpeedModifier
        var underwaterSpeedModifier: Double = CobbleRideMod.config.general.underwaterSpeedModifier
        var waterVerticalClimbSpeed: Double = CobbleRideMod.config.general.waterVerticalClimbSpeed
        var airVerticalClimbSpeed: Double = CobbleRideMod.config.general.airVerticalClimbSpeed
        var rideSpeedLimit: Double = CobbleRideMod.config.general.rideSpeedLimit
        var isWaterBreathingShared: Boolean = CobbleRideMod.config.general.isWaterBreathingShared
    }

    object Restrictions {
        var blacklistedDimensions: List<String> = CobbleRideMod.config.restrictions.blacklistedDimensions
    }

    object SpeedStat {
        var affectsSpeed: Boolean = CobbleRideMod.config.speedStat.affectsSpeed
        var minStatThreshold: Int = CobbleRideMod.config.speedStat.minStatThreshold
        var maxStatThreshold: Int = CobbleRideMod.config.speedStat.maxStatThreshold
        var minSpeedModifier: Double = CobbleRideMod.config.speedStat.minSpeedModifier
        var maxSpeedModifier: Double = CobbleRideMod.config.speedStat.maxSpeedModifier
    }

    object Sprinting {
        var canSprint: Boolean = CobbleRideMod.config.sprinting.canSprint
        var rideSprintSpeed: Double = CobbleRideMod.config.sprinting.rideSprintSpeed
        var canSprintOnLand: Boolean = CobbleRideMod.config.sprinting.canSprintOnLand
        var canSprintInWater: Boolean = CobbleRideMod.config.sprinting.canSprintInWater
        var canSprintInAir: Boolean = CobbleRideMod.config.sprinting.canSprintInAir
        var canExhaust: Boolean = CobbleRideMod.config.sprinting.canExhaust
        var maxStamina: Int = CobbleRideMod.config.sprinting.maxStamina
        var recoveryTime: Int = CobbleRideMod.config.sprinting.recoveryTime
        var recoveryDelay: Int = CobbleRideMod.config.sprinting.recoveryDelay
        var exhaustionSpeed: Double = CobbleRideMod.config.sprinting.exhaustionSpeed
        var exhaustionDuration: Double = CobbleRideMod.config.sprinting.exhaustionDuration
    }
}