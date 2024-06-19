package org.polyfrost.overflowparticles.utils

import net.minecraft.world.IWorldAccess
import org.polyfrost.overflowparticles.config.MainConfig
import org.polyfrost.overflowparticles.config.ParticleConfig

fun color(color: Int, targetColor: Float, cfg: ParticleConfig): Float =
    if (cfg.entry.customColor) color / 255f * if (cfg.entry.colorMode) 1f else targetColor else targetColor

fun colorInt(color: Int, targetColor: Float, cfg: ParticleConfig): Int = (color(color, targetColor, cfg) * 255f).toInt()

fun spawn(
    config: ParticleConfig,
    worldAccesses: List<IWorldAccess>,
    particleID: Int,
    ignoreRange: Boolean,
    x: Double,
    y: Double,
    z: Double,
    xOffset: Double,
    yOffset: Double,
    zOffset: Double,
    vararg arguments: Int
) {
    val multiplier = config.entry.multiplier
    val integerPart = multiplier.toInt()
    val fractionalPart = multiplier - integerPart

    repeat(integerPart) {
        spawnParticle(worldAccesses, particleID, ignoreRange, x, y, z, xOffset, yOffset, zOffset, *arguments)
    }

    if (fractionalPart > 0) {
        if (Math.random() < fractionalPart) {
            spawnParticle(worldAccesses, particleID, ignoreRange, x, y, z, xOffset, yOffset, zOffset, *arguments)
        }
    }
}

private fun spawnParticle(
    worldAccesses: List<IWorldAccess>,
    particleID: Int,
    ignoreRange: Boolean,
    x: Double,
    y: Double,
    z: Double,
    xOffset: Double,
    yOffset: Double,
    zOffset: Double,
    vararg arguments: Int
) {
    for (worldAccess in worldAccesses) {
        val modX = x - (if (!MainConfig.settings.cleanerParticles) 0.5 + Math.random() else 0.0)
        val modY = y - (if (!MainConfig.settings.cleanerParticles) 0.5 + Math.random() else 0.0)
        val modZ = z - (if (!MainConfig.settings.cleanerParticles) 0.5 + Math.random() else 0.0)
        worldAccess.spawnParticle(particleID, ignoreRange, modX, modY, modZ, xOffset, yOffset, zOffset, *arguments)
    }
}