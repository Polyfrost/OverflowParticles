package org.polyfrost.overflowparticles.utils

import net.minecraft.client.particle.Particle
import net.minecraft.core.particles.ParticleType

@Suppress("FunctionName")
interface ParticleIdentifier {
    companion object {
        @JvmStatic
        fun get(entity: Particle): ParticleType<*>? {
            return (entity as? ParticleIdentifier)?.getId()
        }

        @JvmStatic
        fun set(entity: Particle, id: ParticleType<*>) {
            (entity as? ParticleIdentifier)?.setId(id)
        }
    }

    fun `overflowparticles$getId`(): ParticleType<*>
    fun `overflowparticles$setId`(id: ParticleType<*>)

    fun getId(): ParticleType<*> {
        return `overflowparticles$getId`()
    }

    fun setId(id: ParticleType<*>) {
        `overflowparticles$setId`(id)
    }
}
