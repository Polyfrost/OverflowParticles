package org.polyfrost.overflowparticles.mixin.client;

//#if MC <= 1.12.2

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import org.polyfrost.overflowparticles.client.OverflowParticlesClient;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EffectRenderer.class)
public class Mixin_EffectRenderer_UpdateIconRendererState {

    @WrapOperation(method = "renderLitParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/EntityFX;renderParticle(Lnet/minecraft/client/renderer/WorldRenderer;Lnet/minecraft/entity/Entity;FFFFFF)V"))
    private void overflowparticles$updateRenderingEntityState(EntityFX instance, WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ, Operation<Void> original) {
        OverflowParticlesClient.setRenderingEntity(instance);
        ParticleConfig config = PerParticleConfigManager.getConfig(instance);
        if (config != null && !config.getEnabled() && config.getId() != 37) {
            return;
        }

        OverflowParticlesClient.setRendering(true);
        original.call(instance, worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
        OverflowParticlesClient.setRendering(false);
    }

    @SuppressWarnings({"MixinAnnotationTarget", "InvalidInjectorMethodSignature"})
    @ModifyVariable(method = "renderParticles", at = @At(value = "STORE"), ordinal = 0)
    private EntityFX overflowparticles$captureRenderingEntity(EntityFX entityFX) {
        OverflowParticlesClient.setRenderingEntity(entityFX);
        return entityFX;
    }

}
//#endif
