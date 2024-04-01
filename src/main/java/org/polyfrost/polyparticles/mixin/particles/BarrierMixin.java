package org.polyfrost.polyparticles.mixin.particles;

import net.minecraft.client.particle.Barrier;
import org.polyfrost.polyparticles.PolyParticles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(Barrier.class)
public class BarrierMixin {
    @ModifyConstant(method = "renderParticle", constant = @Constant(floatValue = 0.5f, ordinal = 0))
    private float scale(float constant) {
        return constant * PolyParticles.INSTANCE.getConfigs().get(35).getEntry().getSize();
    }
}
