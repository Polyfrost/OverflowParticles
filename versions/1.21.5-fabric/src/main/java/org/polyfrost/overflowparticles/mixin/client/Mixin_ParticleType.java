package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.particle.Particle;
import net.minecraft.particle.ParticleType;
import org.jetbrains.annotations.NotNull;
import org.polyfrost.overflowparticles.utils.ParticleIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Particle.class)
public class Mixin_ParticleType implements ParticleIdentifier {
    @Unique private ParticleType<?> overflowparticles$particleType;

    @Override
    public @NotNull ParticleType<?> overflowparticles$getId() {
        return overflowparticles$particleType;
    }

    @Override
    public void overflowparticles$setId(@NotNull ParticleType<?> particleType) {
        this.overflowparticles$particleType = particleType;
    }
}
