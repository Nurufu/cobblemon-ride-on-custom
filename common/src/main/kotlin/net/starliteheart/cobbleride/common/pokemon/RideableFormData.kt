package net.starliteheart.cobbleride.common.pokemon

import com.cobblemon.mod.common.api.data.ShowdownIdentifiable
import com.cobblemon.mod.common.api.net.Decodable
import com.cobblemon.mod.common.api.net.Encodable
import com.cobblemon.mod.common.util.readEnumConstant
import com.cobblemon.mod.common.util.readString
import com.cobblemon.mod.common.util.writeEnumConstant
import com.cobblemon.mod.common.util.writeString
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.world.phys.Vec3
import java.util.*

class RideableFormData(
    var name: String = "Normal",
    var enabled: Boolean = true,
    var offsets: EnumMap<RiderOffsetType, Vec3> = EnumMap(RiderOffsetType::class.java),
    var shouldRiderSit: Boolean = true,
    var baseSpeedModifier: Float = 1.0F,
    var landSpeedModifier: Float = 1.0F,
    var waterSpeedModifier: Float = 1.0F,
    var airSpeedModifier: Float = 1.0F
) : Encodable, Decodable, ShowdownIdentifiable {
    @Transient
    lateinit var species: RideableSpecies

    fun initialize(species: RideableSpecies): RideableFormData {
        this.species = species
        return this
    }

    override fun equals(other: Any?): Boolean = other is RideableFormData && other.showdownId() == this.showdownId()

    override fun hashCode(): Int = this.showdownId().hashCode()

    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeString(this.name)
        buffer.writeBoolean(this.enabled)
        buffer.writeMap(this.offsets, { _, k -> buffer.writeEnumConstant(k) }, { _, v -> buffer.writeVec3(v) })
        buffer.writeBoolean(this.shouldRiderSit)
        buffer.writeFloat(this.baseSpeedModifier)
        buffer.writeFloat(this.landSpeedModifier)
        buffer.writeFloat(this.waterSpeedModifier)
        buffer.writeFloat(this.airSpeedModifier)
    }

    override fun decode(buffer: RegistryFriendlyByteBuf) {
        this.name = buffer.readString()
        this.enabled = buffer.readBoolean()
        this.offsets.clear()
        this.offsets += buffer.readMap(
            { _ -> buffer.readEnumConstant(RiderOffsetType::class.java) },
            { _ -> buffer.readVec3() })
        this.shouldRiderSit = buffer.readBoolean()
        this.baseSpeedModifier = buffer.readFloat()
        this.landSpeedModifier = buffer.readFloat()
        this.waterSpeedModifier = buffer.readFloat()
        this.airSpeedModifier = buffer.readFloat()
    }

    override fun showdownId(): String = this.species.showdownId() + this.formOnlyShowdownId()

    private fun formOnlyShowdownId(): String = Regex("[^a-z0-9]+").replace(this.name.lowercase(), "")

}