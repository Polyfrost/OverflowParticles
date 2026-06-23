package org.polyfrost.overflowparticles.client

import net.minecraft.client.particle.Particle
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig
import org.polyfrost.overflowparticles.client.particles.VanillaParticles

object OverflowParticlesClient {
    @JvmStatic var renderingEntity: Particle? = null
    @JvmStatic var isRendering = false

    fun initialize() {
        VanillaParticles.preload()
        OverflowParticlesConfig.preload()
        OverflowParticlesEventHandler.initialize()
    }
}
