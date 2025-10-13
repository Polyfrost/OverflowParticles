package org.polyfrost.overflowparticles.client.config

import net.minecraft.client.particle.EntityFX
import org.polyfrost.overflowparticles.client.particles.ParticleInfo
import org.polyfrost.overflowparticles.client.particles.ParticleRegistry
import org.polyfrost.overflowparticles.utils.ParticleIdentifier

object PerParticleConfigManager {
    @JvmStatic var blockSetting = BlockParticleEntry()
    @JvmStatic var configs = HashMap<Int, ParticleConfig>()

    fun fillConfigs() {
        if (configs.isNotEmpty()) {
            return
        }

        for ((id, type) in ParticleRegistry.registry) {
            if (type.isIgnored) {
                continue
            }

            configs[id] = ParticleConfig(type.name, id)
        }
    }

    @JvmStatic
    fun getConfig(entity: EntityFX?): ParticleConfig? {
        if (entity !is ParticleIdentifier) {
            return null
        }

        return getConfigById(entity.getId())
    }

    @JvmStatic
    fun getConfigByType(info: ParticleInfo): ParticleConfig {
        return getConfigById(info.id) ?: throw IllegalArgumentException("No config found for particle type: ${info.name} (ID: ${info.id})")
    }

    private fun getConfigById(id: Int): ParticleConfig? {
        // Check if the particle type is redirected to another for whatever reason,
        // and if so, use the redirected ID to get the config. Otherwise, just use the ID we were given.
        val particleId = ParticleRegistry.of(id)?.id ?: id
        return configs[particleId]
    }
}
