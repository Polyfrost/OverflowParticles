package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.particle.Particle;
import net.minecraft.world.phys.AABB;
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Particle.class)
public abstract class Mixin_ParticleNoClip {

    @Shadow public abstract void setBoundingBox(AABB par1);

    @Shadow protected abstract void setLocationFromBoundingbox();

    @Shadow public abstract AABB getBoundingBox();

    @Inject(method = "move", at = @At("HEAD"), cancellable = true)
    private void overflowparticles$noClip(double x, double y, double z, CallbackInfo ci) {
        if (OverflowParticlesConfig.INSTANCE.getParticleNoClip()) {
            this.setBoundingBox(this.getBoundingBox().move(x, y, z));
            this.setLocationFromBoundingbox();
            ci.cancel();
        }
    }
}
