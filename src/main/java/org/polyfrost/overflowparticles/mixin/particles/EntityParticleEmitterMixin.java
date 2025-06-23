package org.polyfrost.overflowparticles.mixin.particles;

import net.minecraft.client.particle.EntityParticleEmitter;
import net.minecraft.util.EnumParticleTypes;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.utils.ParticleSpawnerKt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityParticleEmitter.class)
public class EntityParticleEmitterMixin {

    @Shadow private EnumParticleTypes particleTypes;

    @ModifyConstant(method = "onUpdate", constant = @Constant(intValue = 16))
    private int multiplier(int constant) {
        ParticleConfig config = PerParticleConfigManager.getConfigs().get(this.particleTypes.getParticleID());
        if (config == null || config.getMultiplier() == 1) {
            return constant;
        }

        return (int) (constant * config.getMultiplier());
    }

    @Inject(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnParticle(Lnet/minecraft/util/EnumParticleTypes;ZDDDDDD[I)V"))
    private void cancel(CallbackInfo ci) {
        ParticleSpawnerKt.setMultiplied(true);
    }

}