package org.polyfrost.polyparticles.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.Slider
import cc.polyfrost.oneconfig.config.annotations.Switch
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
import cc.polyfrost.oneconfig.events.EventManager
import cc.polyfrost.oneconfig.events.event.ReceivePacketEvent
import cc.polyfrost.oneconfig.internal.config.core.ConfigCore
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe
import cc.polyfrost.oneconfig.utils.dsl.mc
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.EnumCreatureAttribute
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.network.play.server.S19PacketEntityStatus
import net.minecraft.potion.Potion
import net.minecraft.util.EnumParticleTypes
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.player.AttackEntityEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import org.polyfrost.polyparticles.PolyParticles

object MainConfig : Config(Mod("Settings", ModType.UTIL_QOL), "") {

    private var attacker: EntityPlayer? = null
    private var targetId = -1

    @Switch(
        name = "Clean View",
        description = "Stop rendering your potion effect particles.",
        subcategory = "Miscellaneous"
    )
    var cleanView = false

    @Switch(
        name = "Static Particle Color",
        description = "Disable particle lighting checks each frame.",
        subcategory = "Miscellaneous"
    )
    var staticParticleColor = false

    @Slider(
        name = "Max Particle Limit",
        description = "Stop additional particles from appearing when there are too many at once.",
        subcategory = "Miscellaneous",
        min = 1f, max = 10000f
    )
    var maxParticleLimit = 4000

    @Switch(
        name = "Always Show Critical",
        subcategory = "Hit Particle"
    )
    var alwaysCritical = false

    @Switch(
        name = "Always Show Sharpness",
        subcategory = "Hit Particle"
    )
    var alwaysSharp = false

    @Switch(
        name = "Check Invulnerability",
        subcategory = "Hit Particle"
    )
    var checkInvulnerable = false

    @Subscribe
    fun onPacketReceive(event: ReceivePacketEvent) {
        if (!checkInvulnerable) return

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
        if (checkInvulnerable) {
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
        if (!alwaysCritical)
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
        if (!alwaysSharp)
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
        generateOptionList(this, mod.defaultPage, mod, false)
        ConfigCore.mods.add(this.mod)
        PolyParticles.mods.add(this.mod)
        MinecraftForge.EVENT_BUS.register(this)
        EventManager.INSTANCE.register(this)
    }

    override fun reInitialize() {
    }

    override fun load() {
        alwaysCritical = ModConfig.alwaysCritical
        alwaysSharp = ModConfig.alwaysSharp
        checkInvulnerable = ModConfig.checkInvulnerable
        cleanView = ModConfig.cleanView
        maxParticleLimit = ModConfig.maxParticleLimit
        staticParticleColor = ModConfig.staticParticleColor
    }

    override fun save() {
        ModConfig.alwaysCritical = alwaysCritical
        ModConfig.alwaysSharp = alwaysSharp
        ModConfig.checkInvulnerable = checkInvulnerable
        ModConfig.cleanView = cleanView
        ModConfig.maxParticleLimit = maxParticleLimit
        ModConfig.staticParticleColor = staticParticleColor
    }

}