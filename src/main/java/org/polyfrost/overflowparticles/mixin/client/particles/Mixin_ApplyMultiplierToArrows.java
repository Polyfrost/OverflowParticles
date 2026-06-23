package org.polyfrost.overflowparticles.mixin.client.particles;

//? if >=1.21.11 {
/*import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
*///?} else {
import net.minecraft.world.entity.projectile.AbstractArrow;
//?}
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.particles.VanillaParticles;
import org.polyfrost.overflowparticles.client.utils.ParticleSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public class Mixin_ApplyMultiplierToArrows {
    @ModifyConstant(method = "tick", constant = @Constant(intValue = 4, ordinal = 0))
    private int multiplier(int constant) {
        ParticleConfig config = PerParticleConfigManager.getConfigByType(VanillaParticles.CRITICAL);
        if (config.getMultiplier() == 1) {
            return constant;
        }

        return (int) (constant * config.getMultiplier());
    }

    @ModifyConstant(method = "tick", constant = @Constant(doubleValue = 4.0D))
    private double multiplier1(double constant) {
        ParticleConfig config = PerParticleConfigManager.getConfigByType(VanillaParticles.CRITICAL);
        if (config.getMultiplier() == 1) {
            return constant;
        }

        return (int) (constant * config.getMultiplier());
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V", ordinal = 0))
    private void cancel(CallbackInfo ci) {
        ParticleSpawner.setMultiplied(true);
    }
}
