package org.polyfrost.overflowparticles.client.config

import club.sk1er.patcher.config.OldPatcherConfig
import dev.isxander.particlesenhanced.config.ParticlesEnhancedConfig
import org.polyfrost.oneconfig.api.config.v1.Config
import org.polyfrost.oneconfig.api.config.v1.Tree
import org.polyfrost.oneconfig.api.config.v1.annotations.Include
import org.polyfrost.oneconfig.api.config.v1.annotations.Slider
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch
import org.polyfrost.oneconfig.api.config.v1.collect.impl.OneConfigCollector

//? if >=1.16.5 {
import net.minecraft.core.particles.ParticleTypes
//?}

object OverflowParticlesConfig : Config("overflowparticles.json", "", "OverflowParticles", Category.COMBAT) {
    @JvmStatic
    val maxParticleLimit: Int
        get() {
            //? if >=1.12.2 {
            return modernMaxParticleLimit
            //?} else {
            /*return legacyMaxParticleLimit
            *///?}
        }

    @Switch(
        title = "Clean View",
        description = "Stop rendering your own potion effect particles.",
        subcategory = "Features"
    )
    @JvmStatic
    var isCleanView = false

    @Switch(
        title = "Static Particle Color",
        description = "Disable particle lighting checks each frame.",
        subcategory = "Features"
    )
    @JvmStatic
    var isStaticParticleColor = false

    @Slider(
        title = "Max Particle Limit",
        description = "The maximum number of particles that can be rendered at once. Set to 0 to disable.",
        subcategory = "Features",
        min = 1f, max = 10_000f
    )
    var legacyMaxParticleLimit = 4000

    @Slider(
        title = "Max Particle Limit",
        description = "The maximum number of particles that can be rendered at once. Set to 0 to disable.",
        subcategory = "Features",
        min = 0f, max = 20_000f
    )
    var modernMaxParticleLimit = 16_384

    @Switch(
        title = "Particles No-Clip",
        description = "Allows particles to no clip through blocks by simply not checking for collisions.",
        subcategory = "Features"
    )
    var particleNoClip = false

    @Switch(
        title = "Always Show Critical",
        subcategory = "Hit Particle"
    )
    var alwaysCritical = false

    @Switch(
        title = "Always Show Sharpness",
        subcategory = "Hit Particle"
    )
    var alwaysSharp = false

    @Switch(
        title = "Check Invulnerability",
        subcategory = "Hit Particle"
    )
    var checkInvulnerable = false

    override fun initialize(byConfigManager: Boolean) {
        super.initialize(byConfigManager)
        PerParticleConfigManager.fillConfigs()

        val collector = OneConfigCollector()
        var i = -1
        for (particle in PerParticleConfigManager.configs) {
            i++
            try {
                val name =
                    //? if >=1.16.5 {
                    particle.key.toString()
                    //?} else {
                    /*"Particle${particle.key}"
                    *///?}
                val t: Tree = Tree.tree(name)
                t.addMetadata(mapOf(
                    "title" to particle.value.name,
                    "description" to "Settings for the ${particle.value.name} particle.",
                    "icon" to "",
                    "category" to "General",
                    "subcategory" to "Particles",
                    "canBeEnabled" to true,
                    "index" to i,
                    "collapsed" to true
                ))
                //? if >=1.16.5 {
                 if (particle.value.particleType == ParticleTypes.BLOCK) {
                //?} else {
                /*if (particle.value.id == 37) {
                *///?}
                    collector.handle(t, PerParticleConfigManager.blockSetting, 0)
                    //todo t.addDependency("hideMode", "hideRunning")
                } else {
                    collector.handle(t, particle.value, 0)
                    particle.value.handle(t)
                }
                tree.put(t)
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }

        //? if >=1.12.2 {
        hideIf("legacyMaxParticleLimit") { true }
        //?} else {
        /*hideIf("modernMaxParticleLimit") { true }
        *///?}
    }

}
