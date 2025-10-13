package org.polyfrost.overflowparticles.utils

import net.minecraft.client.particle.EntityFX

@Suppress("FunctionName")
interface ParticleIdentifier {
    companion object {
        @JvmStatic
        fun get(entity: EntityFX): Int? {
            return (entity as? ParticleIdentifier)?.getId()
        }

        @JvmStatic
        fun set(entity: EntityFX, id: Int) {
            (entity as? ParticleIdentifier)?.setId(id)
        }
    }

    fun `overflowparticles$getId`(): Int
    fun `overflowparticles$setId`(id: Int)

    fun getId(): Int {
        return `overflowparticles$getId`()
    }

    fun setId(id: Int) {
        `overflowparticles$setId`(id)
    }
}