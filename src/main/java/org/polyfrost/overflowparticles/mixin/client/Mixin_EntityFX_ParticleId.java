package org.polyfrost.overflowparticles.mixin.client;

//#if MC <= 1.12.2

import net.minecraft.client.particle.EntityFX;
import org.polyfrost.overflowparticles.hook.ParticleId;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityFX.class)
public class Mixin_EntityFX_ParticleId implements ParticleId {

    @Unique
    private int overflowparticles$id;

    @Override
    public int overflowparticles$getParticleId() {
        return overflowparticles$id;
    }

    @Override
    public void overflowparticles$setParticleId(int id) {
        this.overflowparticles$id = id;
    }

}
//#endif
