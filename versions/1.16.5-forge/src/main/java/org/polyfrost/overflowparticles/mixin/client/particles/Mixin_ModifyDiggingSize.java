package org.polyfrost.overflowparticles.mixin.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TerrainParticle.class)
public abstract class Mixin_ModifyDiggingSize extends SingleQuadParticle {
    protected Mixin_ModifyDiggingSize(ClientLevel arg, double d, double e, double f) {
        super(arg, d, e, f);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void overflowparticles$scale(CallbackInfo ci) {
        ParticleConfig config = PerParticleConfigManager.getConfig(this);
        if (config == null) {
            return;
        }

        this.quadSize *= config.getSize();
    }
}
