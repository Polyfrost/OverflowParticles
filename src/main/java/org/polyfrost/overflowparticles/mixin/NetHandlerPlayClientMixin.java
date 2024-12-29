package org.polyfrost.overflowparticles.mixin;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.util.EnumParticleTypes;
import org.polyfrost.overflowparticles.OverflowParticles;
import org.polyfrost.overflowparticles.config.BlockParticleEntry;
import org.polyfrost.overflowparticles.config.ModConfig;
import org.polyfrost.overflowparticles.config.ParticleConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class NetHandlerPlayClientMixin {

    @Inject(method = "handleParticles", at = @At("HEAD"), cancellable = true)
    private void serverFalling(S2APacketParticles packetIn, CallbackInfo ci) {
        if (packetIn.getParticleType() != EnumParticleTypes.BLOCK_DUST) return;
        ParticleConfig config = OverflowParticles.INSTANCE.getConfigs().get(37);
        if (!config.getEntry().getEnabled()) ci.cancel();
        BlockParticleEntry entry = ModConfig.INSTANCE.getBlockSetting();
        if (entry.getHideRunning()) ci.cancel();
    }
}
