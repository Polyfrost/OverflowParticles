package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(
        //? if >=1.21.10 {
        SingleQuadParticle.class
        //?} else {
        /*Particle.class
        *///?}
)
public interface Mixin_AccessBillboardParticleData {
    @Accessor float getAlpha();
    @Accessor("alpha") void overflowparticles$setAlpha(float alpha);

    //? if forge_like {
    /*@Accessor float getRCol();
    @Accessor float getGCol();
    @Accessor float getBCol();
    *///?} else {
    @Accessor("rCol") float getRCol();
    @Accessor("gCol") float getGCol();
    @Accessor("bCol") float getBCol();
    //?}
}
