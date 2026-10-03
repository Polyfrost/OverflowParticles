package org.polyfrost.overflowparticles.mixin.client.particles;

//? if =1.8.9 {
/*import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.entity.particle.FootstepParticle;
import net.minecraft.client.particle.Particle;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FootstepParticle.class)
public class Mixin_ModifyFootstepSize {
    @ModifyExpressionValue(method = "render", at = @At(value = "CONSTANT", args = "floatValue=0.125F"))
    private float overflowparticles$scale(float constant) {
        ParticleConfig config = PerParticleConfigManager.getConfig((Particle) (Object) this);
        return constant * (config == null ? 1 : Math.min(config.getSize(), 1f));
    }
}
*///?} else {
// no-op above 1.12.2
import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Particle.class)
public class Mixin_ModifyFootstepSize {
}
//?}
