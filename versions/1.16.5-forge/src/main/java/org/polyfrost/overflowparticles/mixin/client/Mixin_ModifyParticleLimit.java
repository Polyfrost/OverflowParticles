package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.particle.ParticleEngine;
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ParticleEngine.class)
public class Mixin_ModifyParticleLimit {
    @ModifyConstant(method = "method_18125", constant = @Constant(intValue = 16384), remap = false)
    private static int overflowparticles$changeMaxParticleLimit(int original) {
        return OverflowParticlesConfig.getMaxParticleLimit();
    }
}
