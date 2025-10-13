package org.polyfrost.overflowparticles.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import java.util.Queue;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumerProvider;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.polyui.color.PolyColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public class Mixin_ParticleColor {
    @Inject(
            method = "renderParticles(Lnet/minecraft/client/render/Camera;FLnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/particle/ParticleTextureSheet;Ljava/util/Queue;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/particle/Particle;render(Lnet/minecraft/client/render/VertexConsumer;Lnet/minecraft/client/render/Camera;F)V"
            )
    )
    private static void overflowparticles$setParticleColor(Camera camera, float f, VertexConsumerProvider.Immediate immediate, ParticleTextureSheet particleTextureSheet, Queue<Particle> queue, CallbackInfo ci, @Local Particle particle) {
        ParticleConfig config = PerParticleConfigManager.getConfig(particle);
        if (config != null && config.getCustomColor()) {
            PolyColor color = config.getColor();

            System.out.println("Setting particle color: " + color);

            float r = color.red() / 255.0F;
            float g = color.green() / 255.0F;
            float b = color.blue() / 255.0F;
            particle.setColor(r, g, b);
        }
    }
}
