package org.polyfrost.overflowparticles.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.polyfrost.overflowparticles.client.config.BlockParticleEntry;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.particles.VanillaParticles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class Mixin_FallStateParticles extends Entity {
    public Mixin_FallStateParticles(World worldIn) {
        super(worldIn);
    }

    //#if FABRIC
    @ModifyExpressionValue(method = "fall", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getMaterial()Lnet/minecraft/block/material/Material;"))
    private Material fall(Material original) {
        if (this.world != null && !this.world.isClient) {
            return original;
        }

        ParticleConfig config = PerParticleConfigManager.getConfigByType(VanillaParticles.BLOCKS);
        if (!config.getEnabled()) {
            return Material.AIR;
        }

        BlockParticleEntry entry = PerParticleConfigManager.getBlockSetting();
        if (entry.getHideRunning()) {
            if (entry.getHideMode() == 1) {
                return Material.AIR;
            } else if (!isInvisible()) {
                return Material.AIR;
            }
        }

        return original;
    }
    //#else
    //$$ @ModifyExpressionValue(method = "updateFallState", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;isAir(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;)Z", remap = false))
    //$$ private boolean fall(boolean original) {
    //$$     if (this.world != null && !this.world.isRemote) {
    //$$        return original;
    //$$     }
    //$$
    //$$     ParticleConfig config = PerParticleConfigManager.getConfigByType(VanillaParticles.BLOCKS);
    //$$     if (!config.getEnabled()) {
    //$$         return true;
    //$$     }
    //$$
    //$$     BlockParticleEntry entry = PerParticleConfigManager.getBlockSetting();
    //$$     if (entry.getHideRunning()) {
    //$$         if (entry.getHideMode() == 1) {
    //$$             return true;
    //$$         } else if (!isInvisible()) {
    //$$             return true;
    //$$         }
    //$$     }
    //$$
    //$$     return original;
    //$$ }
    //#endif
}
