package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.particle.EntityFX;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityFX.class)
public interface Mixin_AccessParticleData {
    @Accessor("particleAge") int getAge();
    @Accessor("particleAlpha") float getAlpha();
    @Accessor("particleAlpha") void setAlpha(float alpha);
}
