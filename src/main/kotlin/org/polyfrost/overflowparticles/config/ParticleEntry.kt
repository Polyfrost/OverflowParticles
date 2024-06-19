package org.polyfrost.overflowparticles.config

import cc.polyfrost.oneconfig.config.annotations.*
import cc.polyfrost.oneconfig.config.core.*
import org.polyfrost.overflowparticles.OverflowParticles

class ParticleEntry {

    var active = true

    @Switch(name = "Custom Color", size = 2)
    var customColor = false

    @DualOption(name = "Mode", left = "Multiply", right = "Override")
    var colorMode = false

    @Color(name = "Color")
    var color = OneColor(255, 255, 255, 255)

    @Slider(name = "Size", min = 0.5f, max = 5f)
    var size = 1.0f
        get() = field.coerceIn(0f, 5f)

    @Slider(name = "Multiplier", min = 0f, max = 10f)
    var multiplier = 1f

    @Button(name = "", text = "Reset", size = 2)
    var reset = Runnable {
        loadFrom(ParticleEntry())
        if (getID() == 37) {
            ModConfig.blockSetting.reset()
        }
    }

    fun getID(): Int {
        for (i in ModConfig.particles) {
            if (i.value == this) {
                return OverflowParticles.names.indexOf(i.key)
            }
        }
        return 100
    }

    fun loadFrom(entry: ParticleEntry) {
        val newFields = ConfigUtils.getClassFields(entry::class.java)
        val fields = ConfigUtils.getClassFields(this::class.java)
        for (i in 0..<fields.size) {
            fields[i].set(this, ConfigUtils.getField(newFields[i], entry))
        }
    }

}