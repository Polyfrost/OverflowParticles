package org.polyfrost.overflowparticles.mixin.client;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import dev.deftu.omnicore.api.client.OmniClient;
import dev.deftu.omnicore.api.client.options.OmniPerspective;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class Mixin_CleanView {
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
        if (!OverflowParticlesConfig.isCleanView() || OmniPerspective.getCurrentPerspective() != OmniPerspective.FIRST_PERSON) {
            return false;
        }

        LivingEntity self = (LivingEntity) (Object) this;
        if (self != OmniClient.getPlayer()) {
            return false;
        }

        return particle == ParticleTypes.ENTITY_EFFECT
                //#if MC >= 1.21.1
                //$$ ;
                //#else
                || particle == ParticleTypes.AMBIENT_ENTITY_EFFECT;
                //#endif
    }
}
