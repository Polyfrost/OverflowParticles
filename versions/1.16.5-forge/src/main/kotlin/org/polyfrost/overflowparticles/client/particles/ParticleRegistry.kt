package org.polyfrost.overflowparticles.client.particles

import dev.deftu.omnicore.api.identifierOrThrow
import net.minecraft.core.particles.ParticleType
import net.minecraft.resources.ResourceLocation
import org.apache.logging.log4j.LogManager

//#if MC >= 1.20.1
//$$ import net.minecraft.registry.Registries
//#else
import net.minecraft.core.Registry
//#endif

object ParticleRegistry {
    private val LOGGER = LogManager.getLogger("OverflowParticles / Particle Registry")

    private val _registry = mutableMapOf<ParticleType<*>, ParticleInfo>()

    /**
     * View-only instance of the particle type registry.
     */
    val registry: Map<ParticleType<*>, ParticleInfo>
        get() = _registry.toMap()

    fun create(
        name: String,
        id: ParticleType<*>,
        redirectsTo: ParticleInfo? = null,
        isIgnored: Boolean = false,
        isUnfair: Boolean = false
    ): ParticleInfo {
        val particleInfo = ParticleInfo(name, id, redirectsTo, isIgnored, isUnfair)
        if (particleInfo.id in _registry) {
            LOGGER.warn("Particle with id ${location(particleInfo.id)} is already registered.")
            return particleInfo
        }

        _registry[particleInfo.id] = particleInfo
        return particleInfo
    }

    @JvmStatic
    fun ofSingular(id: ParticleType<*>): ParticleInfo? {
        return _registry[id]
    }

    @JvmStatic
    fun of(id: ParticleType<*>): ParticleInfo? {
        val value = ofSingular(id) ?: return null
        return value.redirectsTo ?: value
    }

    fun location(id: ParticleType<*>): ResourceLocation {
        //#if MC >= 1.20.1
        //$$ return Registries.PARTICLE_TYPE.getId(id) ?: identifierOrThrow("unknown")
        //#else
        return Registry.PARTICLE_TYPE.getKey(id) ?: identifierOrThrow("unknown")
        //#endif
    }
}
