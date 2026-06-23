package org.polyfrost.overflowparticles.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.particle.SingleQuadParticle;
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SingleQuadParticle.class)
public class Mixin_StaticParticleColor {
    // Full-bright packed lightmap value, used to skip per-frame particle lighting lookups.
    @Unique private static final int STATIC_PARTICLE_COLOR = 15728880;

    @WrapOperation(
            //? if >=26.1 {
            /*method = "extractRotatedQuad(Lnet/minecraft/client/renderer/state/level/QuadParticleRenderState;Lorg/joml/Quaternionf;FFFF)V",
            *///?} elif >=1.21.10 {
            /*method = "extractRotatedQuad(Lnet/minecraft/client/renderer/state/QuadParticleRenderState;Lorg/joml/Quaternionf;FFFF)V",
            *///?} else {
            method = "renderRotatedQuad(Lcom/mojang/blaze3d/vertex/VertexConsumer;Lorg/joml/Quaternionf;FFFF)V",
            //?}
            at = @At(
                    value = "INVOKE",
                    //? if >=26.1 {
                    /*target = "Lnet/minecraft/client/particle/SingleQuadParticle;getLightCoords(F)I"
                    *///?} else {
                    target = "Lnet/minecraft/client/particle/SingleQuadParticle;getLightColor(F)I"
                    //?}
            )
    )
    private int overflowparticles$staticParticleColor(SingleQuadParticle instance, float partialTicks, Operation<Integer> original) {
        return OverflowParticlesConfig.isStaticParticleColor()
                ? STATIC_PARTICLE_COLOR
                : original.call(instance, partialTicks);
    }
}
