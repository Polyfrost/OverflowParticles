package org.polyfrost.overflowparticles.mixin.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BarrierParticle;
import net.minecraft.client.particle.Particle;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.particles.VanillaParticles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BarrierParticle.class)
public abstract class Mixin_ModifyBarrierSize extends Particle {
    protected Mixin_ModifyBarrierSize(ClientLevel arg, double d, double e, double f) {
        super(arg, d, e, f);
    }

    @Inject(method = "getQuadSize", at = @At("RETURN"), cancellable = true)
    private void overflowparticles$scale(float tickDelta, CallbackInfoReturnable<Float> cir) {
        float size = PerParticleConfigManager.getConfigByType(VanillaParticles.BARRIER).getSize();
        cir.setReturnValue(cir.getReturnValueF() * size);
    }
}
