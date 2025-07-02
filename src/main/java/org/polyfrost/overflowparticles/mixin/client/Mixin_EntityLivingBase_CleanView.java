package org.polyfrost.overflowparticles.mixin.client;

//#if MC <= 1.12.2

import dev.deftu.omnicore.client.OmniClientPlayer;
import net.minecraft.entity.EntityLivingBase;
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityLivingBase.class)
public class Mixin_EntityLivingBase_CleanView {

    @Inject(
            method = "updatePotionEffects",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;spawnParticle(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V"
            ),
            cancellable = true
    )
    private void overflowparticles$cleanView(CallbackInfo ci) {
        EntityLivingBase $this = (EntityLivingBase) (Object) this;
        if ($this != OmniClientPlayer.getInstance() || !OverflowParticlesConfig.isCleanView()) {
            return;
        }

        ci.cancel();
    }

}
//#endif
