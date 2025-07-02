package org.polyfrost.overflowparticles.mixin.client.particles;

//#if MC <= 1.12.2

import net.minecraft.entity.projectile.EntityArrow;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.utils.ParticleSpawner;
import org.polyfrost.overflowparticles.client.utils.VanillaParticles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityArrow.class)
public class Mixin_EntityArrow_ApplyMultiplier {

    @ModifyConstant(method = "onUpdate", constant = @Constant(intValue = 4, ordinal = 0))
    private int multiplier(int constant) {
        ParticleConfig config = PerParticleConfigManager.getConfigByType(VanillaParticles.CRITICAL);
        if (config.getMultiplier() == 1) {
            return constant;
        }

        return (int) (constant * config.getMultiplier());
    }

    @ModifyConstant(method = "onUpdate", constant = @Constant(doubleValue = 4.0D))
    private double multiplier1(double constant) {
        ParticleConfig config = PerParticleConfigManager.getConfigByType(VanillaParticles.CRITICAL);
        if (config.getMultiplier() == 1) {
            return constant;
        }

        return (int) (constant * config.getMultiplier());
    }

    @Inject(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnParticle(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V", ordinal = 0))
    private void cancel(CallbackInfo ci) {
        ParticleSpawner.setMultiplied(true);
    }

}
//#endif
