package org.polyfrost.overflowparticles.mixin.client.particles;

import net.minecraft.client.particle.Barrier;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.utils.VanillaParticles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(Barrier.class)
public class Mixin_Barrier_ModifyParticleSize {

    @ModifyConstant(method = "renderParticle", constant = @Constant(floatValue = 0.5f))
    private float scale(float constant) {
        return constant * PerParticleConfigManager.getConfigByType(VanillaParticles.BARRIER).getSize();
    }

}
