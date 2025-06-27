package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.particle.EntityFX;
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityFX.class)
public class Mixin_EntityFX_StaticParticleColor {

    @Unique
    private static final int STATIC_PARTICLE_COLOR = 15728880; // #F0F0F0

    @Redirect(method = "renderParticle", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/EntityFX;getBrightnessForRender(F)I"))
    private int overflowparticles$staticParticleColor(EntityFX instance, float partialTicks) {
        return OverflowParticlesConfig.isStaticParticleColor()
                ? STATIC_PARTICLE_COLOR
                //#if MC >= 1.12.2
                //$$ : instance.method_12243(partialTicks);
                //#else
                : instance.getBrightnessForRender(partialTicks);
                //#endif
    }

}
