package org.polyfrost.polyparticles.mixin;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import org.polyfrost.polyparticles.config.MainConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    @SuppressWarnings({"ConstantConditions"})
    @Inject(method = "getBrightnessForRender", at = @At("HEAD"), cancellable = true)
    private void staticColor(float partialTicks, CallbackInfoReturnable<Integer> cir) {
        if (MainConfig.INSTANCE.getStaticParticleColor() && ((Entity) (Object) this) instanceof EntityFX) {
            cir.setReturnValue(15728880);
        }
    }
}
