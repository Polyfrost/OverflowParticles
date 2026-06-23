package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.particles.VanillaParticles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >=1.21.10 {
/*@Mixin(ClientLevel.class)
*///?} else {
@Mixin(ParticleEngine.class)
//?}
public class Mixin_CancelBreakingParticles {
    @Inject(
            //? if >=1.21.10 {
            /*method = "addDestroyBlockEffect",
            *///?} else {
            method = "destroy",
            //?}
            at = @At("HEAD"),
            cancellable = true
    )
    private void overflowparticles$cancelBlockDestroy(CallbackInfo ci) {
        if (!PerParticleConfigManager.getConfigByType(VanillaParticles.BLOCKS).getEnabled() || PerParticleConfigManager.getBlockSetting().getHideDigging()) {
            ci.cancel();
        }
    }
}
