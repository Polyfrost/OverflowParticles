package org.polyfrost.overflowparticles.mixin.client;

//#if MC <= 1.12.2

import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//#if MC >= 1.12.2
//$$ @Mixin(Particle.class)
//#else
@Mixin(Entity.class)
//#endif
public abstract class Mixin_Entity_ParticleNoClip {

    @Shadow public World worldObj;

    @Shadow
    public abstract void setEntityBoundingBox(AxisAlignedBB bb);

    @Shadow
    public abstract AxisAlignedBB getEntityBoundingBox();

    @Shadow
    protected abstract void resetPositionToBB();

    @Inject(method = "moveEntity", at = @At("HEAD"), cancellable = true)
    private void enableNoClip(double x, double y, double z, CallbackInfo ci) {
        if (this.worldObj != null && !this.worldObj.isRemote) {
            return;
        }

        //#if MC <= 1.8.9
        if (!(((Entity) (Object) this) instanceof EntityFX)) {
            return;
        }
        //#endif

        if (OverflowParticlesConfig.INSTANCE.getParticleNoClip()) {
            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(x, y, z));
            this.resetPositionToBB();
            ci.cancel();
        }
    }

}
//#endif
