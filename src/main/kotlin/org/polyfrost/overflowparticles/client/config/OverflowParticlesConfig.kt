package org.polyfrost.overflowparticles.client.config

//#if MC >= 1.16.5
//$$ import net.minecraft.core.particles.ParticleTypes
//#endif

import club.sk1er.patcher.config.OldPatcherConfig
import dev.isxander.particlesenhanced.config.ParticlesEnhancedConfig
import org.polyfrost.oneconfig.api.config.v1.Config
import org.polyfrost.oneconfig.api.config.v1.Tree
import org.polyfrost.oneconfig.api.config.v1.annotations.Include
import org.polyfrost.oneconfig.api.config.v1.annotations.Slider
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch
import org.polyfrost.oneconfig.api.config.v1.collect.impl.OneConfigCollector
import org.polyfrost.oneconfig.api.ui.v1.Notifications

object OverflowParticlesConfig : Config("overflowparticles.json", "overflowparticles.svg", "OverflowParticles", Category.COMBAT) {

    val maxParticleLimit: Int
        get() {
            //#if MC >= 1.12.2
            return modernMaxParticleLimit
            //#else
            //$$ return legacyMaxParticleLimit
            //#endif
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
    var isStaticParticleColor = true

    @Slider(
        title = "Max Particle Limit",
        description = "Stop additional particles from appearing when there are too many at once.",
        subcategory = "Features",
        min = 1f, max = 10_000f
    )
    var legacyMaxParticleLimit = 4000

    @Slider(
        title = "Particle Limit",
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

    @Include var hasMigratedPatcher = false
    @Include var hasMigratedParticlesEnhanced = false

    init {
        PerParticleConfigManager.fillConfigs()

        val collector = OneConfigCollector()
        var i = -1
        for (particle in PerParticleConfigManager.configs) {
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
                //#if MC >= 1.16.5
                //$$  if (particle.value.particleType == ParticleTypes.BLOCK) {
                //#else
                if (particle.value.id == 37) {
                    //#endif
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

        dirtyMigration()

        //#if MC >= 1.12.2
        //$$ hideIf("legacyMaxParticleLimit") { true }
        //#else
        hideIf("modernMaxParticleLimit") { true }
        //#endif
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
                PerParticleConfigManager.blockSetting.hideDigging = true
                didAnythingForPatcher = true
            }
            if (OldPatcherConfig.cleanView) {
                isCleanView = OldPatcherConfig.cleanView
                didAnythingForPatcher = true
            }
            if (OldPatcherConfig.staticParticleColor) {
                isStaticParticleColor = OldPatcherConfig.staticParticleColor
                didAnythingForPatcher = true
            }
            if (OldPatcherConfig.maxParticleLimit != 4000) {
                legacyMaxParticleLimit = OldPatcherConfig.maxParticleLimit
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
                    for (i in PerParticleConfigManager.configs) {
                        i.value.fade = false
                    }
                    PerParticleConfigManager.blockSetting.fade = false
                    didAnything = true
                }
                if (ParticlesEnhancedConfig.fadeOutStart != 0.5f) {
                    for (i in PerParticleConfigManager.configs) {
                        i.value.fadeStart = ParticlesEnhancedConfig.fadeOutStart
                    }
                    PerParticleConfigManager.blockSetting.fadeStart = ParticlesEnhancedConfig.fadeOutStart
                    didAnything = true
                }
                if (ParticlesEnhancedConfig.critMultiplier != 1) {
                    for (i in PerParticleConfigManager.configs) {
                        if (i.value.name == "Critical") {
                            i.value.multiplier = ParticlesEnhancedConfig.critMultiplier.toFloat()
                            ParticlesEnhancedConfig.critMultiplier = 1
                            didAnything = true
                        }
                    }
                }
                if (ParticlesEnhancedConfig.sharpMultiplier != 1) {
                    for (i in PerParticleConfigManager.configs) {
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
