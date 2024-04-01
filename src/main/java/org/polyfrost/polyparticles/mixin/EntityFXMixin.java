package org.polyfrost.polyparticles.mixin;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import org.polyfrost.polyparticles.config.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityFX.class)
public class EntityFXMixin {

    @Shadow protected float particleScale;
    @Unique private float scale;

    @Inject(method = "renderParticle", at = @At(value = "HEAD"))
    private void setScale(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ, CallbackInfo ci) {
        scale = particleScale;
        ParticleConfig config = ModConfig.INSTANCE.getConfig((EntityFX) (Object) this);
        if (config == null) return;
        particleScale *= config.getEntry().getSize();
    }

    @Inject(method = "renderParticle", at = @At(value = "TAIL"))
    private void reset(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ, CallbackInfo ci) {
        particleScale = scale;
    }

}
