package org.polyfrost.overflowparticles.mixin.client;

import dev.deftu.omnicore.api.client.OmniClient;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityParticleEmitter;
import net.minecraft.entity.Entity;
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityParticleEmitter.class)
public abstract class Mixin_CleanEmitterView extends EntityFX {
    @Shadow private Entity attachedEntity;

    public Mixin_CleanEmitterView() {
        super(null, 0, 0, 0, 0, 0, 0);
    }

    @Inject(method = "onUpdate", at = @At("HEAD"), cancellable = true)
    public void cleanView(CallbackInfo ci) {
        if (OverflowParticlesConfig.isCleanView() && this.attachedEntity == OmniClient.getPlayer()) {
            this.setDead();
            ci.cancel();
        }
    }
}
