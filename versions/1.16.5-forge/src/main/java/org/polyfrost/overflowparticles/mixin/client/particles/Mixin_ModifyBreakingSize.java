package org.polyfrost.overflowparticles.mixin.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BreakingItemParticle.class)
public abstract class Mixin_ModifyBreakingSize extends SingleQuadParticle {
    protected Mixin_ModifyBreakingSize(ClientLevel arg, double d, double e, double f) {
        super(arg, d, e, f);
    }

    @Inject(method = "<init>(Lnet/minecraft/client/multiplayer/ClientLevel;DDDLnet/minecraft/world/item/ItemStack;)V", at = @At("RETURN"))
    private void overflowparticles$scale(CallbackInfo ci) {
        ParticleConfig config = PerParticleConfigManager.getConfig(this);
        if (config == null) {
            return;
        }
        
        this.quadSize *= config.getSize();
    }
}
