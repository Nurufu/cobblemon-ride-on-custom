package net.starliteheart.cobbleride.common

import com.cobblemon.mod.common.CobblemonNetwork
import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.cobblemon.mod.common.data.CobblemonDataProvider
import com.cobblemon.mod.common.platform.events.PlatformEvents
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import net.starliteheart.cobbleride.common.api.pokemon.RideablePokemonSpecies
import net.starliteheart.cobbleride.common.config.CobbleRideConfig
import net.starliteheart.cobbleride.common.entity.pokemon.RideablePokemonEntity
import net.starliteheart.cobbleride.common.net.messages.client.pokemon.ai.ClientMoveBehaviour
import net.starliteheart.cobbleride.common.net.messages.client.pokemon.sync.UpdatePokemonBehaviourPacket
import net.starliteheart.cobbleride.common.net.messages.client.settings.SendServerSettingsPacket
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.io.File
import java.io.FileReader
import java.io.FileWriter

object CobbleRideMod {
    const val MOD_ID = "cobbleride"
    var LOGGER: Logger = LogManager.getLogger()
    lateinit var config: CobbleRideConfig
    lateinit var implementation: CobbleRideModImpl

    fun initialize(implementation: CobbleRideModImpl) {
        this.implementation = implementation
        CobblemonDataProvider.register(RideablePokemonSpecies)
        PlatformEvents.CHANGE_DIMENSION.subscribe {
            if (it.player.vehicle is RideablePokemonEntity) {
                it.player.removeVehicle()
            }
        }
        PlatformEvents.SERVER_PLAYER_LOGOUT.subscribe {
            if (it.player.vehicle is RideablePokemonEntity) {
                it.player.removeVehicle()
            }
        }
        loadConfig()

        CobblemonEvents.DATA_SYNCHRONIZED.subscribe {
            SendServerSettingsPacket(
                this.config.general.globalBaseSpeedModifier,
                this.config.general.globalLandSpeedModifier,
                this.config.general.globalWaterSpeedModifier,
                this.config.general.globalAirSpeedModifier,
                this.config.general.underwaterSpeedModifier,
                this.config.general.waterVerticalClimbSpeed,
                this.config.general.airVerticalClimbSpeed,
                this.config.general.rideSpeedLimit,
                this.config.general.isWaterBreathingShared,
                this.config.restrictions.blacklistedDimensions,
                this.config.speedStat.affectsSpeed,
                this.config.speedStat.minStatThreshold,
                this.config.speedStat.maxStatThreshold,
                this.config.speedStat.minSpeedModifier,
                this.config.speedStat.maxSpeedModifier,
                this.config.sprinting.canSprint,
                this.config.sprinting.rideSprintSpeed,
                this.config.sprinting.canSprintOnLand,
                this.config.sprinting.canSprintInWater,
                this.config.sprinting.canSprintInAir,
                this.config.sprinting.canExhaust,
                this.config.sprinting.maxStamina,
                this.config.sprinting.recoveryTime,
                this.config.sprinting.recoveryDelay,
                this.config.sprinting.exhaustionSpeed,
                this.config.sprinting.exhaustionDuration
            ).sendToPlayer(it)
        }

        CobblemonEvents.EVOLUTION_COMPLETE.subscribe {
            val entity = it.pokemon.entity
            if (entity is RideablePokemonEntity) {
                val behaviour = ClientMoveBehaviour(entity.exposedForm.behaviour.moving)
                entity.moveBehaviour = behaviour
                CobblemonNetwork.sendToAllPlayers(UpdatePokemonBehaviourPacket(entity.id, behaviour))
            }
        }
    }

    /**
     * Chasem, who made CobblemonExtras, was nice enough to let me copy and modify their config loading and saving code. Be sure to support them!
     */
    private fun loadConfig() {
        val configFileLoc =
            System.getProperty("user.dir") + File.separator + "config" + File.separator + MOD_ID + File.separator + "config.json"
        LOGGER.info("Loading config file found at: $configFileLoc")
        val configFile = File(configFileLoc)
        configFile.parentFile.mkdirs()

        // Check config existence and load if it exists, otherwise create default.
        if (configFile.exists()) {
            try {
                val fileReader = FileReader(configFile)

                // Create a default config instance
                val defaultConfig = CobbleRideConfig()
                val defaultConfigJson: String = CobbleRideConfig.GSON.toJson(defaultConfig)


                val fileConfigElement: JsonElement = JsonParser.parseReader(fileReader)


                // Convert default config JSON string to JsonElement
                val defaultConfigElement: JsonElement = JsonParser.parseString(defaultConfigJson)


                // Merge default config with the file config
                val mergedConfigElement: JsonElement =
                    mergeConfigs(defaultConfigElement.getAsJsonObject(), fileConfigElement.getAsJsonObject())


                // Deserialize the merged JsonElement back to CobbleRideConfig
                val finalConfig: CobbleRideConfig =
                    CobbleRideConfig.GSON.fromJson(mergedConfigElement, CobbleRideConfig::class.java)

                CobbleRideConfig.validate(finalConfig)

                this.config = finalConfig

                fileReader.close()
            } catch (e: Exception) {
                LOGGER.error("Failed to load the config! Using default config as fallback.")
                e.printStackTrace()
                config = CobbleRideConfig()
            }
        } else {
            config = CobbleRideConfig()
        }
        saveConfig()
    }

    private fun mergeConfigs(defaultConfig: JsonObject, fileConfig: JsonObject): JsonElement {
        // For every entry in the default config, check if it exists in the file config
        LOGGER.info("Checking for config merge.")
        var merged = false
        for (key in defaultConfig.keySet()) {
            if (!fileConfig.has(key)) {
                // If the file config does not have the key, add it from the default config
                fileConfig.add(key, defaultConfig.get(key))
                LOGGER.info("$key not found in file config, adding from default.")
                merged = true
            } else {
                // If it's a nested object, recursively merge it
                if (defaultConfig.get(key).isJsonObject && fileConfig.get(key).isJsonObject) {
                    mergeConfigs(defaultConfig.getAsJsonObject(key), fileConfig.getAsJsonObject(key))
                }
            }
        }
        if (merged) {
            LOGGER.info("Successfully merged config!")
        }
        return fileConfig
    }

    private fun saveConfig() {
        try {
            val configFileLoc =
                System.getProperty("user.dir") + File.separator + "config" + File.separator + MOD_ID + File.separator + "config.json"
            LOGGER.info("Saving config to: $configFileLoc")
            val configFile = File(configFileLoc)
            val fileWriter = FileWriter(configFile)
            CobbleRideConfig.GSON.toJson(config, fileWriter)
            fileWriter.flush()
            fileWriter.close()
        } catch (e: java.lang.Exception) {
            LOGGER.error("Failed to save config!")
            e.printStackTrace()
        }
    }
}
