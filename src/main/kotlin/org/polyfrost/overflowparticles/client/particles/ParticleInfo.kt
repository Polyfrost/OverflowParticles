package org.polyfrost.overflowparticles.client.particles

data class ParticleInfo(
    val name: String,
    val id: Int,
    val redirectsTo: ParticleInfo? = null,
    val isIgnored: Boolean = false,
    val isUnfair: Boolean = false
) {
    val location: Int
        get() = redirectsTo?.location ?: id

    val isFireworkTriggered: Boolean
        get() = this in VanillaParticles.fireworkTriggered
}
