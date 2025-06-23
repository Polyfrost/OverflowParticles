package org.polyfrost.overflowparticles.client

enum class ParticleType(
    val particleName: String,
    val id: Int,
    val redirect: ParticleType? = null,
    val isIgnored: Boolean = false,
    val isUnfair: Boolean = false
) {

    EXPLOSION("Entity Death", 0),
    LARGE_EXPLOSION("Large Explosion", 1),
    HUGE_EXPLOSION("Huge Explosion", 2, redirect = EXPLOSION, isIgnored = true),
    FIREWORK_SPARK("Firework Spark", 3),
    WATER_BUBBLE("Water Bubble", 4),
    WATER_SPLASH("Water Splash", 5),
    WATER_WAKE("Water Wake", 6),
    LIQUID("Liquid", 7),
    DEPTH_SUSPENDED("Depth Suspended", 8),
    CRITICAL("Critical", 9),
    SHARPNESS("Sharpness", 10),
    SMOKE("Smoke", 11),
    LARGE_SMOKE("Large Smoke", 12),
    SPLASH_POTION("Splash Potion", 13),
    INSTANT_POTION("Instant Potion", 14),
    MOB_POTION("Potion", 15),
    BEACON_EFFECT("Beacon Effect", 16),
    WITCH_SPELL("Witch Spell", 17),
    WATER_DRIP("Water Drip", 18),
    LAVA_DRIP("Lava Drip", 19),
    ANGRY_VILLAGER("Angry Villager", 20),
    HAPPY_VILLAGER("Happy Villager", 21),
    MYCELIUM("Mycelium", 22),
    NOTE("Note", 23),
    PORTAL("Portal", 24),
    ENCHANTMENT("Enchantment", 25),
    FLAME("Flame", 26),
    LAVA("Lava", 27),
    FOOTSTEP("Footstep", 28, isIgnored = true, isUnfair = true),
    CLOUD("Cloud", 29),
    REDSTONE("Redstone", 30),
    SNOWBALL("Snowball", 31),
    SHOVEL_SNOW("Shovel Snow", 32, isIgnored = true),
    SLIME("Slime", 33),
    HEART("Heart", 34),
    BARRIER("Barrier", 35),
    ITEM_EAT_BREAK("Item Eat / Break", 36),
    BLOCKS("Blocks", 37, isUnfair = true),
    BLOCK_DUST("Block Dust", 38, redirect = BLOCKS, isIgnored = true),
    RAIN_DROP("Rain Drop", 39),
    ITEM_PICKUP("Item Pickup", 40, isIgnored = true),
    MOB_APPEARANCE("Mob Appearance", 41, isIgnored = true);

    companion object {

        fun of(id: Int): ParticleType? {
            return entries.firstOrNull { it.id == id }
        }

        fun redirected(id: Int): ParticleType? {
            return entries.firstOrNull { it.id == id }?.redirect
        }

    }

}
