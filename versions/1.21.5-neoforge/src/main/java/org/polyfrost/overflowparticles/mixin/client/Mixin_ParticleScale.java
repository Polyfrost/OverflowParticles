package org.polyfrost.overflowparticles.mixin.client;

//#if FABRIC
//$$ import net.minecraft.client.particle.BillboardParticle;
//$$ import net.minecraft.client.render.VertexConsumer;
//#else

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.particle.SingleQuadParticle;
//#endif
import org.joml.Quaternionf;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//#if FABRIC
//$$ @Mixin(BillboardParticle.class)
//#else
@Mixin(SingleQuadParticle.class)
//#endif
public abstract class Mixin_ParticleScale {

    //#if FABRIC
    //$$@Shadow
    //$$protected float scale;
    //#else
    @Shadow
    protected float quadSize;
    //#endif

    @Unique
    private float overflowparticles$scale;

    //#if FABRIC
    //$$ @Inject(method = "render(Lnet/minecraft/client/render/VertexConsumer;Lorg/joml/Quaternionf;FFFF)V", at = @At(value = "HEAD"))
    //#else
    @Inject(method = "renderRotatedQuad(Lcom/mojang/blaze3d/vertex/VertexConsumer;Lorg/joml/Quaternionf;FFFF)V", at = @At(value = "HEAD"))
    //#endif
    private void overflowparticles$overrideScale(VertexConsumer arg, Quaternionf quaternionf, float g, float h, float j, float k, CallbackInfo ci) {

        //#if FABRIC
        //$$ this.overflowparticles$scale = this.scale;
        //#else
        this.overflowparticles$scale = this.quadSize;
        //#endif
        ParticleConfig config = PerParticleConfigManager.getConfig((SingleQuadParticle) (Object) this);
        if (config == null) {
            return;
        }

        //#if FABRIC
        //$$ this.scale *= config.getSize();
        //#else
        this.quadSize *= config.getSize();
        //#endif
    }

    //#if FABRIC
    //$$ @Inject(method = "render(Lnet/minecraft/client/render/VertexConsumer;Lorg/joml/Quaternionf;FFFF)V", at = @At(value = "RETURN"))
    //#else
    @Inject(method = "renderRotatedQuad(Lcom/mojang/blaze3d/vertex/VertexConsumer;Lorg/joml/Quaternionf;FFFF)V", at = @At(value = "RETURN"))
    //#endif
    private void overflowparticles$reset(VertexConsumer arg, Quaternionf quaternionf, float g, float h, float j, float k, CallbackInfo ci) {
        //#if FABRIC
        //$$ this.scale = this.overflowparticles$scale;
        //#else
        this.quadSize = this.overflowparticles$scale;
        //#endif
    }

}
