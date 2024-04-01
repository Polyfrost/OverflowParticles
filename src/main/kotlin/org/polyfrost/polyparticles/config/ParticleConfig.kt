package org.polyfrost.polyparticles.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.*
import cc.polyfrost.oneconfig.config.data.*
import cc.polyfrost.oneconfig.internal.config.core.ConfigCore
import org.polyfrost.polyparticles.PolyParticles

open class ParticleConfig(name: String, @Exclude var id: Int) : Config(Mod(name, ModType.UTIL_QOL), "") {

    var entry = ParticleEntry()

    init {
        initialize()
    }

    override fun initialize() {
        mod.config = this
        generateOptionList(this.entry, mod.defaultPage, mod, false)
        ConfigCore.mods.add(this.mod)
        PolyParticles.mods.add(this.mod)
        addDependency("color", "customColor")
        addDependency("colorMode", "customColor")
        hideIf("multiplier") { id == 37 }
    }

    override fun reInitialize() {
    }

    override fun load() {
        val entry = ModConfig.particles[PolyParticles.names[id]] ?: return
        this.entry.loadFrom(entry)
        enabled = entry.active
    }

    override fun save() {
        entry.active = enabled
        ModConfig.particles[PolyParticles.names[id]] = this.entry
    }

}