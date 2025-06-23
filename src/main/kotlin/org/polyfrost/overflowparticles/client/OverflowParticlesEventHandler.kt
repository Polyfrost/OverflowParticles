package org.polyfrost.overflowparticles.client

import dev.deftu.omnicore.common.OmniEquipment
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.EnumCreatureAttribute
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.network.Packet
import net.minecraft.network.play.server.S19PacketEntityStatus
import net.minecraft.util.EnumParticleTypes
import org.polyfrost.oneconfig.api.event.v1.eventHandler
import org.polyfrost.oneconfig.api.event.v1.events.PacketEvent
import org.polyfrost.oneconfig.utils.v1.dsl.mc
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig
import org.polyfrost.overflowparticles.event.AttackEntityEvent

//#if MC >= 1.12.2
//$$ import net.minecraft.entity.effect.StatusEffects
//#else
import net.minecraft.potion.Potion
//#endif

object OverflowParticlesEventHandler {

    private val EntityLivingBase.isBlind: Boolean
        get() {
            //#if MC >= 1.12.2
            //$$ return this.hasStatusEffect(StatusEffects.BLINDNESS)
            //#else
            return this.isPotionActive(Potion.blindness)
            //#endif
        }

    private var lastAttacker: EntityPlayer? = null

    private var targetId = -1

    fun initialize() {
        eventHandler { event: PacketEvent.Receive ->
            if (!OverflowParticlesConfig.checkInvulnerable) {
                return@eventHandler
            }

            val packet: Packet<*> = event.getPacket()

            if (packet is S19PacketEntityStatus) {
                if (packet.opCode.toInt() != 2) {
                    return@eventHandler
                }

                val target = packet.getEntity(mc.theWorld) ?: return@eventHandler
                if (lastAttacker != null && targetId == target.entityId) {
                    doCritical(lastAttacker!!, target)
                    doSharpness(lastAttacker!!, target)
                    lastAttacker = null
                    targetId = -1
                }
            }
        }.register()

        eventHandler { event: AttackEntityEvent ->
            if (!event.target.worldObj.isRemote) {
                return@eventHandler
            }

            if (OverflowParticlesConfig.checkInvulnerable) {
                if (event.player.entityId == mc.thePlayer.entityId) {
                    lastAttacker = event.player
                    targetId = event.target.entityId
                }
            } else {
                doSharpness(event.player, event.target)
                doCritical(event.player, event.target)
            }
        }.register()
    }

    private fun doCritical(attacker: EntityPlayer, target: Entity) {
        if (!OverflowParticlesConfig.alwaysCritical) {
            return
        }

        val criticalHit = attacker.fallDistance > 0.0F
                && !attacker.onGround
                && !attacker.isOnLadder
                && !attacker.isInWater
                && !attacker.isBlind
                && attacker.ridingEntity == null
                && target is EntityLivingBase

        if (!criticalHit) {
            mc.effectRenderer.emitParticleAtEntity(target, EnumParticleTypes.CRIT)
        }
    }

    private fun doSharpness(attacker: EntityPlayer, target: Entity) {
        if (!OverflowParticlesConfig.alwaysSharp) {
            return
        }

        val heldItem = OmniEquipment.getEquipment(attacker, OmniEquipment.EquipmentType.MAIN_HAND) ?: return
        val modifier = if (target is EntityLivingBase) {
            EnchantmentHelper.getModifierForCreature(heldItem, target.creatureAttribute)
        } else {
            EnchantmentHelper.getModifierForCreature(heldItem, EnumCreatureAttribute.UNDEFINED)
        }

        if (modifier <= 0f) {
            mc.effectRenderer.emitParticleAtEntity(target, EnumParticleTypes.CRIT_MAGIC)
        }
    }
}