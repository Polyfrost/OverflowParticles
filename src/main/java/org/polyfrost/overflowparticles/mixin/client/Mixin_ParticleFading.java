package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.Camera;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.ParticleType;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.particles.ParticleInfo;
import org.polyfrost.overflowparticles.client.particles.ParticleRegistry;
import org.polyfrost.overflowparticles.client.particles.VanillaParticles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
//? if >=1.21.10 {
/*import net.minecraft.client.particle.SingleQuadParticle;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
*///?} else {
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.particle.ParticleEngine;
//?}

//? if >=1.21.10 {
/*@Mixin(SingleQuadParticle.class)
*///?} else {
@Mixin(ParticleEngine.class)
//?}
public abstract class Mixin_ParticleFading {
    //? if >=1.21.10 {
    /*@Inject(method = "extract", at = @At("HEAD"))
    private void overflowparticle$fade(CallbackInfo ci) {
        Particle instance = (Particle) (Object) this;
        overflowparticles$applyFade(instance);
    }
    *///?} else {
    @WrapOperation(
            //? if <1.21.4 {
            method = "render(Lnet/minecraft/client/renderer/LightTexture;Lnet/minecraft/client/Camera;F)V",
            //?} else {
            /*method = "render(Lnet/minecraft/client/Camera;FLnet/minecraft/client/renderer/MultiBufferSource$BufferSource;)V",
            *///?}
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/particle/Particle;render(Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/client/Camera;F)V"
            )
    )
    private void overflowparticle$fade(
            Particle instance,
            VertexConsumer vertexConsumer,
            Camera camera,
            float tickDelta,
            Operation<Void> original
    ) {
        overflowparticles$applyFade(instance);
        original.call(instance, vertexConsumer, camera, tickDelta);
    }
    //?}

    @Unique
    private static void overflowparticles$applyFade(Particle instance) {
        ParticleConfig config = PerParticleConfigManager.getConfig(instance);
        boolean fade = false;
        float fadeStart = 0.0f;

        if (config != null) {
            ParticleType<?> typeId = config.getParticleType();
            ParticleInfo info = ParticleRegistry.of(typeId);

            fade = config.getFade();
            fadeStart = config.getFadeStart();

            if (info == VanillaParticles.BLOCKS) {
                fade = PerParticleConfigManager.getBlockSetting().getFade();
                fadeStart = PerParticleConfigManager.getBlockSetting().getFadeStart();
            }

            if (info != null && info.isFireworkTriggered()) {
                fade = false;
            }
        }

        if (fade) {
            if (!(fadeStart >= 0.0f) || fadeStart >= 1.0f || Float.isInfinite(fadeStart)) {
                fadeStart = 0.0f;
            }

            int lifetime = instance.getLifetime();
            int age = ((Mixin_AccessParticleData) instance).getAge();
            if (lifetime > 0 && age >= 0) {
                float particleAge = (float) age / (float) lifetime;

                if (particleAge > fadeStart) {
                    float currentAlpha = ((Mixin_AccessBillboardParticleData) instance).getAlpha();

                    float newAlpha = overflowparticles$getNewAlpha(fadeStart, particleAge, currentAlpha);
                    if (!Float.isNaN(newAlpha) && newAlpha >= 0.0f && newAlpha != currentAlpha) {
                        ((Mixin_AccessBillboardParticleData) instance).overflowparticles$setAlpha(newAlpha);
                    }
                }
            }
        }
    }

    @Unique
    private static float overflowparticles$getNewAlpha(float fadeStart, float particleAge, float currentAlpha) {
        float denom = 1.0f - fadeStart;
        if (denom < 1e-6f) {
            denom = 1e-6f;
        }

        float t = (particleAge - fadeStart) / denom;
        if (t < 0.0f) t = 0.0f;
        if (t > 1.0f) t = 1.0f;

        return currentAlpha * (1.0f - t);
    }
}
