package org.polyfrost.overflowparticles.mixin.client;

//? if <=1.12.2 {
/*import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleDigging;
import net.minecraft.client.particle.Particle;
import org.polyfrost.overflowparticles.utils.ParticleIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleEngine.class)
public class Mixin_FixBlockBreakingId {
    @Inject(
            //? if >=1.12.2 {
            method = "addEffect",
            //?} else {
            /^method = "add(Lnet/minecraft/client/particle/Particle;)V",
            ^///?}
            at = @At("HEAD")
    )
    private void overflowparticles$checkDiggingEffects(Particle effect, CallbackInfo ci) {
        if (effect instanceof ParticleDigging) {
            ParticleIdentifier.set(effect, 37);
        }
    }
}
*///?}
