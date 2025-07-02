package org.polyfrost.overflowparticles.mixin.client;

//#if MC <= 1.12.2

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityFX.class, priority = 1001) // After ParticlesEnhanced
public class Mixin_EntityFX_ParticleScaling {

    @Shadow
    protected float particleScale;

    @Unique
    private float overflowparticles$scale;

    @Inject(method = "renderParticle", at = @At(value = "HEAD"))
    private void overflowparticles$overrideScale(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ, CallbackInfo ci) {
        this.overflowparticles$scale = this.particleScale;
        ParticleConfig config = PerParticleConfigManager.getConfig((EntityFX) (Object) this);
        if (config == null) {
            return;
        }

        this.particleScale *= config.getSize();
    }

    @Inject(method = "renderParticle", at = @At(value = "RETURN"))
    private void overflowparticles$resetScale(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ, CallbackInfo ci) {
        this.particleScale = this.overflowparticles$scale;
    }

}
//#endif
