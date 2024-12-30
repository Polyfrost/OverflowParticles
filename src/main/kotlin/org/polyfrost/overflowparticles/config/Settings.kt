package org.polyfrost.overflowparticles.config

import club.sk1er.patcher.config.OldPatcherConfig
import dev.isxander.particlesenhanced.config.ParticlesEnhancedConfig
import org.polyfrost.oneconfig.api.config.v1.Config
import org.polyfrost.oneconfig.api.config.v1.Tree
import org.polyfrost.oneconfig.api.config.v1.annotations.Include
import org.polyfrost.oneconfig.api.config.v1.annotations.Slider
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch
import org.polyfrost.oneconfig.api.config.v1.collect.impl.OneConfigCollector
import org.polyfrost.oneconfig.api.ui.v1.Notifications

object Settings : Config("overflowparticles.json", "overflowparticles.svg", "OverflowParticles", Category.COMBAT) {

    @Switch(
        title = "Clean View",
        description = "Stop rendering your own potion effect particles.",
        subcategory = "Features"
    )
    var cleanView = false

    @Switch(
        title = "Static Particle Color",
        description = "Disable particle lighting checks each frame.",
        subcategory = "Features"
    )
    var staticParticleColor = true

    @Slider(
        title = "Max Particle Limit",
        description = "Stop additional particles from appearing when there are too many at once.",
        subcategory = "Features",
        min = 1f, max = 10000f
    )
    var maxParticleLimit = 4000

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

    @Include var hasMigratedPatcher = false
    @Include var hasMigratedParticlesEnhanced = false

    init {
        ConfigManager.fillConfigs()
        val collector = OneConfigCollector()
        var i = -1
        for (particle in ConfigManager.configs) {
            i++
            try {
                val t: Tree = Tree.tree("Particle${particle.key}")
                t.addMetadata(mapOf(
                    "title" to particle.value.name,
                    "description" to "Settings for the ${particle.value.name} particle.",
                    "icon" to "",
                    "category" to "General",
                    "subcategory" to "Particles",
                    "canBeEnabled" to true,
                    "index" to i
                ))
                if (particle.value.id == 37) {
                    collector.handle(t, ConfigManager.blockSetting, 0)
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

        dirtyMigration()
    }

    private fun dirtyMigration() {
        val patcher = try {
            Class.forName("club.sk1er.patcher.config.OldPatcherConfig")
            true
        } catch (_: ClassNotFoundException) {
            false
        }

        if (!hasMigratedPatcher && patcher) {
            var didAnythingForPatcher = false

            if (OldPatcherConfig.disableBlockBreakParticles) {
                ConfigManager.blockSetting.hideDigging = true
                didAnythingForPatcher = true
            }
            if (OldPatcherConfig.cleanView) {
                cleanView = OldPatcherConfig.cleanView
                didAnythingForPatcher = true
            }
            if (OldPatcherConfig.staticParticleColor) {
                staticParticleColor = OldPatcherConfig.staticParticleColor
                didAnythingForPatcher = true
            }
            if (OldPatcherConfig.maxParticleLimit != 4000) {
                maxParticleLimit = OldPatcherConfig.maxParticleLimit
                didAnythingForPatcher = true
            }
            hasMigratedPatcher = true
            save()

            if (didAnythingForPatcher) {
                Notifications.enqueue(Notifications.Type.Info, "OverflowParticles", "Migrated Patcher settings replaced by OverflowParticles. Please check OverflowParticles's settings to make sure they are correct.")
            }
        }

        if (!hasMigratedParticlesEnhanced) {
            try {
                val clazz = Class.forName("dev.isxander.particlesenhanced.config.ParticlesEnhancedConfig")
                var didAnything = false

                if (ParticlesEnhancedConfig.alwaysCrit) {
                    alwaysCritical = true
                    ParticlesEnhancedConfig.alwaysCrit = false
                    didAnything = true
                }
                if (ParticlesEnhancedConfig.alwaysSharp) {
                    alwaysSharp = true
                    ParticlesEnhancedConfig.alwaysSharp = false
                    didAnything = true
                }
                ParticlesEnhancedConfig.checkInvulnerable = checkInvulnerable
                if (!ParticlesEnhancedConfig.fade) {
                    for (i in ConfigManager.configs) {
                        i.value.fade = false
                    }
                    ConfigManager.blockSetting.fade = false
                    didAnything = true
                }
                if (ParticlesEnhancedConfig.fadeOutStart != 0.5f) {
                    for (i in ConfigManager.configs) {
                        i.value.fadeStart = ParticlesEnhancedConfig.fadeOutStart
                    }
                    ConfigManager.blockSetting.fadeStart = ParticlesEnhancedConfig.fadeOutStart
                    didAnything = true
                }
                if (ParticlesEnhancedConfig.critMultiplier != 1) {
                    for (i in ConfigManager.configs) {
                        if (i.value.name == "Critical") {
                            i.value.multiplier = ParticlesEnhancedConfig.critMultiplier.toFloat()
                            ParticlesEnhancedConfig.critMultiplier = 1
                            didAnything = true
                        }
                    }
                }
                if (ParticlesEnhancedConfig.sharpMultiplier != 1) {
                    for (i in ConfigManager.configs) {
                        if (i.value.name == "Sharpness") {
                            i.value.multiplier = ParticlesEnhancedConfig.sharpMultiplier.toFloat()
                            ParticlesEnhancedConfig.sharpMultiplier = 1
                            didAnything = true
                        }
                    }
                }

                hasMigratedParticlesEnhanced = true
                save()

                try {
                    val field = clazz.getDeclaredField("INSTANCE")
                    val instance = field.get(null)
                    if (clazz.superclass.name.equals("gg.essential.vigilance.Vigilant")) {
                        val markDirty = clazz.getDeclaredMethod("markDirty")
                        val writeData = clazz.getDeclaredMethod("writeData")
                        markDirty.invoke(instance)
                        writeData.invoke(instance)
                    } else if (clazz.superclass.name.equals("cc.polyfrost.oneconfig.config.Config")) {
                        val save = clazz.getDeclaredMethod("save")
                        save.invoke(instance)
                    }
                } catch (e: NoSuchFieldException) {
                    e.printStackTrace()
                } catch (e: NoSuchMethodException) {
                    e.printStackTrace()
                }

                if (didAnything) {
                    Notifications.enqueue(Notifications.Type.Info, "OverflowParticles", "Migrated ParticlesEnhanced settings replaced by OverflowParticles. Please check OverflowParticles's settings to make sure they are correct. ParticlesEnhanced can now be deleted.")
                }
            } catch (_: ClassNotFoundException) {
            }
        }
    }

}