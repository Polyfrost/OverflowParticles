package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.Box;
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//#if MC >= 1.16.5
//$$ import net.minecraft.client.multiplayer.ClientLevel;
//$$ import org.spongepowered.asm.mixin.Final;
//#else
import net.minecraft.world.World;
//#endif

//#if MC >= 1.12.2
@Mixin(Particle.class)
//#else
//$$ @Mixin(Entity.class)
//#endif
public abstract class Mixin_ParticleNoClip {
    @Shadow
    //#if MC >= 1.16.5
    //$$ @Final
    //$$ protected ClientLevel level;
    //#else
    protected World field_13424;
    //#endif

    @Inject(method = "method_12242", at = @At("HEAD"), cancellable = true)
    private void enableNoClip(double x, double y, double z, CallbackInfo ci) {
        if (this.field_13424 != null && !this.field_13424.isClient) {
            return;
        }

        //#if MC <= 1.8.9
        //$$ if (!(((Entity) (Object) this) instanceof Particle)) {
        //$$     return;
        //$$ }
        //#endif

        if (OverflowParticlesConfig.INSTANCE.getParticleNoClip()) {
            this.method_12246(this.method_12254().offset(x, y, z));
            this.method_12252();
            ci.cancel();
        }
    }

    @Shadow public abstract Box method_12254();
    @Shadow public abstract void method_12246(Box bb);
    @Shadow protected abstract void method_12252();
}
