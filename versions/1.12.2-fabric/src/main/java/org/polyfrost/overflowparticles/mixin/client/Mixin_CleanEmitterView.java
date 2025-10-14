package org.polyfrost.overflowparticles.mixin.client;

import dev.deftu.omnicore.api.client.OmniClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.EmitterParticle;
import net.minecraft.entity.Entity;
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EmitterParticle.class)
public abstract class Mixin_CleanEmitterView extends Particle {
    @Shadow @Final private Entity entity;

    public Mixin_CleanEmitterView() {
        super(null, 0, 0, 0, 0, 0, 0);
    }

    @Inject(method = "method_12241", at = @At("HEAD"), cancellable = true)
    public void cleanView(CallbackInfo ci) {
        System.out.println("Clean view check (" + OverflowParticlesConfig.isCleanView() + ", " + this.entity + ", " + (this.entity == OmniClient.getPlayer()) + ")");
        if (OverflowParticlesConfig.isCleanView() && this.entity == OmniClient.getPlayer()) {
            this.method_12251();
            ci.cancel();
        }
    }
}
