package org.polyfrost.overflowparticles.client

import dev.deftu.omnicore.common.OmniEquipment
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.MobType
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.enchantment.EnchantmentHelper
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

            val packet: Packet<*> = event.getPacket()

            if (packet is ClientboundEntityEventPacket) {

                val target = packet.getEntity(mc.level) ?: return@eventHandler
                if (lastAttacker != null && targetId == target.id) {
                    doCritical(lastAttacker!!, target)
                    doSharpness(lastAttacker!!, target)
                    lastAttacker = null
                    targetId = -1
                }
            }
        }.register()

        eventHandler { event: AttackEntityEvent ->
            if (!event.target.level.isClientSide) {
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
                && !attacker.isOnGround
                && !attacker.onClimbable()
                && !attacker.isInWater
                && !attacker.isBlind
                && attacker.vehicle == null
                && target is LivingEntity

        if (!criticalHit) {
            val pos = target.position()
            mc.level?.addParticle(ParticleTypes.CRIT, pos.x, pos.y + target.bbHeight / 2.0, pos.z, 0.0, 0.0, 0.0)
        }
    }

    private fun doSharpness(attacker: Player, target: Entity) {
        if (!OverflowParticlesConfig.alwaysSharp) {
            return
        }

        val heldItem = OmniEquipment.getEquipment(attacker, OmniEquipment.EquipmentType.MAIN_HAND) ?: return
        val modifier = if (target is LivingEntity) {
            EnchantmentHelper.getDamageBonus(heldItem, target.mobType)
        } else {
            EnchantmentHelper.getDamageBonus(heldItem, MobType.UNDEFINED)
        }

        if (modifier <= 0f && target is LivingEntity) {
            val pos = target.position()
            mc.level?.addParticle(ParticleTypes.CRIT, pos.x, pos.y + target.bbHeight / 2.0, pos.z, 0.0, 0.0, 0.0)
        }
    }
}
