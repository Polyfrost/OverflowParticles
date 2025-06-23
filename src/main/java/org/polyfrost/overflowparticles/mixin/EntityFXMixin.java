package org.polyfrost.overflowparticles.mixin;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.hook.ParticleId;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityFX.class, priority = 1001) // after particlesehanced
public abstract class EntityFXMixin implements ParticleId {

    @Shadow protected float particleScale;
    @Shadow protected int particleAge;

    @Shadow public abstract float getAlpha();

    @Shadow public abstract void setAlphaF(float alpha);

    @Shadow protected int particleMaxAge;
    @Unique private float overflowparticles$scale;
    @Unique private int overflowParticles$ID;

    @Inject(method = "renderParticle", at = @At(value = "HEAD"))
    private void setScale(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ, CallbackInfo ci) {
        this.overflowparticles$scale = this.particleScale;
        ParticleConfig config = PerParticleConfigManager.getConfig((EntityFX) (Object) this);
        if (config == null) {
            return;
        }

        this.particleScale *= config.getSize();
        int id = config.getId();
        boolean fade = config.getFade();
        float fadeStart = config.getFadeStart();
        if (id == 37) {
            fade = PerParticleConfigManager.getBlockSetting().getFade();
            fadeStart = PerParticleConfigManager.getBlockSetting().getFadeStart();
        }

        float age = (float) this.particleAge / this.particleMaxAge;
        if ((id != 0 && id != 1 && id != 2 && id != 3 && id != 100) // fireworks
                && fade && age > fadeStart) {
            float alpha = 1 - age + fadeStart;
            if (getAlpha() != alpha) setAlphaF(alpha);
        }
    }

    @Inject(method = "renderParticle", at = @At(value = "RETURN"))
    private void reset(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ, CallbackInfo ci) {
        this.particleScale = this.overflowparticles$scale;
    }

    @Override
    public int overflowparticles$getParticleId() {
        return overflowParticles$ID;
    }

    @Override
    public void overflowparticles$setParticleId(int id) {
        overflowParticles$ID = id;
    }
}
