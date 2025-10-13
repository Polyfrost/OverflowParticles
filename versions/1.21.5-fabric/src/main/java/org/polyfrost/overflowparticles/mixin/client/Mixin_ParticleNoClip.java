package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.Box;
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Particle.class)
public abstract class Mixin_ParticleNoClip {
    @Inject(method = "move(DDD)V", at = @At("HEAD"), cancellable = true)
    private void overflowparticles$noClip(double x, double y, double z, CallbackInfo ci) {
        if (OverflowParticlesConfig.INSTANCE.getParticleNoClip()) {
            this.setBoundingBox(this.getBoundingBox().offset(x, y, z));
            this.repositionFromBoundingBox();
            ci.cancel();
        }
    }

    @Shadow public abstract Box getBoundingBox();
    @Shadow public abstract void setBoundingBox(Box box);
    @Shadow protected abstract void repositionFromBoundingBox();
}
