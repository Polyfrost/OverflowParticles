package org.polyfrost.overflowparticles.mixin.particles;

import cc.polyfrost.oneconfig.libs.universal.UMinecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityParticleEmitter;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import org.polyfrost.overflowparticles.OverflowParticles;
import org.polyfrost.overflowparticles.config.MainConfig;
import org.polyfrost.overflowparticles.config.ParticleConfig;
import org.polyfrost.overflowparticles.utils.UtilKt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityParticleEmitter.class)
public class EntityParticleEmitterMixin extends EntityFX {
    @Shadow
    private EnumParticleTypes particleTypes;
    @Shadow
    private Entity attachedEntity;

    public EntityParticleEmitterMixin() {
        super(null, 0, 0, 0, 0, 0, 0);
    }

    @Inject(method = "onUpdate", at = @At("HEAD"), cancellable = true)
    public void cleanView(CallbackInfo ci) {
        if (MainConfig.INSTANCE.getSettings().getCleanView() && this.attachedEntity == UMinecraft.getPlayer()) {
            this.setDead();
            ci.cancel();
        }
    }

    @ModifyConstant(method = "onUpdate", constant = @Constant(intValue = 16))
    private int multiplier(int constant) {
        ParticleConfig config = OverflowParticles.INSTANCE.getConfigs().get(particleTypes.getParticleID());
        if (config == null || config.getEntry().getMultiplier() == 1) return constant;
        return (int) (constant * config.getEntry().getMultiplier());
    }

    @Inject(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnParticle(Lnet/minecraft/util/EnumParticleTypes;ZDDDDDD[I)V"))
    private void cancel(CallbackInfo ci) {
        UtilKt.setMultiplied(true);
    }
}