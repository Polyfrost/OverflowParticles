@file:Suppress("UnstableApiUsage", "PropertyName")

import dev.deftu.gradle.utils.GameSide

plugins {
    java
    kotlin("jvm")
    id("dev.deftu.gradle.multiversion")
    id("dev.deftu.gradle.tools")
    id("dev.deftu.gradle.tools.resources")
    id("dev.deftu.gradle.tools.bloom")
    id("dev.deftu.gradle.tools.shadow")
    id("dev.deftu.gradle.tools.minecraft.loom")
}

toolkitLoomHelper {
    // Adds OneConfig to our project
    useOneConfig {
        version = "1.0.0-alpha.48"
        loaderVersion = "1.1.0-alpha.35"

        usePolyMixin = true
        polyMixinVersion = "0.8.4+build.2"

        for (module in arrayOf("commands", "config-impl", "events", "hud", "internal", "ui")) {
            +module
        }
    }
    useMixinExtras("0.4.1")
    useDevAuth("1.2.1")

    // Removes the server configs from IntelliJ IDEA, leaving only client runs.
    // If you're developing a server-side mod, you can remove this line.
    disableRunConfigs(GameSide.SERVER)

    // Sets up our Mixin refmap naming
    if (!mcData.isNeoForge) {
        useMixinRefMap(modData.id)
    }

    // Adds the tweak class if we are building legacy version of forge as per the documentation (https://docs.polyfrost.org)
    if (mcData.isLegacyForge) {
        useForgeMixin(modData.id) // Configures the mixins if we are building for forge, useful for when we are dealing with cross-platform projects.
    }
}

// Configures the output directory for when building from the `src/resources` directory.
sourceSets {
    val dummy by creating
    main {
        dummy.compileClasspath += compileClasspath
        compileClasspath += dummy.output
        output.setResourcesDir(java.classesDirectory)
    }
}

// Adds the Polyfrost maven repository so that we can get the libraries necessary to develop the mod.
repositories {
    mavenLocal()
    maven("https://repo.polyfrost.org/releases")
    maven("https://repo.polyfrost.org/snapshots")
}

// Configures the libraries/dependencies for your mod.
dependencies {
    implementation("org.polyfrost.oneconfig:internal:1.0.0-alpha.48")
}