package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.client.particle.EntityFX;
import org.polyfrost.overflowparticles.hook.ParticleId;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EffectRenderer.class)
public class Mixin_EffectRenderer_FixBlockBreakingId {

    @Inject(
            //#if MC >= 1.12.2
            //$$ method = "method_12256",
            //#else
            method = "addEffect",
            //#endif
            at = @At("HEAD")
    )
    private void overflowparticles$checkDiggingEffects(EntityFX effect, CallbackInfo ci) {
        if (effect instanceof EntityDiggingFX) {
            ParticleId.setParticleId(effect, 37);
        }
    }

}
