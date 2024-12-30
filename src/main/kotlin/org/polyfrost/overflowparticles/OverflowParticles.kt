package org.polyfrost.overflowparticles

import net.minecraft.client.particle.EntityFX
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import org.polyfrost.oneconfig.api.event.v1.EventManager
import org.polyfrost.overflowparticles.config.ConfigManager
import org.polyfrost.overflowparticles.config.Settings
import org.polyfrost.overflowparticles.utils.IconRenderer

@Mod(modid = OverflowParticles.MODID, name = OverflowParticles.NAME, version = OverflowParticles.VERSION, modLanguageAdapter = "org.polyfrost.oneconfig.utils.v1.forge.KotlinLanguageAdapter")
object OverflowParticles {
    const val MODID = "@ID@"
    const val NAME = "@NAME@"
    const val VERSION = "@VER@"

    var renderingEntity: EntityFX? = null

    var rendering = false

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
        ConfigManager
        Settings
        IdkImRenamingThisLater
        MinecraftForge.EVENT_BUS.register(IconRenderer)
        EventManager.INSTANCE.register(IconRenderer)
    }
}