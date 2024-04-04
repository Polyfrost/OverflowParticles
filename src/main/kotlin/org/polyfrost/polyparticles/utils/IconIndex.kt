package org.polyfrost.polyparticles.utils

import org.lwjgl.util.vector.Vector2f

enum class IconIndex(val id: Int, val x: Number, val y: Number) {
    BUBBLE(4, 1, 3),
    HAPPY(21, 3, 6),
    NOTE(23, 1, 5),
    HEART(34, 1, 6),
    ANGRY(20, 2, 6),
    CRIT(9, 2, 5),
    ENCHANT(25, 2, 15),
    SHARP(10, 3, 5);


    companion object {
        fun getFromID(id: Int): Vector2f? {
            for (i in entries) {
                if (i.id == id) return Vector2f(((i.x.toFloat() - 1) * 8), ((i.y.toFloat() - 1) * 8))
            }
            return null
        }
    }
}