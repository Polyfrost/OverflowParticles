package org.polyfrost.overflowparticles.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.Particle;
import org.polyfrost.overflowparticles.client.OverflowParticlesClient;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.particles.VanillaParticles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ParticleEngine.class)
public class Mixin_TrackRenderState {
    @WrapOperation(
            //#if MC >= 1.20.1 && FORGE-LIKE
            //#if MC >= 1.21.1
            //$$ method = "render(Lnet/minecraft/client/renderer/LightTexture;Lnet/minecraft/client/Camera;FLnet/minecraft/client/renderer/culling/Frustum;Ljava/util/function/Predicate;)V",
            //#else
            //$$ method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;Lnet/minecraft/client/renderer/LightTexture;Lnet/minecraft/client/Camera;FLnet/minecraft/client/renderer/culling/Frustum;)V",
            //#endif
            //#else
            //#if MC >= 1.21.4
            //$$ method = "renderParticles(Lnet/minecraft/client/render/Camera;FLnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/particle/ParticleTextureSheet;Ljava/util/Queue;)V",
            //#else
            method = "renderParticles",
            //#endif
            //#endif
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/particle/Particle;render(Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/client/Camera;F)V"
            )
    )
    private
    //#if MC >= 1.21.4
    //$$ static
    //#endif
    void overflowparticles$updateRenderingEntityState(
            Particle instance,
            VertexConsumer vertexConsumer,
            Camera camera,
            float tickDelta,
            Operation<Void> original
    ) {
        OverflowParticlesClient.setRenderingEntity(instance);
        ParticleConfig config = PerParticleConfigManager.getConfig(instance);
        if (config == null || !config.getEnabled() || config.getParticleType() == VanillaParticles.BLOCKS.getId()) {
            original.call(instance, vertexConsumer, camera, tickDelta);
            return;
        }
        
        OverflowParticlesClient.setRendering(true);
        OverflowParticlesClient.setRenderingEntity(instance);
        original.call(instance, vertexConsumer, camera, tickDelta);
        OverflowParticlesClient.setRendering(false);
    }
}
