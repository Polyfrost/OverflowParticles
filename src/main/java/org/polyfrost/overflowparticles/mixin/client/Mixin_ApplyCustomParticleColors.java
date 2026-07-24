package org.polyfrost.overflowparticles.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.Particle;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.particles.VanillaParticles;
import org.polyfrost.overflowparticles.client.utils.ParticleSpawner;
import org.polyfrost.compose.render.PolyColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.ToIntFunction;

@Mixin(SingleQuadParticle.class)
public abstract class Mixin_ApplyCustomParticleColors {
    //? if forge_like {
    /*@ModifyExpressionValue(method = "renderVertex", at = @At(value = "FIELD", target = "Lnet/minecraft/client/particle/SingleQuadParticle;rCol:F"))
    private float overflowparticles$redirectRCol(float original) {
        return overflowparticles$adjust((Particle) (Object) this, original, PolyColor::getRed);
    }

    @ModifyExpressionValue(method = "renderVertex", at = @At(value = "FIELD", target = "Lnet/minecraft/client/particle/SingleQuadParticle;gCol:F"))
    private float overflowparticles$redirectGCol(float original) {
        return overflowparticles$adjust((Particle) (Object) this, original, PolyColor::getGreen);
    }

    @ModifyExpressionValue(method = "renderVertex", at = @At(value = "FIELD", target = "Lnet/minecraft/client/particle/SingleQuadParticle;bCol:F"))
    private float overflowparticles$redirectBCol(float original) {
        return overflowparticles$adjust((Particle) (Object) this, original, PolyColor::getBlue);
    }

    @ModifyExpressionValue(method = "renderVertex", at = @At(value = "FIELD", target = "Lnet/minecraft/client/particle/Particle;alpha:F"))
    private float overflowparticles$redirectAlpha(float original) {
        return overflowparticles$adjust((Particle) (Object) this, original, PolyColor::getAlpha);
    }
    *///?} else {
    @ModifyExpressionValue(
            //? if >=26.1 {
            method = "extractRotatedQuad(Lnet/minecraft/client/renderer/state/level/QuadParticleRenderState;Lorg/joml/Quaternionf;FFFF)V",
            //?} elif >=1.21.10 {
            /*method = "extractRotatedQuad(Lnet/minecraft/client/renderer/state/QuadParticleRenderState;Lorg/joml/Quaternionf;FFFF)V",
            *///?} else {
            /*method = "renderVertex",
            *///?}
            at = @At(
                    value = "FIELD",
                        //? if >=1.20.1 {
                        target = "Lnet/minecraft/client/particle/SingleQuadParticle;rCol:F"
                        //?} else {
                        /*target = "Lnet/minecraft/client/particle/SingleQuadParticle;colorRed:F"
                        *///?}
            )
    )
    private float overflowparticles$redirectRCol(float original) {
        return overflowparticles$adjust((Particle) (Object) this, original, PolyColor::getRed);
    }

    @ModifyExpressionValue(
            //? if >=26.1 {
            method = "extractRotatedQuad(Lnet/minecraft/client/renderer/state/level/QuadParticleRenderState;Lorg/joml/Quaternionf;FFFF)V",
            //?} elif >=1.21.10 {
            /*method = "extractRotatedQuad(Lnet/minecraft/client/renderer/state/QuadParticleRenderState;Lorg/joml/Quaternionf;FFFF)V",
            *///?} else {
            /*method = "renderVertex",
            *///?}
            at = @At(
                    value = "FIELD",
                        //? if >=1.20.1 {
                        target = "Lnet/minecraft/client/particle/SingleQuadParticle;gCol:F"
                        //?} else {
                        /*target = "Lnet/minecraft/client/particle/SingleQuadParticle;colorGreen:F"
                        *///?}
            )
    )
    private float overflowparticles$redirectGCol(float original) {
        return overflowparticles$adjust((Particle) (Object) this, original, PolyColor::getGreen);
    }

    @ModifyExpressionValue(
            //? if >=26.1 {
            method = "extractRotatedQuad(Lnet/minecraft/client/renderer/state/level/QuadParticleRenderState;Lorg/joml/Quaternionf;FFFF)V",
            //?} elif >=1.21.10 {
            /*method = "extractRotatedQuad(Lnet/minecraft/client/renderer/state/QuadParticleRenderState;Lorg/joml/Quaternionf;FFFF)V",
            *///?} else {
            /*method = "renderVertex",
            *///?}
            at = @At(
                    value = "FIELD",
                        //? if >=1.20.1 {
                        target = "Lnet/minecraft/client/particle/SingleQuadParticle;bCol:F"
                        //?} else {
                        /*target = "Lnet/minecraft/client/particle/SingleQuadParticle;colorBlue:F"
                        *///?}
            )
    )
    private float overflowparticles$redirectBCol(float original) {
        return overflowparticles$adjust((Particle) (Object) this, original, PolyColor::getBlue);
    }

    @ModifyExpressionValue(
            //? if >=26.1 {
            method = "extractRotatedQuad(Lnet/minecraft/client/renderer/state/level/QuadParticleRenderState;Lorg/joml/Quaternionf;FFFF)V",
            //?} elif >=1.21.10 {
            /*method = "extractRotatedQuad(Lnet/minecraft/client/renderer/state/QuadParticleRenderState;Lorg/joml/Quaternionf;FFFF)V",
            *///?} else {
            /*method = "renderVertex",
            *///?}
            at = @At(
                    value = "FIELD",
                        //? if >=1.20.1 {
                        target = "Lnet/minecraft/client/particle/SingleQuadParticle;alpha:F"
                        //?} else {
                        /*target = "Lnet/minecraft/client/particle/SingleQuadParticle;colorAlpha:F"
                        *///?}
            )
    )
    private float overflowparticles$redirectAlpha(float original) {
        return overflowparticles$adjust((Particle) (Object) this, original, PolyColor::getAlpha);
    }
    //?}

    @Unique
    private static float overflowparticles$adjust(Particle particle, float original, ToIntFunction<PolyColor> pick) {
        ParticleConfig config = PerParticleConfigManager.getConfig(particle);
        if (config == null || config.getParticleType() == VanillaParticles.FOOTSTEP.getId()) {
            return original;
        }

        PolyColor color = config.getColor();
        int newColor = pick.applyAsInt(color);
        // System.out.println(ParticleRegistry.location(config.getParticleType()) + " | Original: " + original + ", New: " + newColor);
        return ParticleSpawner.color(newColor, original, config);
    }
}
