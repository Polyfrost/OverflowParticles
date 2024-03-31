package org.polyfrost.polyparticles.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.*
import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.config.data.*
import cc.polyfrost.oneconfig.internal.config.core.ConfigCore
import org.polyfrost.polyparticles.PolyParticles

open class ParticleConfig(name: String, @Exclude var id: Int) : Config(Mod(name, ModType.UTIL_QOL), "") {

    @Switch(name = "Custom Color", size = 2)
    var customColor = false

    @DualOption(name = "Mode", left = "Multiply", right = "Override")
    var colorMode = false

    @Color(name = "Color")
    var color: OneColor = OneColor(255, 255, 255, 255)

    @Slider(name = "Size", min = 0.5f, max = 5f)
    var size = 1.0f

    @Slider(name = "Multiplier", min = 1f, max = 10f)
    var multiplier = 1

    init {
        initialize()
    }

    override fun initialize() {
        mod.config = this
        generateOptionList(this, mod.defaultPage, mod, false)
        ConfigCore.mods.add(this.mod)
        PolyParticles.mods.add(this.mod)
        addDependency("color", "customColor")
        addDependency("colorMode", "customColor")
        hideIf("multiplier") { id == 37 }
    }

    override fun reInitialize() {
    }

    override fun load() {
        val entry = ModConfig.entries[PolyParticles.names[id]] ?: return
        enabled = entry.active
        color = entry.color
        size = entry.size
        customColor = entry.customColor
        colorMode = entry.colorMode
        multiplier = entry.multiplier
    }

    override fun save() {
        val entry = ModConfig.entries[PolyParticles.names[id]] ?: return
        entry.active = enabled
        entry.size = size
        entry.color = color
        entry.customColor = customColor
        entry.colorMode = colorMode
        entry.multiplier = multiplier
    }

}