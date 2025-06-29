package org.polyfrost.overflowparticles.client

import dev.deftu.omnicore.common.OmniEquipment
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityGroup
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.network.Packet
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket
import net.minecraft.particle.ParticleTypes
import org.polyfrost.oneconfig.api.event.v1.eventHandler
import org.polyfrost.oneconfig.api.event.v1.events.PacketEvent
import org.polyfrost.oneconfig.utils.v1.dsl.mc
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig
import org.polyfrost.overflowparticles.client.event.AttackEntityEvent

object OverflowParticlesEventHandler {

    private val LivingEntity.isBlind: Boolean
        get() {
            return this.hasStatusEffect(StatusEffects.BLINDNESS)
        }

    private var lastAttacker: PlayerEntity? = null

    private var targetId = -1

    fun initialize() {
        eventHandler { event: PacketEvent.Receive ->
            if (!OverflowParticlesConfig.checkInvulnerable) {
                return@eventHandler
            }

            val packet: Packet<*> = event.getPacket()

            if (packet is EntityStatusS2CPacket) {

                val target = packet.getEntity(mc.world) ?: return@eventHandler
                if (lastAttacker != null && targetId == target.id) {
                    doCritical(lastAttacker!!, target)
                    doSharpness(lastAttacker!!, target)
                    lastAttacker = null
                    targetId = -1
                }
            }
        }.register()

        eventHandler { event: AttackEntityEvent ->
            if (!event.target.world.isClient) {
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

    private fun doCritical(attacker: PlayerEntity, target: Entity) {
        if (!OverflowParticlesConfig.alwaysCritical) {
            return
        }

        val criticalHit = attacker.fallDistance > 0.0F
                && !attacker.isOnGround
                && !attacker.isClimbing
                && !attacker.isTouchingWater
                && !attacker.isBlind
                && attacker.vehicle == null
                && target is LivingEntity

        if (!criticalHit) {
            val pos = target.pos
            mc.world?.addParticle(ParticleTypes.CRIT, pos.x, pos.y + target.height / 2.0, pos.z, 0.0, 0.0, 0.0)
        }
    }

    private fun doSharpness(attacker: PlayerEntity, target: Entity) {
        if (!OverflowParticlesConfig.alwaysSharp) {
            return
        }

        val heldItem = OmniEquipment.getEquipment(attacker, OmniEquipment.EquipmentType.MAIN_HAND) ?: return
        val modifier = if (target is LivingEntity) {
            EnchantmentHelper.getAttackDamage(heldItem, target.group)
        } else {
            EnchantmentHelper.getAttackDamage(heldItem, EntityGroup.DEFAULT)
        }

        if (modifier <= 0f && target is LivingEntity) {
            val pos = target.pos
            mc.world?.addParticle(ParticleTypes.CRIT, pos.x, pos.y + target.height / 2.0, pos.z, 0.0, 0.0, 0.0)
        }
    }
}
