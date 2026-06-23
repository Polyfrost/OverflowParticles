package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.particle.Particle;
import net.minecraft.world.phys.AABB;
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >=1.16.5 {
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Final;
//?} else {
/*import net.minecraft.world.level.Level;
*///?}

//? if >=1.12.2 {
@Mixin(Particle.class)
//?} else {
/*@Mixin(Entity.class)
*///?}
public abstract class Mixin_ParticleNoClip {
    @Shadow
    //? if >=1.16.5 {
    @Final
    protected ClientLevel level;
    //?} else {
    /*protected Level level;
    *///?}

    @Inject(method = "move(DDD)V", at = @At("HEAD"), cancellable = true)
    private void enableNoClip(double x, double y, double z, CallbackInfo ci) {
        if (this.level != null && !this.level.isClientSide()) {
            return;
        }

        //? if <=1.8.9 {
        /*if (!(((Entity) (Object) this) instanceof Particle)) {
            return;
        }
        *///?}

        if (OverflowParticlesConfig.INSTANCE.getParticleNoClip()) {
            this.setBoundingBox(this.getBoundingBox().move(x, y, z));
            this.setLocationFromBoundingbox();
            ci.cancel();
        }
    }

    @Shadow public abstract AABB getBoundingBox();
    @Shadow public abstract void setBoundingBox(AABB bb);
    @Shadow protected abstract void setLocationFromBoundingbox();
}
