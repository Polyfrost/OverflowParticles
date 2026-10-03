package org.polyfrost.overflowparticles.mixin.client;

//? if >1.8.9 {
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
//?} else {
/*import net.minecraft.client.network.handler.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
*///?}
import org.polyfrost.overflowparticles.client.config.BlockParticleEntry;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.particles.VanillaParticles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >1.8.9 {
@Mixin(ClientPacketListener.class)
public class Mixin_DealWithBlockParticles {
    @Inject(method = "handleParticleEvent", at = @At("HEAD"), cancellable = true)
    private void overflowparticles$onServerFell(ClientboundLevelParticlesPacket packetIn, CallbackInfo ci) {
        //~ if < 26.3 'particle()' -> 'getParticle()'
        if (packetIn.particle().getType() != VanillaParticles.BLOCK_DUST.getId()) {
//?} else {
/*@Mixin(ClientPlayNetworkHandler.class)
public class Mixin_DealWithBlockParticles {
    @Inject(method = "handleParticle", at = @At("HEAD"), cancellable = true)
    private void overflowparticles$onServerFell(ParticleS2CPacket packetIn, CallbackInfo ci) {
        if (packetIn.getType() != VanillaParticles.BLOCK_DUST.getId()) {
*///?}
            return;
        }

        ParticleConfig config = PerParticleConfigManager.getConfigByType(VanillaParticles.BLOCKS);
        if (!config.getEnabled()) {
            ci.cancel();
        }

        BlockParticleEntry entry = PerParticleConfigManager.getBlockSetting();
        if (entry.getHideRunning()) {
            ci.cancel();
        }
    }
}
