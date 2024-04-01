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
        if (id == 2) return PolyParticles.configs[2]
        return PolyParticles.configs[id]
    }

    override fun initialize() {
        super.initialize()
        val remove = HashMap<String, ParticleEntry>()
        for (i in particles) {
            if (!PolyParticles.names.contains(i.key)) remove[i.key] = i.value
        }
        for (i in remove) {
            particles.remove(i.key, i.value)
        }
    }

    override fun load() {
        super.load()
        MainConfig.load()
        for (i in PolyParticles.configs) {
            i.value.load()
        }
    }

    override fun save() {
        MainConfig.save()
        for (i in 0..41) {
            if (PolyParticles.ignores.contains(i)) continue
            particles[PolyParticles.names[i]] ?: particles.put(PolyParticles.names[i], ParticleEntry())
            val config = PolyParticles.configs[i] ?: continue
            config.save()
        }
        super.save()
    }

    init {
        initialize()
    }

}