package org.polyfrost.overflowparticles.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.particle.Particle;
//? if >1.8.9
import net.minecraft.client.particle.SingleQuadParticle;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//? if >1.8.9 {
@Mixin(value = SingleQuadParticle.class)
//?} else
//@Mixin(value = Particle.class)
public class Mixin_ParticleScaling {
    // Wraps the call site instead of injecting into getQuadSize so subclass overrides
    // (e.g. CritParticle) are scaled too
    @ModifyExpressionValue(
            //? if >=26.1 {
            method = "extractRotatedQuad(Lnet/minecraft/client/renderer/state/level/QuadParticleRenderState;Lorg/joml/Quaternionf;FFFF)V",
            //?} else if >=1.21.10 {
            /*method = "extractRotatedQuad(Lnet/minecraft/client/renderer/state/QuadParticleRenderState;Lorg/joml/Quaternionf;FFFF)V",
            *///?} elif >1.8.9 {
            /*method = "renderRotatedQuad(Lcom/mojang/blaze3d/vertex/VertexConsumer;Lorg/joml/Quaternionf;FFFF)V",
            *///?} else {
            /*method = "render",
            *///?}
            //? if >1.8.9 {
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/SingleQuadParticle;getQuadSize(F)F")
            //?} else
            //at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/particle/Particle;size:F")
    )
    private float overflowparticles$overrideScale(float original) {
        ParticleConfig config = PerParticleConfigManager.getConfig((Particle) (Object) this);
        if (config == null) {
            return original;
        }

        return original * config.getSize();
    }
}
