package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.particle.EffectRenderer;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.particles.VanillaParticles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EffectRenderer.class)
public class Mixin_CancelBreakingParticles {
    @Inject(
            method = "addBlockDestroyEffects",
            at = @At("HEAD"),
            cancellable = true
    )
    private void removeBlockBreakingParticles(CallbackInfo ci) {
        if (!PerParticleConfigManager.getConfigByType(VanillaParticles.BLOCKS).getEnabled() || PerParticleConfigManager.getBlockSetting().getHideDigging()) {
            ci.cancel();
        }
    }
}
