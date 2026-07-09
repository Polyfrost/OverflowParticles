package org.polyfrost.overflowparticles.client.config

import net.minecraft.client.particle.Particle
import net.minecraft.core.particles.ParticleType
//? if >=1.21.11 {
import net.minecraft.resources.Identifier as ResourceLocation
//?} else {
/*import net.minecraft.resources.ResourceLocation
*///?}
import org.polyfrost.overflowparticles.client.particles.ParticleInfo
import org.polyfrost.overflowparticles.client.particles.ParticleRegistry
import org.polyfrost.overflowparticles.utils.ParticleIdentifier

object PerParticleConfigManager {
    @JvmStatic var blockSetting = BlockParticleEntry()
    @JvmStatic var configs = HashMap<ResourceLocation, ParticleConfig>()

    fun fillConfigs() {
        if (configs.isNotEmpty()) {
            return
        }

        for ((type, data) in ParticleRegistry.registry) {
            if (data.isIgnored) {
                continue
            }

            configs[ParticleRegistry.location(type)] = ParticleConfig(data.name, type)
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
    fun getConfigByType(info: ParticleInfo): ParticleConfig {
        return getConfigByType(info.id) ?: throw IllegalArgumentException("No config found for particle type: ${info.name} (ID: ${info.id})")
    }

    @JvmStatic
    fun getConfigByType(particleType: ParticleType<*>?): ParticleConfig? {
        if (particleType == null) {
            return null
        }

        // Check if the particle type is redirected to another for whatever reason,
        // and if so, use the redirected type to get the config. Otherwise, just use the type we were given.
        val redirectedType = ParticleRegistry.of(particleType)?.id ?: particleType
        return configs[ParticleRegistry.location(redirectedType)]
    }
}
