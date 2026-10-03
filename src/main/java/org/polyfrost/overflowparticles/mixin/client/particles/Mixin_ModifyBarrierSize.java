package org.polyfrost.overflowparticles.mixin.client.particles;

//? if =1.8.9 {
/*import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.entity.particle.BarrierParticle;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.particles.VanillaParticles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BarrierParticle.class)
public class Mixin_ModifyBarrierSize {
    @ModifyExpressionValue(method = "render", at = @At(value = "CONSTANT", args = "floatValue=0.5F"))
    private float overflowparticles$scale(float constant) {
        return constant * PerParticleConfigManager.getConfigByType(VanillaParticles.BARRIER).getSize();
    }
}
*///?} else {
// no-op above 1.16.5
import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Particle.class)
public class Mixin_ModifyBarrierSize {
}
//?}
