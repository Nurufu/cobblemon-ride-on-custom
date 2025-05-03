package net.starliteheart.cobbleride.common.config

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

class CobbleRideConfig {
    @SerializedName("general")
    var general: General = General()

    @SerializedName("client")
    var client: Client = Client()

    @SerializedName("restrictions")
    var restrictions: Restrictions = Restrictions()

    @SerializedName("speedStat")
    var speedStat: SpeedStat = SpeedStat()

    @SerializedName("sprinting")
    var sprinting: Sprinting = Sprinting()

    inner class General {
        @SerializedName("globalBaseSpeedModifier")
        var globalBaseSpeedModifier: Double = ConfigConstants.Speed.DEFAULT

        @SerializedName("globalLandSpeedModifier")
        var globalLandSpeedModifier: Double = ConfigConstants.Speed.DEFAULT

        @SerializedName("globalWaterSpeedModifier")
        var globalWaterSpeedModifier: Double = ConfigConstants.Speed.DEFAULT

        @SerializedName("globalAirSpeedModifier")
        var globalAirSpeedModifier: Double = ConfigConstants.Speed.DEFAULT

        @SerializedName("underwaterSpeedModifier")
        var underwaterSpeedModifier: Double = ConfigConstants.Speed.UNDERWATER

        @SerializedName("waterVerticalClimbSpeed")
        var waterVerticalClimbSpeed: Double = ConfigConstants.Height.SWIM

        @SerializedName("airVerticalClimbSpeed")
        var airVerticalClimbSpeed: Double = ConfigConstants.Height.FLY

        @SerializedName("rideSpeedLimit")
        var rideSpeedLimit: Double = ConfigConstants.SpeedLimit.VALUE

        @SerializedName("isWaterBreathingShared")
        var isWaterBreathingShared: Boolean = ConfigConstants.Feature.IS_WATER_BREATHING_SHARED
    }

    inner class Client {
        @SerializedName("canDismountInMidair")
        var canDismountInMidair: Boolean = ConfigConstants.Feature.CAN_DISMOUNT_IN_MIDAIR

        @SerializedName("useCameraNavigation")
        var useCameraNavigation: Boolean = ConfigConstants.Feature.USE_CAMERA_NAVIGATION
    }

    inner class Restrictions {
        @SerializedName("blacklistedDimensions")
        var blacklistedDimensions: List<String> = listOf()
    }

    inner class SpeedStat {
        @SerializedName("affectsSpeed")
        var affectsSpeed: Boolean = ConfigConstants.SpeedStat.ACTIVE

        @SerializedName("minStatThreshold")
        var minStatThreshold: Int = ConfigConstants.SpeedStat.Stat.MIN_STAT

        @SerializedName("maxStatThreshold")
        var maxStatThreshold: Int = ConfigConstants.SpeedStat.Stat.MAX_STAT

        @SerializedName("minSpeedModifier")
        var minSpeedModifier: Double = ConfigConstants.SpeedStat.Speed.MIN_SPEED

        @SerializedName("maxSpeedModifier")
        var maxSpeedModifier: Double = ConfigConstants.SpeedStat.Speed.MAX_SPEED
    }

    inner class Sprinting {
        @SerializedName("canSprint")
        var canSprint: Boolean = ConfigConstants.Sprinting.ACTIVE

        @SerializedName("rideSprintSpeed")
        var rideSprintSpeed: Double = ConfigConstants.Sprinting.Speed.VALUE

        @SerializedName("canSprintOnLand")
        var canSprintOnLand: Boolean = ConfigConstants.Sprinting.ON_LAND

        @SerializedName("canSprintInWater")
        var canSprintInWater: Boolean = ConfigConstants.Sprinting.IN_WATER

        @SerializedName("canSprintInAir")
        var canSprintInAir: Boolean = ConfigConstants.Sprinting.IN_AIR

        @SerializedName("canExhaust")
        var canExhaust: Boolean = ConfigConstants.Sprinting.Exhaust.ACTIVE

        @SerializedName("maxStamina")
        var maxStamina: Int = ConfigConstants.Sprinting.Stamina.VALUE

        @SerializedName("recoveryTime")
        var recoveryTime: Int = ConfigConstants.Sprinting.Recovery.VALUE

        @SerializedName("recoveryDelay")
        var recoveryDelay: Int = ConfigConstants.Sprinting.Delay.VALUE

        @SerializedName("exhaustionSpeed")
        var exhaustionSpeed: Double = ConfigConstants.Sprinting.Exhaust.VALUE

        @SerializedName("exhaustionDuration")
        var exhaustionDuration: Double = ConfigConstants.Sprinting.Exhaust.DURATION
    }

    companion object {
        var GSON: Gson = GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .create()

        fun validate(config: CobbleRideConfig) {
            with(config.general) {
                globalBaseSpeedModifier =
                    globalBaseSpeedModifier.coerceIn(ConfigConstants.Speed.MIN, ConfigConstants.Speed.MAX)
                globalLandSpeedModifier =
                    globalLandSpeedModifier.coerceIn(ConfigConstants.Speed.MIN, ConfigConstants.Speed.MAX)
                globalWaterSpeedModifier =
                    globalWaterSpeedModifier.coerceIn(ConfigConstants.Speed.MIN, ConfigConstants.Speed.MAX)
                globalAirSpeedModifier =
                    globalAirSpeedModifier.coerceIn(ConfigConstants.Speed.MIN, ConfigConstants.Speed.MAX)
                underwaterSpeedModifier =
                    underwaterSpeedModifier.coerceIn(ConfigConstants.Speed.MIN, ConfigConstants.Speed.MAX)
                waterVerticalClimbSpeed =
                    waterVerticalClimbSpeed.coerceIn(ConfigConstants.Height.MIN, ConfigConstants.Height.MAX)
                airVerticalClimbSpeed =
                    airVerticalClimbSpeed.coerceIn(ConfigConstants.Height.MIN, ConfigConstants.Height.MAX)
                rideSpeedLimit =
                    rideSpeedLimit.coerceIn(ConfigConstants.SpeedLimit.MIN, ConfigConstants.SpeedLimit.MAX)
            }

            with(config.speedStat) {
                minStatThreshold =
                    minStatThreshold.coerceIn(ConfigConstants.SpeedStat.Stat.MIN, ConfigConstants.SpeedStat.Stat.MAX)
                maxStatThreshold =
                    maxStatThreshold.coerceIn(minStatThreshold, ConfigConstants.SpeedStat.Stat.MAX)
                minSpeedModifier =
                    minSpeedModifier.coerceIn(ConfigConstants.SpeedStat.Speed.MIN, ConfigConstants.SpeedStat.Speed.MAX)
                maxSpeedModifier =
                    maxSpeedModifier.coerceIn(minSpeedModifier, ConfigConstants.SpeedStat.Speed.MAX)
            }

            with(config.sprinting) {
                rideSprintSpeed =
                    rideSprintSpeed.coerceIn(ConfigConstants.Sprinting.Speed.MIN, ConfigConstants.Sprinting.Speed.MAX)
                maxStamina =
                    maxStamina.coerceIn(ConfigConstants.Sprinting.Stamina.MIN, ConfigConstants.Sprinting.Stamina.MAX)
                recoveryTime = recoveryTime.coerceIn(
                    ConfigConstants.Sprinting.Recovery.MIN,
                    ConfigConstants.Sprinting.Recovery.MAX
                )
                recoveryDelay =
                    recoveryDelay.coerceIn(ConfigConstants.Sprinting.Delay.MIN, ConfigConstants.Sprinting.Delay.MAX)
                exhaustionSpeed = exhaustionSpeed.coerceIn(
                    ConfigConstants.Sprinting.Exhaust.MIN,
                    ConfigConstants.Sprinting.Exhaust.MAX
                )
                exhaustionDuration = exhaustionDuration.coerceIn(
                    ConfigConstants.Sprinting.Exhaust.MIN,
                    ConfigConstants.Sprinting.Exhaust.MAX
                )
            }
        }
    }
}
