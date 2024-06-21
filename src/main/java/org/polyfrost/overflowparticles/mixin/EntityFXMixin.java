package org.polyfrost.overflowparticles.mixin;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import org.polyfrost.overflowparticles.config.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityFX.class)
public abstract class EntityFXMixin {

    @Shadow protected float particleScale;
    @Shadow protected int particleAge;

    @Shadow public abstract float getAlpha();

    @Shadow public abstract void setAlphaF(float alpha);

    @Shadow protected int particleMaxAge;
    @Unique private float overflowParticles$scale;

    @Inject(method = "renderParticle", at = @At(value = "HEAD"))
    private void setScale(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ, CallbackInfo ci) {
        overflowParticles$scale = particleScale;
        ParticleConfig config = ModConfig.INSTANCE.getConfig((EntityFX) (Object) this);
        if (config == null) return;
        ParticleEntry entry = config.getEntry();
        particleScale *= entry.getSize();
        int id = entry.getID();

        float age = (float) particleAge / particleMaxAge;
        if ((id != 0 && id != 1 && id != 2 && id != 3 && id != 100) // fireworks
                && entry.getFade() && age > entry.getFadeStart()) {
            float alpha = 1 - age + entry.getFadeStart();
            if (getAlpha() != alpha) setAlphaF(alpha);
        }
    }

    @Inject(method = "renderParticle", at = @At(value = "RETURN"))
    private void reset(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ, CallbackInfo ci) {
        particleScale = overflowParticles$scale;
    }

}
