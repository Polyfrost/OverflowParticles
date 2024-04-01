package org.polyfrost.polyparticles.mixin.particles;

import net.minecraft.client.particle.*;
import org.polyfrost.polyparticles.config.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(EntityDiggingFX.class)
public class EntityDiggingFXMixin {
    @ModifyConstant(method = "renderParticle", constant = @Constant(floatValue = 0.1f, ordinal = 0))
    private float scale(float constant) {
        ParticleConfig config = ModConfig.INSTANCE.getConfig((EntityFX) (Object) this);
        return constant * (config == null ? 1 : config.getEntry().getSize());
    }
}
