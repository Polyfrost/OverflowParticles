package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Particle.class)
public interface Mixin_AccessParticleData {
    @Accessor int getAge();

    //? if >=26.1 {
    @Invoker("getLightCoords") int overflowparticles$invokeGetLight(float partialTicks);
    //?} elif >1.8.9 {
    /*@Invoker("getLightColor") int overflowparticles$invokeGetLight(float partialTicks);
    *///?} else {
    /*@Accessor int getLifetime();
    *///?}
}
