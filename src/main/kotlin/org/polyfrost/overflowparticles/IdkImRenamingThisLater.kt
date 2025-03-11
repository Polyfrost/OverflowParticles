package org.polyfrost.overflowparticles

import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.EnumCreatureAttribute
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.network.Packet
import net.minecraft.network.play.server.S19PacketEntityStatus
import net.minecraft.potion.Potion
import net.minecraft.util.EnumParticleTypes
import org.polyfrost.oneconfig.api.event.v1.eventHandler
import org.polyfrost.oneconfig.api.event.v1.events.PacketEvent
import org.polyfrost.oneconfig.utils.v1.dsl.mc
import org.polyfrost.overflowparticles.config.Settings
import org.polyfrost.overflowparticles.event.AttackEntityEvent

/**
 * :fire:
 */
object IdkImRenamingThisLater {

    private var attacker: EntityPlayer? = null
    private var targetId = -1

    init {
        eventHandler { event: PacketEvent.Receive ->
            if (!Settings.checkInvulnerable) return@eventHandler

            val packet: Packet<*> = event.getPacket()

            if (packet is S19PacketEntityStatus) {
                if (packet.opCode.toInt() != 2) return@eventHandler

                val target = packet.getEntity(mc.theWorld) ?: return@eventHandler
                if (attacker != null && targetId == target.entityId) {
                    doCritical(attacker!!, target)
                    doSharpness(attacker!!, target)
                    attacker = null
                    targetId = -1
                }
            }
        }.register()
        eventHandler { event: AttackEntityEvent ->
            if (!event.target.worldObj.isRemote) return@eventHandler
            if (Settings.checkInvulnerable) {
                if (event.player.entityId == mc.thePlayer.entityId) {
                    attacker = event.player
                    targetId = event.target.entityId
                }
            } else {
                doSharpness(event.player, event.target)
                doCritical(event.player, event.target)
            }
        }.register()
    }

    private fun doCritical(attacker: EntityPlayer, target: Entity) {
        if (!Settings.alwaysCritical)
            return
        val criticalHit = attacker.fallDistance > 0.0F
                && !attacker.onGround
                && !attacker.isOnLadder
                && !attacker.isInWater
                && !attacker.isPotionActive(Potion.blindness)
                && attacker.ridingEntity == null
                && target is EntityLivingBase

        if (!criticalHit) {
            mc.effectRenderer.emitParticleAtEntity(target, EnumParticleTypes.CRIT)
        }
    }

    private fun doSharpness(attacker: EntityPlayer, target: Entity) {
        if (!Settings.alwaysSharp)
            return

        val modifier = if (target is EntityLivingBase) {
            EnchantmentHelper.getModifierForCreature(attacker.heldItem, target.creatureAttribute)
        } else {
            EnchantmentHelper.getModifierForCreature(attacker.heldItem, EnumCreatureAttribute.UNDEFINED)
        }

        if (modifier <= 0f) {
            mc.effectRenderer.emitParticleAtEntity(target, EnumParticleTypes.CRIT_MAGIC)
        }
    }
}