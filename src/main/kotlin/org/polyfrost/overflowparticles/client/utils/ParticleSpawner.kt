@file:JvmName("ParticleSpawner")

package org.polyfrost.overflowparticles.client.utils

//? if >1.8.9 {
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.world.level.Level
//?} else {
/*import net.minecraft.entity.particle.ParticleType
import net.minecraft.world.World
*///?}
import org.polyfrost.overflowparticles.client.config.ParticleConfig
import kotlin.math.ceil

var isMultiplied = false

fun color(color: Int, targetColor: Float, config: ParticleConfig): Float {
    if (config.customColor) {
        val scalar = if (config.colorMode == 1) 1f else targetColor
        return color / 255f * scalar
    }

    return targetColor
}

fun colorInt(color: Int, targetColor: Float, cfg: ParticleConfig): Int {
    return (color(color, targetColor, cfg) * 255f).toInt()
}

//? if >1.8.9 {
fun spawn(
    options: ParticleOptions,
    config: ParticleConfig,
    level: Level,
    ignoreRange: Boolean,
    x: Double, y: Double, z: Double,
    xOffset: Double, yOffset: Double, zOffset: Double,
) {
    repeat(ceil(config.multiplier).toInt()) {
        isMultiplied = true
        val modX = x - 0.5 + Math.random()
        val modY = y - 0.5 + Math.random()
        val modZ = z - 0.5 + Math.random()
        level.addParticle(
            options,
            ignoreRange,
            //? if >=1.21.4 {
            true,
            //?}
            modX, modY, modZ,
            xOffset, yOffset, zOffset,
        )
    }
}
//?} else {
/*fun spawn(
    type: ParticleType,
    config: ParticleConfig,
    level: World,
    ignoreRange: Boolean,
    x: Double, y: Double, z: Double,
    xOffset: Double, yOffset: Double, zOffset: Double,
    vararg parameters: Int,
) {
    repeat(ceil(config.multiplier).toInt()) {
        isMultiplied = true
        val modX = x - 0.5 + Math.random()
        val modY = y - 0.5 + Math.random()
        val modZ = z - 0.5 + Math.random()
        level.addParticle(
            type,
            ignoreRange,
            modX, modY, modZ,
            xOffset, yOffset, zOffset,
            *parameters,
        )
    }
}
*///?}
