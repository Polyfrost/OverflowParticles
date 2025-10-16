package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Particle.class)
public interface Mixin_AccessParticleData {
    @Accessor int getAge();
    @Accessor("field_13421") float getAlpha();
    @Accessor("field_13421") void setAlpha(float alpha);
}
