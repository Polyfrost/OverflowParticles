package org.polyfrost.overflowparticles.mixin.client;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
//? if >1.8.9 {
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
//?} else {
/*import net.minecraft.entity.particle.ParticleType;
import net.minecraft.world.World;
*///?}
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class Mixin_CleanView {
    //? if >1.8.9 {
    @WrapWithCondition(
        method = "tickEffects",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"
        )
    )
    private boolean overflowparticles$cleanView(
            Level level,
            ParticleOptions particle,
            double x, double y, double z,
            double xSpeed, double ySpeed, double zSpeed
    ) {
        return !shouldBlock(particle);
    }

    @Unique
    private boolean shouldBlock(ParticleOptions particle) {
        if (!OverflowParticlesConfig.isCleanView() || !Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
            return false;
        }

        LivingEntity self = (LivingEntity) (Object) this;
        if (self != Minecraft.getInstance().player) {
            return false;
        }

        return particle.getType() == ParticleTypes.ENTITY_EFFECT
            || particle instanceof ColorParticleOption;
    }
    //?} else {
    /*@WrapWithCondition(
        method = "tickStatusEffects",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/entity/particle/ParticleType;DDDDDD[I)V"
        )
    )
    private boolean overflowparticles$cleanView(
            World level,
            ParticleType particle,
            double x, double y, double z,
            double xSpeed, double ySpeed, double zSpeed,
            int[] parameters
    ) {
        return !shouldBlock(particle);
    }

    @Unique
    private boolean shouldBlock(ParticleType particle) {
        if (!OverflowParticlesConfig.isCleanView() || Minecraft.getInstance().options.perspective != 0) {
            return false;
        }

        LivingEntity self = (LivingEntity) (Object) this;
        if (self != Minecraft.getInstance().player) {
            return false;
        }

        return particle == ParticleType.SPELL_MOB
            || particle == ParticleType.SPELL_MOB_AMBIENT;
    }
    *///?}
}
