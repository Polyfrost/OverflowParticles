package org.polyfrost.overflowparticles.config

import net.minecraft.client.particle.EntityFX
import net.minecraft.util.EnumParticleTypes
import org.polyfrost.overflowparticles.hook.EntityFXHook

object ConfigManager {

    var blockSetting = BlockParticleEntry()

    var configs = HashMap<Int, ParticleConfig>()

    val names = listOf("Entity Death", "Large Explosion", "Huge Explosion", "Firework Spark", "Water Bubble", "Water Splash", "Water Wake", "Liquid", "Depth Suspended", "Critical", "Sharpness", "Smoke", "Large Smoke", "Splash Potion", "Instant Potion", "Potion", "Beacon Effect", "Witch Spell", "Water Drip", "Lava Drip", "Angry Villager", "Happy Villager", "Mycelium", "Note", "Portal", "Enchantment", "Flame", "Lava", "Footstep", "Cloud", "Redstone", "Snowball", "Shovel Snow", "Slime", "Heart", "Barrier", "Item Eat / Break", "Blocks", "Block Dust", "Rain Drop", "Item Pickup", "Mob Appearance")

    val ignores = listOf(2, 32, 38, 40, 41)

    val unfair = listOf(28, 37)

    fun fillConfigs() {
        if (configs.isNotEmpty()) return
        for (i in 0..<EnumParticleTypes.entries.size) {
            if (ignores.contains(i)) continue
            configs[i] = ParticleConfig(names[i], i)
        }
    }

    fun getConfig(entity: EntityFX?): ParticleConfig? {
        if (entity !is EntityFXHook) return null
        return getConfigByID(entity.`overflowParticles$getParticleID`())
    }

    private fun getConfigByID(id: Int): ParticleConfig? {
        if (id == 2) return configs[1]
        if (id == 38) return configs[37]
        return configs[id]
    }

}
