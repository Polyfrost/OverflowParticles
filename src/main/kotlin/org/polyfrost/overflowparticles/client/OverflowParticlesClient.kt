package org.polyfrost.overflowparticles.client

import net.minecraft.client.particle.EntityFX
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig
import org.polyfrost.overflowparticles.client.utils.IconRenderer

object OverflowParticlesClient {

    @JvmStatic
    var renderingEntity: EntityFX? = null

    @JvmStatic
    var rendering = false

    fun initialize() {
        OverflowParticlesConfig.preload()
        OverflowParticlesEventHandler.initialize()
        IconRenderer.initialize()
    }

}
