package org.polyfrost.overflowparticles.mixin.client.event;

import net.minecraft.world.entity.Entity;
//? if >1.8.9 {
import net.minecraft.world.entity.player.Player;
//?} else {
/*import net.minecraft.entity.living.player.PlayerEntity;
*///?}
import org.polyfrost.oneconfig.api.event.v1.EventManager;
import org.polyfrost.overflowparticles.client.event.AttackEntityEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >1.8.9 {
@Mixin(Player.class)
public class Mixin_AttackEntityEvent {
    @Unique private final Player overflowparticles$self = (Player) (Object) this;
//?} else {
/*@Mixin(PlayerEntity.class)
public class Mixin_AttackEntityEvent {
    @Unique private final PlayerEntity overflowparticles$self = (PlayerEntity) (Object) this;
*///?}

    @Inject(method = "attack", at = @At("HEAD"))
    private void callEvent(Entity target, CallbackInfo ci) {
        EventManager.INSTANCE.post(new AttackEntityEvent(overflowparticles$self, target));
    }
}
