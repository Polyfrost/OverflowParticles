package org.polyfrost.overflowparticles.client.utils

import net.minecraft.core.Registry
import net.minecraft.core.particles.ParticleTypes


object VanillaParticles {

    @JvmField
    val ANGRY_VILLAGER = ParticleData("Angry Villager", ParticleTypes.ANGRY_VILLAGER)

    @JvmField
    val BLOCK = ParticleData("Block", ParticleTypes.BLOCK, isUnfair = true)

    @JvmField
    val entries = listOf(
        ANGRY_VILLAGER,
        BLOCK
    )

    @JvmStatic
    fun initialize() {
        entries.forEach(ParticleData::register)
    }

}
