package net.starliteheart.cobbleride.common.net.messages.server.pokemon.update

import com.cobblemon.mod.common.api.net.NetworkPacket
import net.minecraft.network.RegistryFriendlyByteBuf
import net.starliteheart.cobbleride.common.util.rideableResource

class SetRidePokemonExhaustPacket(
    val pokemonID: Int,
    val bl: Boolean
) : NetworkPacket<SetRidePokemonExhaustPacket> {
    override val id = ID

    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeInt(pokemonID)
        buffer.writeBoolean(bl)
    }

    companion object {
        val ID = rideableResource("set_ride_state")
        fun decode(buffer: RegistryFriendlyByteBuf) = SetRidePokemonExhaustPacket(
            buffer.readInt(), buffer.readBoolean()
        )
    }
}