package org.polyfrost.overflowparticles.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.polyfrost.overflowparticles.client.config.BlockParticleEntry;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.particles.VanillaParticles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class Mixin_FallStateParticles extends Entity {
    public Mixin_FallStateParticles(EntityType<?> arg, Level arg2) {
        super(arg, arg2);
    }

    @ModifyExpressionValue(
            method = "checkFallDamage",
            at = @At(
                    value = "INVOKE",
                    //#if FABRIC
                    //$$ target = "Lnet/minecraft/block/BlockState;isAir()Z"
                    //#else
                    target = "Lnet/minecraft/world/level/block/state/BlockState;isAir(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z",
                    remap = false
                    //#endif
            )
    )
    private boolean fall(boolean original) {
        if (this.level != null && !this.level.isClientSide) {
           return original;
        }

        ParticleConfig config = PerParticleConfigManager.getConfigByType(VanillaParticles.BLOCKS);
        if (!config.getEnabled()) {
            return true;
        }

        BlockParticleEntry entry = PerParticleConfigManager.getBlockSetting();
        if (entry.getHideRunning()) {
            if (entry.getHideMode() == 1) {
                return true;
            } else if (!isInvisible()) {
                return true;
            }
        }

        return original;
    }
}
