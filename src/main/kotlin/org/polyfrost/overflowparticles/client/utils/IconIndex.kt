package org.polyfrost.overflowparticles.client.utils

import org.lwjgl.util.vector.Vector2f

enum class IconIndex(val id: Int, val x: Number, val y: Number) {
    DEATH(0, 1, 1),
    BUBBLE(4, 1, 3),
    SPLASH(5, 7, 2),
    LIQUID(7, 1, 1),
    CRIT(9, 2, 5),
    SHARP(10, 3, 5),
    SMOKE(11, 1, 1),
    SMOKE_LARGE(12, 1, 1),
    SPELL(13, 1, 9),
    WITCH(17, 1, 10),
    ANGRY(20, 2, 6),
    HAPPY(21, 3, 6),
    NOTE(23, 1, 5),
    ENCHANT(25, 2, 15),
    LAVA(27, 2, 4),
    CLOUD(29, 1, 1),
    SHOVEL(32, 1, 1),
    HEART(34, 1, 6),
    RAINDROP(39, 4, 2);

    companion object {
        fun getFromID(id: Int): Vector2f? {
            for (i in entries) {
                if (i.id == id) return Vector2f(((i.x.toFloat() - 1) * 8), ((i.y.toFloat() - 1) * 8))
            }
            return null
        }
    }
}