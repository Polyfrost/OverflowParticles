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
import org.polyfrost.overflowparticles.client.OverflowParticlesClient;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig;
import org.polyfrost.overflowparticles.hook.ParticleId;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EffectRenderer.class)
public abstract class EffectRendererMixin {

    @Unique
    private int overflowParticles$id;

    @WrapOperation(method = "renderLitParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/EntityFX;renderParticle(Lnet/minecraft/client/renderer/WorldRenderer;Lnet/minecraft/entity/Entity;FFFFFF)V"))
    private void a(EntityFX instance, WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ, Operation<Void> original) {
        OverflowParticlesClient.setRenderingEntity(instance);
        ParticleConfig config = PerParticleConfigManager.getConfig(instance);
        if (config != null && !config.getEnabled() && config.getId() != 37) {
            return;
        }

        OverflowParticlesClient.setRendering(true);
        original.call(instance, worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
        OverflowParticlesClient.setRendering(false);
    }

    @SuppressWarnings("MixinAnnotationTarget")
    @ModifyVariable(method = "renderParticles", at = @At(value = "STORE"), ordinal = 0)
    private EntityFX capture(EntityFX entityFX) {
        OverflowParticlesClient.setRenderingEntity(entityFX);
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
        OverflowParticlesClient.setRendering(true);
    }

    @Inject(method = "renderParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/EntityFX;renderParticle(Lnet/minecraft/client/renderer/WorldRenderer;Lnet/minecraft/entity/Entity;FFFFFF)V", shift = At.Shift.AFTER))
    private void end(Entity entityIn, float partialTicks, CallbackInfo ci) {
        OverflowParticlesClient.setRendering(false);
        ParticleConfig config = PerParticleConfigManager.getConfig(OverflowParticlesClient.getRenderingEntity());
        if (config != null && !config.getEnabled() && config.getId() != 37) {
            Tessellator.getInstance().getWorldRenderer().reset();
        }

        Tessellator.getInstance().draw();
    }

    @Inject(method = "spawnEffectParticle", at = @At("HEAD"))
    private void spawn(int particleId, double xCoord, double yCoord, double zCoord, double xSpeed, double p_178927_10_, double p_178927_12_, int[] p_178927_14_, CallbackInfoReturnable<EntityFX> cir) {
        overflowParticles$id = particleId;
    }

    @ModifyArg(
            method = "spawnEffectParticle",
            at = @At(
                    value = "INVOKE",
                    //#if MC >= 1.12.2
                    //$$ target = "Lnet/minecraft/client/particle/ParticleManager;method_12256(Lnet/minecraft/client/particle/Particle;)V",
                    //#else
                    target = "Lnet/minecraft/client/particle/EffectRenderer;addEffect(Lnet/minecraft/client/particle/EntityFX;)V",
                    //#endif
                    ordinal = 0
            )
    )
    private EntityFX spawn(EntityFX effect) {
        ParticleId.setParticleId(effect, overflowParticles$id);
        return effect;
    }

    @Inject(
            //#if MC >= 1.12.2
            //$$ method = "method_12256",
            //#else
            method = "addEffect",
            //#endif
            at = @At("HEAD")
    )
    private void check(EntityFX effect, CallbackInfo ci) {
        if (effect instanceof EntityDiggingFX) {
            ParticleId.setParticleId(effect, 37);
        }
    }

    //#if MC >= 1.12.2
    //$$ @ModifyConstant(method = "tick", constant = @Constant(intValue = 16384))
    //#else
    @ModifyConstant(method = "addEffect", constant = @Constant(intValue = 4000))
    //#endif
    private int changeMaxParticleLimit(int original) {
        return OverflowParticlesConfig.INSTANCE.getMaxParticleLimit();
    }

    @Inject(
            method = {
                    "addBlockHitEffects(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)V",
                    "addBlockDestroyEffects"
            },
            at = @At("HEAD"), cancellable = true
    )
    private void removeBlockBreakingParticles(CallbackInfo ci) {
        if (!PerParticleConfigManager.getConfigs().get(37).getEnabled() || PerParticleConfigManager.getBlockSetting().getHideDigging()) {
            ci.cancel();
        }
    }

    //#if FORGE
    @Inject(
            method = "addBlockHitEffects(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/MovingObjectPosition;)V",
            at = @At("HEAD"), cancellable = true, remap = false
    )
    private void removeBlockBreakingParticles_Forge(CallbackInfo ci) {
        if (!PerParticleConfigManager.getConfigs().get(37).getEnabled() || PerParticleConfigManager.getBlockSetting().getHideDigging()) {
            ci.cancel();
        }
    }
    //#endif

}
