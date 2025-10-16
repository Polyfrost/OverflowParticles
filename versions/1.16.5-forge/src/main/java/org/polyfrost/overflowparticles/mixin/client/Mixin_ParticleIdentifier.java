package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.ParticleType;
import org.polyfrost.overflowparticles.utils.ParticleIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Particle.class)
public class Mixin_ParticleIdentifier implements ParticleIdentifier {
    @Unique private ParticleType<?> overflowparticles$id;

    @Override
    public ParticleType<?> overflowparticles$getId() {
        return overflowparticles$id;
    }

    @Override
    public void overflowparticles$setId(ParticleType<?> id) {
        this.overflowparticles$id = id;
    }

}
