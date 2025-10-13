package org.polyfrost.overflowparticles.client.particles

import net.minecraft.util.EnumParticleTypes
import org.apache.logging.log4j.LogManager

object ParticleRegistry {
    private val LOGGER = LogManager.getLogger("OverflowParticles / Particle Registry")

    private val _registry = mutableMapOf<Int, ParticleInfo>()

    /**
     * View-only instance of the particle type registry.
     */
    val registry: Map<Int, ParticleInfo>
        get() = _registry.toMap()

    fun create(
        name: String,
        id: Int,
        redirectsTo: ParticleInfo? = null,
        isIgnored: Boolean = false,
        isUnfair: Boolean = false
    ): ParticleInfo {
        val particleInfo = ParticleInfo(name, id, redirectsTo, isIgnored, isUnfair)
        if (particleInfo.id in _registry) {
            LOGGER.warn("Particle with id ${particleInfo.id} is already registered.")
            return particleInfo
        }

        _registry[particleInfo.id] = particleInfo
        return particleInfo
    }

    fun create(
        name: String,
        vanilla: EnumParticleTypes,
        redirectsTo: ParticleInfo? = null,
        isIgnored: Boolean = false,
        isUnfair: Boolean = false
    ): ParticleInfo {
        return create(name, vanilla.particleID, redirectsTo, isIgnored, isUnfair)
    }

    @JvmStatic
    fun ofSingular(id: Int): ParticleInfo? {
        return _registry[id]
    }

    @JvmStatic
    fun of(id: Int): ParticleInfo? {
        return ofSingular(id)?.redirectsTo
    }
}
