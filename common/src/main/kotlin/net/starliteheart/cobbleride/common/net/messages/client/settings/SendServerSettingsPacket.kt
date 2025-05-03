package net.starliteheart.cobbleride.common.net.messages.client.settings

import com.cobblemon.mod.common.api.net.NetworkPacket
import com.cobblemon.mod.common.util.readString
import com.cobblemon.mod.common.util.writeString
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.starliteheart.cobbleride.common.CobbleRideMod
import net.starliteheart.cobbleride.common.util.rideableResource

class SendServerSettingsPacket internal constructor(
    val globalBaseSpeedModifier: Double,
    val globalLandSpeedModifier: Double,
    val globalWaterSpeedModifier: Double,
    val globalAirSpeedModifier: Double,
    val underwaterSpeedModifier: Double,
    val waterVerticalClimbSpeed: Double,
    val airVerticalClimbSpeed: Double,
    val rideSpeedLimit: Double,
    val isWaterBreathingShared: Boolean,
    val blacklistedDimensions: List<String>,
    val affectsSpeed: Boolean,
    val minStatThreshold: Int,
    val maxStatThreshold: Int,
    val minSpeedModifier: Double,
    val maxSpeedModifier: Double,
    val canSprint: Boolean,
    val rideSprintSpeed: Double,
    val canSprintOnLand: Boolean,
    val canSprintInWater: Boolean,
    val canSprintInAir: Boolean,
    val canExhaust: Boolean,
    val maxStamina: Int,
    val recoveryTime: Int,
    val recoveryDelay: Int,
    val exhaustionSpeed: Double,
    val exhaustionDuration: Double
) : NetworkPacket<SendServerSettingsPacket> {
    override val id: ResourceLocation = ID

    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeDouble(CobbleRideMod.config.general.globalBaseSpeedModifier)
        buffer.writeDouble(CobbleRideMod.config.general.globalLandSpeedModifier)
        buffer.writeDouble(CobbleRideMod.config.general.globalWaterSpeedModifier)
        buffer.writeDouble(CobbleRideMod.config.general.globalAirSpeedModifier)
        buffer.writeDouble(CobbleRideMod.config.general.underwaterSpeedModifier)
        buffer.writeDouble(CobbleRideMod.config.general.waterVerticalClimbSpeed)
        buffer.writeDouble(CobbleRideMod.config.general.airVerticalClimbSpeed)
        buffer.writeDouble(CobbleRideMod.config.general.rideSpeedLimit)
        buffer.writeBoolean(CobbleRideMod.config.general.isWaterBreathingShared)
        buffer.writeCollection(CobbleRideMod.config.restrictions.blacklistedDimensions) { pb, value ->
            pb.writeString(
                value
            )
        }
        buffer.writeBoolean(CobbleRideMod.config.speedStat.affectsSpeed)
        buffer.writeInt(CobbleRideMod.config.speedStat.minStatThreshold)
        buffer.writeInt(CobbleRideMod.config.speedStat.maxStatThreshold)
        buffer.writeDouble(CobbleRideMod.config.speedStat.minSpeedModifier)
        buffer.writeDouble(CobbleRideMod.config.speedStat.maxSpeedModifier)
        buffer.writeBoolean(CobbleRideMod.config.sprinting.canSprint)
        buffer.writeDouble(CobbleRideMod.config.sprinting.rideSprintSpeed)
        buffer.writeBoolean(CobbleRideMod.config.sprinting.canSprintOnLand)
        buffer.writeBoolean(CobbleRideMod.config.sprinting.canSprintInWater)
        buffer.writeBoolean(CobbleRideMod.config.sprinting.canSprintInAir)
        buffer.writeBoolean(CobbleRideMod.config.sprinting.canExhaust)
        buffer.writeInt(CobbleRideMod.config.sprinting.maxStamina)
        buffer.writeInt(CobbleRideMod.config.sprinting.recoveryTime)
        buffer.writeInt(CobbleRideMod.config.sprinting.recoveryDelay)
        buffer.writeDouble(CobbleRideMod.config.sprinting.exhaustionSpeed)
        buffer.writeDouble(CobbleRideMod.config.sprinting.exhaustionDuration)
    }

    companion object {
        val ID = rideableResource("server_settings")
        fun decode(buffer: RegistryFriendlyByteBuf) = SendServerSettingsPacket(
            buffer.readDouble(),
            buffer.readDouble(),
            buffer.readDouble(),
            buffer.readDouble(),
            buffer.readDouble(),
            buffer.readDouble(),
            buffer.readDouble(),
            buffer.readDouble(),
            buffer.readBoolean(),
            buffer.readList { it.readString() },
            buffer.readBoolean(),
            buffer.readInt(),
            buffer.readInt(),
            buffer.readDouble(),
            buffer.readDouble(),
            buffer.readBoolean(),
            buffer.readDouble(),
            buffer.readBoolean(),
            buffer.readBoolean(),
            buffer.readBoolean(),
            buffer.readBoolean(),
            buffer.readInt(),
            buffer.readInt(),
            buffer.readInt(),
            buffer.readDouble(),
            buffer.readDouble()
        )
    }
}