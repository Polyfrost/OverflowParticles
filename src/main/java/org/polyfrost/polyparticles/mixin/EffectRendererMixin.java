package org.polyfrost.polyparticles.mixin;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.polyfrost.polyparticles.PolyParticles;
import org.polyfrost.polyparticles.config.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.List;

@Mixin(EffectRenderer.class)
public abstract class EffectRendererMixin {

    @Shadow private List<EntityFX>[][] fxLayers;
    @Shadow protected World worldObj;

    @Shadow public abstract void addEffect(EntityFX effect);

    @Unique private int ID;

    @Redirect(method = "renderLitParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/EntityFX;renderParticle(Lnet/minecraft/client/renderer/WorldRenderer;Lnet/minecraft/entity/Entity;FFFFFF)V"))
    private void a(EntityFX instance, WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        handle(instance, worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
    }

    @Redirect(method = "renderParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/EntityFX;renderParticle(Lnet/minecraft/client/renderer/WorldRenderer;Lnet/minecraft/entity/Entity;FFFFFF)V"))
    private void check(EntityFX instance, WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        handle(instance, worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
    }

    @Unique
    private void handle(EntityFX instance, WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        PolyParticles.INSTANCE.setEntityFX(instance);
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

    @Unique
    private void remove(EntityFX entityFX) {
        PolyParticles.INSTANCE.getParticles().remove(entityFX.getEntityId());
    }

    @Unique
    private void put(EntityFX entityFX, int id) {
        PolyParticles.INSTANCE.getParticles().put(entityFX.getEntityId(), id);
    }

}
