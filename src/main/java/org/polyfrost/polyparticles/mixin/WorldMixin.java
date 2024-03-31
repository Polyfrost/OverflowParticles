package org.polyfrost.polyparticles.mixin;

import net.minecraft.world.IWorldAccess;
import net.minecraft.world.World;
import org.polyfrost.polyparticles.PolyParticles;
import org.polyfrost.polyparticles.config.ParticleConfig;
import org.polyfrost.polyparticles.utils.UtilKt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(World.class)
public class WorldMixin {

    @Shadow protected List<IWorldAccess> worldAccesses;

    @Inject(method = "spawnParticle(IZDDDDDD[I)V", at = @At("HEAD"), cancellable = true)
    private void multiplier(int particleID, boolean ignoreRange, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int[] arguments, CallbackInfo ci) {
        ParticleConfig config = PolyParticles.INSTANCE.getConfigs().get(particleID);
        if (config == null || config.getMultiplier() == 1) return;
        UtilKt.spawn(config, worldAccesses, particleID, ignoreRange, xCoord, yCoord, zCoord, xOffset, yOffset, zOffset, arguments);
        ci.cancel();
    }
}
