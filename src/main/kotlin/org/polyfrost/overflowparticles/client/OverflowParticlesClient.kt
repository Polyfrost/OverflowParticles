package org.polyfrost.overflowparticles.client

import net.minecraft.client.particle.EntityFX
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig
import org.polyfrost.overflowparticles.client.utils.IconRenderer
import org.polyfrost.overflowparticles.client.utils.VanillaParticles

object OverflowParticlesClient {

    @JvmStatic
    var renderingEntity: EntityFX? = null

    @JvmStatic
    var rendering = false

    fun initialize() {
        VanillaParticles.initialize()
        OverflowParticlesConfig.preload()
        OverflowParticlesEventHandler.initialize()
        IconRenderer.initialize()
    }

}
