package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.particle.EffectRenderer;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.particles.VanillaParticles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EffectRenderer.class)
public class Mixin_CancelDiggingParticles {
    @Inject(
            method = "addBlockHitEffects(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void removeBlockBreakingParticles(CallbackInfo ci) {
        if (!PerParticleConfigManager.getConfigByType(VanillaParticles.BLOCKS).getEnabled() || PerParticleConfigManager.getBlockSetting().getHideDigging()) {
            ci.cancel();
        }
    }

    //#if FORGE
    @Inject(
            //#if MC >= 1.12.2
            //$$ method = "addBlockHitEffects(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/RayTraceResult;)V",
            //#else
            method = "addBlockHitEffects(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/MovingObjectPosition;)V",
            //#endif
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void removeBlockBreakingParticles_Forge(CallbackInfo ci) {
        if (!PerParticleConfigManager.getConfigByType(VanillaParticles.BLOCKS).getEnabled() || PerParticleConfigManager.getBlockSetting().getHideDigging()) {
            ci.cancel();
        }
    }
    //#endif
}
