package org.polyfrost.overflowparticles.client.particles

import net.minecraft.core.particles.ParticleType
//? if >=1.21.11 {
import net.minecraft.resources.Identifier as ResourceLocation
//?} else {
/*import net.minecraft.resources.ResourceLocation
*///?}
import org.apache.logging.log4j.LogManager

//? if >=1.20.1 {
import net.minecraft.core.registries.BuiltInRegistries
//?} else {
/*import net.minecraft.util.registry.Registry
*///?}

object ParticleRegistry {
    private val LOGGER = LogManager.getLogger("OverflowParticles / Particle Registry")

    private val _cache = mutableMapOf<ParticleType<*>, ResourceLocation>()
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

    @JvmStatic
    fun location(id: ParticleType<*>): ResourceLocation {
        return _cache.getOrPut(id) {
            //? if >=1.20.1 {
            BuiltInRegistries.PARTICLE_TYPE.getKey(id) ?: ResourceLocation.tryParse("unknown")!!
            //?} else {
            /*Registry.PARTICLE_TYPE.getId(id) ?: ResourceLocation("unknown")
            *///?}
        }
    }
}
