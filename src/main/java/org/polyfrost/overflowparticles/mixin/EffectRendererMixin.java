package org.polyfrost.overflowparticles.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import org.polyfrost.overflowparticles.OverflowParticles;
import org.polyfrost.overflowparticles.config.ConfigManager;
import org.polyfrost.overflowparticles.config.ParticleConfig;
import org.polyfrost.overflowparticles.config.Settings;
import org.polyfrost.overflowparticles.utils.UtilKt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EffectRenderer.class)
public abstract class EffectRendererMixin {

    @Unique
    private int overflowParticles$ID;

    @WrapOperation(method = "renderLitParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/EntityFX;renderParticle(Lnet/minecraft/client/renderer/WorldRenderer;Lnet/minecraft/entity/Entity;FFFFFF)V"))
    private void a(EntityFX instance, WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ, Operation<Void> original) {
        OverflowParticles.INSTANCE.setRenderingEntity(instance);
        ParticleConfig config = ConfigManager.INSTANCE.getConfig(instance);
        if (config != null && !config.getEnabled() && config.getId() != 37) return;
        OverflowParticles.INSTANCE.setRendering(true);
        original.call(instance, worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
        OverflowParticles.INSTANCE.setRendering(false);
    }

    @SuppressWarnings("MixinAnnotationTarget")
    @ModifyVariable(method = "renderParticles", at = @At(value = "STORE"), ordinal = 0)
    private EntityFX capture(EntityFX entityFX) {
        OverflowParticles.INSTANCE.setRenderingEntity(entityFX);
        return entityFX;
    }

    @Inject(method = "renderParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;begin(ILnet/minecraft/client/renderer/vertex/VertexFormat;)V", shift = At.Shift.AFTER))
    private void begin(Entity entityIn, float partialTicks, CallbackInfo ci) {
        Tessellator.getInstance().draw();
    }

    @Inject(method = "renderParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/Tessellator;draw()V"))
    private void no(Entity entityIn, float partialTicks, CallbackInfo ci) {
        Tessellator.getInstance().getWorldRenderer().begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
    }

    @Inject(method = "renderParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/EntityFX;renderParticle(Lnet/minecraft/client/renderer/WorldRenderer;Lnet/minecraft/entity/Entity;FFFFFF)V"))
    private void start(Entity entityIn, float partialTicks, CallbackInfo ci) {
        Tessellator.getInstance().getWorldRenderer().begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
        OverflowParticles.INSTANCE.setRendering(true);
    }

    @Inject(method = "renderParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/EntityFX;renderParticle(Lnet/minecraft/client/renderer/WorldRenderer;Lnet/minecraft/entity/Entity;FFFFFF)V", shift = At.Shift.AFTER))
    private void end(Entity entityIn, float partialTicks, CallbackInfo ci) {
        OverflowParticles.INSTANCE.setRendering(false);
        ParticleConfig config = ConfigManager.INSTANCE.getConfig(OverflowParticles.INSTANCE.getRenderingEntity());
        if (config != null && !config.getEnabled() && config.getId() != 37) {
            Tessellator.getInstance().getWorldRenderer().reset();
        }
        Tessellator.getInstance().draw();
    }

    @Inject(method = "spawnEffectParticle", at = @At("HEAD"))
    private void spawn(int particleId, double xCoord, double yCoord, double zCoord, double xSpeed, double p_178927_10_, double p_178927_12_, int[] p_178927_14_, CallbackInfoReturnable<EntityFX> cir) {
        overflowParticles$ID = particleId;
    }

    @ModifyArg(method = "spawnEffectParticle", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/EffectRenderer;addEffect(Lnet/minecraft/client/particle/EntityFX;)V", ordinal = 0))
    private EntityFX spawn(EntityFX effect) {
        UtilKt.setParticleEntityID(effect, overflowParticles$ID);
        return effect;
    }

    @Inject(method = "addEffect", at = @At("HEAD"))
    private void check(EntityFX effect, CallbackInfo ci) {
        if (effect instanceof EntityDiggingFX) {
            UtilKt.setParticleEntityID(effect, 37);
        }
    }

    @ModifyConstant(method = "addEffect", constant = @Constant(intValue = 4000))
    private int changeMaxParticleLimit(int original) {
        return Settings.INSTANCE.getMaxParticleLimit();
    }

    @Inject(
            method = {
                    "addBlockHitEffects(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)V",
                    "addBlockDestroyEffects"
            },
            at = @At("HEAD"), cancellable = true
    )
    private void removeBlockBreakingParticles(CallbackInfo ci) {
        if (!ConfigManager.INSTANCE.getConfigs().get(37).getEnabled() || ConfigManager.INSTANCE.getBlockSetting().getHideDigging()) {
            ci.cancel();
        }
    }

    @Inject(
            method = "addBlockHitEffects(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/MovingObjectPosition;)V",
            at = @At("HEAD"), cancellable = true, remap = false
    )
    private void removeBlockBreakingParticles_Forge(CallbackInfo ci) {
        if (!ConfigManager.INSTANCE.getConfigs().get(37).getEnabled() || ConfigManager.INSTANCE.getBlockSetting().getHideDigging()) {
            ci.cancel();
        }
    }
}
