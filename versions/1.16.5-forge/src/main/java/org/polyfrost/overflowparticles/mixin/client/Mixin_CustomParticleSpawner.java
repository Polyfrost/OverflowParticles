package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.level.Level;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.utils.ParticleSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Level.class, priority = 999)
public class Mixin_CustomParticleSpawner {

    @Inject(method = "addParticle(Lnet/minecraft/core/particles/ParticleOptions;ZDDDDDD)V", at = @At("HEAD"), cancellable = true)
    private void overflowparticles$useCustomSpawner(
            ParticleOptions options,
            boolean ignoreRange,
            //#if MC >= 1.21.4
            //$$ boolean idk,
            //#endif
            double xCoord,
            double yCoord,
            double zCoord,
            double xOffset,
            double yOffset,
            double zOffset,
            CallbackInfo ci
    ) {
        if (ParticleSpawner.isMultiplied()) {
            ParticleSpawner.setMultiplied(false);
            return;
        }

        ParticleType<?> type = options.getType();
        ParticleConfig config = PerParticleConfigManager.getConfigs().get(type);
        if (config == null || config.getMultiplier() == 1) {
            return;
        }
        
        ParticleSpawner.spawn(options, config, (Level) (Object) this, ignoreRange, xCoord, yCoord, zCoord, xOffset, yOffset, zOffset);
        ci.cancel();
    }
}
