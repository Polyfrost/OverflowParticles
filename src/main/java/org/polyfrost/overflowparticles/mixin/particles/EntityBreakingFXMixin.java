package org.polyfrost.overflowparticles.mixin.particles;

import net.minecraft.client.particle.*;
import org.polyfrost.overflowparticles.config.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(EntityBreakingFX.class)
public class EntityBreakingFXMixin {
    @ModifyConstant(method = "renderParticle", constant = @Constant(floatValue = 0.1f, ordinal = 0))
    private float scale(float constant) {
        ParticleConfig config = ConfigManager.INSTANCE.getConfig((EntityFX) (Object) this);
        return constant * (config == null ? 1 : config.getSize());
    }
}
