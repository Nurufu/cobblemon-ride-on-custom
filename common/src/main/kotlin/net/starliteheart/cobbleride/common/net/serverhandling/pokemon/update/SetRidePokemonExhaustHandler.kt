package net.starliteheart.cobbleride.common.net.serverhandling.pokemon.update

import com.cobblemon.mod.common.api.net.ServerNetworkPacketHandler
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayerEntity
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity
import net.starliteheart.cobbleride.common.net.messages.server.pokemon.update.SetRidePokemonExhaustPacket

class SetRidePokemonExhaustHandler : ServerNetworkPacketHandler<SetRidePokemonExhaustPacket> {

    override fun handle(packet: SetRidePokemonExhaustPacket, server: MinecraftServer, player: ServerPlayerEntity) {
        val pokemon = player.serverWorld.getEntityById(packet.pokemonID)
        if (pokemon is RideablePokemonEntity) {
            pokemon.isExhausted = packet.bl
        }
    }
}