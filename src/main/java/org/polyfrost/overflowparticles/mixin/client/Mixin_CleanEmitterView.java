package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.TrackingEmitter;
import net.minecraft.world.entity.Entity;
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TrackingEmitter.class)
public abstract class Mixin_CleanEmitterView extends Particle {
    //? if >1.8.9 {
    @Shadow @Final private Entity entity;
    //?} else
    //@Shadow private Entity target;

    public Mixin_CleanEmitterView() {
        super(null, 0, 0, 0, 0, 0, 0);
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void cleanView(CallbackInfo ci) {
        //? if >1.8.9 {
        if (OverflowParticlesConfig.isCleanView() && this.entity == overflowparticles$getPlayer()) {
        //?} else
        //if (OverflowParticlesConfig.isCleanView() && this.target == overflowparticles$getPlayer()) {
            this.remove();
            ci.cancel();
        }
    }

    private static Entity overflowparticles$getPlayer() {
        return Minecraft.getInstance().player;
    }
}
