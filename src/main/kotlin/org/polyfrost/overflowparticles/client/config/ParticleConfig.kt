package org.polyfrost.overflowparticles.client.config

import org.polyfrost.oneconfig.api.config.v1.Node
import org.polyfrost.oneconfig.api.config.v1.Property
import org.polyfrost.oneconfig.api.config.v1.Tree
import org.polyfrost.oneconfig.api.config.v1.annotations.*
import org.polyfrost.oneconfig.utils.v1.MHUtils.setAccessible
import org.polyfrost.overflowparticles.client.ParticleType
import org.polyfrost.polyui.color.toColor

class ParticleConfig(val name: String, val id: Int) {

    @Include
    var enabled = true

    @Switch(title = "Custom Color")
    var customColor = false

    @RadioButton(title = "Mode", options = ["Multiply", "Override"])
    var colorMode = 0

    @Color(title = "Color")
    var color = (-1).toColor()

    @Switch(title = "Fade", description = "Make particles fade rather than just disappearing.")
    var fade = true

    @Slider(title = "Fade Out Start", description = "How far into the lifespan of the particle before it starts to fade.", max = 1F, min = 0F)
    var fadeStart = 0.5f

    @Slider(title = "Size", min = 0.5f, max = 5f)
    var size = 1.0f
        get() = field.coerceIn(0f, 5f)

    @Slider(title = "Multiplier", min = 0f, max = 10f)
    var multiplier = 1f

    @Suppress("UNCHECKED_CAST")
    fun handle(t: Tree) {
        val theMap = Tree::class.java.getDeclaredField("theMap")
        theMap.setAccessible()
        val map = theMap.get(t) as LinkedHashMap<String, Node>

        val particle = ParticleType.of(id) ?: throw IllegalArgumentException("Invalid particle ID: $id")
        if (particle.isUnfair) {
            map.remove("multiplier")
            t.getProp("size").metadata?.set("max", 1.0f)
        }

        if (particle == ParticleType.FOOTSTEP) {
            map.remove("customColor")
            map.remove("colorMode")
            map.remove("color")
        }

        if (particle in arrayOf(ParticleType.EXPLOSION, ParticleType.LARGE_EXPLOSION, ParticleType.HUGE_EXPLOSION, ParticleType.FIREWORK_SPARK)) {
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
        opt.getOrPutMetadata<ArrayList<String>>("dependencyNames") { ArrayList(3) }.add(cond.title)
    }

    /**
     * Mirror of [Config.getProperty]
     */
    private fun Tree.getProperty(option: String): Property<*> {
        return if (option.indexOf('.') >= 0) getProp(*option.split(dotRegex).dropLastWhile { it.isEmpty() }
            .toTypedArray<String>()) else getProp(option)
    }

}