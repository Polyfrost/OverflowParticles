package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Particle.class)
public interface Mixin_AccessParticleData {
    @Accessor int getAge();

    //? if >=26.1 {
    /*@Invoker("getLightCoords") int overflowparticles$invokeGetLight(float partialTicks);
    *///?} else {
    @Invoker("getLightColor") int overflowparticles$invokeGetLight(float partialTicks);
    //?}

    //? if <1.20.1 {
    /*@Accessor("colorAlpha") float getAlpha();
    @Accessor("colorAlpha") void setAlpha(float alpha);

    @Accessor("colorRed") float getRCol();
    @Accessor("colorGreen") float getGCol();
    @Accessor("colorBlue") float getBCol();
    *///?}
}
