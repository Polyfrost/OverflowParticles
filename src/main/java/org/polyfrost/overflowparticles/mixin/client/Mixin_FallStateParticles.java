package org.polyfrost.overflowparticles.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
//? if >1.8.9
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
//? if >1.8.9 {
import net.minecraft.world.level.Level;
//?} else {
/*import net.minecraft.block.material.Material;
import net.minecraft.world.World;
*///?}
import org.polyfrost.overflowparticles.client.config.BlockParticleEntry;
import org.polyfrost.overflowparticles.client.config.ParticleConfig;
import org.polyfrost.overflowparticles.client.config.PerParticleConfigManager;
import org.polyfrost.overflowparticles.client.particles.VanillaParticles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class Mixin_FallStateParticles extends Entity {
    //? if >1.8.9 {
    public Mixin_FallStateParticles(EntityType<?> arg, Level arg2) {
        super(arg, arg2);
    }

    @ModifyExpressionValue(
            method = "checkFallDamage",
            at = @At(
                    value = "INVOKE",
                    //? if fabric {
                    target = "Lnet/minecraft/world/level/block/state/BlockState;isAir()Z"
                    //?} else {
                    /*target = "Lnet/minecraft/world/level/block/state/BlockState;isAir(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z",
                    remap = false
                    *///?}
            )
    )
    private boolean fall(boolean original) {
        Level world = overflowparticles$getWorld();
        if (world != null && !overflowparticles$isClient(world)) {
           return original;
        }

        ParticleConfig config = PerParticleConfigManager.getConfigByType(VanillaParticles.BLOCKS);
        if (!config.getEnabled()) {
            return true;
        }

        BlockParticleEntry entry = PerParticleConfigManager.getBlockSetting();
        if (entry.getHideRunning()) {
            if (entry.getHideMode() == 1) {
                return true;
            } else if (!isInvisible()) {
                return true;
            }
        }

        return original;
    }

    private Level overflowparticles$getWorld() {
        return this.level();
    }

    private static boolean overflowparticles$isClient(Level world) {
        return world.isClientSide();
    }
    //?} else {
    /*public Mixin_FallStateParticles(World world) {
        super(world);
    }

    @ModifyExpressionValue(
            method = "checkFallDamage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/Block;getMaterial()Lnet/minecraft/block/material/Material;"
            )
    )
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
    *///?}
}
