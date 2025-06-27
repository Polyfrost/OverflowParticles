package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import org.polyfrost.overflowparticles.hook.ParticleId;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EffectRenderer.class)
public class Mixin_EffectRenderer_SetParticleIds {

    @Unique
    private int overflowparticles$currentId;

    @Inject(method = "spawnEffectParticle", at = @At("HEAD"))
    private void overflowparticles$updateCurrentId(int particleId, double xCoord, double yCoord, double zCoord, double xSpeed, double p_178927_10_, double p_178927_12_, int[] p_178927_14_, CallbackInfoReturnable<EntityFX> cir) {
        overflowparticles$currentId = particleId;
    }

    @ModifyArg(
            method = "spawnEffectParticle",
            at = @At(
                    value = "INVOKE",
                    //#if MC >= 1.12.2
                    //$$ target = "Lnet/minecraft/client/particle/ParticleManager;method_12256(Lnet/minecraft/client/particle/Particle;)V",
                    //#else
                    target = "Lnet/minecraft/client/particle/EffectRenderer;addEffect(Lnet/minecraft/client/particle/EntityFX;)V",
                    //#endif
                    ordinal = 0
            )
    )
    private EntityFX overflowparticles$setId(EntityFX effect) {
        ParticleId.setParticleId(effect, overflowparticles$currentId);
        return effect;
    }

}
