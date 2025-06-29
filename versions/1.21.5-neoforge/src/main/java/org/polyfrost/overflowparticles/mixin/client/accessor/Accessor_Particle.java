package org.polyfrost.overflowparticles.mixin.client.accessor;

import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Particle.class)
public interface Accessor_Particle {

    //#if FABRIC
    //$$ @Invoker("getBrightness")
    //#else
    @Invoker("getLightColor")
    //#endif
    int overflowparticles$invokeGetLightColor(float v);

}
