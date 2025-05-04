package net.starliteheart.cobbleride.common.net.serverhandling.pokemon.sync

import com.cobblemon.mod.common.CobblemonNetwork
import com.cobblemon.mod.common.api.net.ServerNetworkPacketHandler
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayerEntity
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity
import net.starliteheart.cobbleride.common.net.messages.client.pokemon.ai.ClientMoveBehaviour
import net.starliteheart.cobbleride.common.net.messages.client.pokemon.sync.UpdatePokemonBehaviourPacket
import net.starliteheart.cobbleride.common.net.messages.server.pokemon.sync.GetRidePokemonBehaviourPacket

class GetRidePokemonBehaviourHandler : ServerNetworkPacketHandler<GetRidePokemonBehaviourPacket> {

    override fun handle(packet: GetRidePokemonBehaviourPacket, server: MinecraftServer, player: ServerPlayerEntity) {
        val entity = player.serverWorld.getEntityById(packet.pokemonID)
        if (entity is RideablePokemonEntity) {
            val behaviour = ClientMoveBehaviour(entity.exposedForm.behaviour.moving)
            entity.moveBehaviour = behaviour
            CobblemonNetwork.sendPacketToPlayer(player, UpdatePokemonBehaviourPacket(packet.pokemonID, behaviour))
        }
    }
}