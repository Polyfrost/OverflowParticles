package org.polyfrost.overflowparticles.mixin.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HugeExplosionParticle.class)
public abstract class Mixin_ModifyLargeExplosionSize extends Particle {
    protected Mixin_ModifyLargeExplosionSize(ClientLevel arg, double d, double e, double f) {
        super(arg, d, e, f);
    }

    @Inject(method = "<init>(Lnet/minecraft/client/multiplayer/ClientLevel;DDDDLnet/minecraft/client/particle/SpriteSet;)V", at = @At("RETURN"))
    private void overflowparticles$scale(CallbackInfo ci) {
        ParticleConfig config = PerParticleConfigManager.getConfig(this);
        if (config == null) {
            return;
        }
        
        this.scale(config.getSize());
    }
}
