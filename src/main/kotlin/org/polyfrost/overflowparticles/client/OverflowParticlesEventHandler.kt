package org.polyfrost.overflowparticles.client

import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
//? if >1.8.9 {
import net.minecraft.world.effect.MobEffects
//?} else {
/*import net.minecraft.entity.living.effect.StatusEffect
*///?}
import net.minecraft.world.entity.player.Player
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket
import net.minecraft.core.particles.ParticleTypes
import org.polyfrost.oneconfig.api.event.v1.eventHandler
import org.polyfrost.oneconfig.api.event.v1.events.PacketEvent
import org.polyfrost.oneconfig.utils.v1.dsl.mc
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig
import org.polyfrost.overflowparticles.client.event.AttackEntityEvent

object OverflowParticlesEventHandler {

    private val LivingEntity.isBlind: Boolean
        get() {
            //? if >1.8.9 {
            return this.hasEffect(MobEffects.BLINDNESS)
            //?} else {
            /*return this.hasStatusEffect(StatusEffect.BLINDNESS.id)
            *///?}
        }

    private var lastAttacker: Player? = null

    private var targetId = -1

    fun initialize() {
        eventHandler { event: PacketEvent.Receive ->
            if (!OverflowParticlesConfig.checkInvulnerable) {
                return@eventHandler
            }

            //? if >1.8.9 {
            val world = mc.level ?: return@eventHandler
            //?} else {
            /*val world = mc.world ?: return@eventHandler
            *///?}
            val packet: Packet<*> = event.getPacket()
            if (packet is ClientboundEntityEventPacket) {
                val target = packet.getEntity(world) ?: return@eventHandler
                //? if >1.8.9 {
                if (lastAttacker != null && targetId == target.id) {
                //?} else {
                /*if (lastAttacker != null && targetId == target.networkId) {
                *///?}
                    doCritical(lastAttacker!!, target)
                    doSharpness(lastAttacker!!, target)
                    lastAttacker = null
                    targetId = -1
                }
            }
        }.register()

        eventHandler { event: AttackEntityEvent ->
            //? if >1.8.9 {
            val targetWorld = event.target.level()

            val isClientWorld = targetWorld.isClientSide
            //?} else {
            /*val isClientWorld = event.target.world.isClient
            *///?}

            if (!isClientWorld) {
                return@eventHandler
            }

            if (OverflowParticlesConfig.checkInvulnerable) {
                //? if >1.8.9 {
                if (event.player.id == mc.player?.id) {
                    lastAttacker = event.player
                    targetId = event.target.id
                }
                //?} else {
                /*if (event.player.networkId == mc.player?.networkId) {
                    lastAttacker = event.player
                    targetId = event.target.networkId
                }
                *///?}
            } else {
                doSharpness(event.player, event.target)
                doCritical(event.player, event.target)
            }
        }.register()
    }

    private fun doCritical(attacker: Player, target: Entity) {
        if (!OverflowParticlesConfig.alwaysCritical) {
            return
        }

        val criticalHit = attacker.fallDistance > 0.0F
                //? if >1.8.9 {
                && !attacker.onGround()
                && !attacker.onClimbable()
                //?} else {
                /*&& !attacker.onGround
                && !attacker.isClimbing
                *///?}
                && !attacker.isInWater()
                && !attacker.isBlind
                && attacker.vehicle == null
                && target is LivingEntity

        if (!criticalHit) {
            //? if >1.8.9 {
            mc.particleEngine.createTrackingEmitter(target, ParticleTypes.CRIT)
            //?} else {
            /*mc.particleManager.addEmitter(target, ParticleTypes.CRIT)
            *///?}
        }
    }

    private fun doSharpness(attacker: Player, target: Entity) {
        if (!OverflowParticlesConfig.alwaysSharp) {
            return
        }

        if (target is LivingEntity) {
            //? if >1.8.9 {
            val heldItem = attacker.mainHandItem
            if (heldItem.isEnchanted()) {
                return
            }

            mc.particleEngine.createTrackingEmitter(target, ParticleTypes.ENCHANTED_HIT)
            //?} else {
            /*val heldItem = attacker.itemInHand
            if (heldItem != null && heldItem.hasEnchantments()) {
                return
            }

            mc.particleManager.addEmitter(target, ParticleTypes.CRIT_MAGIC)
            *///?}
        }
    }
}
