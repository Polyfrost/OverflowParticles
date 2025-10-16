package org.polyfrost.overflowparticles.mixin.client;

import com.mojang.blaze3d.vertex.BufferBuilder;
import org.polyfrost.overflowparticles.client.OverflowParticlesClient;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.particles.ParticleRegistry;
import org.polyfrost.overflowparticles.client.particles.VanillaParticles;
import org.polyfrost.overflowparticles.client.utils.ParticleSpawner;
import org.polyfrost.polyui.color.PolyColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.function.ToIntFunction;

@Mixin(BufferBuilder.class)
public abstract class Mixin_ApplyCustomParticleColors {
    //#if MC >= 1.21.1
    //$$ private static final String METHOD = "color(IIII)Lnet/minecraft/client/render/VertexConsumer;";
    //#else
    private static final String METHOD = "color";
    //#endif

    @ModifyVariable(method = METHOD, at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private int overflowparticles$adjustRed(int red) {
        return overflowparticles$adjust(red, PolyColor::red);
    }

    @ModifyVariable(method = METHOD, at = @At("HEAD"), argsOnly = true, ordinal = 1)
    private int overflowparticles$adjustGreen(int green) {
        return overflowparticles$adjust(green, PolyColor::green);
    }

    @ModifyVariable(method = METHOD, at = @At("HEAD"), argsOnly = true, ordinal = 2)
    private int overflowparticles$adjustBlue(int blue) {
        return overflowparticles$adjust(blue, PolyColor::blue);
    }

    @ModifyVariable(method = METHOD, at = @At("HEAD"), argsOnly = true, ordinal = 3)
    private int overflowparticles$adjustAlpha(int alpha) {
        return overflowparticles$adjust(alpha, PolyColor::alpha);
    }

    @Unique
    private static int overflowparticles$adjust(int original, ToIntFunction<PolyColor> pick) {
        if (!OverflowParticlesClient.isRendering()) {
            return original;
        }

        ParticleConfig config = PerParticleConfigManager.getConfig(OverflowParticlesClient.getRenderingEntity());
        if (config == null || config.getParticleType() == VanillaParticles.FOOTSTEP.getId()) {
            return original;
        }

        PolyColor color = config.getColor();
        int newColor = pick.applyAsInt(color);
        System.out.println(ParticleRegistry.location(config.getParticleType()) + " | Original: " + original + ", New: " + newColor);
        return ParticleSpawner.colorInt(newColor, original, config);
    }
}
