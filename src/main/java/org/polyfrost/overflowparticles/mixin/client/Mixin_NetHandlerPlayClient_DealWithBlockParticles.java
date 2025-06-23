package org.polyfrost.overflowparticles.mixin.client;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.util.EnumParticleTypes;
import org.polyfrost.overflowparticles.client.ParticleType;
import org.polyfrost.overflowparticles.client.config.BlockParticleEntry;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class Mixin_NetHandlerPlayClient_DealWithBlockParticles {

    @Inject(method = "handleParticles", at = @At("HEAD"), cancellable = true)
    private void serverFalling(S2APacketParticles packetIn, CallbackInfo ci) {
        if (packetIn.getParticleType() != EnumParticleTypes.BLOCK_DUST) {
            return;
        }

        ParticleConfig config = PerParticleConfigManager.getConfigByType(ParticleType.BLOCKS);
        if (!config.getEnabled()) {
            ci.cancel();
        }

        BlockParticleEntry entry = PerParticleConfigManager.getBlockSetting();
        if (entry.getHideRunning()) {
            ci.cancel();
        }
    }

}
