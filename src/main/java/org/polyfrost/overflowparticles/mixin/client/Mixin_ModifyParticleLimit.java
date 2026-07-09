package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleRenderType;
//? if >=1.21.10 {
import net.minecraft.client.particle.ParticleGroup;
//?} else {
/*import java.util.Queue;
*///?}
import java.util.Map;
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleEngine.class)
public class Mixin_ModifyParticleLimit {
    //? if >=1.21.10 {
    @Shadow @Final private Map<ParticleRenderType, ParticleGroup<?>> particles;
    //?} else {
    /*@Shadow @Final private Map<ParticleRenderType, Queue<Particle>> particles;
    *///?}

    @Inject(method = "add", at = @At("HEAD"), cancellable = true)
    private void overflowparticles$limitParticles(Particle particle, CallbackInfo ci) {
        int limit = OverflowParticlesConfig.getMaxParticleLimit();
        if (limit <= 0) {
            return;
        }

        //? if >=1.21.10 {
        ParticleGroup<?> group = this.particles.get(particle.getGroup());
        //?} else {
        /*Queue<Particle> group = this.particles.get(particle.getRenderType());
        *///?}
        if (group != null && group.size() >= limit) {
            ci.cancel();
        }
    }
}
