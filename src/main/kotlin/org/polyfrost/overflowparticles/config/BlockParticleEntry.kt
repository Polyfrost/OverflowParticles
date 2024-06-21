package org.polyfrost.overflowparticles.config

import cc.polyfrost.oneconfig.config.annotations.DualOption
import cc.polyfrost.oneconfig.config.annotations.Slider
import cc.polyfrost.oneconfig.config.annotations.Switch
import cc.polyfrost.oneconfig.config.core.ConfigUtils

class BlockParticleEntry {

    @Switch(
        name = "Hide Block Digging Particles",
        size = 2
    )
    var hideDigging = false

    @Switch(
        name = "Hide Entity Running / Falling Particles"
    )
    var hideRunning = false

    @DualOption(
        name = "Mode",
        left = "Visible entities only",
        right = "ALL"
    )
    var hideMode = true

    @Switch(name = "Fade", description = "Make particles fade rather than just disappearing.")
    var fade = true

    @Slider(name = "Fade Out Start", description = "How far into the lifespan of the particle before it starts to fade.", max = 1F, min = 0F)
    var fadeStart = 0.5f

    fun reset() {
        val entry = BlockParticleEntry()
        val newFields = ConfigUtils.getClassFields(entry::class.java)
        val fields = ConfigUtils.getClassFields(this::class.java)
        for (i in 0..<fields.size) {
            fields[i].set(this, ConfigUtils.getField(newFields[i], entry))
        }
    }

}