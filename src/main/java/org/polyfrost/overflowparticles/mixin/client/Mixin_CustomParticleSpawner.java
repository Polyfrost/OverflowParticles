package org.polyfrost.overflowparticles.mixin.client;

//? if >1.8.9 {
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.level.Level;
//?} else {
/*import net.minecraft.entity.particle.ParticleType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
*///?}
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.utils.ParticleSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >1.8.9 {
@Mixin(value = ClientLevel.class, priority = 999)
public class Mixin_CustomParticleSpawner {
    @Inject(
            //? if >=1.21.4 {
            method = "addParticle(Lnet/minecraft/core/particles/ParticleOptions;ZZDDDDDD)V",
            //?} else {
            /*method = "addParticle(Lnet/minecraft/core/particles/ParticleOptions;ZDDDDDD)V",
            *///?}
            at = @At("HEAD"),
            cancellable = true
    )
    private void overflowparticles$useCustomSpawner(
            ParticleOptions options,
            boolean ignoreRange,
            //? if >=1.21.4 {
            boolean idk,
            //?}
            double xCoord,
            double yCoord,
            double zCoord,
            double xOffset,
            double yOffset,
            double zOffset,
            CallbackInfo ci
    ) {
        overflowparticles$spawn(options, ignoreRange, xCoord, yCoord, zCoord, xOffset, yOffset, zOffset, ci);
    }

    @Inject(method = "addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V", at = @At("HEAD"), cancellable = true)
    private void overflowparticles$useCustomSpawnerNoRange(
            ParticleOptions options,
            double xCoord,
            double yCoord,
            double zCoord,
            double xOffset,
            double yOffset,
            double zOffset,
            CallbackInfo ci
    ) {
        overflowparticles$spawn(options, false, xCoord, yCoord, zCoord, xOffset, yOffset, zOffset, ci);
    }

    @Unique
    private void overflowparticles$spawn(
            ParticleOptions options,
            boolean ignoreRange,
            double xCoord,
            double yCoord,
            double zCoord,
            double xOffset,
            double yOffset,
            double zOffset,
            CallbackInfo ci
    ) {
        if (ParticleSpawner.isMultiplied()) {
            ParticleSpawner.setMultiplied(false);
            return;
        }

        ParticleType<?> type = options.getType();
        ParticleConfig config = PerParticleConfigManager.getConfigByType(type);
        if (config == null) {
            return;
        }

        if (!config.getEnabled()) {
            ci.cancel();
            return;
        }

        if (config.getMultiplier() == 1) {
            return;
        }

        ParticleSpawner.spawn(options, config, (Level) (Object) this, ignoreRange, xCoord, yCoord, zCoord, xOffset, yOffset, zOffset);
        ci.cancel();
    }
}
//?} else {
/*@Mixin(value = World.class, priority = 999)
public class Mixin_CustomParticleSpawner {
    @Shadow @Final public boolean isClient;

    @Inject(method = "addParticle(IZDDDDDD[I)V", at = @At("HEAD"), cancellable = true)
    private void overflowparticles$useCustomSpawner(
            int particleId,
            boolean ignoreRange,
            double xCoord,
            double yCoord,
            double zCoord,
            double xOffset,
            double yOffset,
            double zOffset,
            int[] parameters,
            CallbackInfo ci
    ) {
        if (!this.isClient) {
            return;
        }

        if (ParticleSpawner.isMultiplied()) {
            ParticleSpawner.setMultiplied(false);
            return;
        }

        ParticleType type = ParticleType.byId(particleId);
        ParticleConfig config = PerParticleConfigManager.getConfigByType(type);
        if (config == null) {
            return;
        }

        if (!config.getEnabled()) {
            ci.cancel();
            return;
        }

        if (config.getMultiplier() == 1) {
            return;
        }

        ParticleSpawner.spawn(type, config, (World) (Object) this, ignoreRange, xCoord, yCoord, zCoord, xOffset, yOffset, zOffset, parameters);
        ci.cancel();
    }
}
*///?}
