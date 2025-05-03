package net.starliteheart.cobbleride.common.client

import com.cobblemon.mod.common.platform.events.PlatformEvents
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity
import net.starliteheart.cobbleride.common.net.messages.server.pokemon.sync.GetRidePokemonBehaviourPacket
import net.starliteheart.cobbleride.common.net.messages.server.pokemon.sync.GetRidePokemonPassengersPacket

object CobbleRideClient {
    fun initialize() {
        PlatformEvents.CLIENT_ENTITY_LOAD.subscribe {
            if (it.entity is RideablePokemonEntity) {
                GetRidePokemonPassengersPacket(it.entity.id).sendToServer()
                GetRidePokemonBehaviourPacket(it.entity.id).sendToServer()
            }
        }
    }
}
