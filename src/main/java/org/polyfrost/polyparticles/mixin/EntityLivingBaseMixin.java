package org.polyfrost.polyparticles.mixin;

import cc.polyfrost.oneconfig.libs.universal.UMinecraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.polyfrost.polyparticles.PolyParticles;
import org.polyfrost.polyparticles.config.BlockParticleEntry;
import org.polyfrost.polyparticles.config.MainConfig;
import org.polyfrost.polyparticles.config.ModConfig;
import org.polyfrost.polyparticles.config.ParticleConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityLivingBase.class)
public class EntityLivingBaseMixin {
    @SuppressWarnings({"ConstantConditions"})
    @Inject(method = "updatePotionEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnParticle(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V"), cancellable = true)
    private void cleanView(CallbackInfo ci) {
        if (MainConfig.INSTANCE.getSettings().getCleanView() && (Object) this == UMinecraft.getPlayer()) {
            ci.cancel();
        }
    }

    @Redirect(method = "updateFallState", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getMaterial()Lnet/minecraft/block/material/Material;"))
    private Material fall(Block instance) {
        ParticleConfig config = PolyParticles.INSTANCE.getConfigs().get(37);
        if (!config.enabled) return Material.air;
        BlockParticleEntry entry = ModConfig.INSTANCE.getBlockSetting();
        if (entry.getHideRunning()) {
            if (entry.getHideMode()) {
                return Material.air;
            } else if (!((Entity) (Object) this).isInvisible()) {
                return Material.air;
            }
        }

        return instance.getMaterial();
    }
}
