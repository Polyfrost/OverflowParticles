package org.polyfrost.overflowparticles.mixin.client;

//#if MC <= 1.12.2

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.utils.ParticleSpawner;
import org.polyfrost.overflowparticles.client.utils.VanillaParticles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityLivingBase.class)
public abstract class Mixin_EntityLivingBase_DeathParticleMultiplier extends Entity {

    public Mixin_EntityLivingBase_DeathParticleMultiplier(World worldIn) {
        super(worldIn);
    }

    @ModifyConstant(method = "onDeathUpdate", constant = @Constant(intValue = 20, ordinal = 1))
    private int overflowparticles$applyMultiplier(int constant) {
        ParticleConfig config = PerParticleConfigManager.getConfigByType(VanillaParticles.EXPLOSION_NORMAL);
        if (config.getMultiplier() == 1) {
            return constant;
        }

        return (int) (constant * config.getMultiplier());
    }

    @Inject(method = "onDeathUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnParticle(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V"))
    private void overflowparticles$updateMultiplyState(CallbackInfo ci) {
        ParticleSpawner.setMultiplied(true);
    }

}
//#endif
