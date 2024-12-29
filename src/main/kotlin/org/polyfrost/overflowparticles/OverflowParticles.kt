package org.polyfrost.overflowparticles

import net.minecraft.client.particle.EntityFX
import net.minecraft.util.EnumParticleTypes
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import org.polyfrost.oneconfig.api.event.v1.EventManager
import org.polyfrost.overflowparticles.config.ModConfig
import org.polyfrost.overflowparticles.config.ParticleConfig
import org.polyfrost.overflowparticles.config.Settings
import org.polyfrost.overflowparticles.utils.IconRenderer

@Mod(modid = OverflowParticles.MODID, name = OverflowParticles.NAME, version = OverflowParticles.VERSION, modLanguageAdapter = "org.polyfrost.oneconfig.utils.v1.forge.KotlinLanguageAdapter")
object OverflowParticles {
    const val MODID = "@ID@"
    const val NAME = "@NAME@"
    const val VERSION = "@VER@"

    var renderingEntity: EntityFX? = null

    var rendering = false

    var configs = HashMap<Int, ParticleConfig>()

    val names = listOf("Entity Death", "Large Explosion", "Huge Explosion", "Firework Spark", "Water Bubble", "Water Splash", "Water Wake", "Liquid", "Depth Suspended", "Critical", "Sharpness", "Smoke", "Large Smoke", "Splash Potion", "Instant Potion", "Potion", "Beacon Effect", "Witch Spell", "Water Drip", "Lava Drip", "Angry Villager", "Happy Villager", "Mycelium", "Note", "Portal", "Enchantment", "Flame", "Lava", "Footstep", "Cloud", "Redstone", "Snowball", "Shovel Snow", "Slime", "Heart", "Barrier", "Item Eat / Break", "Blocks", "Block Dust", "Rain Drop", "Item Pickup", "Mob Appearance")

    val ignores = listOf(2, 32, 38, 40, 41)

    val unfair = listOf(28, 37)

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
        ModConfig
        Settings
        IdkImRenamingThisLater
        MinecraftForge.EVENT_BUS.register(IconRenderer)
        EventManager.INSTANCE.register(IconRenderer)
    }

    fun fillConfigs() {
        if (configs.isNotEmpty()) return
        for (i in 0..<EnumParticleTypes.entries.size) {
            if (ignores.contains(i)) continue
            configs[i] = ParticleConfig(names[i], i)
        }
    }
}