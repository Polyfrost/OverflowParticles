package org.polyfrost.overflowparticles.config

import net.minecraft.client.particle.EntityFX
import org.polyfrost.overflowparticles.OverflowParticles
import org.polyfrost.overflowparticles.hook.EntityFXHook

object ModConfig {

    var particles = HashMap<String, ParticleEntry>()

    var blockSetting = BlockParticleEntry()

    fun getConfig(entity: EntityFX?): ParticleConfig? {
        if (entity !is EntityFXHook) return null
        return getConfigByID(entity.`overflowParticles$getParticleID`())
    }

    fun getConfigByID(id: Int): ParticleConfig? {
        if (id == 2) return OverflowParticles.configs[1]
        if (id == 38) return OverflowParticles.configs[37]
        return OverflowParticles.configs[id]
    }

}
