package org.polyfrost.overflowparticles.mixin.oneconfig;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = Minecraft.class, remap = false)
public class ModCardMixin {

    //@Shadow @Final private Mod modData;
//
    //@Inject(method = "drawIcon", at = @At("HEAD"), cancellable = true)
    //private void drawICON(NanoVGHelper nanoVGHelper, long vg, float x, float y, String cleanName, CallbackInfo ci) {
    //    if (modData.config instanceof ParticleConfig) {
    //        int id = ((ParticleConfig) modData.config).getId();
    //        IconRenderer.INSTANCE.getParticleInfo().add(new IconRenderer.ParticleInfo(id, x + 94, y + 15));
    //        ci.cancel();
    //    }
    //}

}
