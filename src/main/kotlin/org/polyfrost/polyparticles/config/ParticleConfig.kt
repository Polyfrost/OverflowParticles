package org.polyfrost.polyparticles.config

import cc.polyfrost.oneconfig.config.elements.SubConfig
import cc.polyfrost.oneconfig.internal.config.core.ConfigCore
import org.polyfrost.polyparticles.PolyParticles

class ParticleConfig(val name: String, val id: Int) : SubConfig(name, "") {

    val entry: ParticleEntry
        get() {
            ModConfig.particles[name] ?: ModConfig.particles.put(name, ParticleEntry())
            return ModConfig.particles[name]!!
        }

    override fun initialize() {
        mod.config = this
        generateOptionList(entry, mod.defaultPage, mod, false)
        ConfigCore.mods.add(this.mod)
        addDependency("color", "customColor")
        addDependency("colorMode", "customColor")
        hideIf("multiplier") { PolyParticles.unfair.contains(id) }
        val colors = listOf("customColor", "colorMode", "color")
        for (i in colors) {
            hideIf(i) { id == 28 }
        }
    }

    override fun reInitialize() {
    }

    override fun load() {
    }

    override fun save() {
        entry.active = enabled
    }

}