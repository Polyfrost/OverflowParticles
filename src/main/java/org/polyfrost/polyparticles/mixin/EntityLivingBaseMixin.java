package org.polyfrost.polyparticles.mixin;

import cc.polyfrost.oneconfig.libs.universal.UMinecraft;
import net.minecraft.entity.EntityLivingBase;
import org.polyfrost.polyparticles.config.MainConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityLivingBase.class)
public class EntityLivingBaseMixin {
    @SuppressWarnings({"ConstantConditions"})
    @Inject(method = "updatePotionEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnParticle(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V"), cancellable = true)
    private void patcher$cleanView(CallbackInfo ci) {
        if (MainConfig.INSTANCE.getCleanView() && (Object) this == UMinecraft.getPlayer()) {
            ci.cancel();
        }
    }
}
