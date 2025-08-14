package org.polyfrost.overflowparticles.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import java.util.Queue;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.polyui.color.PolyColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleEngine.class)
public class Mixin_ParticleColor {

    @Inject(
            method = "renderParticleType(Lnet/minecraft/client/Camera;FLnet/minecraft/client/renderer/MultiBufferSource$BufferSource;Lnet/minecraft/client/particle/ParticleRenderType;Ljava/util/Queue;Lnet/minecraft/client/renderer/culling/Frustum;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/Particle;render(Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/client/Camera;F)V")
    )
    private static void overflowparticles$setParticleColor(Camera arg, float f, MultiBufferSource.BufferSource arg2, ParticleRenderType arg3, Queue<Particle> queue, Frustum frustum, CallbackInfo ci, @Local Particle particle) {


        ParticleConfig config = PerParticleConfigManager.getConfig(particle);
        if (config != null && config.getCustomColor()) {
            PolyColor color = config.getColor();

            System.out.println("Setting particle color: " + color);

            float r = color.red() / 255.0F;
            float g = color.green() / 255.0F;
            float b = color.blue() / 255.0F;
            particle.setColor(r, g, b);
        }
    }

}
