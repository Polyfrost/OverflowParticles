package org.polyfrost.overflowparticles.mixin.client.particles;

import net.minecraft.client.particle.*;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(EntityFootStepFX.class)
public class Mixin_EntityFootStepFX_ModifyParticleSize {

    @ModifyConstant(method = "renderParticle", constant = @Constant(floatValue = 0.125f))
    private float scale(float constant) {
        ParticleConfig config = PerParticleConfigManager.getConfig((EntityFX) (Object) this);
        return constant * (config == null ? 1 : Math.min(config.getSize(), 1f));
    }

}
