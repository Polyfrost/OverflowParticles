package org.polyfrost.overflowparticles.mixin.client.particles;

import net.minecraft.client.particle.TrackingEmitter;
import net.minecraft.core.particles.ParticleOptions;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.particles.ParticleInfo;
import org.polyfrost.overflowparticles.client.particles.ParticleRegistry;
import org.polyfrost.overflowparticles.client.utils.ParticleSpawner;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TrackingEmitter.class)
public class Mixin_ApplyMultiplierToEmitters {
    @Shadow @Final private ParticleOptions particleType;

    @ModifyConstant(method = "tick", constant = @Constant(intValue = 16))
    private int multiplier(int constant) {
        ParticleInfo type = ParticleRegistry.of(this.particleType.getType());
        if (type == null) {
            return constant;
        }

        ParticleConfig config = PerParticleConfigManager.getConfigByType(type);
        if (config.getMultiplier() == 1) {
            return constant;
        }

        return (int) (constant * config.getMultiplier());
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;addParticle(Lnet/minecraft/core/particles/ParticleOptions;ZDDDDDD)V"))
    private void cancel(CallbackInfo ci) {
        ParticleSpawner.setMultiplied(true);
    }
}
