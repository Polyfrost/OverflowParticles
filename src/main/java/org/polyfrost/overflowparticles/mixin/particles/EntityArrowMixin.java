package org.polyfrost.overflowparticles.mixin.particles;

import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.EnumParticleTypes;
import org.polyfrost.overflowparticles.OverflowParticles;
import org.polyfrost.overflowparticles.config.ParticleConfig;
import org.polyfrost.overflowparticles.utils.UtilKt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityArrow.class)
public class EntityArrowMixin {

    @ModifyConstant(method = "onUpdate", constant = @Constant(intValue = 4, ordinal = 0))
    private int multiplier(int constant) {
        ParticleConfig config = OverflowParticles.INSTANCE.getConfigs().get(EnumParticleTypes.CRIT.getParticleID());
        if (config == null || config.getEntry().getMultiplier() == 1) return constant;
        return (int) (constant * config.getEntry().getMultiplier());
    }

    @ModifyConstant(method = "onUpdate", constant = @Constant(doubleValue = 4.0D))
    private double multiplier1(double constant) {
        ParticleConfig config = OverflowParticles.INSTANCE.getConfigs().get(EnumParticleTypes.CRIT.getParticleID());
        if (config == null || config.getEntry().getMultiplier() == 1) return constant;
        return (int) (constant * config.getEntry().getMultiplier());
    }

    @Inject(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnParticle(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V", ordinal = 0))
    private void cancel(CallbackInfo ci) {
        UtilKt.setMultiplied(true);
    }
}