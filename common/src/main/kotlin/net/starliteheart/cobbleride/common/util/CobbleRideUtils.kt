package net.starliteheart.cobbleride.common.util

import com.cobblemon.mod.common.entity.npc.NPCEntity
import com.cobblemon.mod.common.util.EntityTraceResult
import com.cobblemon.mod.common.util.math.geometry.toDegrees
import com.cobblemon.mod.common.util.math.geometry.toRadians
import com.cobblemon.mod.common.util.traceEntityCollision
import com.cobblemon.mod.common.util.traceFirstEntityCollision
import net.minecraft.core.particles.SimpleParticleType
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.particle.DefaultParticleType
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Identifier
import net.minecraft.util.math.Box
import net.minecraft.util.math.Vec3d
import net.minecraft.world.RaycastContext
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.ClipContext
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.HitResult
import net.minecraft.world.phys.Vec3
import net.starliteheart.cobbleride.common.CobbleRideMod
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

fun rideableResource(path: String): Identifier = Identifier(CobbleRideMod.MOD_ID, path)

fun averageOfTwoRots(f1: Float, f2: Float): Float {
    val r1 = f1.toRadians().toDouble()
    val r2 = f2.toRadians().toDouble()
    val list = doubleArrayOf(r1, r2)
    val xSum = list.sumOf { cos(it) }
    val ySum = list.sumOf { sin(it) }
    return (atan2(ySum, xSum).toDegrees())
}

fun rotateVec3(offset: Vec3d, angle: Float): Vec3d {
    val r = angle.toRadians()
    val x = offset.x * cos(r) - offset.z * sin(r)
    val z = offset.x * sin(r) + offset.z * cos(r)
    return Vec3d(x, offset.y, z)
}

fun emitParticle(entity: Entity, particle: DefaultParticleType) {
    fun getRandomAngle(): Double {
        return entity.random.nextDouble() * 2 * Math.PI
    }

    val particleSpeed = entity.random.nextDouble()
    val particleAngle = getRandomAngle()
    val particleXSpeed = cos(particleAngle) * particleSpeed
    val particleYSpeed = sin(particleAngle) * particleSpeed

    if (entity.world is ServerWorld) {
        (entity.world as ServerWorld).spawnParticles(
            particle,
            entity.pos.x + cos(getRandomAngle()) * entity.boundingBox.maxX,
            entity.boundingBox.maxY,
            entity.pos.z + cos(getRandomAngle()) * entity.boundingBox.maxZ,
            1,     //Amount?
            particleXSpeed, 0.5, particleYSpeed,
            1.0   //Scale?
        )
    }
}

fun <T : Entity> PlayerEntity.traceEntityCollisionAndReturnRider(
    maxDistance: Float = 10F,
    stepDistance: Float = 0.05F,
    entityClass: Class<T>,
    ignoreEntity: T? = null,
    collideBlock: RaycastContext.FluidHandling?
): T? {
    val entity = this.traceFirstEntityCollision(maxDistance, stepDistance, entityClass, ignoreEntity)
    if (entity != null && entity.isVehicle && entity !is PlayerEntity && entity !is NPCEntity) {
        val list =
            if (this.vehicle != null) listOf(ignoreEntity, this.vehicle, entity) else listOf(ignoreEntity, entity)
        val nextClosest = traceEntityCollisionWithIgnoreList(
            maxDistance, stepDistance, entityClass, list, collideBlock
        )?.let { result -> result.entities.minByOrNull { it.distanceTo(this) } }
        if (nextClosest != null && entity.hasPassenger(nextClosest)) {
            return nextClosest
        }
    }
    return entity
}

fun <T : Entity> PlayerEntity.resolveTraceEntityCollision(
    maxDistance: Float = 10F,
    stepDistance: Float = 0.05F,
    entityClass: Class<T>,
    ignoreEntity: T? = null,
    collideBlock: RaycastContext.FluidHandling?
): EntityTraceResult<T>? {
    return if (this.vehicle != null) {
        val list = listOf(ignoreEntity, this.vehicle)
        traceEntityCollisionWithIgnoreList(maxDistance, stepDistance, entityClass, list, collideBlock)
    } else {
        traceEntityCollision(maxDistance, stepDistance, entityClass, ignoreEntity)
    }
}

/*
    Copied from Cobblemon's PlayerExtensions, modified to allow for multiple ignored entities
 */
fun <T : Entity> PlayerEntity.traceEntityCollisionWithIgnoreList(
    maxDistance: Float = 10F,
    stepDistance: Float = 0.05F,
    entityClass: Class<T>,
    ignoreEntities: List<Entity?> = listOf(),
    collideBlock: RaycastContext.FluidHandling?
): EntityTraceResult<T>? {
    var step = stepDistance
    val startPos = eyePosition
    val direction = lookAngle
    val maxDistanceVector = Vec3d(1.0, 1.0, 1.0).multiply(maxDistance.toDouble())

    val entities = world.getOtherEntities(
        null,
        Box(startPos.subtract(maxDistanceVector), startPos.add(maxDistanceVector))
    ) { entityClass.isInstance(it) }

    while (step <= maxDistance) {
        val location = startPos.add(direction.scale(step.toDouble()))
        step += stepDistance

        val collided = entities.filter {
            !ignoreEntities.contains(it) && location in it.boundingBox && entityClass.isInstance(it) && !it.isSpectator
        }

        if (collided.isNotEmpty()) {
            if (collideBlock != null && world.clip(
                    RaycastContext(
                        startPos,
                        location,
                        RaycastContext.ShapeType.COLLIDER,
                        collideBlock,
                        this
                    )
                ).type == HitResult.Type.BLOCK
            ) {
                // Collided with block on the way to the entity
                return null
            }
            return EntityTraceResult(location, collided.filterIsInstance(entityClass))
        }
    }

    return null
}