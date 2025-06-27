package org.polyfrost.overflowparticles.mixin;

//import net.minecraft.client.particle.EffectRenderer;
//import net.minecraft.client.renderer.Tessellator;
//import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
//import net.minecraft.entity.Entity;
//import org.polyfrost.overflowparticles.client.OverflowParticlesClient;
//import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
//import org.polyfrost.overflowparticles.client.config.ParticleConfig;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.*;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//@Mixin(EffectRenderer.class)
//public abstract class EffectRendererMixin {
//
//    @Inject(method = "renderParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;begin(ILnet/minecraft/client/renderer/vertex/VertexFormat;)V", shift = At.Shift.AFTER))
//    private void begin(Entity entityIn, float partialTicks, CallbackInfo ci) {
//        Tessellator.getInstance().draw();
//    }
//
//    @Inject(method = "renderParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/Tessellator;draw()V"))
//    private void no(Entity entityIn, float partialTicks, CallbackInfo ci) {
//        Tessellator.getInstance().getWorldRenderer().begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
//    }
//
//    @Inject(method = "renderParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/EntityFX;renderParticle(Lnet/minecraft/client/renderer/WorldRenderer;Lnet/minecraft/entity/Entity;FFFFFF)V"))
//    private void start(Entity entityIn, float partialTicks, CallbackInfo ci) {
//        Tessellator.getInstance().getWorldRenderer().begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
//        OverflowParticlesClient.setRendering(true);
//    }
//
//    @Inject(method = "renderParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/EntityFX;renderParticle(Lnet/minecraft/client/renderer/WorldRenderer;Lnet/minecraft/entity/Entity;FFFFFF)V", shift = At.Shift.AFTER))
//    private void end(Entity entityIn, float partialTicks, CallbackInfo ci) {
//        OverflowParticlesClient.setRendering(false);
//        ParticleConfig config = PerParticleConfigManager.getConfig(OverflowParticlesClient.getRenderingEntity());
//        if (config != null && !config.getEnabled() && config.getId() != 37) {
//            Tessellator.getInstance().getWorldRenderer().reset();
//        }
//
//        Tessellator.getInstance().draw();
//    }
//
//}
