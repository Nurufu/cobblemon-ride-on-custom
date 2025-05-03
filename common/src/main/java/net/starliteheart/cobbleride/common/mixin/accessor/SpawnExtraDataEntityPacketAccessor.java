package net.starliteheart.cobbleride.common.mixin.accessor;

import com.cobblemon.mod.common.net.messages.client.spawn.SpawnExtraDataEntityPacket;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = SpawnExtraDataEntityPacket.class)
public interface SpawnExtraDataEntityPacketAccessor {
    @Accessor
    ClientboundAddEntityPacket getVanillaSpawnPacket();
}
