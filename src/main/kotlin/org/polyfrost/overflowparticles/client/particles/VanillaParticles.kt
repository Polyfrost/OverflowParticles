package org.polyfrost.overflowparticles.client.particles

import net.minecraft.util.EnumParticleTypes
import org.apache.logging.log4j.LogManager

object VanillaParticles {
    private val LOGGER = LogManager.getLogger("OverflowParticles / Vanilla Particles")

    private val _registry = mutableMapOf<Int, ParticleInfo>()

    /**
     * View-only instance of the particle type registry.
     */
    val registry: Map<Int, ParticleInfo>
        get() = _registry.toMap()

    @JvmField val EXPLOSION_NORMAL = create("Explosion", EnumParticleTypes.EXPLOSION_NORMAL)
    @JvmField val EXPLOSION_LARGE = create("Large Explosion", EnumParticleTypes.EXPLOSION_LARGE)
    @JvmField val EXPLOSION_HUGE = create("Huge Explosion", EnumParticleTypes.EXPLOSION_HUGE, redirectsTo = EXPLOSION_NORMAL, isIgnored = true)
    @JvmField val FIREWORK_SPARK = create("Firework Spark", EnumParticleTypes.FIREWORKS_SPARK)
    @JvmField val WATER_BUBBLE = create("Water Bubble", EnumParticleTypes.WATER_BUBBLE)
    @JvmField val WATER_SPLASH = create("Water Splash", EnumParticleTypes.WATER_SPLASH)
    @JvmField val WATER_WAKE = create("Water Wake", EnumParticleTypes.WATER_WAKE)
    @JvmField val SUSPENDED = create("Suspended", EnumParticleTypes.SUSPENDED)
    @JvmField val SUSPENDED_DEPTH = create("Suspended Depth", EnumParticleTypes.SUSPENDED_DEPTH)
    @JvmField val CRITICAL = create("Critical", EnumParticleTypes.CRIT)
    @JvmField val CRITICAL_MAGIC = create("Magic Critical (Sharpness)", EnumParticleTypes.CRIT_MAGIC)
    @JvmField val SMOKE_NORMAL = create("Smoke", EnumParticleTypes.SMOKE_NORMAL)
    @JvmField val SMOKE_LARGE = create("Large Smoke", EnumParticleTypes.SMOKE_LARGE)
    @JvmField val SPLASH_POTION = create("Splash Potion", EnumParticleTypes.SPELL)
    @JvmField val INSTANT_POTION = create("Instant Potion", EnumParticleTypes.SPELL_INSTANT)
    @JvmField val MOB_POTION = create("Potion", EnumParticleTypes.SPELL_MOB)
    @JvmField val BEACON_EFFECT = create("Beacon Effect", EnumParticleTypes.SPELL_MOB_AMBIENT)
    @JvmField val WITCH_SPELL = create("Witch Spell", EnumParticleTypes.SPELL_WITCH)
    @JvmField val WATER_DRIP = create("Water Drip", EnumParticleTypes.DRIP_WATER)
    @JvmField val LAVA_DRIP = create("Lava Drip", EnumParticleTypes.DRIP_LAVA)
    @JvmField val ANGRY_VILLAGER = create("Angry Villager", EnumParticleTypes.VILLAGER_ANGRY)
    @JvmField val HAPPY_VILLAGER = create("Happy Villager", EnumParticleTypes.VILLAGER_HAPPY)
    @JvmField val MYCELIUM = create("Mycelium", EnumParticleTypes.TOWN_AURA)
    @JvmField val NOTE = create("Note", EnumParticleTypes.NOTE)
    @JvmField val PORTAL = create("Portal", EnumParticleTypes.PORTAL)
    @JvmField val ENCHANTMENT_RUNE = create("Enchantment Rune", EnumParticleTypes.ENCHANTMENT_TABLE)
    @JvmField val FLAME = create("Flame", EnumParticleTypes.FLAME)
    @JvmField val LAVA = create("Lava", EnumParticleTypes.LAVA)
    @JvmField val FOOTSTEP = create("Footstep", EnumParticleTypes.FOOTSTEP, isIgnored = true, isUnfair = true)
    @JvmField val CLOUD = create("Cloud", EnumParticleTypes.CLOUD)
    @JvmField val REDSTONE = create("Redstone", EnumParticleTypes.REDSTONE)
    @JvmField val SNOWBALL = create("Snowball", EnumParticleTypes.SNOWBALL)
    @JvmField val SHOVEL_SNOW = create("Shovel Snow", EnumParticleTypes.SNOW_SHOVEL, isIgnored = true)
    @JvmField val SLIME = create("Slime", EnumParticleTypes.SLIME)
    @JvmField val HEART = create("Heart", EnumParticleTypes.HEART)
    @JvmField val BARRIER = create("Barrier", EnumParticleTypes.BARRIER)
    @JvmField val ITEM_EAT_BREAK = create("Item Eat / Break", EnumParticleTypes.ITEM_CRACK)
    @JvmField val BLOCKS = create("Blocks", EnumParticleTypes.BLOCK_CRACK, isUnfair = true)
    @JvmField val BLOCK_DUST = create("Block Dust", EnumParticleTypes.BLOCK_DUST, redirectsTo = BLOCKS, isIgnored = true)
    @JvmField val RAIN_DROP = create("Rain Drop", EnumParticleTypes.WATER_DROP)
    @JvmField val ITEM_PICKUP = create("Item Pickup", EnumParticleTypes.ITEM_TAKE, isIgnored = true)
    @JvmField val MOB_APPEARANCE = create("Mob Appearance", EnumParticleTypes.MOB_APPEARANCE, isIgnored = true)

    //#if MC >= 1.12.2
    //$$ @JvmField val DRAGON_BREATH = create("Dragon Breath", ParticleType.DRAGON_BREATH)
    //$$ @JvmField val END_ROD = create("End Rod", ParticleType.END_ROD)
    //$$ @JvmField val DAMAGE_INDICATOR = create("Damage Indicator", ParticleType.DAMAGE_INDICATOR)
    //$$ @JvmField val SWEEP_ATTACK = create("Sweep Attack", ParticleType.SWEEP_ATTACK)
    //$$ @JvmField val FALLING_DUST = create("Falling Dust", ParticleType.FALLING_DUST, isIgnored = true)
    //$$ @JvmField val TOTEM = create("Totem", ParticleType.TOTEM)
    //$$ @JvmField val SPIT = create("Spit", ParticleType.SPIT)
    //#endif

    val fireworkTriggered = setOf(EXPLOSION_NORMAL, EXPLOSION_LARGE, EXPLOSION_HUGE, FIREWORK_SPARK)

    fun preload() {
        // Intentionally left blank.
        // This method is just to ensure the class is loaded and the static initializers run.
    }

    private fun create(
        name: String,
        id: Int,
        redirectsTo: ParticleInfo? = null,
        isIgnored: Boolean = false,
        isUnfair: Boolean = false
    ): ParticleInfo {
        val particleInfo = ParticleRegistry.create(name, id, redirectsTo, isIgnored, isUnfair)
        if (particleInfo.id in _registry) {
            LOGGER.warn("Particle with ID ${particleInfo.id} is already registered as a vanilla particle.")
            return particleInfo
        }

        _registry[particleInfo.id] = particleInfo
        return particleInfo
    }

    private fun create(
        name: String,
        vanilla: EnumParticleTypes,
        redirectsTo: ParticleInfo? = null,
        isIgnored: Boolean = false,
        isUnfair: Boolean = false
    ): ParticleInfo {
        return create(name, vanilla.particleID, redirectsTo, isIgnored, isUnfair)
    }
}
