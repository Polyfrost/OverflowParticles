package org.polyfrost.overflowparticles.client.config

import club.sk1er.patcher.config.OldPatcherConfig
import dev.isxander.particlesenhanced.config.ParticlesEnhancedConfig
import org.apache.logging.log4j.LogManager
import org.polyfrost.oneconfig.api.config.v1.Config
import org.polyfrost.oneconfig.api.config.v1.Tree
import org.polyfrost.oneconfig.api.config.v1.annotations.Include
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch
import org.polyfrost.oneconfig.api.config.v1.collect.impl.OneConfigCollector
import org.polyfrost.overflowparticles.client.particles.VanillaParticles

//? if >=1.16.5 {
import net.minecraft.core.particles.ParticleTypes
//?}

object OverflowParticlesConfig : Config("overflowparticles.json", "/assets/overflowparticles/overflowparticles.svg", "OverflowParticles", Category.COMBAT) {
    private val LOGGER = LogManager.getLogger("OverflowParticles / Config")

    private const val CONFIG_VERSION = 1

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

    @Include
    var configVersion = 0

    override fun initialize(byConfigManager: Boolean) {
        super.initialize(byConfigManager)
        try {
            migrate()
        } catch (e: Throwable) {
            LOGGER.error("Failed to migrate config to version {}", CONFIG_VERSION, e)
        }
    }

    private fun migrate() {
        if (tree == null || configVersion >= CONFIG_VERSION) return

        if (configVersion < 1) {
            isStaticParticleColor = false
        }

        configVersion = CONFIG_VERSION
        save()
    }

    override fun makeTree(): Tree {
        val tree = super.makeTree()
        PerParticleConfigManager.fillConfigs()

        val collector = OneConfigCollector()
        val priorityTypes = listOf(VanillaParticles.CRITICAL.id, VanillaParticles.CRITICAL_MAGIC.id)
        val orderedConfigs = PerParticleConfigManager.configs.entries.sortedBy {
            val index = priorityTypes.indexOf(it.value.particleType)
            if (index >= 0) index else priorityTypes.size
        }
        var i = -1
        for (particle in orderedConfigs) {
            i++
            try {
                val name = particle.key.toString()
                val t: Tree = Tree.tree(name)
                t.addMetadata(mapOf(
                    "title" to particle.value.name,
                    "description" to "Settings for the ${particle.value.name} particle.",
                    "icon" to "",
                    "category" to "General",
                    "subcategory" to "Particles",
                    "canBeEnabled" to true,
                    "index" to i,
                    "collapsed" to (particle.value.particleType !in priorityTypes)
                ))
                //? if >=1.16.5 {
                 if (particle.value.particleType == ParticleTypes.BLOCK) {
                //?} else {
                /*if (particle.value.particleType == VanillaParticles.BLOCKS.id) {
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
        return tree
    }

}
