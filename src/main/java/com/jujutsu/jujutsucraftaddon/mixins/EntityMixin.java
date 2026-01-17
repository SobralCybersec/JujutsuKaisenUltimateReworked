package com.jujutsu.jujutsucraftaddon.mixins;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "getTeamColor", at = @At("HEAD"), cancellable = true)
    private void modifyGlowColor(CallbackInfoReturnable<Integer> cir) {
        Entity self = (Entity)(Object)this;

        if (self.level().isClientSide && self instanceof LivingEntity living && living.getPersistentData().getBoolean("glowing")) {
            if (living instanceof Monster) {
                cir.setReturnValue(0xFFFF8080); // Light red
            } else if (living instanceof Animal) {
                cir.setReturnValue(0xFF00FFFF); // Aqua / light blue
            } else if (living instanceof Player) {
                cir.setReturnValue(0xFFFFFFFF); // White
            }
        }
    }

}
