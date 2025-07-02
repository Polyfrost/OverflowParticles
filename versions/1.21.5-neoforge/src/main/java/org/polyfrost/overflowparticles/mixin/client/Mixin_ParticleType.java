package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.ParticleType;
import org.jetbrains.annotations.NotNull;
import org.polyfrost.overflowparticles.hook.OFParticleType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Particle.class)
public class Mixin_ParticleType implements OFParticleType {

    @Unique private ParticleType<?> overflowparticles$particleType;

    @Override
    public @NotNull ParticleType<?> overflowparticles$getParticleType() {

        return overflowparticles$particleType;
    }

    @Override
    public void overflowparticles$setParticleType(@NotNull ParticleType<?> particleType) {
        this.overflowparticles$particleType = particleType;
    }

}
