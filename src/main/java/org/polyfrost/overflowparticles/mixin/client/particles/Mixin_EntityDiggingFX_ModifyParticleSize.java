package org.polyfrost.overflowparticles.mixin.client.particles;

//#if MC <= 1.12.2

import net.minecraft.client.particle.*;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(EntityDiggingFX.class)
public class Mixin_EntityDiggingFX_ModifyParticleSize {

    @ModifyConstant(method = "renderParticle", constant = @Constant(floatValue = 0.1f, ordinal = 0))
    private float scale(float constant) {
        ParticleConfig config = PerParticleConfigManager.getConfig((EntityFX) (Object) this);
        return constant * (config == null ? 1 : Math.min(config.getSize(), 1f));
    }

}

//#endif
