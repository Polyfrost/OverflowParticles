package org.polyfrost.overflowparticles.mixin.client;

//#if MC <= 1.12.2
import net.minecraft.client.particle.EntityFX;
import org.polyfrost.overflowparticles.utils.ParticleIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityFX.class)
public class Mixin_ParticleIdentifier implements ParticleIdentifier {
    @Unique private int overflowparticles$id;

    @Override
    public int overflowparticles$getId() {
        return overflowparticles$id;
    }

    @Override
    public void overflowparticles$setId(int id) {
        this.overflowparticles$id = id;
    }
}
//#endif
