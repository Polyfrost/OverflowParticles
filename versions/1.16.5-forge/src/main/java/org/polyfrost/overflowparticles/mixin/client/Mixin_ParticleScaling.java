package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SingleQuadParticle;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SingleQuadParticle.class)
public class Mixin_ParticleScaling {
    @Inject(method = "getQuadSize", at = @At(value = "RETURN"), cancellable = true)
    private void overflowparticles$overrideScale(float tickDelta, CallbackInfoReturnable<Float> cir) {
        ParticleConfig config = PerParticleConfigManager.getConfig((Particle) (Object) this);
        if (config == null) {
            return;
        }

        float size = config.getSize();
        cir.setReturnValue(cir.getReturnValueF() * size);
    }
}
