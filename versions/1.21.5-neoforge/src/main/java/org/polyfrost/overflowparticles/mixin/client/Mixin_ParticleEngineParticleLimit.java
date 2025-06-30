package org.polyfrost.overflowparticles.mixin.client;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Queue;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleGroup;
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParticleEngine.class)
public abstract class Mixin_ParticleEngineParticleLimit {

    @Shadow
    protected abstract boolean hasSpaceInParticleLimit(ParticleGroup arg);

    @Shadow
    @Final
    private Queue<Particle> particlesToAdd;

    @Shadow
    protected abstract void updateCount(ParticleGroup arg, int i);

    @Shadow
    @Final
    private Object2IntOpenHashMap<ParticleGroup> trackedParticleCounts;

    // not used just need a group to track the particle limit
    @Unique
    private final ParticleGroup overflowparticles$group = new ParticleGroup(1);

    @Inject(method = "add", at = @At("HEAD"), cancellable = true)
    private void overflowparticles$addParticle(Particle arg, CallbackInfo ci) {

        if (this.hasSpaceInParticleLimit(overflowparticles$group)) {
            this.particlesToAdd.add(arg);
            this.updateCount(this.overflowparticles$group, 1);
        } else {
            ci.cancel();
        }
    }

    @Inject(method = "hasSpaceInParticleLimit", at = @At("HEAD"), cancellable = true)
    private void overflowparticles$hasSpaceInParticleLimit(ParticleGroup arg, CallbackInfoReturnable<Boolean> cir) {

        cir.setReturnValue(this.trackedParticleCounts.getInt(arg) < OverflowParticlesConfig.INSTANCE.getMaxParticleLimit());
    }

}
