package org.polyfrost.polyparticles.mixin.oneconfig;

import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.gui.OneConfigGui;
import cc.polyfrost.oneconfig.gui.elements.ModCard;
import cc.polyfrost.oneconfig.gui.pages.ModsPage;
import cc.polyfrost.oneconfig.renderer.NanoVGHelper;
import kotlin.Suppress;
import org.polyfrost.polyparticles.config.*;
import org.polyfrost.polyparticles.utils.IconRenderer;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Suppress(names = "UnstableAPIUsage")
@Mixin(value = ModCard.class, remap = false)
public class ModCardMixin {

    @Shadow @Final private Mod modData;

    @Inject(method = "onClick", at = @At(value = "INVOKE", target = "Lcc/polyfrost/oneconfig/gui/OneConfigGui;openPage(Lcc/polyfrost/oneconfig/gui/pages/Page;)V"), cancellable = true)
    private void page(CallbackInfo ci) {
        if (modData == ModConfig.INSTANCE.mod) {
            ModsPage page = new ParticlePage();
            ModConfig.INSTANCE.setPage(page);
            OneConfigGui.INSTANCE.openPage(page);
            ci.cancel();
        }
    }

    @Inject(method = "drawIcon", at = @At("HEAD"), cancellable = true)
    private void drawICON(NanoVGHelper nanoVGHelper, long vg, float x, float y, String cleanName, CallbackInfo ci) {
        if (modData.config instanceof ParticleConfig) {
            int id = ((ParticleConfig) modData.config).getId();
            IconRenderer.INSTANCE.getParticleInfo().add(new IconRenderer.ParticleInfo(id, x + 94, y + 15));
            ci.cancel();
        }
    }

}
