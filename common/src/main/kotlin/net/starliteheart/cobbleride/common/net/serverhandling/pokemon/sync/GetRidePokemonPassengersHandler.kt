package net.starliteheart.cobbleride.common.net.serverhandling.pokemon.sync

import com.cobblemon.mod.common.api.net.ServerNetworkPacketHandler
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity
import net.starliteheart.cobbleride.common.net.messages.server.pokemon.sync.GetRidePokemonPassengersPacket

class GetRidePokemonPassengersHandler : ServerNetworkPacketHandler<GetRidePokemonPassengersPacket> {

    override fun handle(packet: GetRidePokemonPassengersPacket, server: MinecraftServer, player: ServerPlayer) {
        val entity = player.serverLevel().getEntity(packet.pokemonID)
        if (entity is RideablePokemonEntity && entity.passengers.isNotEmpty())
            player.connection.send(ClientboundSetPassengersPacket(entity))
    }
}