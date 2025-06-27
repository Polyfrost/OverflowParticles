package org.polyfrost.overflowparticles.client.utils

import net.minecraft.util.EnumParticleTypes

data class ParticleData(
    val particleName: String,
    val id: Int,
    val redirect: ParticleData? = null,
    val isIgnored: Boolean = false,
    val isUnfair: Boolean = false
) {

    constructor(
        particleName: String,
        vanilla: EnumParticleTypes,
        redirect: ParticleData? = null,
        isIgnored: Boolean = false,
        isUnfair: Boolean = false
    ) : this(particleName, vanilla.particleID, redirect, isIgnored, isUnfair)

    val isFireworkTriggered: Boolean
        get() = this in fireworkTriggered

    companion object {

        private val _registry = mutableMapOf<Int, ParticleData>()

        private val fireworkTriggered = setOf(VanillaParticles.EXPLOSION_NORMAL, VanillaParticles.EXPLOSION_LARGE, VanillaParticles.EXPLOSION_HUGE, VanillaParticles.FIREWORK_SPARK)

        /**
         * View-only instance of the particle type registry.
         */
        @JvmStatic
        val registry: Map<Int, ParticleData>
            get() = _registry.toMap()

        @JvmStatic
        fun register(particleData: ParticleData) {
            if (particleData.id in _registry) {
                throw IllegalArgumentException("Particle with id ${particleData.id} is already registered.")
            }

            _registry[particleData.id] = particleData
        }

        @JvmStatic
        fun of(id: Int): ParticleData? {
            return _registry[id]
        }

        @JvmStatic
        fun redirected(id: Int): ParticleData? {
            return of(id)?.redirect
        }

    }

}
