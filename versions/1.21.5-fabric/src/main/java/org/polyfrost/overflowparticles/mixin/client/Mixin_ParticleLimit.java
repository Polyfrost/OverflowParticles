package org.polyfrost.overflowparticles.mixin.client;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Queue;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleGroup;
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParticleManager.class)
public abstract class Mixin_ParticleLimit {
    @Shadow @Final private Queue<Particle> newParticles;
    @Shadow @Final private Object2IntOpenHashMap<ParticleGroup> groupCounts;

    @Unique private final ParticleGroup overflowparticles$group = new ParticleGroup(1);

    @Inject(method = "addParticle(Lnet/minecraft/client/particle/Particle;)V", at = @At("HEAD"), cancellable = true)
    private void overflowparticles$addParticle(Particle arg, CallbackInfo ci) {
        if (this.canAdd(overflowparticles$group)) {
            this.newParticles.add(arg);
            this.addTo(this.overflowparticles$group, 1);
        } else {
            ci.cancel();
        }
    }

    @Inject(method = "canAdd", at = @At("HEAD"), cancellable = true)
    private void overflowparticles$hasSpaceInParticleLimit(ParticleGroup arg, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(this.groupCounts.getInt(arg) < OverflowParticlesConfig.INSTANCE.getMaxParticleLimit());
    }

    @Shadow protected abstract void addTo(ParticleGroup group, int i);
    @Shadow protected abstract boolean canAdd(ParticleGroup group);
}
