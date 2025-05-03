plugins {
    id("java")
    id("java-library")
    kotlin("jvm") version ("1.9.23")

    id("dev.architectury.loom") version ("1.7-SNAPSHOT") apply false
    id("architectury-plugin") version ("3.4-SNAPSHOT")
    id("com.github.johnrengelman.shadow") version ("8.1.1") apply false
}

architectury {
    minecraft = "${rootProject.property("minecraft_version")}"
}

allprojects {
    group = "${rootProject.property("maven_group")}"
    version = "${rootProject.property("mod_version")}+${rootProject.property("minecraft_version")}${
        if (rootProject.property("is_snapshot") == "true") {
            "-SNAPSHOT"
        } else {
            ""
        }
    }"
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "dev.architectury.loom")
    apply(plugin = "architectury-plugin")

    base {
        // Set up a suffixed format for the mod jar names, e.g. `example-fabric`.
        archivesName = "${rootProject.property("archives_name")}-${project.name}"
    }

    repositories {
        mavenCentral()
        maven(url = "https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
        maven("https://maven.impactdev.net/repository/development/")
    }
}
