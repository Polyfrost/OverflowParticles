package org.polyfrost.overflowparticles.mixin;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import org.polyfrost.overflowparticles.client.config.BlockParticleEntry;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

//#if MC >= 1.12.2
//$$ import net.minecraft.entity.MovementType;
//#endif

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

    @Inject(method = "moveEntity", at = @At("HEAD"), cancellable = true)
    private void enableNoClip(
            //#if MC >= 1.12.2
            //$$ MovementType type,
            //#endif
            double x,
            double y,
            double z,
            CallbackInfo ci
    ) {
        if (this.worldObj != null && !this.worldObj.isRemote) {
            return;
        }

        if (OverflowParticlesConfig.INSTANCE.getParticleNoClip() && ((Entity) (Object) this) instanceof EntityFX) {
            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(x, y, z));
            this.resetPositionToBB();
            ci.cancel();
        }
    }

    @Inject(method = "createRunningParticles", at = @At("HEAD"), cancellable = true)
    private void runningParticle(CallbackInfo ci) {
        if (this.worldObj != null && !this.worldObj.isRemote) {
            return;
        }

        ParticleConfig config = PerParticleConfigManager.getConfigs().get(37);
        if (!config.getEnabled()) {
            ci.cancel();
        }

        BlockParticleEntry entry = PerParticleConfigManager.getBlockSetting();
        if (entry.getHideRunning()) {
            if (entry.getHideMode() == 1) {
                ci.cancel();
            } else if (!((Entity) (Object) this).isInvisible()) {
                ci.cancel();
            }
        }
    }

}
