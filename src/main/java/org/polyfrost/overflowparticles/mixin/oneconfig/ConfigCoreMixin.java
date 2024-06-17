package org.polyfrost.overflowparticles.mixin.oneconfig;

import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.internal.config.core.ConfigCore;
import org.polyfrost.overflowparticles.config.MainConfig;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = ConfigCore.class, remap = false)
public class ConfigCoreMixin {
    @Shadow public static List<Mod> mods;

    @Dynamic
    @Inject(method = "sortMods", at = @At(value = "INVOKE", target = "Lcc/polyfrost/oneconfig/internal/config/OneConfigConfig;save()V"))
    private static void sort(CallbackInfo ci) {
        mods.remove(MainConfig.INSTANCE.mod);
        mods.add(0, MainConfig.INSTANCE.mod);
    }
}
