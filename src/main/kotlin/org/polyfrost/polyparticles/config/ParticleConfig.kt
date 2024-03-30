package org.polyfrost.polyparticles.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.Color
import cc.polyfrost.oneconfig.config.annotations.Exclude
import cc.polyfrost.oneconfig.config.annotations.Slider
import cc.polyfrost.oneconfig.config.annotations.Switch
import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
import cc.polyfrost.oneconfig.internal.config.core.ConfigCore
import org.polyfrost.polyparticles.PolyParticles

class ParticleConfig(name: String, @Exclude var index: Int) : Config(Mod(name, ModType.UTIL_QOL), "") {

    @Switch(name = "Override Color")
    var overrideColor = false

    @Color(name = "Color")
    var color : OneColor = OneColor(255, 255, 255, 255)

    @Slider(name = "Size", min = 0.5f, max = 5f)
    var size = 1.0f

    init {
        initialize()
    }

    override fun save() {
        val entry = ModConfig.entries[PolyParticles.names[index]] ?: return
        entry.active = enabled
        entry.size = size
        entry.color = color
        entry.overrideColor = overrideColor
    }

    override fun initialize() {
        mod.config = this
        generateOptionList(this, mod.defaultPage, mod, false)
        ConfigCore.mods.add(this.mod)
        PolyParticles.mods.add(this.mod)
        addDependency("color", "overrideColor")
    }

    override fun load() {
    }

}