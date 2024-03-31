package org.polyfrost.polyparticles.mixin.particles;

import net.minecraft.client.particle.*;
import org.polyfrost.polyparticles.config.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(EntityFootStepFX.class)
public class EntityFootStepFXMixin {
    @ModifyConstant(method = "renderParticle", constant = @Constant(floatValue = 0.125f))
    private float scale(float constant) {
        ParticleConfig config = ModConfig.INSTANCE.getConfig((EntityFX) (Object) this);
        return constant * (config == null ? 1 : config.getSize());
    }
}
