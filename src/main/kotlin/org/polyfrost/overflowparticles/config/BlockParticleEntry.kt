package org.polyfrost.overflowparticles.config

import org.polyfrost.oneconfig.api.config.v1.annotations.RadioButton
import org.polyfrost.oneconfig.api.config.v1.annotations.Slider
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch

class BlockParticleEntry {

    @Switch(
        title = "Hide Block Digging Particles"
    )
    var hideDigging = false

    @Switch(
        title = "Hide Entity Running / Falling Particles"
    )
    var hideRunning = false

    @RadioButton(
        title = "Mode",
        options = ["Visible entities only", "ALL"]
    )
    var hideMode = 1

    @Switch(title = "Fade", description = "Make particles fade rather than just disappearing.")
    var fade = true

    @Slider(title = "Fade Out Start", description = "How far into the lifespan of the particle before it starts to fade.", max = 1F, min = 0F)
    var fadeStart = 0.5f

}