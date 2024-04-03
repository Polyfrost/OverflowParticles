package org.polyfrost.polyparticles.mixin;

import cc.polyfrost.oneconfig.config.core.OneColor;
import net.minecraft.client.renderer.WorldRenderer;
import org.polyfrost.polyparticles.PolyParticles;
import org.polyfrost.polyparticles.config.*;
import org.polyfrost.polyparticles.utils.UtilKt;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

    @Shadow public abstract WorldRenderer color(int red, int green, int blue, int alpha);

    @Inject(method = "color(FFFF)Lnet/minecraft/client/renderer/WorldRenderer;", at =  @At("HEAD"), cancellable = true)
    private void color(float red, float green, float blue, float alpha, CallbackInfoReturnable<WorldRenderer> cir) {
        if (PolyParticles.INSTANCE.getRendering()) {
            ParticleConfig config = ModConfig.INSTANCE.getConfig(PolyParticles.INSTANCE.getRenderingEntity());
            if (config != null && config.getId() != 28) {
                OneColor c = config.getEntry().getColor();
                cir.setReturnValue(this.color(UtilKt.colorInt(c.getRed(), red, config), UtilKt.colorInt(c.getGreen(), green, config), UtilKt.colorInt(c.getBlue(), blue, config), UtilKt.colorInt(c.getAlpha(), alpha, config)));
            }
        }
    }
}
