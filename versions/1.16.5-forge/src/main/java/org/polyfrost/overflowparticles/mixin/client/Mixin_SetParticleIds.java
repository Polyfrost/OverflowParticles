package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import org.polyfrost.overflowparticles.utils.ParticleIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParticleEngine.class)
public class Mixin_SetParticleIds {
    @Unique private ParticleType<?> overflowparticles$currentType;

    @Inject(method = "createParticle", at = @At("HEAD"))
    private void overflowparticles$captureTypeA(ParticleOptions options, double d, double e, double f, double g, double h, double i, CallbackInfoReturnable<Particle> cir) {
        overflowparticles$currentType = options.getType();
    }

    @ModifyArg(
            method = "createParticle",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/particle/ParticleEngine;add(Lnet/minecraft/client/particle/Particle;)V"
            )
    )
    private Particle overflowparticles$setType(Particle effect) {
        ParticleIdentifier.set(effect, overflowparticles$currentType);
        return effect;
    }
}
