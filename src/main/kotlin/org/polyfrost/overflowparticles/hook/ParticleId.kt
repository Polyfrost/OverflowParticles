package org.polyfrost.overflowparticles.hook

//#if MC <= 1.12.2

import net.minecraft.client.particle.EntityFX

interface ParticleId {

    companion object {

        @JvmStatic
        fun getParticleId(entity: EntityFX): Int? {
            return (entity as? ParticleId)?.`overflowparticles$getParticleId`()
        }

        @JvmStatic
        fun setParticleId(entity: EntityFX, id: Int) {
            (entity as? ParticleId)?.`overflowparticles$setParticleId`(id)
        }

    }

    fun `overflowparticles$getParticleId`(): Int

    fun `overflowparticles$setParticleId`(id: Int)

}
//#endif
