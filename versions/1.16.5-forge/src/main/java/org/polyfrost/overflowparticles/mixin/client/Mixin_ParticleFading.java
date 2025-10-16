package org.polyfrost.overflowparticles.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.Particle;
import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.particles.ParticleInfo;
import org.polyfrost.overflowparticles.client.particles.ParticleRegistry;
import org.polyfrost.overflowparticles.client.particles.VanillaParticles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleEngine.class)
public abstract class Mixin_ParticleFading {
    @WrapOperation(method = "renderParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/Particle;render(Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/client/Camera;F)V", shift = At.Shift.BEFORE))
    private void overflowparticle$fade(
            Particle instance,
            BufferBuilder buffer,
            Camera camera,
            Operation<Void> original
    ) {
        ParticleConfig config = PerParticleConfigManager.getConfig((Particle) (Object) this);
        if (config == null) {
            original.call(instance, buffer, camera);
            return;
        }

        ParticleType<?> id = config.getParticleType();
        ParticleInfo type = ParticleRegistry.of(id);
        if (type == null) {
            return;
        }

        boolean fade = config.getFade();
        float fadeStart = config.getFadeStart();
        if (type == VanillaParticles.BLOCKS) {
            fade = PerParticleConfigManager.getBlockSetting().getFade();
            fadeStart = PerParticleConfigManager.getBlockSetting().getFadeStart();
        }

        int age = ((Mixin_AccessParticleData) instance).getAge();
        int lifetime = instance.getLifetime();
        float particleAge = (float) age / lifetime;
        if (!type.isFireworkTriggered() && fade && particleAge > fadeStart) {
            float particleAlpha = ((Mixin_AccessParticleData) instance).getAlpha();
            float alpha = 1 - particleAge + fadeStart;
            if (particleAlpha != alpha) {
                ((Mixin_AccessParticleData) instance).setAlpha(alpha);
            }
        }

        original.call(instance, buffer, camera);
    }
}
