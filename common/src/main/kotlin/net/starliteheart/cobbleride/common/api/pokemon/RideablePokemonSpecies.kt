package net.starliteheart.cobbleride.common.api.pokemon

import com.cobblemon.mod.common.api.data.JsonDataRegistry
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.cobblemon.mod.common.api.reactive.SimpleObservable
import com.cobblemon.mod.common.util.adapters.VerboseVec3dAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.world.phys.Vec3
import net.starliteheart.cobbleride.common.CobbleRideMod
import net.starliteheart.cobbleride.common.net.messages.client.data.RideableSpeciesRegistrySyncPacket
import net.starliteheart.cobbleride.common.pokemon.RideableSpecies
import net.starliteheart.cobbleride.common.util.rideableResource

object RideablePokemonSpecies : JsonDataRegistry<RideableSpecies> {
    override val id: ResourceLocation = rideableResource("rideable_species")
    override val type: PackType = PackType.SERVER_DATA

    override val gson: Gson = GsonBuilder()
        .disableHtmlEscaping()
        .registerTypeAdapter(Vec3::class.java, VerboseVec3dAdapter)
        .create()

    override val typeToken: TypeToken<RideableSpecies> = TypeToken.get(RideableSpecies::class.java)

    override val resourcePath: String = "rideable_species"

    override val observable = SimpleObservable<RideablePokemonSpecies>()

    private val speciesByIdentifier = hashMapOf<ResourceLocation, RideableSpecies>()

    private val species: Collection<RideableSpecies>
        get() = speciesByIdentifier.values

    init {
        PokemonSpecies.observable.subscribe {
            species.forEach(RideableSpecies::initialize)
        }
    }

    fun getByIdentifier(identifier: ResourceLocation) = speciesByIdentifier[identifier]

    fun getByName(name: String) = getByIdentifier(rideableResource(name))

    override fun reload(data: Map<ResourceLocation, RideableSpecies>) {
        speciesByIdentifier.clear()
        data.forEach { (identifier, species) ->
            species.identifier = identifier
            speciesByIdentifier[identifier] = species
        }

        CobbleRideMod.LOGGER.info("Loaded {} Pokémon species with ride details", species.size)
        observable.emit(this)
    }

    override fun sync(player: ServerPlayer) {
        RideableSpeciesRegistrySyncPacket(species.toList()).sendToPlayer(player)
    }
}