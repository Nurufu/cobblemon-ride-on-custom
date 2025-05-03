package net.starliteheart.cobbleride.common.client.net.settings

import com.cobblemon.mod.common.api.net.ClientNetworkPacketHandler
import net.minecraft.client.Minecraft
import net.starliteheart.cobbleride.common.client.settings.ServerSettings
import net.starliteheart.cobbleride.common.net.messages.client.settings.SendServerSettingsPacket

class SendServerSettingsHandler : ClientNetworkPacketHandler<SendServerSettingsPacket> {
    override fun handle(packet: SendServerSettingsPacket, client: Minecraft) {
        ServerSettings.general.globalBaseSpeedModifier = packet.globalBaseSpeedModifier
        ServerSettings.general.globalLandSpeedModifier = packet.globalLandSpeedModifier
        ServerSettings.general.globalWaterSpeedModifier = packet.globalWaterSpeedModifier
        ServerSettings.general.globalAirSpeedModifier = packet.globalAirSpeedModifier
        ServerSettings.general.underwaterSpeedModifier = packet.underwaterSpeedModifier
        ServerSettings.general.waterVerticalClimbSpeed = packet.waterVerticalClimbSpeed
        ServerSettings.general.airVerticalClimbSpeed = packet.airVerticalClimbSpeed
        ServerSettings.general.rideSpeedLimit = packet.rideSpeedLimit
        ServerSettings.general.isWaterBreathingShared = packet.isWaterBreathingShared
        ServerSettings.restrictions.blacklistedDimensions = packet.blacklistedDimensions
        ServerSettings.speedStat.affectsSpeed = packet.affectsSpeed
        ServerSettings.speedStat.minStatThreshold = packet.minStatThreshold
        ServerSettings.speedStat.maxStatThreshold = packet.maxStatThreshold
        ServerSettings.speedStat.minSpeedModifier = packet.minSpeedModifier
        ServerSettings.speedStat.maxSpeedModifier = packet.maxSpeedModifier
        ServerSettings.sprinting.canSprint = packet.canSprint
        ServerSettings.sprinting.rideSprintSpeed = packet.rideSprintSpeed
        ServerSettings.sprinting.canSprintOnLand = packet.canSprintOnLand
        ServerSettings.sprinting.canSprintInWater = packet.canSprintInWater
        ServerSettings.sprinting.canSprintInAir = packet.canSprintInAir
        ServerSettings.sprinting.canExhaust = packet.canExhaust
        ServerSettings.sprinting.maxStamina = packet.maxStamina
        ServerSettings.sprinting.recoveryTime = packet.recoveryTime
        ServerSettings.sprinting.recoveryDelay = packet.recoveryDelay
        ServerSettings.sprinting.exhaustionSpeed = packet.exhaustionSpeed
        ServerSettings.sprinting.exhaustionDuration = packet.exhaustionDuration
    }
}