package org.polyfrost.overflowparticles.mixin.client;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import dev.deftu.omnicore.api.client.OmniClient;
import dev.deftu.omnicore.api.client.options.OmniPerspective;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.EntityEffectParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class Mixin_CleanView {
    @WrapWithCondition(
        method = "tickStatusEffects",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"
        )
    )
    private boolean overflowparticles$cleanView(
            World level,
            ParticleEffect particle,
            double x, double y, double z,
            double xSpeed, double ySpeed, double zSpeed
    ) {
        return !shouldBlock(particle);
    }

    @Unique
    private boolean shouldBlock(ParticleEffect particle) {
        if (!OverflowParticlesConfig.isCleanView() || OmniPerspective.getCurrentPerspective() != OmniPerspective.FIRST_PERSON) {
            return false;
        }

        LivingEntity self = (LivingEntity) (Object) this;
        if (self != OmniClient.getPlayer()) {
            return false;
        }

        return particle.getType() == ParticleTypes.ENTITY_EFFECT
            || particle instanceof EntityEffectParticleEffect;
    }
}
