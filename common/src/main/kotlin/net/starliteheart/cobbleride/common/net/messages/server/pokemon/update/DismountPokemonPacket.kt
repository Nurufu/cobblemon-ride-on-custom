package net.starliteheart.cobbleride.common.net.messages.server.pokemon.update

import com.cobblemon.mod.common.api.net.NetworkPacket
import net.minecraft.network.PacketByteBuf
import net.starliteheart.cobbleride.common.util.rideableResource

class DismountPokemonPacket(
    val slot: Int
) : NetworkPacket<DismountPokemonPacket> {
    override val id = ID

    override fun encode(buffer: PacketByteBuf) {
        buffer.writeInt(slot)
    }

    companion object {
        val ID = rideableResource("dismount_pokemon")
        fun decode(buffer: PacketByteBuf) = DismountPokemonPacket(
            buffer.readInt()
        )
    }
}