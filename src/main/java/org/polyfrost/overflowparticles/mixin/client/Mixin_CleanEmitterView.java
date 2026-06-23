package org.polyfrost.overflowparticles.mixin.client;

//? if >=1.16.5 {
import net.minecraft.client.Minecraft;
//?} else {
/*import dev.deftu.omnicore.api.client.OmniClient;
*///?}
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.TrackingEmitter;
import net.minecraft.world.entity.Entity;
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TrackingEmitter.class)
public abstract class Mixin_CleanEmitterView extends Particle {
    @Shadow @Final private Entity entity;

    public Mixin_CleanEmitterView() {
        super(null, 0, 0, 0, 0, 0, 0);
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void cleanView(CallbackInfo ci) {
        if (OverflowParticlesConfig.isCleanView() && this.entity == overflowparticles$getPlayer()) {
            this.remove();
            ci.cancel();
        }
    }

    private static Entity overflowparticles$getPlayer() {
        //? if >=1.16.5 {
        return Minecraft.getInstance().player;
        //?} else {
        /*return OmniClient.getPlayer();
        *///?}
    }
}
