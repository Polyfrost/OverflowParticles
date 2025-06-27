package org.polyfrost.overflowparticles.client.utils

import net.minecraft.util.EnumParticleTypes

object VanillaParticles {

    @JvmField
    val EXPLOSION_NORMAL = ParticleData("Explosion", EnumParticleTypes.EXPLOSION_NORMAL)

    @JvmField
    val EXPLOSION_LARGE = ParticleData("Large Explosion", EnumParticleTypes.EXPLOSION_LARGE)

    @JvmField
    val EXPLOSION_HUGE = ParticleData("Huge Explosion", EnumParticleTypes.EXPLOSION_HUGE, redirect = EXPLOSION_NORMAL, isIgnored = true)

    @JvmField
    val FIREWORK_SPARK = ParticleData("Firework Spark", EnumParticleTypes.FIREWORKS_SPARK)

    @JvmField
    val WATER_BUBBLE = ParticleData("Water Bubble", EnumParticleTypes.WATER_BUBBLE)

    @JvmField
    val WATER_SPLASH = ParticleData("Water Splash", EnumParticleTypes.WATER_SPLASH)

    @JvmField
    val WATER_WAKE = ParticleData("Water Wake", EnumParticleTypes.WATER_WAKE)

    @JvmField
    val SUSPENDED = ParticleData("Suspended", EnumParticleTypes.SUSPENDED)

    @JvmField
    val SUSPENDED_DEPTH = ParticleData("Suspended Depth", EnumParticleTypes.SUSPENDED_DEPTH)

    @JvmField
    val CRITICAL = ParticleData("Critical", EnumParticleTypes.CRIT)

    @JvmField
    val CRITICAL_MAGIC = ParticleData("Magic Critical (Sharpness)", EnumParticleTypes.CRIT_MAGIC)

    @JvmField
    val SMOKE_NORMAL = ParticleData("Smoke", EnumParticleTypes.SMOKE_NORMAL)

    @JvmField
    val SMOKE_LARGE = ParticleData("Large Smoke", EnumParticleTypes.SMOKE_LARGE)

    @JvmField
    val SPLASH_POTION = ParticleData("Splash Potion", EnumParticleTypes.SPELL)

    @JvmField
    val INSTANT_POTION = ParticleData("Instant Potion", EnumParticleTypes.SPELL_INSTANT)

    @JvmField
    val MOB_POTION = ParticleData("Potion", EnumParticleTypes.SPELL_MOB)

    @JvmField
    val BEACON_EFFECT = ParticleData("Beacon Effect", EnumParticleTypes.SPELL_MOB_AMBIENT)

    @JvmField
    val WITCH_SPELL = ParticleData("Witch Spell", EnumParticleTypes.SPELL_WITCH)

    @JvmField
    val WATER_DRIP = ParticleData("Water Drip", EnumParticleTypes.DRIP_WATER)

    @JvmField
    val LAVA_DRIP = ParticleData("Lava Drip", EnumParticleTypes.DRIP_LAVA)

    @JvmField
    val ANGRY_VILLAGER = ParticleData("Angry Villager", EnumParticleTypes.VILLAGER_ANGRY)

    @JvmField
    val HAPPY_VILLAGER = ParticleData("Happy Villager", EnumParticleTypes.VILLAGER_HAPPY)

    @JvmField
    val MYCELIUM = ParticleData("Mycelium", EnumParticleTypes.TOWN_AURA)

    @JvmField
    val NOTE = ParticleData("Note", EnumParticleTypes.NOTE)

    @JvmField
    val PORTAL = ParticleData("Portal", EnumParticleTypes.PORTAL)

    @JvmField
    val ENCHANTMENT_RUNE = ParticleData("Enchantment Rune", EnumParticleTypes.ENCHANTMENT_TABLE)

    @JvmField
    val FLAME = ParticleData("Flame", EnumParticleTypes.FLAME)

    @JvmField
    val LAVA = ParticleData("Lava", EnumParticleTypes.LAVA)

    @JvmField
    val FOOTSTEP = ParticleData("Footstep", EnumParticleTypes.FOOTSTEP, isIgnored = true, isUnfair = true)

    @JvmField
    val CLOUD = ParticleData("Cloud", EnumParticleTypes.CLOUD)

    @JvmField
    val REDSTONE = ParticleData("Redstone", EnumParticleTypes.REDSTONE)

    @JvmField
    val SNOWBALL = ParticleData("Snowball", EnumParticleTypes.SNOWBALL)

    @JvmField
    val SHOVEL_SNOW = ParticleData("Shovel Snow", EnumParticleTypes.SNOW_SHOVEL, isIgnored = true)

    @JvmField
    val SLIME = ParticleData("Slime", EnumParticleTypes.SLIME)

    @JvmField
    val HEART = ParticleData("Heart", EnumParticleTypes.HEART)

    @JvmField
    val BARRIER = ParticleData("Barrier", EnumParticleTypes.BARRIER)

    @JvmField
    val ITEM_EAT_BREAK = ParticleData("Item Eat / Break", EnumParticleTypes.ITEM_CRACK)

    @JvmField
    val BLOCKS = ParticleData("Blocks", EnumParticleTypes.BLOCK_CRACK, isUnfair = true)

    @JvmField
    val BLOCK_DUST = ParticleData("Block Dust", EnumParticleTypes.BLOCK_DUST, redirect = BLOCKS, isIgnored = true)

    @JvmField
    val RAIN_DROP = ParticleData("Rain Drop", EnumParticleTypes.WATER_DROP)

    @JvmField
    val ITEM_PICKUP = ParticleData("Item Pickup", EnumParticleTypes.ITEM_TAKE, isIgnored = true)

    @JvmField
    val MOB_APPEARANCE = ParticleData("Mob Appearance", EnumParticleTypes.MOB_APPEARANCE, isIgnored = true)

    //#if MC >= 1.12.2
    //$$ @JvmField
    //$$ val DRAGON_BREATH = ParticleData("Dragon Breath", ParticleType.DRAGON_BREATH)
    //$$
    //$$ @JvmField
    //$$ val END_ROD = ParticleData("End Rod", ParticleType.END_ROD)
    //$$
    //$$ @JvmField
    //$$ val DAMAGE_INDICATOR = ParticleData("Damage Indicator", ParticleType.DAMAGE_INDICATOR)
    //$$
    //$$ @JvmField
    //$$ val SWEEP_ATTACK = ParticleData("Sweep Attack", ParticleType.SWEEP_ATTACK)
    //$$
    //$$ @JvmField
    //$$ val FALLING_DUST = ParticleData("Falling Dust", ParticleType.FALLING_DUST, isIgnored = true)
    //$$
    //$$ @JvmField
    //$$ val TOTEM = ParticleData("Totem", ParticleType.TOTEM)
    //$$
    //$$ @JvmField
    //$$ val SPIT = ParticleData("Spit", ParticleType.SPIT)
    //#endif

    @JvmField
    val entries = listOf(
        EXPLOSION_NORMAL, EXPLOSION_LARGE, EXPLOSION_HUGE,
        FIREWORK_SPARK, WATER_BUBBLE, WATER_SPLASH,
        WATER_WAKE, SUSPENDED, SUSPENDED_DEPTH,
        CRITICAL, CRITICAL_MAGIC, SMOKE_NORMAL,
        SMOKE_LARGE, SPLASH_POTION, INSTANT_POTION,
        MOB_POTION, BEACON_EFFECT, WITCH_SPELL,
        WATER_DRIP, LAVA_DRIP, ANGRY_VILLAGER,
        HAPPY_VILLAGER, MYCELIUM, NOTE,
        PORTAL, ENCHANTMENT_RUNE, FLAME,
        LAVA, FOOTSTEP, CLOUD,
        REDSTONE, SNOWBALL, SHOVEL_SNOW,
        SLIME, HEART, BARRIER,
        ITEM_EAT_BREAK, BLOCKS, BLOCK_DUST,
        RAIN_DROP, ITEM_PICKUP, MOB_APPEARANCE,
        //#if MC >= 1.12.2
        //$$ DRAGON_BREATH, END_ROD, DAMAGE_INDICATOR,
        //$$ SWEEP_ATTACK, FALLING_DUST, TOTEM, SPIT
        //#endif
    )

    @JvmStatic
    fun initialize() {
        entries.forEach(ParticleData::register)
    }

}
