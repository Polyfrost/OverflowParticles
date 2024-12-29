package org.polyfrost.overflowparticles.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import org.polyfrost.overflowparticles.OverflowParticles;
import org.polyfrost.overflowparticles.config.BlockParticleEntry;
import org.polyfrost.overflowparticles.config.ModConfig;
import org.polyfrost.overflowparticles.config.ParticleConfig;
import org.polyfrost.overflowparticles.config.Settings;
import org.polyfrost.overflowparticles.utils.UtilKt;
import org.polyfrost.universal.UMinecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin extends Entity {
    public EntityLivingBaseMixin(World worldIn) {
        super(worldIn);
    }

    @SuppressWarnings({"ConstantConditions"})
    @Inject(method = "updatePotionEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnParticle(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V"), cancellable = true)
    private void cleanView(CallbackInfo ci) {
        if (worldObj != null && !worldObj.isRemote) return;
        if (Settings.INSTANCE.getCleanView() && (Object) this == UMinecraft.getPlayer()) {
            ci.cancel();
        }
    }

    @ModifyExpressionValue(method = "updateFallState", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getMaterial()Lnet/minecraft/block/material/Material;"))
    private Material fall(Material original) {
        if (worldObj != null && !worldObj.isRemote) return original;
        ParticleConfig config = OverflowParticles.INSTANCE.getConfigs().get(37);
        if (!config.getEntry().getEnabled()) return Material.air;
        BlockParticleEntry entry = ModConfig.INSTANCE.getBlockSetting();
        if (entry.getHideRunning()) {
            if (entry.getHideMode() == 1) {
                return Material.air;
            } else if (!isInvisible()) {
                return Material.air;
            }
        }

        return original;
    }

    @ModifyConstant(method = "onDeathUpdate", constant = @Constant(intValue = 20, ordinal = 1))
    private int multiplier(int constant) {
        ParticleConfig config = OverflowParticles.INSTANCE.getConfigs().get(0);
        if (config == null || config.getEntry().getMultiplier() == 1) return constant;
        return (int) (constant * config.getEntry().getMultiplier());
    }

    @Inject(method = "onDeathUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnParticle(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V"))
    private void cancel(CallbackInfo ci) {
        UtilKt.setMultiplied(true);
    }
}
