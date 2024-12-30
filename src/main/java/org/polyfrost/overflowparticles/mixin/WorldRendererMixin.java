package org.polyfrost.overflowparticles.mixin;

import net.minecraft.client.renderer.WorldRenderer;
import org.polyfrost.overflowparticles.OverflowParticles;
import org.polyfrost.overflowparticles.config.ConfigManager;
import org.polyfrost.overflowparticles.config.ParticleConfig;
import org.polyfrost.overflowparticles.utils.UtilKt;
import org.polyfrost.polyui.color.PolyColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

    @Shadow public abstract WorldRenderer color(int red, int green, int blue, int alpha);

    @Inject(method = "color(FFFF)Lnet/minecraft/client/renderer/WorldRenderer;", at =  @At("HEAD"), cancellable = true)
    private void color(float red, float green, float blue, float alpha, CallbackInfoReturnable<WorldRenderer> cir) {
        if (OverflowParticles.INSTANCE.getRendering()) {
            ParticleConfig config = ConfigManager.INSTANCE.getConfig(OverflowParticles.INSTANCE.getRenderingEntity());
            if (config != null && config.getId() != 28) {
                PolyColor c = config.getColor();
                cir.setReturnValue(this.color(UtilKt.colorInt(c.red(), red, config), UtilKt.colorInt(c.green(), green, config), UtilKt.colorInt(c.blue(), blue, config), UtilKt.colorInt(c.alpha(), alpha, config)));
            }
        }
    }
}
