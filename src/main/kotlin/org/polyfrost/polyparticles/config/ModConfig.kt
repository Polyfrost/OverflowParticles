package org.polyfrost.polyparticles.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.Exclude
import cc.polyfrost.oneconfig.config.data.*
import cc.polyfrost.oneconfig.gui.pages.ModsPage
import net.minecraft.client.particle.EntityFX
import org.polyfrost.polyparticles.PolyParticles

object ModConfig : Config(Mod(PolyParticles.NAME, ModType.UTIL_QOL), "${PolyParticles.MODID}.json") {

    var entries = HashMap<String, ParticleEntry>()

    @Exclude
    var page: ModsPage? = null

    fun getConfig(entity: EntityFX): ParticleConfig? {
        val index = PolyParticles.particles[entity] ?: return null
        return PolyParticles.configs[index]
    }

    override fun load() {
        super.load()
        for (i in 0..41) {
            val config = PolyParticles.configs[i]
            val entry = entries[PolyParticles.names[i]] ?: ParticleEntry()
            config.enabled = entry.active
            config.color = entry.color
            config.size = entry.size
            config.overrideColor = entry.overrideColor
        }
    }

    override fun save() {
        for (i in 0..41) {
            entries[PolyParticles.names[i]] ?: entries.put(PolyParticles.names[i], ParticleEntry())
            val config = PolyParticles.configs[i]
            val entry = entries[PolyParticles.names[i]] ?: break
            entry.active = config.enabled
            entry.color = config.color
            entry.size = config.size
            entry.overrideColor = config.overrideColor
        }
        super.save()
    }

    init {
        initialize()
    }

}