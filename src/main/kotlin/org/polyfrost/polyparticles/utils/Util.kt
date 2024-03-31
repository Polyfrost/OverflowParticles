package org.polyfrost.polyparticles.utils

import net.minecraft.world.IWorldAccess
import org.polyfrost.polyparticles.config.ParticleConfig

fun color(color: Int, targetColor: Float, cfg: ParticleConfig) : Float =
    if (cfg.customColor) color / 255f * if (cfg.colorMode) 1f else targetColor else targetColor

fun colorInt(color: Int, targetColor: Float, cfg: ParticleConfig) : Int =
    (color(color, targetColor, cfg) * 255f).toInt()

fun spawn(config: ParticleConfig, worldAccesses: List<IWorldAccess>, particleID: Int, ignoreRange: Boolean, x: Double, y: Double, z: Double, xOffset: Double, yOffset: Double, zOffset: Double, vararg arguments: Int) {
    repeat(config.multiplier) {
        for (worldAccess in worldAccesses) {
            val modX = x - 0.5 + Math.random()
            val modY = y - 0.5 + Math.random()
            val modZ = z - 0.5 + Math.random()
            worldAccess.spawnParticle(particleID, ignoreRange, modX, modY, modZ, xOffset, yOffset, zOffset, *arguments);
        }
    }
}