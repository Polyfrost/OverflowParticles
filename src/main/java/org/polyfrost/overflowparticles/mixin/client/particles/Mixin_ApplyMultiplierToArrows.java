package org.polyfrost.overflowparticles.mixin.client.particles;

//? if >=1.21.11 {
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
//?} elif >1.8.9 {
/*import net.minecraft.world.entity.projectile.AbstractArrow;
*///?} else {
/*import net.minecraft.entity.projectile.ArrowEntity;
*///?}
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.particles.VanillaParticles;
import org.polyfrost.overflowparticles.client.utils.ParticleSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >1.8.9 {
@Mixin(AbstractArrow.class)
//?} else
//@Mixin(ArrowEntity.class)
public class Mixin_ApplyMultiplierToArrows {
    @ModifyExpressionValue(method = "tick", at = @At(value = "CONSTANT", args = "intValue=4", ordinal = 0))
    private int multiplier(int constant) {
        ParticleConfig config = PerParticleConfigManager.getConfigByType(VanillaParticles.CRITICAL);
        if (config.getMultiplier() == 1) {
            return constant;
        }

        return (int) (constant * config.getMultiplier());
    }

    @ModifyExpressionValue(method = "tick", at = @At(value = "CONSTANT", args = "doubleValue=4.0D"))
    private double multiplier1(double constant) {
        ParticleConfig config = PerParticleConfigManager.getConfigByType(VanillaParticles.CRITICAL);
        if (config.getMultiplier() == 1) {
            return constant;
        }

        return (int) (constant * config.getMultiplier());
    }

    //? if >1.8.9 {
    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V", ordinal = 0))
    //?} else
    //@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/entity/particle/ParticleType;DDDDDD[I)V", ordinal = 0))
    private void cancel(CallbackInfo ci) {
        ParticleSpawner.setMultiplied(true);
    }
}
