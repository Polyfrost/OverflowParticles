package org.polyfrost.polyparticles

import net.minecraft.client.particle.EntityFX
import net.minecraft.util.EnumParticleTypes
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import org.polyfrost.polyparticles.config.*

@Mod(modid = PolyParticles.MODID, name = PolyParticles.NAME, version = PolyParticles.VERSION, modLanguageAdapter = "cc.polyfrost.oneconfig.utils.KotlinLanguageAdapter")
object PolyParticles {
    const val MODID = "@ID@"
    const val NAME = "@NAME@"
    const val VERSION = "@VER@"

    var renderingEntity: EntityFX? = null

    var rendering = false

    var configs = HashMap<Int, ParticleConfig>()

    var mods: ArrayList<cc.polyfrost.oneconfig.config.data.Mod> = ArrayList()

    val names = listOf("Explosion", "Large Explosion", "Huge Explosion", "Firework Spark", "Water Bubble", "Water Splash", "Water Wake", "Suspended", "Depth Suspended", "Critical", "Sharpness", "Smoke", "Large Smoke", "Spell", "Instant Spell", "Potion", "Ambient Mob Spell", "Witch Spell", "Water Drip", "Lava Drip", "Angry Villager", "Happy Villager", "Town Aura", "Note", "Portal", "Enchantment", "Flame", "Lava", "Footstep", "Cloud", "Redstone", "Snowball", "Shovel Snow", "Slime", "Heart", "Barrier", "Item Eat / Break", "Block Break", "Block Dust", "Water Drop", "Item Pickup", "Mob Appearance")

    var entitiesCache = HashMap<Int, Int>()

    val ignores = listOf(2, 40, 41)

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent?) {
        for (i in 0..<EnumParticleTypes.entries.size) {
            if (ignores.contains(i)) continue
            configs[i] = ParticleConfig(names[i], i)
        }
        MainConfig
        ModConfig
    }


}