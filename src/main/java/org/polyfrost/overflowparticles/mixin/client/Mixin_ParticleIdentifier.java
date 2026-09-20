package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.particle.Particle;
//? if >1.8.9 {
import net.minecraft.core.particles.ParticleType;
//?} else
//import net.minecraft.entity.particle.ParticleType;
import org.polyfrost.overflowparticles.utils.ParticleIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Particle.class)
public class Mixin_ParticleIdentifier implements ParticleIdentifier {
    //? if >1.8.9 {
    @Unique private ParticleType<?> overflowparticles$id;

    @Override
    public ParticleType<?> overflowparticles$getId() {
        return overflowparticles$id;
    }

    @Override
    public void overflowparticles$setId(ParticleType<?> id) {
        this.overflowparticles$id = id;
    }
    //?} else {
    /*@Unique private ParticleType overflowparticles$id;

    @Override
    public ParticleType overflowparticles$getId() {
        return overflowparticles$id;
    }

    @Override
    public void overflowparticles$setId(ParticleType id) {
        this.overflowparticles$id = id;
    }
    *///?}

}
