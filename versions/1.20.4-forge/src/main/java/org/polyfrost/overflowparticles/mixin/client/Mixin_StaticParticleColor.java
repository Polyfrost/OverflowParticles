package org.polyfrost.overflowparticles.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.deftu.omnicore.api.color.ColorFormat;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ParticleEngine.class)
public class Mixin_StaticParticleColor {
    @Unique private static final int STATIC_PARTICLE_COLOR = 15728880; // #F0F0F0

    @WrapOperation(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;Lnet/minecraft/client/renderer/LightTexture;Lnet/minecraft/client/Camera;FLnet/minecraft/client/renderer/culling/Frustum;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/Particle;render(Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/client/Camera;F)V"))
    private void overflowparticles$staticParticleColor(Particle instance, VertexConsumer vertexConsumer, Camera camera, float v, Operation<Void> original) {
        ColorFormat format = ColorFormat.RGBA;
        int red = format.red(STATIC_PARTICLE_COLOR);
        int green = format.green(STATIC_PARTICLE_COLOR);
        int blue = format.blue(STATIC_PARTICLE_COLOR);
        instance.setColor(red, green, blue);
    }
}
