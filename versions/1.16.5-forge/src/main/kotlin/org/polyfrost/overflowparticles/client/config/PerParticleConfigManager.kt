package org.polyfrost.overflowparticles.client.config

import net.minecraft.client.particle.Particle
import net.minecraft.core.particles.ParticleType
import org.polyfrost.overflowparticles.client.particles.ParticleRegistry
import org.polyfrost.overflowparticles.utils.ParticleIdentifier

object PerParticleConfigManager {
    @JvmStatic var blockSetting = BlockParticleEntry()
    @JvmStatic var configs = HashMap<ParticleType<*>, ParticleConfig>()

    fun fillConfigs() {
        if (configs.isNotEmpty()) {
            return
        }

        for ((id, data) in ParticleRegistry.registry) {
            if (data.isIgnored) {
                continue
            }

            configs[id] = ParticleConfig(data.name, id)
        }
    }

    @JvmStatic
    fun getConfig(entity: Particle?): ParticleConfig? {
        if (entity !is ParticleIdentifier) {
            return null
        }

        val particleType = entity.getId()
        return getConfigByType(particleType)
    }

    @JvmStatic
    fun getConfigByType(particleType: ParticleType<*>): ParticleConfig? {
        // Check if the particle type is redirected to another for whatever reason,
        // and if so, use the redirected type to get the config. Otherwise, just use the type we were given.
        val redirectedType = ParticleRegistry.of(particleType)?.id ?: particleType
        return configs[redirectedType]
    }
}
