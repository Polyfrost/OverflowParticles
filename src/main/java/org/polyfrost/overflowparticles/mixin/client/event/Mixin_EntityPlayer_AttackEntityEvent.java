package org.polyfrost.overflowparticles.mixin.client.event;

//#if MC <= 1.12.2

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.polyfrost.oneconfig.api.event.v1.EventManager;
import org.polyfrost.overflowparticles.client.event.AttackEntityEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayer.class)
public class Mixin_EntityPlayer_AttackEntityEvent {

    @Unique private final EntityPlayer overflowparticles$self = (EntityPlayer) (Object) this;

    @Inject(method = "attackTargetEntityWithCurrentItem", at = @At("HEAD"))
    private void callEvent(Entity target, CallbackInfo ci) {
        EventManager.INSTANCE.post(new AttackEntityEvent(overflowparticles$self, target));
    }

}

//#endif
