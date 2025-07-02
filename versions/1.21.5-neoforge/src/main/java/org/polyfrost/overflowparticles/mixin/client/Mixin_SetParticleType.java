package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import org.polyfrost.overflowparticles.hook.OFParticleType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParticleEngine.class)
public class Mixin_SetParticleType {

    @Unique
    private ParticleType overflowparticles$currentType;


    @Inject(method = "makeParticle", at = @At("HEAD"))
    private void overflowparticles$updateCurrentId(ParticleOptions arg, double d, double e, double f, double g, double h, double i, CallbackInfoReturnable<Particle> cir) {
        overflowparticles$currentType = arg.getType();
    }

    @Inject(method = "add", at = @At("HEAD"))
    private void overflowparticles$setParticleType(Particle particle, CallbackInfo ci) {

        OFParticleType.setParticleType(particle, overflowparticles$currentType);
    }


}
