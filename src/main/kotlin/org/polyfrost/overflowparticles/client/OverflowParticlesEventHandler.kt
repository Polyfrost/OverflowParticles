package org.polyfrost.overflowparticles.client

import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.effect.MobEffects
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
            return this.hasEffect(MobEffects.BLINDNESS)
        }

    private var lastAttacker: Player? = null

    private var targetId = -1

    fun initialize() {
        eventHandler { event: PacketEvent.Receive ->
            if (!OverflowParticlesConfig.checkInvulnerable) {
                return@eventHandler
            }

            val world = mc.level ?: return@eventHandler
            val packet: Packet<*> = event.getPacket()
            if (packet is ClientboundEntityEventPacket) {
                val target = packet.getEntity(world) ?: return@eventHandler
                if (lastAttacker != null && targetId == target.id) {
                    doCritical(lastAttacker!!, target)
                    doSharpness(lastAttacker!!, target)
                    lastAttacker = null
                    targetId = -1
                }
            }
        }.register()

        eventHandler { event: AttackEntityEvent ->
            val targetWorld = event.target.level()

            val isClientWorld = targetWorld.isClientSide

            if (!isClientWorld) {
                return@eventHandler
            }

            if (OverflowParticlesConfig.checkInvulnerable) {
                if (event.player.id == mc.player?.id) {
                    lastAttacker = event.player
                    targetId = event.target.id
                }
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
                && !attacker.onGround()
                && !attacker.onClimbable()
                && !attacker.isInWater()
                && !attacker.isBlind
                && attacker.vehicle == null
                && target is LivingEntity

        if (!criticalHit) {
            mc.particleEngine.createTrackingEmitter(target, ParticleTypes.CRIT)
        }
    }

    private fun doSharpness(attacker: Player, target: Entity) {
        if (!OverflowParticlesConfig.alwaysSharp) {
            return
        }

        if (target is LivingEntity) {
            val heldItem = attacker.mainHandItem
            if (heldItem.isEnchanted()) {
                return
            }

            mc.particleEngine.createTrackingEmitter(target, ParticleTypes.ENCHANTED_HIT)
        }
    }
}
