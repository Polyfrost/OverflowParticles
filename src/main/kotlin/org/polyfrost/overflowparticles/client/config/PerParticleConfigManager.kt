package org.polyfrost.overflowparticles.client.config

import net.minecraft.client.particle.EntityFX
import org.polyfrost.overflowparticles.client.utils.ParticleData
import org.polyfrost.overflowparticles.hook.ParticleId

object PerParticleConfigManager {

    @JvmStatic
    var blockSetting = BlockParticleEntry()

    @JvmStatic
    var configs = HashMap<Int, ParticleConfig>()

    fun fillConfigs() {
        if (configs.isNotEmpty()) {
            return
        }

        for ((id, type) in ParticleData.registry) {
            if (type.isIgnored) {
                continue
            }

            configs[id] = ParticleConfig(type.particleName, id)
        }
    }

    @JvmStatic
    fun getConfig(entity: EntityFX?): ParticleConfig? {
        if (entity !is ParticleId) {
            return null
        }

        return getConfigById(entity.`overflowparticles$getParticleId`())
    }

    @JvmStatic
    fun getConfigByType(type: ParticleData): ParticleConfig {
        return getConfigById(type.id) ?: throw IllegalArgumentException("No config found for particle type: ${type.particleName} (ID: ${type.id})")
    }

    private fun getConfigById(id: Int): ParticleConfig? {
        // Check if the particle type is redirected to another for whatever reason,
        // and if so, use the redirected ID to get the config. Otherwise, just use the ID we were given.
        val particleId = ParticleData.redirected(id)?.id ?: id
        return configs[particleId]
    }

}
