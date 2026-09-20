package org.polyfrost.overflowparticles.mixin.client;

//? if =1.8.9 {
/*import net.minecraft.world.entity.Entity;
import net.minecraft.world.World;
import org.polyfrost.overflowparticles.client.config.BlockParticleEntry;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.particles.VanillaParticles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class Mixin_CancelFootstepsIfNeeded {
    @Shadow public World world;

    @Inject(method = "doSprintingEffect", at = @At("HEAD"), cancellable = true)
    private void runningParticle(CallbackInfo ci) {
        if (this.world != null && !this.world.isClient) {
            return;
        }

        ParticleConfig config = PerParticleConfigManager.getConfigByType(VanillaParticles.BLOCKS);
        if (!config.getEnabled()) {
            ci.cancel();
        }

        BlockParticleEntry entry = PerParticleConfigManager.getBlockSetting();
        if (entry.getHideRunning()) {
            if (entry.getHideMode() == 1) {
                ci.cancel();
            } else if (!((Entity) (Object) this).isInvisible()) {
                ci.cancel();
            }
        }
    }
}
*///?} else {
// no-op above 1.12.2
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Entity.class)
public class Mixin_CancelFootstepsIfNeeded {
}
//?}
