package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.particle.SingleQuadParticle;
import org.polyfrost.overflowparticles.client.OverflowParticlesClient;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.particles.VanillaParticles;
import org.polyfrost.overflowparticles.client.utils.ParticleSpawner;
import org.polyfrost.polyui.color.PolyColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.ToIntFunction;

@Mixin(SingleQuadParticle.class)
public abstract class Mixin_ApplyCustomParticleColors {
    //#if FORGE-LIKE
    @Redirect(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/particle/SingleQuadParticle;rCol:F"))
    private float overflowparticles$redirectRCol(SingleQuadParticle instance) {
        return overflowparticles$adjust(((Mixin_AccessParticleData) instance).getRCol(), PolyColor::red);
    }

    @Redirect(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/particle/SingleQuadParticle;gCol:F"))
    private float overflowparticles$redirectGCol(SingleQuadParticle instance) {
        return overflowparticles$adjust(((Mixin_AccessParticleData) instance).getGCol(), PolyColor::green);
    }

    @Redirect(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/particle/SingleQuadParticle;bCol:F"))
    private float overflowparticles$redirectBCol(SingleQuadParticle instance) {
        return overflowparticles$adjust(((Mixin_AccessParticleData) instance).getBCol(), PolyColor::blue);
    }

    @Redirect(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/particle/SingleQuadParticle;alpha:F"))
    private float overflowparticles$redirectAlpha(SingleQuadParticle instance) {
        return overflowparticles$adjust(((Mixin_AccessParticleData) instance).getAlpha(), PolyColor::alpha);
    }
    //#else
    //$$ @Redirect(
    //$$         method = "buildGeometry",
    //$$         at = @At(
    //$$                 value = "FIELD",
                        //#if MC >= 1.20.1
                        //$$ target = "Lnet/minecraft/client/particle/BillboardParticle;red:F"
                        //#else
                        //$$ target = "Lnet/minecraft/client/particle/BillboardParticle;colorRed:F"
                        //#endif
    //$$         )
    //$$ )
    //$$ private float overflowparticles$redirectRCol(BillboardParticle instance) {
    //$$     return overflowparticles$adjust(((Mixin_AccessParticleData) instance).getRCol(), PolyColor::red);
    //$$ }
    //$$
    //$$ @Redirect(
    //$$         method = "buildGeometry",
    //$$         at = @At(
    //$$                 value = "FIELD",
                        //#if MC >= 1.20.1
                        //$$ target = "Lnet/minecraft/client/particle/BillboardParticle;green:F"
                        //#else
                        //$$ target = "Lnet/minecraft/client/particle/BillboardParticle;colorGreen:F"
                        //#endif
    //$$         )
    //$$ )
    //$$ private float overflowparticles$redirectGCol(BillboardParticle instance) {
    //$$     return overflowparticles$adjust(((Mixin_AccessParticleData) instance).getGCol(), PolyColor::green);
    //$$ }
    //$$
    //$$ @Redirect(
    //$$         method = "buildGeometry",
    //$$         at = @At(
    //$$                 value = "FIELD",
                        //#if MC >= 1.20.1
                        //$$ target = "Lnet/minecraft/client/particle/BillboardParticle;blue:F"
                        //#else
                        //$$ target = "Lnet/minecraft/client/particle/BillboardParticle;colorBlue:F"
                        //#endif
    //$$         )
    //$$ )
    //$$ private float overflowparticles$redirectBCol(BillboardParticle instance) {
    //$$     return overflowparticles$adjust(((Mixin_AccessParticleData) instance).getBCol(), PolyColor::blue);
    //$$ }
    //$$
    //$$ @Redirect(
    //$$         method = "buildGeometry",
    //$$         at = @At(
    //$$                 value = "FIELD",
                        //#if MC >= 1.20.1
                        //$$ target = "Lnet/minecraft/client/particle/BillboardParticle;alpha:F"
                        //#else
                        //$$ target = "Lnet/minecraft/client/particle/BillboardParticle;colorAlpha:F"
                        //#endif
    //$$         )
    //$$ )
    //$$ private float overflowparticles$redirectAlpha(BillboardParticle instance) {
    //$$     return overflowparticles$adjust(((Mixin_AccessParticleData) instance).getAlpha(), PolyColor::alpha);
    //$$ }
    //#endif

    @Unique
    private static float overflowparticles$adjust(float original, ToIntFunction<PolyColor> pick) {
        if (!OverflowParticlesClient.isRendering()) {
            return original;
        }

        ParticleConfig config = PerParticleConfigManager.getConfig(OverflowParticlesClient.getRenderingEntity());
        if (config == null || config.getParticleType() == VanillaParticles.FOOTSTEP.getId()) {
            return original;
        }

        PolyColor color = config.getColor();
        int newColor = pick.applyAsInt(color);
        // System.out.println(ParticleRegistry.location(config.getParticleType()) + " | Original: " + original + ", New: " + newColor);
        return ParticleSpawner.color(newColor, original, config);
    }
}
