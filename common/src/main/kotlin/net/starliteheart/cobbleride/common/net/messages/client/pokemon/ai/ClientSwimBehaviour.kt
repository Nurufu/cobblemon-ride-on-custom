package net.starliteheart.cobbleride.common.net.messages.client.pokemon.ai

import com.cobblemon.mod.common.api.molang.ExpressionLike
import com.cobblemon.mod.common.pokemon.ai.SwimBehaviour
import com.cobblemon.mod.common.util.asExpressionLike
import com.cobblemon.mod.common.util.readString
import com.cobblemon.mod.common.util.writeString
import net.minecraft.network.RegistryFriendlyByteBuf

class ClientSwimBehaviour(
    val avoidsWater: Boolean = false,
    val hurtByLava: Boolean = true,
    val canSwimInWater: Boolean = true,
    val canSwimInLava: Boolean = true,
    val swimSpeed: ExpressionLike = "0.3".asExpressionLike(),
    val canBreatheUnderwater: Boolean = false,
    val canBreatheUnderlava: Boolean = false,
    val canWalkOnWater: Boolean = false,
    val canWalkOnLava: Boolean = false
) {
    constructor(swimBehaviour: SwimBehaviour) : this(
        swimBehaviour.avoidsWater,
        swimBehaviour.hurtByLava,
        swimBehaviour.canSwimInWater,
        swimBehaviour.canSwimInLava,
        swimBehaviour.swimSpeed,
        swimBehaviour.canBreatheUnderwater,
        swimBehaviour.canBreatheUnderlava,
        swimBehaviour.canWalkOnWater,
        swimBehaviour.canWalkOnLava
    )

    fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeBoolean(this.avoidsWater)
        buffer.writeBoolean(this.hurtByLava)
        buffer.writeBoolean(this.canSwimInWater)
        buffer.writeBoolean(this.canSwimInLava)
        buffer.writeString(this.swimSpeed.getString())
        buffer.writeBoolean(this.canBreatheUnderwater)
        buffer.writeBoolean(this.canBreatheUnderlava)
        buffer.writeBoolean(this.canWalkOnWater)
        buffer.writeBoolean(this.canWalkOnLava)
    }

    companion object {
        fun decode(buffer: RegistryFriendlyByteBuf) = ClientSwimBehaviour(
            buffer.readBoolean(),
            buffer.readBoolean(),
            buffer.readBoolean(),
            buffer.readBoolean(),
            buffer.readString().asExpressionLike(),
            buffer.readBoolean(),
            buffer.readBoolean(),
            buffer.readBoolean(),
            buffer.readBoolean()
        )
    }
}