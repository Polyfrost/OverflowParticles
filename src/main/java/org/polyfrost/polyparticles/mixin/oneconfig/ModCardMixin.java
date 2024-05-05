package org.polyfrost.polyparticles.mixin.oneconfig;

import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.gui.elements.ModCard;
import cc.polyfrost.oneconfig.libs.universal.UChat;
import cc.polyfrost.oneconfig.renderer.NanoVGHelper;
import org.polyfrost.polyparticles.config.*;
import org.polyfrost.polyparticles.utils.IconRenderer;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ModCard.class, remap = false)
public class ModCardMixin {

    @Shadow @Final private Mod modData;

    @Inject(method = "drawIcon", at = @At("HEAD"), cancellable = true)
    private void drawICON(NanoVGHelper nanoVGHelper, long vg, float x, float y, String cleanName, CallbackInfo ci) {
        if (modData.config instanceof ParticleConfig) {
            int id = ((ParticleConfig) modData.config).getId();
            IconRenderer.INSTANCE.getParticleInfo().add(new IconRenderer.ParticleInfo(id, x + 94, y + 15));
            ci.cancel();
        }
    }

}
