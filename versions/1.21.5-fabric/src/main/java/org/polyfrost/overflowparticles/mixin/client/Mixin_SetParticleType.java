package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import org.polyfrost.overflowparticles.utils.ParticleIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParticleManager.class)
public class Mixin_SetParticleType {
    @Unique private ParticleType<?> overflowparticles$currentType;

    @Inject(method = "createParticle", at = @At("HEAD"))
    private void overflowparticles$updateCurrentId(ParticleEffect particleEffect, double d, double e, double f, double g, double h, double i, CallbackInfoReturnable<Particle> cir) {
        overflowparticles$currentType = particleEffect.getType();
    }

    @Inject(method = "addParticle(Lnet/minecraft/client/particle/Particle;)V", at = @At("HEAD"))
    private void overflowparticles$setParticleType(Particle particle, CallbackInfo ci) {
        ParticleIdentifier.set(particle, overflowparticles$currentType);
    }
}
