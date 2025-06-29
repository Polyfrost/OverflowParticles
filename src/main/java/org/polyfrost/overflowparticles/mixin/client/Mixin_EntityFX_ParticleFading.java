package org.polyfrost.overflowparticles.mixin.client;

//#if MC <= 1.12.2

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import org.polyfrost.overflowparticles.client.utils.ParticleData;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.utils.VanillaParticles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityFX.class, priority = 1001) // After ParticlesEnhanced
public abstract class Mixin_EntityFX_ParticleFading {

    @Shadow
    protected int particleAge;

    @Shadow
    protected int particleMaxAge;

    @Shadow
    public abstract float getAlpha();

    @Shadow
    public abstract void setAlphaF(float alpha);

    @Inject(method = "renderParticle", at = @At(value = "HEAD"))
    private void setScale(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ, CallbackInfo ci) {
        ParticleConfig config = PerParticleConfigManager.getConfig((EntityFX) (Object) this);
        if (config == null) {
            return;
        }

        int id = config.getId();
        ParticleData type = ParticleData.of(id);
        if (type == null) {
            return;
        }

        boolean fade = config.getFade();
        float fadeStart = config.getFadeStart();
        if (type == VanillaParticles.BLOCKS) {
            fade = PerParticleConfigManager.getBlockSetting().getFade();
            fadeStart = PerParticleConfigManager.getBlockSetting().getFadeStart();
        }

        float age = (float) this.particleAge / this.particleMaxAge;
        if (!type.isFireworkTriggered() && fade && age > fadeStart) {
            float alpha = 1 - age + fadeStart;
            if (getAlpha() != alpha) {
                setAlphaF(alpha);
            }
        }
    }

}
//#endif
