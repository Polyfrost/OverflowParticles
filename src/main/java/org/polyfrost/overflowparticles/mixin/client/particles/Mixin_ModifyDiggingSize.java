package org.polyfrost.overflowparticles.mixin.client.particles;

//? if >1.8.9 {
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TerrainParticle.class)
public abstract class Mixin_ModifyDiggingSize extends Particle {
    protected Mixin_ModifyDiggingSize(ClientLevel arg, double d, double e, double f) {
        super(arg, d, e, f);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void overflowparticles$scale(CallbackInfo ci) {
        ParticleConfig config = PerParticleConfigManager.getConfig(this);
        if (config == null) {
            return;
        }

        this.scale(config.getSize());
    }
}
//?} else {
/*import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.entity.particle.BlockParticle;
import net.minecraft.client.particle.Particle;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockParticle.class)
public class Mixin_ModifyDiggingSize {
    @ModifyExpressionValue(method = "render", at = @At(value = "CONSTANT", args = "floatValue=0.1F", ordinal = 0))
    private float overflowparticles$scale(float constant) {
        ParticleConfig config = PerParticleConfigManager.getConfig((Particle) (Object) this);
        return constant * (config == null ? 1 : Math.min(config.getSize(), 1f));
    }
}
*///?}
