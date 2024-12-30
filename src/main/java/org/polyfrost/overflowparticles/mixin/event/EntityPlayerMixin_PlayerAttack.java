package org.polyfrost.overflowparticles.mixin.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.polyfrost.oneconfig.api.event.v1.EventManager;
import org.polyfrost.overflowparticles.event.AttackEntityEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayer.class)
public class EntityPlayerMixin_PlayerAttack {

    @Unique private final EntityPlayer overflowParticles$self = (EntityPlayer) (Object) this;

    @Inject(method = "attackTargetEntityWithCurrentItem", at = @At("HEAD"))
    private void callEvent(Entity target, CallbackInfo ci) {
        EventManager.INSTANCE.post(new AttackEntityEvent(overflowParticles$self, target));
    }
}
