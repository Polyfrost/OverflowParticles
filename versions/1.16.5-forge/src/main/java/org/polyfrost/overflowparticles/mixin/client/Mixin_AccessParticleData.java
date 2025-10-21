package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Particle.class)
public interface Mixin_AccessParticleData {
    @Accessor int getAge();
    @Accessor float getAlpha();
    @Accessor void setAlpha(float alpha);

    @Accessor float getRCol();
    @Accessor float getGCol();
    @Accessor float getBCol();
}
