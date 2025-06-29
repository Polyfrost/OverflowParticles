package org.polyfrost.overflowparticles.client.config

import net.minecraft.client.particle.Particle
import net.minecraft.core.particles.ParticleType
import org.polyfrost.overflowparticles.client.utils.ParticleData
import org.polyfrost.overflowparticles.hook.ParticleId

object PerParticleConfigManager {

    @JvmStatic
    var blockSetting = BlockParticleEntry()

    @JvmStatic
    var configs = HashMap<ParticleType<*>, ParticleConfig>()

    fun fillConfigs() {
        if (configs.isNotEmpty()) {
            return
        }

        for ((particleType, data) in ParticleData.registry) {
            if (data.isIgnored) {
                continue
            }

            configs[particleType] = ParticleConfig(data.particleName, particleType)
        }
    }

    @JvmStatic
    fun getConfig(entity: Particle?): ParticleConfig? {
        if (entity !is ParticleId) {
            return null
        }

//        val particleType = entity.`overflowparticles$getParticleType`()
//        return getConfigByType(particleType)
        return null;
    }

    @JvmStatic
    fun getConfigByType(particleType: ParticleType<*>): ParticleConfig? {
        // Check if the particle type is redirected to another for whatever reason,
        // and if so, use the redirected type to get the config. Otherwise, just use the type we were given.
        val redirectedType = ParticleData.redirected(particleType)?.particleType ?: particleType
        return configs[redirectedType]
    }

}
