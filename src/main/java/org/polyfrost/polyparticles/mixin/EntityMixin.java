package org.polyfrost.polyparticles.mixin;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import org.polyfrost.polyparticles.PolyParticles;
import org.polyfrost.polyparticles.config.BlockParticleEntry;
import org.polyfrost.polyparticles.config.MainConfig;
import org.polyfrost.polyparticles.config.ModConfig;
import org.polyfrost.polyparticles.config.ParticleConfig;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@SuppressWarnings({"ConstantConditions"})
@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    public abstract void setEntityBoundingBox(AxisAlignedBB bb);

    @Shadow
    public abstract AxisAlignedBB getEntityBoundingBox();

    @Shadow
    protected abstract void resetPositionToBB();

    @Inject(method = "getBrightnessForRender", at = @At("HEAD"), cancellable = true)
    private void staticColor(float partialTicks, CallbackInfoReturnable<Integer> cir) {
        if (MainConfig.INSTANCE.getSettings().getStaticParticleColor() && ((Entity) (Object) this) instanceof EntityFX) {
            cir.setReturnValue(15728880);
        }
    }

    @Inject(method = "moveEntity", at = @At("HEAD"), cancellable = true)
    private void enableNoClip(double x, double y, double z, CallbackInfo ci) {
        if (MainConfig.INSTANCE.getSettings().getParticleNoClip() && ((Entity) (Object) this) instanceof EntityFX) {
            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(x, y, z));
            this.resetPositionToBB();
            ci.cancel();
        }
    }

    @Inject(method = "spawnRunningParticles", at = @At("HEAD"), cancellable = true)
    private void runningParticle(CallbackInfo ci) {
        ParticleConfig config = PolyParticles.INSTANCE.getConfigs().get(37);
        if (!config.enabled) ci.cancel();
        BlockParticleEntry entry = ModConfig.INSTANCE.getBlockSetting();
        if (entry.getHideRunning()) {
            if (entry.getHideMode()) {
                ci.cancel();
            } else if (!((Entity) (Object) this).isInvisible()) {
                ci.cancel();
            }
        }
    }
}
