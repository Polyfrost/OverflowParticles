package org.polyfrost.overflowparticles.mixin.client;

//? if >1.8.9
import net.minecraft.client.Camera;
import net.minecraft.client.particle.Particle;
//? if >1.8.9 {
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
//?}
import org.polyfrost.overflowparticles.client.OverflowParticlesClient;
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.particles.VanillaParticles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
//? if >=1.21.10 {
import net.minecraft.client.particle.SingleQuadParticle;
//? if >=26.1 {
import net.minecraft.client.renderer.state.level.QuadParticleRenderState;
//?} else
//import net.minecraft.client.renderer.state.QuadParticleRenderState;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//?} else {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
//? if >1.8.9 {
import com.mojang.blaze3d.vertex.VertexConsumer;
//?} else {
/^import net.minecraft.client.render.vertex.BufferBuilder;
import net.minecraft.world.entity.Entity;
^///?}
import net.minecraft.client.particle.ParticleEngine;
*///?}

//? if >=1.21.10 {
@Mixin(SingleQuadParticle.class)
//?} else {
/*@Mixin(ParticleEngine.class)
*///?}
public class Mixin_TrackRenderState {
    //? if >=1.21.10 {
    @Inject(method = "extract", at = @At("HEAD"), cancellable = true)
    private void overflowparticles$updateRenderingEntityState(QuadParticleRenderState state, Camera camera, float partialTick, CallbackInfo ci) {
        Particle instance = (Particle) (Object) this;
        OverflowParticlesClient.setRenderingEntity(instance);
        //? if >=1.21.11 {
        Vec3 cam = camera.position();
        //?} else
        //Vec3 cam = camera.getPosition();
        if (overflowparticles$insideShield(instance, cam.x, cam.y, cam.z, partialTick)) {
            ci.cancel();
            return;
        }
        ParticleConfig config = PerParticleConfigManager.getConfig(instance);
        if (config != null && !config.getEnabled() && config.getParticleType() != VanillaParticles.BLOCKS.getId()) {
            ci.cancel();
        }
    }
    //?} elif >1.8.9 {
    /*@WrapOperation(
            //? if <1.21.4 {
            method = "render(Lnet/minecraft/client/renderer/LightTexture;Lnet/minecraft/client/Camera;F)V",
            //?} else {
            /^method = "renderParticleType",
            ^///?}
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/particle/Particle;render(Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/client/Camera;F)V"
            )
    )
    //? if <1.21.4 {
    private void overflowparticles$updateRenderingEntityState(
    //?} else {
    /^private static void overflowparticles$updateRenderingEntityState(
    ^///?}
            Particle instance,
            VertexConsumer vertexConsumer,
            Camera camera,
            float tickDelta,
            Operation<Void> original
    ) {
        OverflowParticlesClient.setRenderingEntity(instance);
        Vec3 cam = camera.getPosition();
        if (overflowparticles$insideShield(instance, cam.x, cam.y, cam.z, tickDelta)) {
            return;
        }
        ParticleConfig config = PerParticleConfigManager.getConfig(instance);
        if (config != null && !config.getEnabled() && config.getParticleType() != VanillaParticles.BLOCKS.getId()) {
            return;
        }

        if (config == null || config.getParticleType() == VanillaParticles.BLOCKS.getId()) {
            original.call(instance, vertexConsumer, camera, tickDelta);
            return;
        }

        OverflowParticlesClient.setRendering(true);
        OverflowParticlesClient.setRenderingEntity(instance);
        original.call(instance, vertexConsumer, camera, tickDelta);
        OverflowParticlesClient.setRendering(false);
    }
    *///?} else {
    /*@WrapOperation(
            method = {"render(Lnet/minecraft/entity/Entity;F)V", "renderLit(Lnet/minecraft/entity/Entity;F)V"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/entity/particle/Particle;render(Lnet/minecraft/client/render/vertex/BufferBuilder;Lnet/minecraft/entity/Entity;FFFFFF)V"
            )
    )
    private void overflowparticles$updateRenderingEntityState(
            Particle instance,
            BufferBuilder bufferBuilder,
            Entity camera,
            float tickDelta,
            float dx,
            float dy,
            float dz,
            float forwards,
            float sideways,
            Operation<Void> original
    ) {
        OverflowParticlesClient.setRenderingEntity(instance);
        double camX = overflowparticles$lerp(tickDelta, camera.lastX, camera.x);
        double camY = overflowparticles$lerp(tickDelta, camera.lastY, camera.y) + camera.getEyeHeight();
        double camZ = overflowparticles$lerp(tickDelta, camera.lastZ, camera.z);
        if (overflowparticles$insideShield(instance, camX, camY, camZ, tickDelta)) {
            return;
        }
        ParticleConfig config = PerParticleConfigManager.getConfig(instance);
        if (config != null && !config.getEnabled() && config.getParticleType() != VanillaParticles.BLOCKS.getId()) {
            return;
        }

        if (config == null || config.getParticleType() == VanillaParticles.BLOCKS.getId()) {
            original.call(instance, bufferBuilder, camera, tickDelta, dx, dy, dz, forwards, sideways);
            return;
        }

        OverflowParticlesClient.setRendering(true);
        original.call(instance, bufferBuilder, camera, tickDelta, dx, dy, dz, forwards, sideways);
        OverflowParticlesClient.setRendering(false);
    }
    *///?}

    @Unique
    private static boolean overflowparticles$insideShield(Particle instance, double camX, double camY, double camZ, float partialTick) {
        if (!OverflowParticlesConfig.getModEnabled() || !OverflowParticlesConfig.getParticleShield()) {
            return false;
        }

        //? if >1.8.9 {
        Mixin_AccessParticleData data = (Mixin_AccessParticleData) instance;
        double dx = Mth.lerp(partialTick, data.overflowparticles$getXo(), data.overflowparticles$getX()) - camX;
        double dy = Mth.lerp(partialTick, data.overflowparticles$getYo(), data.overflowparticles$getY()) - camY;
        double dz = Mth.lerp(partialTick, data.overflowparticles$getZo(), data.overflowparticles$getZ()) - camZ;
        //?} else {
        /*double dx = overflowparticles$lerp(partialTick, instance.lastX, instance.x) - camX;
        double dy = overflowparticles$lerp(partialTick, instance.lastY, instance.y) - camY;
        double dz = overflowparticles$lerp(partialTick, instance.lastZ, instance.z) - camZ;
        *///?}
        double size = OverflowParticlesConfig.getShieldSize();
        return dx * dx + dy * dy + dz * dz < size * size;
    }

    //? if <=1.8.9 {
    /*@Unique
    private static double overflowparticles$lerp(float delta, double start, double end) {
        return start + (end - start) * delta;
    }
    *///?}
}
