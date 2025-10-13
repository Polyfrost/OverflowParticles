package org.polyfrost.overflowparticles.client.particles

import net.minecraft.core.particles.ParticleType
import net.minecraft.resources.ResourceLocation

data class ParticleInfo(
    val name: String,
    val id: ParticleType<*>,
    val redirectsTo: ParticleInfo? = null,
    val isIgnored: Boolean = false,
    val isUnfair: Boolean = false
) {
    val location: ResourceLocation
        get() = redirectsTo?.location ?: ParticleRegistry.location(id)

    val isFireworkTriggered: Boolean
        get() = this in VanillaParticles.fireworkTriggered
}
