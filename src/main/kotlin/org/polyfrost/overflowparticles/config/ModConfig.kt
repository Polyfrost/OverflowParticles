package org.polyfrost.overflowparticles.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.data.*
import cc.polyfrost.oneconfig.internal.config.core.ConfigCore
import cc.polyfrost.oneconfig.utils.Notifications
import club.sk1er.patcher.config.OldPatcherConfig
import dev.isxander.particlesenhanced.config.ParticlesEnhancedConfig
import net.minecraft.client.particle.EntityFX
import org.polyfrost.overflowparticles.OverflowParticles
import org.polyfrost.overflowparticles.hook.EntityFXHook
import java.util.ArrayList

object ModConfig : Config(Mod(OverflowParticles.NAME, ModType.PVP, "/overflowparticles.svg"), "${OverflowParticles.MODID}.json") {

    var settings = Settings()

    var particles = HashMap<String, ParticleEntry>()

    var blockSetting = BlockParticleEntry()

    fun getConfig(entity: EntityFX?): ParticleConfig? {
        if (entity !is EntityFXHook) return null
        return getConfigByID(entity.`overflowParticles$getParticleID`())
    }

    fun getConfigByID(id: Int): ParticleConfig? {
        if (id == 2) return OverflowParticles.configs[1]
        if (id == 38) return OverflowParticles.configs[37]
        return OverflowParticles.configs[id]
    }

    override fun initialize() {
        super.initialize()
        val remove = HashMap<String, ParticleEntry>()
        for (i in particles) {
            if (!OverflowParticles.names.contains(i.key) || OverflowParticles.ignores.contains(i.value.getID())) remove[i.key] = i.value
        }
        particles.entries.removeAll(remove.entries)
        OverflowParticles.fillConfigs()
        val subMods = ArrayList<Mod>()
        for (i in OverflowParticles.configs) {
            i.value.initialize()
            subMods.add(i.value.mod)
        }
        MainConfig
        subMods.add(MainConfig.mod)
        ConfigCore.subMods[this.mod] = subMods

        val patcher = try {
            Class.forName("club.sk1er.patcher.config.OldPatcherConfig")
            true
        } catch (_: ClassNotFoundException) {
            false
        }

        var didAnythingForPatcher = false
        if (!settings.hasMigratedPatcherPt2CauseImStupid && patcher) {
            settings.hasMigratedPatcherPt2CauseImStupid = true
            if (OldPatcherConfig.disableBlockBreakParticles) {
                blockSetting.hideDigging = true
                didAnythingForPatcher = true
            }
            save()
        }

        if (!settings.hasMigratedPatcher && patcher) {
            if (OldPatcherConfig.cleanView) {
                settings.cleanView = OldPatcherConfig.cleanView
                didAnythingForPatcher = true
            }
            if (OldPatcherConfig.staticParticleColor) {
                settings.staticParticleColor = OldPatcherConfig.staticParticleColor
                didAnythingForPatcher = true
            }
            if (OldPatcherConfig.maxParticleLimit != 4000) {
                settings.maxParticleLimit = OldPatcherConfig.maxParticleLimit
                didAnythingForPatcher = true
            }
            settings.hasMigratedPatcher = true
            save()

            if (didAnythingForPatcher) {
                Notifications.INSTANCE.send("OverflowParticles", "Migrated Patcher settings replaced by OverflowParticles. Please check OverflowParticles's settings to make sure they are correct.")
            }
        }
        if (!settings.hasMigratedParticlesEnhanced) {
            try {
                val clazz = Class.forName("dev.isxander.particlesenhanced.config.ParticlesEnhancedConfig")
                var didAnything = false

                if (ParticlesEnhancedConfig.alwaysCrit) {
                    settings.alwaysCritical = true
                    ParticlesEnhancedConfig.alwaysCrit = false
                    didAnything = true
                }
                if (ParticlesEnhancedConfig.alwaysSharp) {
                    settings.alwaysSharp = true
                    ParticlesEnhancedConfig.alwaysSharp = false
                    didAnything = true
                }
                ParticlesEnhancedConfig.checkInvulnerable = settings.checkInvulnerable
                if (!ParticlesEnhancedConfig.fade) {
                    for (i in particles) {
                        i.value.fade = false
                    }
                    blockSetting.fade = false
                    didAnything = true
                }
                if (ParticlesEnhancedConfig.fadeOutStart != 0.5f) {
                    for (i in particles) {
                        i.value.fadeStart = ParticlesEnhancedConfig.fadeOutStart
                    }
                    blockSetting.fadeStart = ParticlesEnhancedConfig.fadeOutStart
                    didAnything = true
                }
                if (ParticlesEnhancedConfig.critMultiplier != 1) {
                    for (i in particles) {
                        if (i.key == "Critical") {
                            i.value.multiplier = ParticlesEnhancedConfig.critMultiplier.toFloat()
                            ParticlesEnhancedConfig.critMultiplier = 1
                            didAnything = true
                        }
                    }
                }
                if (ParticlesEnhancedConfig.sharpMultiplier != 1) {
                    for (i in particles) {
                        if (i.key == "Sharpness") {
                            i.value.multiplier = ParticlesEnhancedConfig.sharpMultiplier.toFloat()
                            ParticlesEnhancedConfig.sharpMultiplier = 1
                            didAnything = true
                        }
                    }
                }

                settings.hasMigratedParticlesEnhanced = true
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
                    Notifications.INSTANCE.send("OverflowParticles", "Migrated ParticlesEnhanced settings replaced by OverflowParticles. Please check OverflowParticles's settings to make sure they are correct. ParticlesEnhanced can now be deleted.")
                }
            } catch (_: ClassNotFoundException) {
            }
        }
    }

    override fun load() {
        super.load()
        OverflowParticles.fillConfigs()
        for (i in OverflowParticles.configs) {
            particles[i.value.name] ?: particles.put(i.value.name, ParticleEntry())
            i.value.enabled = particles[i.value.name]!!.active
        }
    }

    init {
        initialize()
    }

}
