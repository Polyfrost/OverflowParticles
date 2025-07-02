package org.polyfrost.overflowparticles.client.utils

import net.minecraft.core.particles.ParticleType

data class ParticleData(
    val particleName: String,
    val particleType: ParticleType<*>,
    val redirect: ParticleData? = null,
    val isIgnored: Boolean = false,
    val isUnfair: Boolean = false
) {

    val isFireworkTriggered: Boolean
        get() = false

    companion object {

        private val _registry = mutableMapOf<ParticleType<*>, ParticleData>()


        /**
         * View-only instance of the particle type registry.
         */
        @JvmStatic
        val registry: Map<ParticleType<*>, ParticleData>
            get() = _registry.toMap()

        @JvmStatic
        fun register(particleData: ParticleData) {
            if (particleData.particleType in _registry) {
                throw IllegalArgumentException("Particle with type ${particleData.particleType} is already registered.")
            }
            _registry[particleData.particleType] = particleData
        }

        @JvmStatic
        fun of(type: ParticleType<*>): ParticleData? {
            return _registry[type]
        }

        @JvmStatic
        fun redirected(type: ParticleType<*>): ParticleData? {
            return of(type)?.redirect
        }

    }

}
