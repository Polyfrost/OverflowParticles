package org.polyfrost.overflowparticles.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleType;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.particles.ParticleInfo;
import org.polyfrost.overflowparticles.client.particles.ParticleRegistry;
import org.polyfrost.overflowparticles.client.particles.VanillaParticles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ParticleEngine.class)
public abstract class Mixin_ParticleFading {
    @WrapOperation(
            //#if FORGE-LIKE
            method = "renderParticleType(Lnet/minecraft/client/Camera;FLnet/minecraft/client/renderer/MultiBufferSource$BufferSource;Lnet/minecraft/client/particle/ParticleRenderType;Ljava/util/Queue;Lnet/minecraft/client/renderer/culling/Frustum;)V",
            //#else
            //$$ method = "renderParticles(Lnet/minecraft/client/render/Camera;FLnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/particle/ParticleTextureSheet;Ljava/util/Queue;)V",
            //#endif
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/particle/Particle;render(Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/client/Camera;F)V"
            )
    )
    private static void overflowparticle$fade(
            Particle instance,
            VertexConsumer vertexConsumer,
            Camera camera,
            float tickDelta,
            Operation<Void> original
    ) {
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
                    float currentAlpha = ((Mixin_AccessParticleData) instance).getAlpha();

                    float newAlpha = overflowparticles$getNewAlpha(fadeStart, particleAge, currentAlpha);
                    if (!Float.isNaN(newAlpha) && newAlpha >= 0.0f) {
                        if (newAlpha != currentAlpha) {
                            ((Mixin_AccessParticleData) instance).setAlpha(newAlpha);
                        }
                    }
                }
            }
        }

        // Always render exactly once
        original.call(instance, vertexConsumer, camera, tickDelta);
    }

    @Unique
    private static float overflowparticles$getNewAlpha(float fadeStart, float particleAge, float currentAlpha) {
        float denom = 1.0f - fadeStart;
        // Just in case denom is tiny due to user config:
        if (denom < 1e-6f) {
            denom = 1e-6f;
        }

        float t = (particleAge - fadeStart) / denom; // 0..1
        if (t < 0.0f) t = 0.0f;
        if (t > 1.0f) t = 1.0f;

        // Linear ramp: 1 -> 0
        float fadeFactor = 1.0f - t;

        // Multiply authored alpha rather than overwrite it
        float newAlpha = currentAlpha * fadeFactor;
        return newAlpha;
    }
}
