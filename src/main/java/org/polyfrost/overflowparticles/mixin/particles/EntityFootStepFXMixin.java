package org.polyfrost.overflowparticles.mixin.particles;

import net.minecraft.client.particle.*;
import org.polyfrost.overflowparticles.config.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(EntityFootStepFX.class)
public class EntityFootStepFXMixin {
    @ModifyConstant(method = "renderParticle", constant = @Constant(floatValue = 0.125f))
    private float scale(float constant) {
        ParticleConfig config = ConfigManager.INSTANCE.getConfig((EntityFX) (Object) this);
        return constant * (config == null ? 1 : Math.min(config.getSize(), 1f));
    }
}
