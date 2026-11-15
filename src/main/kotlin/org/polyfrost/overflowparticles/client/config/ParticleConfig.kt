@file:OptIn(ExperimentalOneConfigUiApi::class)

package org.polyfrost.overflowparticles.client.config

import com.google.gson.JsonParser
import net.minecraft.core.particles.ParticleType
import org.polyfrost.oneconfig.api.config.v1.MetaKeys
import org.polyfrost.oneconfig.api.config.v1.Tree
import org.polyfrost.oneconfig.api.config.v1.addDependency
import org.polyfrost.oneconfig.api.config.v1.annotations.*
import org.polyfrost.oneconfig.api.config.v1.set
import org.polyfrost.oneconfig.api.ui.v1.ExperimentalOneConfigUiApi
import org.polyfrost.oneconfig.api.ui.v1.IconSpec
import org.polyfrost.overflowparticles.client.particles.ParticleRegistry
import org.polyfrost.overflowparticles.client.particles.VanillaParticles
import org.polyfrost.overflowparticles.client.utils.IconIndex
import org.polyfrost.compose.render.PolyColor
import java.io.InputStreamReader

class ParticleConfig(val name: String, val particleType: ParticleType<*>) {
    private val info = ParticleRegistry.of(particleType)
    val isBlocks = info === VanillaParticles.BLOCKS
    val isFireworkTriggered = info?.isFireworkTriggered == true

    @Include
    var enabled = true

    @Switch(title = "Custom Color")
    var customColor = false

    @RadioButton(title = "Mode", options = ["Multiply", "Override"])
    var colorMode = 0

    @Color(title = "Color")
    var color = PolyColor(-1)

    @Switch(title = "Fade", description = "Make particles fade rather than just disappearing.")
    var fade = false

    @Slider(title = "Fade Out Start", description = "How far into the lifespan of the particle before it starts to fade.", max = 1F, min = 0F, step = 0.01f)
    var fadeStart = 0.5f

    @Slider(title = "Size", min = 0.5f, max = 5f, step = 0.1f)
    var size = 1.0f
        get() = field.coerceIn(0f, 5f)

    @Slider(title = "Multiplier", min = 0f, max = 10f, step = 0.1f)
    var multiplier = 1f

    fun handle(t: Tree) {
//        val particle = ParticleRegistry.of(particleType) ?: throw IllegalArgumentException("Invalid particle type: ${ParticleRegistry.location(particleType)}")
        val particle = ParticleRegistry.of(particleType) ?: return
        if (particle.isUnfair) {
            t.remove("multiplier")
            t.getProp("size")[MetaKeys.MAX] = 1.0f
        }

        if (particle == VanillaParticles.FOOTSTEP) {
            t.remove("customColor")
            t.remove("colorMode")
            t.remove("color")
        }

        if (particle.isFireworkTriggered) {
            t.remove("fade")
            t.remove("fadeStart")
        }

        //t.addDependency("General.color", "General.customColor")
        //t.addDependency("General.colorMode", "General.customColor")
        if (t.get("customColor") != null) {
            t.addDependency("colorMode", "customColor")
            t.addDependency("color", "customColor")
        }
    }

    fun icon(): Any? {
        //? if >1.8.9 {
        val location = ParticleRegistry.location(particleType)
        val texture = ParticleConfig::class.java.getResourceAsStream("/assets/${location.namespace}/particles/${location.path}.json")?.use { stream ->
            runCatching {
                JsonParser.parseReader(InputStreamReader(stream, Charsets.UTF_8)).asJsonObject.getAsJsonArray("textures")?.firstOrNull()?.asString
            }.getOrNull()
        } ?: return null
        val namespace = texture.substringBefore(':', "minecraft")
        val path = texture.substringAfter(':')
        return IconSpec("/assets/$namespace/textures/particle/$path.png", 0, 0, 1, 1, 1)
        //?} else {
        /*val cell = IconIndex.getFromID(particleType.ordinal) ?: return null
        return IconSpec("/assets/minecraft/textures/particle/particles.png", cell.x.toInt(), cell.y.toInt(), 8, 8, 128)
        *///?}
    }
}
