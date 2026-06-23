package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.particle.ParticleEngine;
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig;
import org.spongepowered.asm.mixin.Mixin;
//? if >=1.21.10 {
/*import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.injection.At;
*///?} else {
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
//?}

@Mixin(ParticleEngine.class)
public class Mixin_ModifyParticleLimit {
    //? if >=1.21.10 {
    /*@ModifyExpressionValue(method = "hasSpaceInParticleLimit", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/particles/ParticleLimit;limit()I"))
    private int overflowparticles$changeMaxParticleLimit(int original) {
        return OverflowParticlesConfig.getMaxParticleLimit();
    }
    *///?} else {
    @ModifyConstant(method = "method_18125", constant = @Constant(intValue = 16384), remap = false)
    private static int overflowparticles$changeMaxParticleLimit(int original) {
        return OverflowParticlesConfig.getMaxParticleLimit();
    }
    //?}
}
