package org.polyfrost.polyparticles.config

import cc.polyfrost.oneconfig.config.core.OneColor

class ParticleEntry {

    var active = true

    var color : OneColor = OneColor(255, 255, 255, 255)

    var colorMode = false

    var customColor = false

    var multiplier = 1

    var size = 1.0f

}