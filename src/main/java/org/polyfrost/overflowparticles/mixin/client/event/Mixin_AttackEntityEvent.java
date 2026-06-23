package org.polyfrost.overflowparticles.mixin.client.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.polyfrost.oneconfig.api.event.v1.EventManager;
import org.polyfrost.overflowparticles.client.event.AttackEntityEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class Mixin_AttackEntityEvent {
    @Unique private final Player overflowparticles$self = (Player) (Object) this;

    @Inject(method = "attack", at = @At("HEAD"))
    private void callEvent(Entity target, CallbackInfo ci) {
        EventManager.INSTANCE.post(new AttackEntityEvent(overflowparticles$self, target));
    }
}
