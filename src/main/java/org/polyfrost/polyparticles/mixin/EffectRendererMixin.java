package org.polyfrost.polyparticles.mixin;

import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import org.polyfrost.polyparticles.PolyParticles;
import org.polyfrost.polyparticles.config.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.List;

@Mixin(EffectRenderer.class)
public abstract class EffectRendererMixin {

    @Shadow
    private List<EntityFX>[][] fxLayers;

    @Unique
    private int ID;

    @Redirect(method = "renderLitParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/EntityFX;renderParticle(Lnet/minecraft/client/renderer/WorldRenderer;Lnet/minecraft/entity/Entity;FFFFFF)V"))
    private void a(EntityFX instance, WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        handle(instance, worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
    }

    @ModifyVariable(method = "renderParticles", at = @At(value = "STORE"), ordinal = 0)
    private EntityFX capture(EntityFX entityFX) {
        PolyParticles.INSTANCE.setRenderingEntity(entityFX);
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
        PolyParticles.INSTANCE.setRendering(true);
    }

    @Inject(method = "renderParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/EntityFX;renderParticle(Lnet/minecraft/client/renderer/WorldRenderer;Lnet/minecraft/entity/Entity;FFFFFF)V", shift = At.Shift.AFTER))
    private void end(Entity entityIn, float partialTicks, CallbackInfo ci) {
        PolyParticles.INSTANCE.setRendering(false);
        ParticleConfig config = ModConfig.INSTANCE.getConfig(PolyParticles.INSTANCE.getRenderingEntity());
        if (config != null && !config.enabled) {
            Tessellator.getInstance().getWorldRenderer().reset();
        }
        Tessellator.getInstance().draw();
    }

    @Unique
    private void handle(EntityFX instance, WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        PolyParticles.INSTANCE.setRenderingEntity(instance);
        ParticleConfig config = ModConfig.INSTANCE.getConfig(instance);
        if (config != null && !config.enabled) return;
        PolyParticles.INSTANCE.setRendering(true);
        instance.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
        PolyParticles.INSTANCE.setRendering(false);
    }

    @Inject(method = "spawnEffectParticle", at = @At("HEAD"))
    private void spawn(int particleId, double xCoord, double yCoord, double zCoord, double xSpeed, double p_178927_10_, double p_178927_12_, int[] p_178927_14_, CallbackInfoReturnable<EntityFX> cir) {
        ID = particleId;
    }

    @ModifyArgs(method = "spawnEffectParticle", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/EffectRenderer;addEffect(Lnet/minecraft/client/particle/EntityFX;)V"))
    private void spawn(Args args) {
        put(args.get(0), ID);
    }

    @ModifyArgs(method = "updateEffectAlphaLayer", at = @At(value = "INVOKE", target = "Ljava/util/List;removeAll(Ljava/util/Collection;)Z"))
    private void update(Args args) {
        List<EntityFX> list = args.get(0);
        for (EntityFX entityFX : list) {
            remove(entityFX);
        }
    }

    @Inject(method = "addEffect", at = @At("HEAD"))
    private void check(EntityFX effect, CallbackInfo ci) {
        if (effect instanceof EntityDiggingFX) {
            put(effect, 37);
        }
    }

    @Inject(method = "addEffect", at = @At(value = "INVOKE", target = "Ljava/util/List;remove(I)Ljava/lang/Object;"))
    private void limit(EntityFX effect, CallbackInfo ci) {
        int i = effect.getFXLayer();
        int j = effect.getAlpha() != 1.0F ? 0 : 1;
        remove(this.fxLayers[i][j].get(0));
    }

    @ModifyConstant(method = "addEffect", constant = @Constant(intValue = 4000))
    private int patcher$changeMaxParticleLimit(int original) {
        return MainConfig.INSTANCE.getSettings().getMaxParticleLimit();
    }

    @Unique
    private void remove(EntityFX entityFX) {
        PolyParticles.INSTANCE.getEntitiesCache().remove(entityFX.getEntityId());
    }

    @Unique
    private void put(EntityFX entityFX, int id) {
        PolyParticles.INSTANCE.getEntitiesCache().put(entityFX.getEntityId(), id);
    }

}
