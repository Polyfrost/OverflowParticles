package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.particle.EffectRenderer;
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EffectRenderer.class)
public class Mixin_EffectRenderer_ModifyParticleLimit {

    //#if MC >= 1.12.2
    //$$ @ModifyConstant(method = "tick", constant = @Constant(intValue = 16384))
    //#else
    @ModifyConstant(method = "addEffect", constant = @Constant(intValue = 4000))
    //#endif
    private int changeMaxParticleLimit(int original) {
        return OverflowParticlesConfig.INSTANCE.getMaxParticleLimit();
    }

}
