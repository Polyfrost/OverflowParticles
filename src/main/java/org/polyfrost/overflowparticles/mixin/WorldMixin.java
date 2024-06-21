package org.polyfrost.overflowparticles.mixin;

import net.minecraft.world.IWorldAccess;
import net.minecraft.world.World;
import org.polyfrost.overflowparticles.OverflowParticles;
import org.polyfrost.overflowparticles.config.ParticleConfig;
import org.polyfrost.overflowparticles.utils.UtilKt;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = World.class, priority = 999)
public class WorldMixin {

    @Shadow protected List<IWorldAccess> worldAccesses;

    @Shadow @Final public boolean isRemote;

    @Inject(method = "spawnParticle(IZDDDDDD[I)V", at = @At("HEAD"), cancellable = true)
    private void multiplier(int particleID, boolean ignoreRange, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int[] arguments, CallbackInfo ci) {
        if (!isRemote) return;
        ParticleConfig config = OverflowParticles.INSTANCE.getConfigs().get(particleID);
        if (config == null || config.getEntry().getMultiplier() == 1 || config.getId() == 28) return;
        UtilKt.spawn(config, worldAccesses, particleID, ignoreRange, xCoord, yCoord, zCoord, xOffset, yOffset, zOffset, arguments);
        ci.cancel();
    }
}
