package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.Particle;
//? if >1.8.9 {
import net.minecraft.client.particle.BreakingItemParticle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
//?} else {
/*import net.minecraft.entity.particle.ParticleType;
*///?}
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.particles.VanillaParticles;
import org.polyfrost.overflowparticles.utils.ParticleIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParticleEngine.class)
public class Mixin_SetParticleIds {
    //? if >1.8.9 {
    @Unique private ParticleType<?> overflowparticles$currentType;

    @Inject(
            method = "createParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)Lnet/minecraft/client/particle/Particle;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void overflowparticles$captureTypeA(ParticleOptions options, double d, double e, double f, double g, double h, double i, CallbackInfoReturnable<Particle> cir) {
        overflowparticles$currentType = options.getType();
        if (!overflowparticles$shouldSpawn(overflowparticles$currentType)) {
            cir.setReturnValue(null);
        }
    }

    @ModifyArg(
            method = "createParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)Lnet/minecraft/client/particle/Particle;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/particle/ParticleEngine;add(Lnet/minecraft/client/particle/Particle;)V"
            )
    )
    private Particle overflowparticles$setType(Particle effect) {
        ParticleIdentifier.set(effect, overflowparticles$currentType);
        return effect;
    }

    @Inject(method = "add(Lnet/minecraft/client/particle/Particle;)V", at = @At("HEAD"), cancellable = true)
    private void overflowparticles$handleRawParticle(Particle effect, CallbackInfo ci) {
        if (effect instanceof BreakingItemParticle) {
            ParticleIdentifier.set(effect, VanillaParticles.ITEM_EAT_BREAK.getId());
        }

        if (!overflowparticles$shouldSpawn(ParticleIdentifier.get(effect))) {
            ci.cancel();
        }
    }
    //?} else {
    /*@Unique private ParticleType overflowparticles$currentType;

    @Inject(
            method = "addParticle(IDDDDDD[I)Lnet/minecraft/client/entity/particle/Particle;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void overflowparticles$captureTypeA(int type, double d, double e, double f, double g, double h, double i, int[] parameters, CallbackInfoReturnable<Particle> cir) {
        overflowparticles$currentType = ParticleType.byId(type);
        if (!overflowparticles$shouldSpawn(overflowparticles$currentType)) {
            cir.setReturnValue(null);
        }
    }

    @ModifyArg(
            method = "addParticle(IDDDDDD[I)Lnet/minecraft/client/entity/particle/Particle;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/ParticleEngine;add(Lnet/minecraft/client/entity/particle/Particle;)V"
            )
    )
    private Particle overflowparticles$setType(Particle effect) {
        ParticleIdentifier.set(effect, overflowparticles$currentType);
        return effect;
    }

    @Inject(method = "add(Lnet/minecraft/client/entity/particle/Particle;)V", at = @At("HEAD"), cancellable = true)
    private void overflowparticles$handleRawParticle(Particle effect, CallbackInfo ci) {
        if (!overflowparticles$shouldSpawn(ParticleIdentifier.get(effect))) {
            ci.cancel();
        }
    }
    *///?}

    @Unique
    //? if >1.8.9 {
    private boolean overflowparticles$shouldSpawn(ParticleType<?> type) {
    //?} else
    //private boolean overflowparticles$shouldSpawn(ParticleType type) {
        if (type == null) {
            return true;
        }

        ParticleConfig config = PerParticleConfigManager.getConfigByType(type);
        return config == null || config.getEnabled();
    }
}
