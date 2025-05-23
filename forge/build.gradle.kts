plugins {
    id("com.github.johnrengelman.shadow")
}

architectury {
    platformSetupLoomIde()
    forge()
}

loom {
    forge{}
    enableTransitiveAccessWideners.set(true)
    silentMojangMappingsLicense()
}

val shadowBundle = configurations.create("shadowBundle")

repositories {
    maven("https://hub.spigotmc.org/nexus/content/groups/public/")
    maven("https://thedarkcolour.github.io/KotlinForForge/")
    maven("https://maven.neoforged.net")
    mavenLocal()
}

dependencies {
    minecraft("net.minecraft:minecraft:${rootProject.property("minecraft_version")}")
    mappings(loom.officialMojangMappings())
    implementation(project(":common", configuration = "namedElements")) {
        isTransitive = false
    }
    "developmentForge"(project(":common", configuration = "namedElements")) {
        isTransitive = false
    }
    shadowBundle(project(":common", configuration = "transformProductionForge")) {
        isTransitive = false
    }

    forge("net.forge:forge:${rootProject.property("forge_version")}")

    modImplementation("com.cobblemon:forge:${rootProject.property("cobblemon_version")}")
    //Needed for cobblemon
    implementation("thedarkcolour:kotlinforforge-neoforge:${rootProject.property("kotlinforforge_version")}") {
        exclude("net.forge.fancymodloader", "loader")
    }

    modCompileOnly("local.com.swordend:pet-your-cobblemon:1.3.3")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
}

tasks {
    processResources {
        inputs.property("mod_version", project.version)
        inputs.property("minecraft_version", rootProject.property("minecraft_version"))
        inputs.property("cobblemon_version", rootProject.property("cobblemon_version"))
        filesMatching("META-INF/forge.mods.toml") {
            expand(
                "mod_version" to project.version,
                "minecraft_version" to rootProject.property("minecraft_version"),
                "cobblemon_version" to rootProject.property("cobblemon_version")
            )
        }
    }

    shadowJar {
        archiveClassifier.set("dev-shadow")
        configurations = listOf(shadowBundle)
    }

    remapJar {
        dependsOn(shadowJar)
        inputFile.set(shadowJar.flatMap { it.archiveFile })
    }
}
