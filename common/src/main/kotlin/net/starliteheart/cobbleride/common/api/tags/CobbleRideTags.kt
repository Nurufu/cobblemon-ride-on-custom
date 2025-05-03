package net.starliteheart.cobbleride.common.api.tags

import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.starliteheart.cobbleride.common.util.rideableResource

object CobbleRideTags {
    @JvmField
    val NO_MOUNT_ITEMS: TagKey<Item> = TagKey.create(Registries.ITEM, rideableResource("no_mount_items"))

    @JvmField
    val NO_MOUNT_BATTLE_ITEMS: TagKey<Item> = TagKey.create(Registries.ITEM, rideableResource("no_mount_battle_items"))
}