package org.polyfrost.overflowparticles.client.event

import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import org.polyfrost.oneconfig.api.event.v1.events.Event

data class AttackEntityEvent(
    val player: Player,
    val target: Entity
) : Event
