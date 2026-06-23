package org.polyfrost.overflowparticles

//? if fabric {
import net.fabricmc.api.ClientModInitializer
//?} elif forge {
/*//? if >=1.16.5 {
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
//?} else {
/^import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.FMLInitializationEvent
^///?}
*///?} elif neoforge {
/*import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
*///?}

import org.polyfrost.overflowparticles.client.OverflowParticlesClient

//? if forge_like {
/*import org.polyfrost.overflowparticles.OverflowParticlesConstants
//? if >=1.16.5 {
@Mod(OverflowParticlesConstants.ID)
//?} else {
/^@Mod(modid = OverflowParticlesConstants.ID, version = OverflowParticlesConstants.VERSION)
^///?}
*///?}
class OverflowParticlesEntrypoint
//? if fabric {
    : ClientModInitializer
//?}
{
    //? if forge && >=1.16.5 {
    /*init {
        setupForgeEvents(FMLJavaModLoadingContext.get().modEventBus)
    }
    *///?} elif neoforge {
    /*constructor(modEventBus: IEventBus) {
        setupForgeEvents(modEventBus)
    }
    *///?}

    //? if fabric {
    override
    //?} elif forge && <=1.12.2 {
    /*@EventHandler
    *///?}
    fun onInitializeClient(
        //? if forge_like {
        /*//? if >=1.16.5 {
        event: FMLClientSetupEvent
        //?} else {
        /^event: FMLInitializationEvent
        ^///?}
        *///?}
    ) {
        //? if forge && <=1.12.2 {
        /*if (!event.side.isClient) {
            return
        }
        *///?}

        OverflowParticlesClient.initialize()
    }

    //? if forge_like && >=1.16.5 {
    /*fun setupForgeEvents(modEventBus: IEventBus) {
        modEventBus.addListener(this::onInitializeClient)
    }
    *///?}
}
