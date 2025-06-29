package org.polyfrost.overflowparticles.mixin.client;

//#if MC <= 1.12.2

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import org.polyfrost.overflowparticles.client.config.BlockParticleEntry;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.utils.VanillaParticles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityLivingBase.class)
public abstract class Mixin_EntityLivingBase_FallStateParticles extends Entity {

    public Mixin_EntityLivingBase_FallStateParticles(World worldIn) {
        super(worldIn);
    }

    @ModifyExpressionValue(method = "updateFallState", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getMaterial()Lnet/minecraft/block/material/Material;"))
    private Material fall(Material original) {
        if (this.worldObj != null && !this.worldObj.isRemote) {
            return original;
        }

        ParticleConfig config = PerParticleConfigManager.getConfigByType(VanillaParticles.BLOCKS);
        if (!config.getEnabled()) {
            return Material.air;
        }

        BlockParticleEntry entry = PerParticleConfigManager.getBlockSetting();
        if (entry.getHideRunning()) {
            if (entry.getHideMode() == 1) {
                return Material.air;
            } else if (!isInvisible()) {
                return Material.air;
            }
        }

        return original;
    }

}
//#endif
