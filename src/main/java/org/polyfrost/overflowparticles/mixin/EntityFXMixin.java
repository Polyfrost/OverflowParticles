package org.polyfrost.overflowparticles.mixin;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import org.polyfrost.overflowparticles.config.*;
import org.polyfrost.overflowparticles.hook.EntityFXHook;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityFX.class, priority = 1001) // after particlesehanced
public abstract class EntityFXMixin implements EntityFXHook {

    @Shadow protected float particleScale;
    @Shadow protected int particleAge;

    @Shadow public abstract float getAlpha();

    @Shadow public abstract void setAlphaF(float alpha);

    @Shadow protected int particleMaxAge;
    @Unique private float overflowParticles$scale;
    @Unique private int overflowParticles$ID;

    @Inject(method = "renderParticle", at = @At(value = "HEAD"))
    private void setScale(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ, CallbackInfo ci) {
        overflowParticles$scale = particleScale;
        ParticleConfig config = ModConfig.INSTANCE.getConfig((EntityFX) (Object) this);
        if (config == null) return;
        ParticleEntry entry = config.getEntry();
        particleScale *= entry.getSize();
        int id = config.getId();
        boolean fade = entry.getFade();
        float fadeStart = entry.getFadeStart();
        if (id == 37) {
            fade = ModConfig.INSTANCE.getBlockSetting().getFade();
            fadeStart = ModConfig.INSTANCE.getBlockSetting().getFadeStart();
        }

        float age = (float) particleAge / particleMaxAge;
        if ((id != 0 && id != 1 && id != 2 && id != 3 && id != 100) // fireworks
                && fade && age > fadeStart) {
            float alpha = 1 - age + fadeStart;
            if (getAlpha() != alpha) setAlphaF(alpha);
        }
    }

    @Inject(method = "renderParticle", at = @At(value = "RETURN"))
    private void reset(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ, CallbackInfo ci) {
        particleScale = overflowParticles$scale;
    }

    @Override
    public int overflowParticles$getParticleID() {
        return overflowParticles$ID;
    }

    @Override
    public void overflowParticles$setParticleID(int id) {
        overflowParticles$ID = id;
    }
}
