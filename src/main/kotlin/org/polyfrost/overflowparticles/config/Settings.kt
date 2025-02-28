package org.polyfrost.overflowparticles.config

import cc.polyfrost.oneconfig.config.annotations.Button
import cc.polyfrost.oneconfig.config.annotations.Slider
import cc.polyfrost.oneconfig.config.annotations.Switch
import cc.polyfrost.oneconfig.config.core.ConfigUtils

class Settings {

    @Switch(
        name = "Cleaner Particles",
        description = "Remove the random offset from particles.",
        subcategory = "Features"
    )
    var cleanerParticles = true

    @Switch(
        name = "Clean View",
        description = "Stop rendering your own particles.",
        subcategory = "Features"
    )
    var cleanView = false

    @Switch(
        name = "Static Particle Color",
        description = "Disable particle lighting checks each frame.",
        subcategory = "Features"
    )
    var staticParticleColor = true

    @Slider(
        name = "Max Particle Limit",
        description = "Stop additional particles from appearing when there are too many at once.",
        subcategory = "Features",
        min = 1f, max = 10000f
    )
    var maxParticleLimit = 4000
        set(value) {
            field = if (value in 1..10000) value else 4000
        }

    @Switch(
        name = "Particles No-Clip",
        description = "Allows particles to no clip through blocks by simply not checking for collisions.",
        subcategory = "Features"
    )
    var particleNoClip = false

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

    @Button(name = "", text = "Reset", size = 2)
    var reset = Runnable {
        loadFrom(Settings())
    }

    var hasMigratedPatcher = false
    var hasMigratedPatcherPt2CauseImStupid = false
    var hasMigratedParticlesEnhanced = false

    fun loadFrom(entry: Settings) {
        val newFields = ConfigUtils.getClassFields(entry::class.java)
        val fields = ConfigUtils.getClassFields(this::class.java)
        for (i in 0..<fields.size) {
            fields[i].set(this, ConfigUtils.getField(newFields[i], entry))
        }
    }

}