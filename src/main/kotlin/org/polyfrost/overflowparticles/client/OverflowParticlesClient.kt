package org.polyfrost.overflowparticles.client

import net.minecraft.client.particle.EntityFX
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig
import org.polyfrost.overflowparticles.client.particles.VanillaParticles

object OverflowParticlesClient {
    @JvmStatic var renderingEntity: EntityFX? = null
    @JvmStatic var isRendering = false

    fun initialize() {
        VanillaParticles.preload()
        OverflowParticlesConfig.preload()
        OverflowParticlesEventHandler.initialize()
    }
}
