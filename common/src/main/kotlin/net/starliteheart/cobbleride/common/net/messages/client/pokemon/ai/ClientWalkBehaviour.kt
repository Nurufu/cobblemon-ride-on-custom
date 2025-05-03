package net.starliteheart.cobbleride.common.net.messages.client.pokemon.ai

import com.cobblemon.mod.common.api.molang.ExpressionLike
import com.cobblemon.mod.common.pokemon.ai.WalkBehaviour
import com.cobblemon.mod.common.util.asExpressionLike
import com.cobblemon.mod.common.util.readString
import com.cobblemon.mod.common.util.writeString
import net.minecraft.network.RegistryFriendlyByteBuf

class ClientWalkBehaviour(
    val canWalk: Boolean = true,
    val avoidsLand: Boolean = false,
    var walkSpeed: ExpressionLike = "0.35".asExpressionLike()
) {
    constructor(walkBehaviour: WalkBehaviour) : this(
        walkBehaviour.canWalk,
        walkBehaviour.avoidsLand,
        walkBehaviour.walkSpeed
    )

    fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeBoolean(this.canWalk)
        buffer.writeBoolean(this.avoidsLand)
        buffer.writeString(this.walkSpeed.getString())
    }

    companion object {
        fun decode(buffer: RegistryFriendlyByteBuf) = ClientWalkBehaviour(
            buffer.readBoolean(),
            buffer.readBoolean(),
            buffer.readString().asExpressionLike()
        )
    }
}