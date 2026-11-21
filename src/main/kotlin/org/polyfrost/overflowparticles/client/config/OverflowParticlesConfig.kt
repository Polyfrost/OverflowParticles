package org.polyfrost.overflowparticles.client.config

import club.sk1er.patcher.config.OldPatcherConfig
import dev.isxander.particlesenhanced.config.ParticlesEnhancedConfig
import org.apache.logging.log4j.LogManager
import org.polyfrost.oneconfig.api.config.v1.Config
import org.polyfrost.oneconfig.api.config.v1.ENABLE_TOGGLE_KEY
import org.polyfrost.oneconfig.api.config.v1.MetaKeys
import org.polyfrost.oneconfig.api.config.v1.Tree
import org.polyfrost.oneconfig.api.config.v1.Trees
import org.polyfrost.oneconfig.api.config.v1.annotations.Dropdown
import org.polyfrost.oneconfig.api.config.v1.annotations.Include
import org.polyfrost.oneconfig.api.config.v1.annotations.Slider
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch
import org.polyfrost.oneconfig.api.config.v1.disableAllUnless
import org.polyfrost.oneconfig.api.config.v1.set
import org.polyfrost.overflowparticles.client.particles.VanillaParticles

//? if >=1.16.5 {
import net.minecraft.core.particles.ParticleTypes
//?}

object OverflowParticlesConfig : Config("overflowparticles.json", "/assets/overflowparticles/overflowparticles.svg", "OverflowParticles", Category.COMBAT) {
    private val LOGGER = LogManager.getLogger("OverflowParticles / Config")

    private const val CONFIG_VERSION = 3

    const val INVULNERABILITY_OFF = 0
    const val INVULNERABILITY_DAMAGEABLE = 1
    const val INVULNERABILITY_DAMAGED = 2

    @Switch(title = "Global Mod Toggle", description = "Toggle all mod features on or off.")
    @JvmStatic
    var modEnabled = true

    @Switch(
        title = "Clean View",
        description = "Stop rendering your own potion effect particles.",
        subcategory = "Features"
    )
    @JvmStatic
    var isCleanView = true

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
        title = "Particle Shield",
        description = "Remove particles that get too close to your eyes.",
        subcategory = "Particle Shield"
    )
    @JvmStatic
    var particleShield = false

    @Slider(
        title = "Shield Size",
        description = "Minimum distance in blocks between your eyes and any visible particle.",
        subcategory = "Particle Shield",
        min = 0f, max = 5f, step = 0.1f
    )
    @JvmStatic
    var shieldSize = 1.4f

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

    @Dropdown(
        title = "Check Invulnerability",
        description = "Off: particles on every hit. Skip Undamageable: no particles on entities that have never taken damage, like holograms. Only When Damaged: particles only on hits that deal damage.",
        subcategory = "Hit Particle",
        options = ["Off", "Skip Undamageable", "Only When Damaged"]
    )
    var invulnerabilityCheck = INVULNERABILITY_OFF

    @Include
    var checkInvulnerable = false

    @Include
    var configVersion = 0

    override fun initialize(byConfigManager: Boolean) {
        super.initialize(byConfigManager)
        if (tree != null) disableAllUnless("modEnabled")
        try {
            migrate()
        } catch (e: Throwable) {
            LOGGER.error("Failed to migrate config to version {}", CONFIG_VERSION, e)
        }
    }

    // The config file is shared between game versions, and OneConfig re-adds saved keys this version doesn't
    // register. Hide those keys from search but keep them, so the other version's settings survive a save.
    override fun onInitialized(tree: Tree) {
        val known = PerParticleConfigManager.configs.keys.mapTo(HashSet()) { it.toString() }
        for ((key, node) in tree.map) {
            if (node is Tree && ':' in key && !key.startsWith("reserved:") && key !in known) {
                node[MetaKeys.HIDDEN] = true
            }
        }
    }

    private fun migrate() {
        if (tree == null || configVersion >= CONFIG_VERSION) return

        if (configVersion < 1) {
            isStaticParticleColor = false
        }

        if (configVersion < 2) {
            isCleanView = true
        }

        if (configVersion < 3 && checkInvulnerable) {
            invulnerabilityCheck = INVULNERABILITY_DAMAGED
        }

        configVersion = CONFIG_VERSION
        save()
    }

    override fun makeTree(): Tree {
        val tree = super.makeTree()
        PerParticleConfigManager.fillConfigs()

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
                //? if >=1.16.5 {
                 val t: Tree = if (particle.value.particleType == ParticleTypes.BLOCK) {
                //?} else {
                /*val t: Tree = if (particle.value.particleType == VanillaParticles.BLOCKS.id) {
                *///?}
                    Trees.fromObject(PerParticleConfigManager.blockSetting, name)
                    //todo t.addDependency("hideMode", "hideRunning")
                } else {
                    Trees.fromObject(particle.value, name).also(particle.value::handle)
                }
                t[MetaKeys.TITLE] = particle.value.name
                t[MetaKeys.DESCRIPTION] = "Settings for the ${particle.value.name} particle."
                t[MetaKeys.ICON] = particle.value.icon()
                t[MetaKeys.CATEGORY] = "General"
                t[MetaKeys.SUBCATEGORY] = "Particles"
                t[MetaKeys.ORDER] = i
                t[MetaKeys.COLLAPSED] = particle.value.particleType !in priorityTypes
                t.getProp("enabled")?.let { toggle ->
                    toggle[MetaKeys.TITLE] = particle.value.name
                    t.addMetadata(ENABLE_TOGGLE_KEY, toggle)
                    t.disableAllUnless("enabled")
                }
                tree.put(t)
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
        return tree
    }

}
