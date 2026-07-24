package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.Camera;
import net.minecraft.client.particle.Particle;
import org.polyfrost.overflowparticles.client.OverflowParticlesClient;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.particles.VanillaParticles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
//? if >=1.21.10 {
import net.minecraft.client.particle.SingleQuadParticle;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//?} else {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.particle.ParticleEngine;
*///?}

//? if >=1.21.10 {
@Mixin(SingleQuadParticle.class)
//?} else {
/*@Mixin(ParticleEngine.class)
*///?}
public class Mixin_TrackRenderState {
    //? if >=1.21.10 {
    @Inject(method = "extract", at = @At("HEAD"), cancellable = true)
    private void overflowparticles$updateRenderingEntityState(CallbackInfo ci) {
        Particle instance = (Particle) (Object) this;
        OverflowParticlesClient.setRenderingEntity(instance);
        ParticleConfig config = PerParticleConfigManager.getConfig(instance);
        if (config != null && !config.getEnabled() && config.getParticleType() != VanillaParticles.BLOCKS.getId()) {
            ci.cancel();
        }
    }
    //?} else {
    /*@WrapOperation(
            //? if <1.21.4 {
            /^method = "render(Lnet/minecraft/client/renderer/LightTexture;Lnet/minecraft/client/Camera;F)V",
            ^///?} else {
            method = "renderParticleType",
            //?}
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/particle/Particle;render(Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/client/Camera;F)V"
            )
    )
    //? if <1.21.4 {
    /^private void overflowparticles$updateRenderingEntityState(
    ^///?} else {
    private static void overflowparticles$updateRenderingEntityState(
    //?}
            Particle instance,
            VertexConsumer vertexConsumer,
            Camera camera,
            float tickDelta,
            Operation<Void> original
    ) {
        OverflowParticlesClient.setRenderingEntity(instance);
        ParticleConfig config = PerParticleConfigManager.getConfig(instance);
        if (config != null && !config.getEnabled() && config.getParticleType() != VanillaParticles.BLOCKS.getId()) {
            return;
        }

        if (config == null || config.getParticleType() == VanillaParticles.BLOCKS.getId()) {
            original.call(instance, vertexConsumer, camera, tickDelta);
            return;
        }

        OverflowParticlesClient.setRendering(true);
        OverflowParticlesClient.setRenderingEntity(instance);
        original.call(instance, vertexConsumer, camera, tickDelta);
        OverflowParticlesClient.setRendering(false);
    }
    *///?}
}
