package org.polyfrost.polyparticles.config

import cc.polyfrost.oneconfig.config.annotations.*
import cc.polyfrost.oneconfig.config.core.ConfigUtils

class Settings {

    @Switch(
        name = "Clean View",
        description = "Stop rendering your potion effect particles.",
        subcategory = "Miscellaneous"
    )
    var cleanView = false

    @Switch(
        name = "Static Particle Color",
        description = "Disable particle lighting checks each frame.",
        subcategory = "Miscellaneous"
    )
    var staticParticleColor = false

    @Slider(
        name = "Max Particle Limit",
        description = "Stop additional particles from appearing when there are too many at once.",
        subcategory = "Miscellaneous",
        min = 1f, max = 10000f
    )
    var maxParticleLimit = 4000

    @Switch(
        name = "Always Show Critical",
        subcategory = "Hit Particle"
    )
    var alwaysCritical = false

    @Switch(
        name = "Always Show Sharpness",
        subcategory = "Hit Particle"
    )
    var alwaysSharp = false

    @Switch(
        name = "Check Invulnerability",
        subcategory = "Hit Particle"
    )
    var checkInvulnerable = false

    @Button(name = "", text = "Reset")
    var reset = Runnable {
        loadFrom(Settings())
    }

    fun loadFrom(entry: Settings) {
        val newFields = ConfigUtils.getClassFields(entry::class.java)
        val fields = ConfigUtils.getClassFields(this::class.java)
        for (i in 0..<fields.size) {
            fields[i].set(this, ConfigUtils.getField(newFields[i], entry))
        }
    }

}