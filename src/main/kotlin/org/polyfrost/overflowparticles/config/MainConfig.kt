package org.polyfrost.overflowparticles.config

import cc.polyfrost.oneconfig.config.annotations.*
import cc.polyfrost.oneconfig.config.elements.SubConfig
import cc.polyfrost.oneconfig.events.EventManager
import cc.polyfrost.oneconfig.events.event.ReceivePacketEvent
import cc.polyfrost.oneconfig.internal.config.core.ConfigCore
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe
import cc.polyfrost.oneconfig.utils.dsl.mc
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.*
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.network.play.server.S19PacketEntityStatus
import net.minecraft.potion.Potion
import net.minecraft.util.EnumParticleTypes
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.player.AttackEntityEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object MainConfig : SubConfig("Settings", "", "/assets/oneconfig/icons/settings-02.svg") {

    @Exclude
    private var attacker: EntityPlayer? = null
    @Exclude
    private var targetId = -1

    val settings
        get() = ModConfig.settings

    @Subscribe
    fun onPacketReceive(event: ReceivePacketEvent) {
        if (!settings.checkInvulnerable) return

        if (event.packet is S19PacketEntityStatus) {
            val packet = event.packet as S19PacketEntityStatus
            if (packet.opCode.toInt() != 2) return

            val target = packet.getEntity(mc.theWorld) ?: return
            if (attacker != null && targetId == target.entityId) {
                doCritical(attacker!!, target)
                doSharpness(attacker!!, target)
                attacker = null
                targetId = -1
            }
        }
    }

    @SubscribeEvent
    fun onAttack(event: AttackEntityEvent) {
        if (!event.target.worldObj.isRemote) return
        if (settings.checkInvulnerable) {
            if (event.entityPlayer.entityId == mc.thePlayer.entityId) {
                attacker = event.entityPlayer
                targetId = event.target.entityId
            }
        } else {
            doSharpness(event.entityPlayer, event.target)
            doCritical(event.entityPlayer, event.target)
        }
    }

    private fun doCritical(attacker: EntityPlayer, target: Entity) {
        if (!settings.alwaysCritical)
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
        if (!settings.alwaysSharp)
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

    override fun initialize() {
        mod.config = this
        generateOptionList(this.settings, mod.defaultPage, mod, false)
        ConfigCore.mods.add(this.mod)
        MinecraftForge.EVENT_BUS.register(this)
        EventManager.INSTANCE.register(this)
    }

    override fun reInitialize() {
    }

    override fun load() {
    }

    override fun save() {
    }

    init {
        initialize()
    }

}