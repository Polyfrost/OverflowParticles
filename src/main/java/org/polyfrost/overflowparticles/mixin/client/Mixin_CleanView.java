package org.polyfrost.overflowparticles.mixin.client;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import dev.deftu.omnicore.api.client.OmniClient;
import dev.deftu.omnicore.api.client.options.OmniPerspective;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import org.polyfrost.overflowparticles.client.config.OverflowParticlesConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityLivingBase.class)
public class Mixin_CleanView {
    @WrapWithCondition(
            method = "updatePotionEffects",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;spawnParticle(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V"
            )
    )
    private boolean overflowparticles$cleanView(World instance, EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int[] parameters) {
        return !shouldBlock(particleType);
    }

    @Unique
    private boolean shouldBlock(EnumParticleTypes type) {
        if (!OverflowParticlesConfig.isCleanView() || OmniPerspective.getCurrentPerspective() != OmniPerspective.FIRST_PERSON) {
            return false;
        }

        EntityLivingBase self = (EntityLivingBase) (Object) this;
        if (self != OmniClient.getPlayer()) {
            return false;
        }

        return type == EnumParticleTypes.SPELL_MOB || type == EnumParticleTypes.SPELL_MOB_AMBIENT;
    }
}
