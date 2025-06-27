package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.polyfrost.overflowparticles.client.config.BlockParticleEntry;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.utils.VanillaParticles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class Mixin_Entity_CancelFootstepsIfNeeded {

    @Shadow
    public World worldObj;

    @Inject(method = "createRunningParticles", at = @At("HEAD"), cancellable = true)
    private void runningParticle(CallbackInfo ci) {
        if (this.worldObj != null && !this.worldObj.isRemote) {
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
