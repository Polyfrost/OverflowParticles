package org.polyfrost.polyparticles.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.data.*
import cc.polyfrost.oneconfig.internal.config.core.ConfigCore
import net.minecraft.client.particle.EntityFX
import org.polyfrost.polyparticles.PolyParticles
import java.util.ArrayList

object ModConfig : Config(Mod(PolyParticles.NAME, ModType.UTIL_QOL), "${PolyParticles.MODID}.json") {

    var settings = Settings()

    var particles = HashMap<String, ParticleEntry>()

    var blockSetting = BlockParticleEntry()

    fun getConfig(entity: EntityFX?): ParticleConfig? {
        val id = PolyParticles.entitiesCache[entity?.entityId] ?: return null
        return getConfigByID(id)
    }

    fun getConfigByID(id: Int): ParticleConfig? {
        if (id == 2) return PolyParticles.configs[1]
        if (id == 38) return PolyParticles.configs[37]
        return PolyParticles.configs[id]
    }

    override fun initialize() {
        super.initialize()
        val remove = HashMap<String, ParticleEntry>()
        for (i in particles) {
            if (!PolyParticles.names.contains(i.key) || PolyParticles.ignores.contains(i.value.getID())) remove[i.key] = i.value
        }
        particles.entries.removeAll(remove.entries)
        PolyParticles.fillConfigs()
        val subMods = ArrayList<Mod>()
        for (i in PolyParticles.configs) {
            i.value.initialize()
            subMods.add(i.value.mod)
        }
        MainConfig
        subMods.add(MainConfig.mod)
        ConfigCore.subMods[this.mod] = subMods
    }

    override fun load() {
        super.load()
        PolyParticles.fillConfigs()
        for (i in PolyParticles.configs) {
            particles[i.value.name] ?: particles.put(i.value.name, ParticleEntry())
            i.value.enabled = particles[i.value.name]!!.active
        }
    }

    init {
        initialize()
    }

}