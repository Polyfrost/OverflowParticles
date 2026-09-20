package org.polyfrost.overflowparticles.mixin.client;

//? if <=1.12.2 {
/*import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.entity.particle.BlockParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.particle.ParticleType;
import org.polyfrost.overflowparticles.utils.ParticleIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleEngine.class)
public class Mixin_FixBlockBreakingId {
    @Inject(
            method = "add(Lnet/minecraft/client/entity/particle/Particle;)V",
            at = @At("HEAD")
    )
    private void overflowparticles$checkDiggingEffects(Particle effect, CallbackInfo ci) {
        if (effect instanceof BlockParticle) {
            ParticleIdentifier.set(effect, ParticleType.BLOCK_CRACK);
        }
    }
}
*///?} else {
import net.minecraft.client.particle.ParticleEngine;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ParticleEngine.class)
public class Mixin_FixBlockBreakingId {
}
//?}
