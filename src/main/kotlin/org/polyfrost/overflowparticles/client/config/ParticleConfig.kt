package org.polyfrost.overflowparticles.client.config

import net.minecraft.core.particles.ParticleType
import org.polyfrost.oneconfig.api.config.v1.Node
import org.polyfrost.oneconfig.api.config.v1.Property
import org.polyfrost.oneconfig.api.config.v1.Tree
import org.polyfrost.oneconfig.api.config.v1.annotations.*
import org.polyfrost.oneconfig.utils.v1.MHUtils.setAccessible
import org.polyfrost.overflowparticles.client.particles.ParticleRegistry
import org.polyfrost.overflowparticles.client.particles.VanillaParticles
import org.polyfrost.compose.render.PolyColor

class ParticleConfig(val name: String, val particleType: ParticleType<*>) {
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

    @Suppress("UNCHECKED_CAST")
    fun handle(t: Tree) {
        val theMap = Tree::class.java.getDeclaredField("theMap")
        theMap.setAccessible()
        val map = theMap.get(t) as LinkedHashMap<String, Node>

//        val particle = ParticleRegistry.of(particleType) ?: throw IllegalArgumentException("Invalid particle type: ${ParticleRegistry.location(particleType)}")
        val particle = ParticleRegistry.of(particleType) ?: return
        if (particle.isUnfair) {
            map.remove("multiplier")
            t.getProp("size").metadata?.set("max", 1.0f)
        }

        if (particle == VanillaParticles.FOOTSTEP) {
            map.remove("customColor")
            map.remove("colorMode")
            map.remove("color")
        }

        if (particle.isFireworkTriggered) {
            map.remove("fade")
            map.remove("fadeStart")
        }

        //t.addDependency("General.color", "General.customColor")
        //t.addDependency("General.colorMode", "General.customColor")
    }

    private val dotRegex = "\\.".toRegex()

    /**
     * Mirror of [Config.addDependency]
     */
    private fun Tree.addDependency(option: String, condition: String) {
        val cond = getProperty(condition)
        require(cond.type == Boolean::class.javaPrimitiveType) { "Condition property must be boolean" }
        val opt = getProperty(option).addDisplayCondition((cond as Property<Boolean?>)!!, false)
        val dependencyNames = opt.getMetadata<ArrayList<String>>("dependencyNames")
            ?: ArrayList<String>(3).also { opt.addMetadata("dependencyNames", it) }
        dependencyNames.add(cond.title.toString())
    }

    /**
     * Mirror of [Config.getProperty]
     */
    private fun Tree.getProperty(option: String): Property<*> {
        return if (option.indexOf('.') >= 0) getProp(*option.split(dotRegex).dropLastWhile { it.isEmpty() }
            .toTypedArray<String>()) else getProp(option)
    }

}
