package org.polyfrost.overflowparticles.event

import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import org.polyfrost.oneconfig.api.event.v1.events.Event

data class AttackEntityEvent(val player: EntityPlayer, val target: Entity) : Event