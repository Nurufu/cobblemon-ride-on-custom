package net.starliteheart.cobbleride.common.client.net.spawn

import com.cobblemon.mod.common.api.net.ClientNetworkPacketHandler
import net.minecraft.client.MinecraftClient
import net.starliteheart.cobbleride.common.net.messages.client.spawn.SpawnRidePokemonPacket

class SpawnRidePokemonHandler : ClientNetworkPacketHandler<SpawnRidePokemonPacket> {
    override fun handle(packet: SpawnRidePokemonPacket, client: MinecraftClient) {
        packet.spawnRidePokemonAndApply(client)
    }
}