package org.polyfrost.overflowparticles.mixin;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import org.polyfrost.overflowparticles.config.BlockParticleEntry;
import org.polyfrost.overflowparticles.config.ConfigManager;
import org.polyfrost.overflowparticles.config.ParticleConfig;
import org.polyfrost.overflowparticles.config.Settings;
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

    @Shadow public World worldObj;

    @Inject(method = "getBrightnessForRender", at = @At("HEAD"), cancellable = true)
    private void staticColor(float partialTicks, CallbackInfoReturnable<Integer> cir) {
        if (Settings.INSTANCE.getStaticParticleColor() && ((Entity) (Object) this) instanceof EntityFX) {
            cir.setReturnValue(15728880);
        }
    }

    @Inject(method = "moveEntity", at = @At("HEAD"), cancellable = true)
    private void enableNoClip(double x, double y, double z, CallbackInfo ci) {
        if (worldObj != null && !worldObj.isRemote) return;
        if (Settings.INSTANCE.getParticleNoClip() && ((Entity) (Object) this) instanceof EntityFX) {
            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(x, y, z));
            this.resetPositionToBB();
            ci.cancel();
        }
    }

    @Inject(method = "createRunningParticles", at = @At("HEAD"), cancellable = true)
    private void runningParticle(CallbackInfo ci) {
        if (worldObj != null && !worldObj.isRemote) return;
        ParticleConfig config = ConfigManager.INSTANCE.getConfigs().get(37);
        if (!config.getEnabled()) ci.cancel();
        BlockParticleEntry entry = ConfigManager.INSTANCE.getBlockSetting();
        if (entry.getHideRunning()) {
            if (entry.getHideMode() == 1) {
                ci.cancel();
            } else if (!((Entity) (Object) this).isInvisible()) {
                ci.cancel();
            }
        }
    }
}
