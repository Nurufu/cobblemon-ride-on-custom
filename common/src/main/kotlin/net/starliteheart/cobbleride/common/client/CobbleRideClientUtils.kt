package net.starliteheart.cobbleride.common.client

import com.cobblemon.mod.common.api.gui.blitk
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.resources.ResourceLocation

fun blitRideIcon(
    matrices: PoseStack,
    texture: ResourceLocation,
    x: Number,
    y: Number,
    width: Number,
    height: Number,
    scale: Float
) {
    blitk(
        matrixStack = matrices,
        texture = texture,
        x = x,
        y = y,
        width = width,
        height = height,
        scale = scale
    )
}