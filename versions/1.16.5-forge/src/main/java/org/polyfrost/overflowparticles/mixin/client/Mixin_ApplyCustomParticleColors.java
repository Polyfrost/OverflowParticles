package org.polyfrost.overflowparticles.mixin.client;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.polyfrost.overflowparticles.client.OverflowParticlesClient;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.particles.VanillaParticles;
import org.polyfrost.overflowparticles.client.utils.ParticleSpawner;
import org.polyfrost.polyui.color.PolyColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BufferBuilder.class)
public abstract class Mixin_ApplyCustomParticleColors {
    @Inject(
            //#if MC >= 1.21.1
            //$$ method = "color(IIII)Lnet/minecraft/client/render/VertexConsumer;",
            //#else
            method = "color",
            //#endif
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void overflowparticles$setParticleColor(int red, int green, int blue, int alpha, CallbackInfoReturnable<VertexConsumer> cir) {
        if (!OverflowParticlesClient.isRendering()) {
            return;
        }

        ParticleConfig config = PerParticleConfigManager.getConfig(OverflowParticlesClient.getRenderingEntity());
        if (config == null || config.getParticleType() == VanillaParticles.FOOTSTEP.getId()) {
            return;
        }

        PolyColor c = config.getColor();
        cir.setReturnValue(this.color(
                ParticleSpawner.colorInt(c.red(), red, config),
                ParticleSpawner.colorInt(c.green(), green, config),
                ParticleSpawner.colorInt(c.blue(), blue, config),
                ParticleSpawner.colorInt(c.alpha(), alpha, config)
        ));
    }

    @Shadow public abstract VertexConsumer color(int red, int green, int blue, int alpha);
}
