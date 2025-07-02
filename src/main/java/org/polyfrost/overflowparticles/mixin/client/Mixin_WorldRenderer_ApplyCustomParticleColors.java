package org.polyfrost.overflowparticles.mixin.client;

//#if MC <= 1.12.2

import net.minecraft.client.renderer.WorldRenderer;
import org.polyfrost.overflowparticles.client.OverflowParticlesClient;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.utils.ParticleSpawner;
import org.polyfrost.polyui.color.PolyColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldRenderer.class)
public abstract class Mixin_WorldRenderer_ApplyCustomParticleColors {

    @Shadow public abstract WorldRenderer color(int red, int green, int blue, int alpha);

    @Inject(method = "color(FFFF)Lnet/minecraft/client/renderer/WorldRenderer;", at =  @At("HEAD"), cancellable = true)
    private void color(float red, float green, float blue, float alpha, CallbackInfoReturnable<WorldRenderer> cir) {
        if (OverflowParticlesClient.getRendering()) {
            ParticleConfig config = PerParticleConfigManager.getConfig(OverflowParticlesClient.getRenderingEntity());
            if (config != null && config.getId() != 28) {
                PolyColor c = config.getColor();
                cir.setReturnValue(this.color(
                        ParticleSpawner.colorInt(c.red(), red, config),
                        ParticleSpawner.colorInt(c.green(), green, config),
                        ParticleSpawner.colorInt(c.blue(), blue, config),
                        ParticleSpawner.colorInt(c.alpha(), alpha, config)
                ));
            }
        }
    }

}
//#endif
