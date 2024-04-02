package org.polyfrost.polyparticles.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.Exclude
import cc.polyfrost.oneconfig.config.data.*
import cc.polyfrost.oneconfig.gui.pages.ModsPage
import net.minecraft.client.particle.EntityFX
import org.polyfrost.polyparticles.PolyParticles

object ModConfig : Config(Mod(PolyParticles.NAME, ModType.UTIL_QOL), "${PolyParticles.MODID}.json") {

    var settings = Settings()

    var particles = HashMap<String, ParticleEntry>()

    @Exclude
    var page: ModsPage? = null

    fun getConfig(entity: EntityFX?): ParticleConfig? {
        val id = PolyParticles.entitiesCache[entity?.entityId] ?: return null
        if (id == 2) return PolyParticles.configs[3]
        return PolyParticles.configs[id]
    }

    override fun initialize() {
        super.initialize()
        for (i in PolyParticles.configs) {
            i.value.initialize()
        }
        MainConfig
    }

    override fun load() {
        super.load()
        for (i in PolyParticles.configs) {
            particles[i.value.name] ?: particles.put(i.value.name, ParticleEntry(i.key))
            particles[i.value.name]!!.id = i.key
            i.value.enabled = particles[i.value.name]!!.active
        }
    }

    init {
        initialize()
    }

}