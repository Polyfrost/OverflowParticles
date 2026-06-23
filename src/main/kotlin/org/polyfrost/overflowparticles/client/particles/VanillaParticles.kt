package org.polyfrost.overflowparticles.client.particles

import net.minecraft.core.particles.ParticleType
import net.minecraft.core.particles.ParticleTypes
import org.apache.logging.log4j.LogManager
import org.polyfrost.overflowparticles.client.particles.ParticleRegistry.location

object VanillaParticles {
    private val LOGGER = LogManager.getLogger("OverflowParticles / Vanilla Particles")

    private val _registry = mutableMapOf<ParticleType<*>, ParticleInfo>()

    /**
     * View-only instance of the particle type registry.
     */
    val registry: Map<ParticleType<*>, ParticleInfo>
        get() = _registry.toMap()

    // 1.8.9 - 1.12.2
    @JvmField val EXPLOSION_NORMAL = create("Explosion", ParticleTypes.EXPLOSION)
    @JvmField val EXPLOSION_LARGE = create("Large Explosion", ParticleTypes.EXPLOSION_EMITTER)
    @JvmField val EXPLOSION_HUGE = create("Huge Explosion", ParticleTypes.EXPLOSION, redirectsTo = EXPLOSION_NORMAL, isIgnored = true)
    @JvmField val FIREWORK_SPARK = create("Firework Spark", ParticleTypes.FIREWORK)
    @JvmField val WATER_BUBBLE = create("Water Bubble", ParticleTypes.BUBBLE)
    @JvmField val WATER_SPLASH = create("Water Splash", ParticleTypes.SPLASH)
    @JvmField val WATER_WAKE = create("Water Wake", ParticleTypes.FISHING)
    @JvmField val SUSPENDED = create("Suspended", ParticleTypes.UNDERWATER)
    @JvmField val SUSPENDED_DEPTH = create("Suspended Depth", ParticleTypes.UNDERWATER)
    @JvmField val CRITICAL = create("Critical", ParticleTypes.CRIT)
    @JvmField val CRITICAL_MAGIC = create("Magic Critical (Sharpness)", ParticleTypes.ENCHANTED_HIT)
    @JvmField val SMOKE_NORMAL = create("Smoke", ParticleTypes.SMOKE)
    @JvmField val SMOKE_LARGE = create("Large Smoke", ParticleTypes.LARGE_SMOKE)
    @JvmField val SPLASH_POTION = create("Splash Potion", ParticleTypes.EFFECT)
    @JvmField val INSTANT_POTION = create("Instant Potion", ParticleTypes.INSTANT_EFFECT)
    @JvmField val MOB_POTION = create("Potion", ParticleTypes.ENTITY_EFFECT)
    @JvmField val WITCH_SPELL = create("Witch Spell", ParticleTypes.WITCH)
    @JvmField val WATER_DRIP = create("Water Drip", ParticleTypes.DRIPPING_WATER)
    @JvmField val LAVA_DRIP = create("Lava Drip", ParticleTypes.DRIPPING_LAVA)
    @JvmField val ANGRY_VILLAGER = create("Angry Villager", ParticleTypes.ANGRY_VILLAGER)
    @JvmField val HAPPY_VILLAGER = create("Happy Villager", ParticleTypes.HAPPY_VILLAGER)
    @JvmField val MYCELIUM = create("Mycelium", ParticleTypes.MYCELIUM)
    @JvmField val NOTE = create("Note", ParticleTypes.NOTE)
    @JvmField val PORTAL = create("Portal", ParticleTypes.PORTAL)
    @JvmField val ENCHANTMENT_RUNE = create("Enchantment Rune", ParticleTypes.ENCHANT)
    @JvmField val FLAME = create("Flame", ParticleTypes.FLAME)
    @JvmField val LAVA = create("Lava", ParticleTypes.LAVA)
    @JvmField val FOOTSTEP = create("Footstep", ParticleTypes.CLOUD, isIgnored = true, isUnfair = true)
    @JvmField val CLOUD = create("Cloud", ParticleTypes.CLOUD)
    @JvmField val REDSTONE = create("Redstone", ParticleTypes.DUST)
    @JvmField val SNOWBALL = create("Snowball", ParticleTypes.ITEM_SNOWBALL)
    @JvmField val SLIME = create("Slime", ParticleTypes.ITEM_SLIME)
    @JvmField val HEART = create("Heart", ParticleTypes.HEART)
    @JvmField val ITEM_EAT_BREAK = create("Item Eat / Break", ParticleTypes.ITEM)
    @JvmField val BLOCKS = create("Blocks", ParticleTypes.BLOCK, isUnfair = true)
    @JvmField val BLOCK_DUST = create("Block Dust", ParticleTypes.FALLING_DUST, redirectsTo = BLOCKS, isIgnored = true)
    @JvmField val RAIN_DROP = create("Rain Drop", ParticleTypes.RAIN)
    @JvmField val ITEM_PICKUP = create("Item Pickup", ParticleTypes.CLOUD, isIgnored = true)
    @JvmField val MOB_APPEARANCE = create("Mob Appearance", ParticleTypes.ELDER_GUARDIAN, isIgnored = true)
    @JvmField val DRAGON_BREATH = create("Dragon Breath", ParticleTypes.DRAGON_BREATH)
    @JvmField val END_ROD = create("End Rod", ParticleTypes.END_ROD)
    @JvmField val DAMAGE_INDICATOR = create("Damage Indicator", ParticleTypes.DAMAGE_INDICATOR)
    @JvmField val SWEEP_ATTACK = create("Sweep Attack", ParticleTypes.SWEEP_ATTACK)
    @JvmField val FALLING_DUST = create("Falling Dust", ParticleTypes.FALLING_DUST, isIgnored = true)
    @JvmField val TOTEM = create("Totem", ParticleTypes.TOTEM_OF_UNDYING)
    @JvmField val SPIT = create("Spit", ParticleTypes.SPIT)

    // 1.16+
    @JvmField val BUBBLE_COLUMN_UP = create("Bubble Column Up", ParticleTypes.BUBBLE_COLUMN_UP)
    @JvmField val BUBBLE_POP = create("Bubble Pop", ParticleTypes.BUBBLE_POP)
    @JvmField val CURRENT_DOWN = create("Bubble Column Down", ParticleTypes.CURRENT_DOWN)
    @JvmField val DOLPHIN = create("Dolphin", ParticleTypes.DOLPHIN)
    @JvmField val NAUTILUS = create("Conduit (Nautilus)", ParticleTypes.NAUTILUS)
    @JvmField val CAMPFIRE_COSY_SMOKE = create("Campfire Smoke", ParticleTypes.CAMPFIRE_COSY_SMOKE)
    @JvmField val CAMPFIRE_SIGNAL_SMOKE = create("Campfire Signal Smoke", ParticleTypes.CAMPFIRE_SIGNAL_SMOKE)
    @JvmField val DRIPPING_HONEY = create("Honey Drip", ParticleTypes.DRIPPING_HONEY)
    @JvmField val FALLING_HONEY = create("Falling Honey", ParticleTypes.FALLING_HONEY)
    @JvmField val LANDING_HONEY = create("Landing Honey", ParticleTypes.LANDING_HONEY)
    @JvmField val FALLING_NECTAR = create("Falling Nectar", ParticleTypes.FALLING_NECTAR)
    @JvmField val SOUL_FIRE_FLAME = create("Soul Fire Flame", ParticleTypes.SOUL_FIRE_FLAME)
    @JvmField val CRIMSON_SPORE = create("Crimson Spore", ParticleTypes.CRIMSON_SPORE)
    @JvmField val WARPED_SPORE = create("Warped Spore", ParticleTypes.WARPED_SPORE)
    @JvmField val ASH = create("Ash", ParticleTypes.ASH)
    @JvmField val WHITE_ASH = create("White Ash", ParticleTypes.WHITE_ASH)
    @JvmField val DRIPPING_OBSIDIAN_TEAR = create("Obsidian Tear Drip", ParticleTypes.DRIPPING_OBSIDIAN_TEAR)
    @JvmField val FALLING_OBSIDIAN_TEAR = create("Falling Obsidian Tear", ParticleTypes.FALLING_OBSIDIAN_TEAR)
    @JvmField val LANDING_OBSIDIAN_TEAR = create("Landing Obsidian Tear", ParticleTypes.LANDING_OBSIDIAN_TEAR)
    //? if <=1.16.5 {
    /*@JvmField val BARRIER = create("Barrier", ParticleTypes.BARRIER)
    *///?}
    //? if <=1.20.4 {
    /*@JvmField val BEACON_EFFECT = create("Beacon Effect", ParticleTypes.AMBIENT_ENTITY_EFFECT)
    *///?}

    // 1.17+
    //? if >=1.17 {
    @JvmField val GLOW = create("Glow", ParticleTypes.GLOW)
    @JvmField val GLOW_SQUID_INK = create("Glow Squid Ink", ParticleTypes.GLOW_SQUID_INK)
    @JvmField val ELECTRIC_SPARK = create("Electric Spark", ParticleTypes.ELECTRIC_SPARK)
    @JvmField val SNOWFLAKE = create("Snowflake", ParticleTypes.SNOWFLAKE)
    @JvmField val DRIPPING_DRIPSTONE_WATER = create("Dripstone Water Drip", ParticleTypes.DRIPPING_DRIPSTONE_WATER)
    @JvmField val FALLING_DRIPSTONE_WATER = create("Falling Dripstone Water", ParticleTypes.FALLING_DRIPSTONE_WATER)
    @JvmField val DRIPPING_DRIPSTONE_LAVA = create("Dripstone Lava Drip", ParticleTypes.DRIPPING_DRIPSTONE_LAVA)
    @JvmField val FALLING_DRIPSTONE_LAVA = create("Falling Dripstone Lava", ParticleTypes.FALLING_DRIPSTONE_LAVA)
    @JvmField val SPORE_BLOSSOM_AIR = create("Spore Blossom Air", ParticleTypes.SPORE_BLOSSOM_AIR)
    @JvmField val FALLING_SPORE_BLOSSOM = create("Falling Spore Blossom", ParticleTypes.FALLING_SPORE_BLOSSOM)
    @JvmField val WAX_ON = create("Wax On", ParticleTypes.WAX_ON)
    @JvmField val WAX_OFF = create("Wax Off", ParticleTypes.WAX_OFF)
    @JvmField val SCRAPE = create("Scrape", ParticleTypes.SCRAPE)
    //?}

    // 1.19+
    //? if >=1.19 {
    @JvmField val VIBRATION = create("Vibration", ParticleTypes.VIBRATION)
    @JvmField val SCULK_CHARGE = create("Sculk Charge", ParticleTypes.SCULK_CHARGE)
    @JvmField val SCULK_CHARGE_POP = create("Sculk Charge Pop", ParticleTypes.SCULK_CHARGE_POP)
    @JvmField val SCULK_SOUL = create("Sculk Soul", ParticleTypes.SCULK_SOUL)
    @JvmField val SHRIEK = create("Shriek", ParticleTypes.SHRIEK)
    @JvmField val SONIC_BOOM = create("Sonic Boom", ParticleTypes.SONIC_BOOM)
    //?}

    // 1.20+
    //? if >=1.20 {
    @JvmField val CHERRY_LEAVES = create("Cherry Leaves", ParticleTypes.CHERRY_LEAVES)
    //?}

    // 1.21.5+
    //? if >=1.21.5 {
    /*@JvmField val GUST = create("Gust", ParticleTypes.GUST)
    @JvmField val GUST_EMITTER_SMALL = create("Gust Emitter (Small)", ParticleTypes.GUST_EMITTER_SMALL)
    @JvmField val GUST_EMITTER_LARGE = create("Gust Emitter (Large)", ParticleTypes.GUST_EMITTER_LARGE)
    @JvmField val DUST_PLUME = create("Dust Plume", ParticleTypes.DUST_PLUME)
    @JvmField val DUST_PILLAR = create("Dust Pillar", ParticleTypes.DUST_PILLAR)
    @JvmField val EGG_CRACK = create("Egg Crack", ParticleTypes.EGG_CRACK)
    @JvmField val OMINOUS_SPAWNING = create("Ominous Spawning", ParticleTypes.OMINOUS_SPAWNING)
    @JvmField val PALE_OAK_LEAVES = create("Pale Oak Leaves", ParticleTypes.PALE_OAK_LEAVES)
    @JvmField val ITEM_COBWEB = create("Item Cobweb", ParticleTypes.ITEM_COBWEB)
    @JvmField val INFESTED = create("Infested", ParticleTypes.INFESTED)
    @JvmField val RAID_OMEN = create("Raid Omen", ParticleTypes.RAID_OMEN)
    @JvmField val WHITE_SMOKE = create("White Smoke", ParticleTypes.WHITE_SMOKE)
    @JvmField val BLOCK_MARKER = create("Block Marker", ParticleTypes.BLOCK_MARKER)
    @JvmField val BLOCK_CRUMBLE = create("Block Crumble", ParticleTypes.BLOCK_CRUMBLE)
    *///?}

    val fireworkTriggered = setOf(EXPLOSION_NORMAL, EXPLOSION_LARGE, EXPLOSION_HUGE, FIREWORK_SPARK)

    fun preload() {
        // Intentionally left blank.
        // This method is just to ensure the class is loaded and the static initializers run.
    }

    private fun create(
        name: String,
        id: ParticleType<*>,
        redirectsTo: ParticleInfo? = null,
        isIgnored: Boolean = false,
        isUnfair: Boolean = false
    ): ParticleInfo {
        val particleInfo = ParticleRegistry.create(name, id, redirectsTo, isIgnored, isUnfair)
        if (particleInfo.id in _registry) {
            LOGGER.warn("Particle with ID ${location(particleInfo.id)} is already registered as a vanilla particle.")
            return particleInfo
        }

        _registry[particleInfo.id] = particleInfo
        return particleInfo
    }
}
