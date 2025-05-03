package net.starliteheart.cobbleride.common.mixin.accessor;

import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = KeyMapping.class)
public interface KeyMappingAccessor {
    @Accessor
    boolean getIsDown();
}
