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

    var configs: ArrayList<ParticleConfig> = ArrayList()

    var mods: ArrayList<cc.polyfrost.oneconfig.config.data.Mod> = ArrayList()

    val names = listOf("Normal Explosion", "Large Explosion", "Huge Explosion", "Firework Spark", "Water Bubble", "Water Splash", "Water Wake", "Suspended", "Suspended Depth", "Critical", "Sharpness", "Normal Smoke", "Large Smoke", "Spell", "Instant Spell", "Mob Spell", "Ambient Mob Spell", "Witch Spell", "Water Drip", "Lava Drip", "Angry Villager", "Happy Villager", "Town Aura", "Note", "Portal", "Enchantment Table", "Flame", "Lava", "Footstep", "Cloud", "Redstone", "Snowball", "Shovel Snow", "Slime", "Heart", "Barrier", "Item Break", "Block Break", "Block Dust", "Water Drop", "Take Item", "Mob Appearance")

    var particles = HashMap<EntityFX, Int>()

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent?) {
        for (i in 0..<EnumParticleTypes.entries.size) {
            configs.add(ParticleConfig(names[i], i))
        }
        ModConfig
    }


}