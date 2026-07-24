package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.particles.VanillaParticles;
import org.polyfrost.overflowparticles.client.utils.ParticleSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class Mixin_MultiplyDeathParticles extends Entity {
    public Mixin_MultiplyDeathParticles(EntityType<?> arg, Level arg2) {
        super(arg, arg2);
    }

    @ModifyConstant(method = "makePoofParticles", constant = @Constant(intValue = 20))
    private int overflowparticles$applyMultiplier(int constant) {
        ParticleConfig config = PerParticleConfigManager.getConfigByType(VanillaParticles.EXPLOSION_NORMAL);
        if (config.getMultiplier() == 1) {
            return constant;
        }

        return (int) (constant * config.getMultiplier());
    }

    @Inject(method = "makePoofParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"))
    private void overflowparticles$updateMultiplyState(CallbackInfo ci) {
        ParticleSpawner.setMultiplied(true);
    }
}
