package org.polyfrost.overflowparticles.mixin.particles;

import net.minecraft.client.particle.Barrier;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(Barrier.class)
public class BarrierMixin {

    @ModifyConstant(method = "renderParticle", constant = @Constant(floatValue = 0.5f))
    private float scale(float constant) {
        return constant * PerParticleConfigManager.getConfigs().get(35).getSize();
    }

}
