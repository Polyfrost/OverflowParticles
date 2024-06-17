package org.polyfrost.overflowparticles.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.data.*
import cc.polyfrost.oneconfig.internal.config.core.ConfigCore
import net.minecraft.client.particle.EntityFX
import org.polyfrost.overflowparticles.OverflowParticles
import java.util.ArrayList

object ModConfig : Config(Mod(OverflowParticles.NAME, ModType.UTIL_QOL), "${OverflowParticles.MODID}.json") {

    var settings = Settings()

    var particles = HashMap<String, ParticleEntry>()

    var blockSetting = BlockParticleEntry()

    fun getConfig(entity: EntityFX?): ParticleConfig? {
        val id = OverflowParticles.entitiesCache[entity?.entityId] ?: return null
        return getConfigByID(id)
    }

    fun getConfigByID(id: Int): ParticleConfig? {
        if (id == 2) return OverflowParticles.configs[1]
        if (id == 38) return OverflowParticles.configs[37]
        return OverflowParticles.configs[id]
    }

    override fun initialize() {
        super.initialize()
        val remove = HashMap<String, ParticleEntry>()
        for (i in particles) {
            if (!OverflowParticles.names.contains(i.key) || OverflowParticles.ignores.contains(i.value.getID())) remove[i.key] = i.value
        }
        particles.entries.removeAll(remove.entries)
        OverflowParticles.fillConfigs()
        val subMods = ArrayList<Mod>()
        for (i in OverflowParticles.configs) {
            i.value.initialize()
            subMods.add(i.value.mod)
        }
        MainConfig
        subMods.add(MainConfig.mod)
        ConfigCore.subMods[this.mod] = subMods
    }

    override fun load() {
        super.load()
        OverflowParticles.fillConfigs()
        for (i in OverflowParticles.configs) {
            particles[i.value.name] ?: particles.put(i.value.name, ParticleEntry())
            i.value.enabled = particles[i.value.name]!!.active
        }
    }

    init {
        initialize()
    }

}