package net.starliteheart.cobbleride.common.net.messages.client.spawn

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.api.pokeball.PokeBalls
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.cobblemon.mod.common.entity.PoseType
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import com.cobblemon.mod.common.mixin.invoker.ClientPlayNetworkHandlerInvoker
import com.cobblemon.mod.common.net.messages.client.spawn.SpawnExtraDataEntityPacket
import com.cobblemon.mod.common.pokemon.Gender
import com.cobblemon.mod.common.util.*
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.protocol.PacketUtils
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.Vec3
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity
import net.starliteheart.cobbleride.common.mixin.accessor.SpawnExtraDataEntityPacketAccessor
import net.starliteheart.cobbleride.common.net.messages.client.pokemon.ai.ClientMoveBehaviour
import net.starliteheart.cobbleride.common.util.rideableResource
import java.util.*

class SpawnRidePokemonPacket(
    private val ownerId: UUID?,
    private val scaleModifier: Float,
    private val speciesId: ResourceLocation,
    private val gender: Gender,
    private val shiny: Boolean,
    private val formName: String,
    private val aspects: Set<String>,
    private val moveBehaviour: ClientMoveBehaviour,
    private val battleId: UUID?,
    private val phasingTargetId: Int,
    private val beamMode: Byte,
    private val nickname: MutableComponent?,
    private val labelLevel: Int,
    private val poseType: PoseType,
    private val unbattleable: Boolean,
    private val hideLabel: Boolean,
    private val caughtBall: ResourceLocation,
    private val spawnYaw: Float,
    private val friendship: Int,
    private val freezeFrame: Float,
    vanillaSpawnPacket: ClientboundAddEntityPacket
) : SpawnExtraDataEntityPacket<SpawnRidePokemonPacket, RideablePokemonEntity>(vanillaSpawnPacket) {

    override val id: ResourceLocation = ID

    constructor(entity: RideablePokemonEntity, vanillaSpawnPacket: ClientboundAddEntityPacket) : this(
        entity.ownerUUID,
        entity.pokemon.scaleModifier,
        entity.exposedSpecies.resourceIdentifier,
        entity.pokemon.gender,
        entity.pokemon.shiny,
        entity.exposedForm.formOnlyShowdownId(),
        entity.exposedAspects,
        ClientMoveBehaviour(entity.exposedForm.behaviour.moving),
        entity.battleId,
        entity.phasingTargetId,
        entity.beamMode.toByte(),
        entity.pokemon.nickname,
        if (Cobblemon.config.displayEntityLevelLabel) entity.entityData.get(PokemonEntity.LABEL_LEVEL) else -1,
        entity.entityData.get(PokemonEntity.POSE_TYPE),
        entity.entityData.get(PokemonEntity.UNBATTLEABLE),
        entity.entityData.get(PokemonEntity.HIDE_LABEL),
        entity.pokemon.caughtBall.name,
        entity.entityData.get(PokemonEntity.SPAWN_DIRECTION),
        entity.entityData.get(PokemonEntity.FRIENDSHIP),
        entity.entityData.get(PokemonEntity.FREEZE_FRAME),
        vanillaSpawnPacket
    )

    override fun encodeEntityData(buffer: RegistryFriendlyByteBuf) {
        buffer.writeNullable(ownerId) { _, v -> buffer.writeUUID(v) }
        buffer.writeFloat(this.scaleModifier)
        buffer.writeIdentifier(this.speciesId)
        buffer.writeEnumConstant(this.gender)
        buffer.writeBoolean(this.shiny)
        buffer.writeString(this.formName)
        buffer.writeCollection(this.aspects) { pb, value -> pb.writeString(value) }
        moveBehaviour.encode(buffer)
        buffer.writeNullable(this.battleId) { pb, value -> pb.writeUUID(value) }
        buffer.writeInt(this.phasingTargetId)
        buffer.writeByte(this.beamMode.toInt())
        buffer.writeNullable(this.nickname) { _, v -> buffer.writeText(v) }
        buffer.writeInt(this.labelLevel)
        buffer.writeEnumConstant(this.poseType)
        buffer.writeBoolean(this.unbattleable)
        buffer.writeBoolean(this.hideLabel)
        buffer.writeIdentifier(this.caughtBall)
        buffer.writeFloat(this.spawnYaw)
        buffer.writeInt(this.friendship)
        buffer.writeFloat(this.freezeFrame)
    }

    override fun applyData(entity: RideablePokemonEntity) {
        entity.ownerUUID = ownerId
        entity.pokemon.apply {
            scaleModifier = this@SpawnRidePokemonPacket.scaleModifier
            species = this@SpawnRidePokemonPacket.speciesId.let {
                PokemonSpecies.getByIdentifier(it) ?: PokemonSpecies.random()
            }
            gender = this@SpawnRidePokemonPacket.gender
            shiny = this@SpawnRidePokemonPacket.shiny
            form =
                this@SpawnRidePokemonPacket.formName.let { formName -> species.forms.find { it.formOnlyShowdownId() == formName } }
                    ?: species.standardForm
            forcedAspects = this@SpawnRidePokemonPacket.aspects
            nickname = this@SpawnRidePokemonPacket.nickname
            PokeBalls.getPokeBall(this@SpawnRidePokemonPacket.caughtBall)?.let { caughtBall = it }
        }
        entity.moveBehaviour = this.moveBehaviour
        entity.phasingTargetId = this.phasingTargetId
        entity.beamMode = this.beamMode.toInt()
        entity.battleId = this.battleId
        entity.entityData.set(PokemonEntity.LABEL_LEVEL, labelLevel)
        entity.entityData.set(PokemonEntity.SPECIES, entity.pokemon.species.resourceIdentifier.toString())
        entity.entityData.set(PokemonEntity.ASPECTS, aspects)
        entity.entityData.set(PokemonEntity.POSE_TYPE, poseType)
        entity.entityData.set(PokemonEntity.UNBATTLEABLE, unbattleable)
        entity.entityData.set(PokemonEntity.HIDE_LABEL, hideLabel)
        entity.entityData.set(PokemonEntity.SPAWN_DIRECTION, spawnYaw)
        entity.entityData.set(PokemonEntity.FRIENDSHIP, friendship)
        entity.entityData.set(PokemonEntity.FREEZE_FRAME, freezeFrame)
    }

    override fun checkType(entity: Entity): Boolean = entity is RideablePokemonEntity

    @Suppress("CAST_NEVER_SUCCEEDS")
    fun spawnRidePokemonAndApply(client: Minecraft) {
        client.execute {
            val player = client.player ?: return@execute
            val world = player.level() as? ClientLevel ?: return@execute
            // This is a copy pasta of ClientPlayNetworkHandler#onEntitySpawn
            // This exists due to us needing to do everything it does except spawn the entity in the world.
            // We invoke applyData then we add the entity to the world.
            val vanillaSpawnPacket = (this as SpawnExtraDataEntityPacketAccessor).vanillaSpawnPacket
            PacketUtils.ensureRunningOnSameThread(vanillaSpawnPacket, player.connection, client)
            val entity = RideablePokemonEntity(world)
            entity.recreateFromPacket(vanillaSpawnPacket)
            entity.deltaMovement = Vec3(
                vanillaSpawnPacket.xa,
                vanillaSpawnPacket.ya,
                vanillaSpawnPacket.za
            )
            // Cobblemon start
            if (this.checkType(entity)) {
                this.applyData(entity)
            }
            // Cobblemon end
            world.addEntity(entity)
            (player.connection as ClientPlayNetworkHandlerInvoker).callPlaySpawnSound(entity)
        }
    }

    companion object {
        val ID = rideableResource("spawn_ride_pokemon_entity")
        fun decode(buffer: RegistryFriendlyByteBuf): SpawnRidePokemonPacket {
            val ownerId = buffer.readNullable { buffer.readUUID() }
            val scaleModifier = buffer.readFloat()
            val speciesId = buffer.readIdentifier()
            val gender = buffer.readEnumConstant(Gender::class.java)
            val shiny = buffer.readBoolean()
            val formName = buffer.readString()
            val aspects = buffer.readList { it.readString() }.toSet()
            val moveBehaviour = ClientMoveBehaviour.decode(buffer)
            val battleId = buffer.readNullable { buffer.readUUID() }
            val phasingTargetId = buffer.readInt()
            val beamModeEmitter = buffer.readByte()
            val nickname = buffer.readNullable { buffer.readText().copy() }
            val labelLevel = buffer.readInt()
            val poseType = buffer.readEnumConstant(PoseType::class.java)
            val unbattleable = buffer.readBoolean()
            val hideLabel = buffer.readBoolean()
            val caughtBall = buffer.readIdentifier()
            val spawnAngle = buffer.readFloat()
            val friendship = buffer.readInt()
            val freezeFrame = buffer.readFloat()
            val vanillaPacket = decodeVanillaPacket(buffer)

            return SpawnRidePokemonPacket(
                ownerId,
                scaleModifier,
                speciesId,
                gender,
                shiny,
                formName,
                aspects,
                moveBehaviour,
                battleId,
                phasingTargetId,
                beamModeEmitter,
                nickname,
                labelLevel,
                poseType,
                unbattleable,
                hideLabel,
                caughtBall,
                spawnAngle,
                friendship,
                freezeFrame,
                vanillaPacket
            )
        }
    }
}