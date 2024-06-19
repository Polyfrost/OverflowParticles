package org.polyfrost.overflowparticles

import cc.polyfrost.oneconfig.events.EventManager
import net.minecraft.client.particle.EntityFX
import net.minecraft.util.EnumParticleTypes
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import org.polyfrost.overflowparticles.config.*
import org.polyfrost.overflowparticles.utils.IconRenderer

@Mod(modid = OverflowParticles.MODID, name = OverflowParticles.NAME, version = OverflowParticles.VERSION, modLanguageAdapter = "cc.polyfrost.oneconfig.utils.KotlinLanguageAdapter")
object OverflowParticles {
    const val MODID = "@ID@"
    const val NAME = "@NAME@"
    const val VERSION = "@VER@"

    var renderingEntity: EntityFX? = null

    var rendering = false

    var configs = HashMap<Int, ParticleConfig>()

    val names = listOf("Entity Death", "Large Explosion", "Huge Explosion", "Firework Spark", "Water Bubble", "Water Splash", "Water Wake", "Liquid", "Depth Suspended", "Critical", "Sharpness", "Smoke", "Large Smoke", "Splash Potion", "Instant Potion", "Potion", "Beacon Effect", "Witch Spell", "Water Drip", "Lava Drip", "Angry Villager", "Happy Villager", "Mycelium", "Note", "Portal", "Enchantment", "Flame", "Lava", "Footstep", "Cloud", "Redstone", "Snowball", "Shovel Snow", "Slime", "Heart", "Barrier", "Item Eat / Break", "Blocks", "Block Dust", "Rain Drop", "Item Pickup", "Mob Appearance")

    var entitiesCache = HashMap<Int, Int>()

    val ignores = listOf(2, 32, 38, 40, 41)

    val unfair = listOf(28, 37)

    var isPolyPatcher = false
        private set

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
        ModConfig
        MinecraftForge.EVENT_BUS.register(IconRenderer)
        EventManager.INSTANCE.register(IconRenderer)
        isPolyPatcher = try {
            Class.forName("club.sk1er.patcher.config.OldPatcherConfig")
            true
        } catch (e: ClassNotFoundException) {
            false
        }
    }

    fun fillConfigs() {
        if (configs.isNotEmpty()) return
        for (i in 0..<EnumParticleTypes.entries.size) {
            if (ignores.contains(i)) continue
            configs[i] = ParticleConfig(names[i], i)
        }
    }
}