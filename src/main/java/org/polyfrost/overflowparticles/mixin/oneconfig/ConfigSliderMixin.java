package org.polyfrost.overflowparticles.mixin.oneconfig;

import cc.polyfrost.oneconfig.config.annotations.Slider;
import cc.polyfrost.oneconfig.gui.elements.config.ConfigSlider;
import org.polyfrost.overflowparticles.OverflowParticles;
import org.polyfrost.overflowparticles.config.ParticleEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Field;
import java.util.Objects;

@Mixin(value = ConfigSlider.class, remap = false)
public class ConfigSliderMixin {

    @Inject(method = "create", at = @At("HEAD"), cancellable = true)
    private static void cap(Field field, Object parent, CallbackInfoReturnable<ConfigSlider> cir) {
        if (parent instanceof ParticleEntry) {
            Slider slider = field.getAnnotation(Slider.class);
            if (Objects.equals(slider.name(), "Size")) {
                ParticleEntry entry = (ParticleEntry) parent;
                int id = entry.getID();
                if (OverflowParticles.INSTANCE.getUnfair().contains(id)) {
                    cir.setReturnValue(new ConfigSlider(field, parent, slider.name(), slider.description(), slider.category(), slider.subcategory(), slider.min(), 1f, slider.step(), slider.instant()));
                }
            }
        }
    }

}
