import org.jetbrains.kotlin.gradle.targets.js.yarn.yarn

architectury {
    common("forge", "fabric")
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    minecraft("net.minecraft:minecraft:${rootProject.property("minecraft_version")}")
    mappings("net.fabricmc:yarn:${rootProject.property("yarn_mappings")}")
    modCompileOnly("com.cobblemon:mod:${rootProject.property("cobblemon_version")}")
    compileOnly("io.github.llamalad7:mixinextras-common:0.4.1")
    // We depend on Fabric Loader here to use the Fabric @Environment annotations,
    // which get remapped to the correct annotations on each platform.
    // Do NOT use other classes from Fabric Loader.
    modImplementation("net.fabricmc:fabric-loader:${rootProject.property("fabric_loader_version")}")
    modCompileOnly("local.com.bedrockk:molang:1.1.11")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
}
