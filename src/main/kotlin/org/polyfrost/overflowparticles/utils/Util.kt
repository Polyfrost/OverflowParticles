package org.polyfrost.overflowparticles.utils

import net.minecraft.client.particle.EntityFX
import net.minecraft.world.IWorldAccess
import org.polyfrost.overflowparticles.config.ParticleConfig
import org.polyfrost.overflowparticles.hook.EntityFXHook
import kotlin.math.ceil

var multiplied = false

fun setParticleEntityID(entity: EntityFX, id: Int) {
    if (entity is EntityFXHook) {
        entity.`overflowParticles$setParticleID`(id)
    }
}

fun color(color: Int, targetColor: Float, cfg: ParticleConfig): Float =
    if (cfg.customColor) color / 255f * if (cfg.colorMode == 1) 1f else targetColor else targetColor

fun colorInt(color: Int, targetColor: Float, cfg: ParticleConfig): Int = (color(color, targetColor, cfg) * 255f).toInt()

fun spawn(config: ParticleConfig, worldAccesses: List<IWorldAccess>, particleID: Int, ignoreRange: Boolean, x: Double, y: Double, z: Double, xOffset: Double, yOffset: Double, zOffset: Double, vararg arguments: Int) {
    repeat(ceil(config.multiplier).toInt()) {
        for (worldAccess in worldAccesses) {
            val modX = x - 0.5 + Math.random()
            val modY = y - 0.5 + Math.random()
            val modZ = z - 0.5 + Math.random()
            worldAccess.spawnParticle(particleID, ignoreRange, modX, modY, modZ, xOffset, yOffset, zOffset, *arguments)
        }
    }
}