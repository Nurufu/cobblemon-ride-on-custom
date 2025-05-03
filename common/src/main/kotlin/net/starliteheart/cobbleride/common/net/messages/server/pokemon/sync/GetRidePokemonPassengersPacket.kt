package net.starliteheart.cobbleride.common.net.messages.server.pokemon.sync

import com.cobblemon.mod.common.api.net.NetworkPacket
import net.minecraft.network.RegistryFriendlyByteBuf
import net.starliteheart.cobbleride.common.util.rideableResource

class GetRidePokemonPassengersPacket(
    val pokemonID: Int,
) : NetworkPacket<GetRidePokemonPassengersPacket> {
    override val id = ID

    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeInt(pokemonID)
    }

    companion object {
        val ID = rideableResource("get_ride_passengers")
        fun decode(buffer: RegistryFriendlyByteBuf) = GetRidePokemonPassengersPacket(
            buffer.readInt()
        )
    }
}