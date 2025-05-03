package net.starliteheart.cobbleride.common.net.messages.client.pokemon.ai

import com.cobblemon.mod.common.api.molang.ExpressionLike
import com.cobblemon.mod.common.pokemon.ai.FlyBehaviour
import com.cobblemon.mod.common.util.asExpressionLike
import com.cobblemon.mod.common.util.readString
import com.cobblemon.mod.common.util.writeString
import net.minecraft.network.RegistryFriendlyByteBuf

class ClientFlyBehaviour(
    val canFly: Boolean = false,
    val flySpeedHorizontal: ExpressionLike = "0.3".asExpressionLike()
) {
    constructor(flyBehaviour: FlyBehaviour) : this(
        flyBehaviour.canFly,
        flyBehaviour.flySpeedHorizontal
    )

    fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeBoolean(this.canFly)
        buffer.writeString(this.flySpeedHorizontal.getString())
    }

    companion object {
        fun decode(buffer: RegistryFriendlyByteBuf) = ClientFlyBehaviour(
            buffer.readBoolean(),
            buffer.readString().asExpressionLike()
        )
    }
}