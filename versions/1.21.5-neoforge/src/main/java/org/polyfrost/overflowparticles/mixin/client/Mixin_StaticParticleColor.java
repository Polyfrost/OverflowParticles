package org.polyfrost.overflowparticles.mixin.client;

//#if FABRIC
//$$ import net.minecraft.client.particle.BillboardParticle;
//#else
import net.minecraft.client.particle.SingleQuadParticle;
//#endif
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig;
import org.polyfrost.overflowparticles.mixin.client.accessor.Accessor_Particle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

//#if FABRIC
//$$ @Mixin(BillboardParticle.class)
//#else
@Mixin(SingleQuadParticle.class)
//#endif
public class Mixin_StaticParticleColor {

    @Unique
    private static final int STATIC_PARTICLE_COLOR = 15728880; // #F0F0F0

    //#if FABRIC
    //$$ @Redirect(
    //$$       method = "render(Lnet/minecraft/client/render/VertexConsumer;Lorg/joml/Quaternionf;FFFF)V",
    //$$       at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/BillboardParticle;getBrightness(F)I")
    //$$   )
    //#else
    @Redirect(
        method = "renderRotatedQuad(Lcom/mojang/blaze3d/vertex/VertexConsumer;Lorg/joml/Quaternionf;FFFF)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/SingleQuadParticle;getLightColor(F)I")
    )
    //#endif
    //#if FABRIC
    //$$ private int overflowparticles$staticParticleColor(BillboardParticle instance, float v) {
    //#else
    private int overflowparticles$staticParticleColor(SingleQuadParticle instance, float v) {
        //#endif
        return OverflowParticlesConfig.isStaticParticleColor()
            ? STATIC_PARTICLE_COLOR
            : ((Accessor_Particle) instance).overflowparticles$invokeGetLightColor(v);
    }
}
