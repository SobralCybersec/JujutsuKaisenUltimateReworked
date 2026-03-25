package com.jujutsu.jujutsucraftaddon.mixins;

import com.jujutsu.jujutsucraftaddon.services.EntityGlowService;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "getTeamColor", at = @At("HEAD"), cancellable = true)
    private void modifyGlowColor(CallbackInfoReturnable<Integer> cir) {
        Entity self = (Entity)(Object)this;
        
        if (self instanceof LivingEntity living && EntityGlowService.shouldGlow(living)) {
            cir.setReturnValue(EntityGlowService.getGlowColor(living));
        }
    }

    @Inject(method = "isCurrentlyGlowing", at = @At("RETURN"), cancellable = true)
    private void modifyGlowingTagCondition(CallbackInfoReturnable<Boolean> cir) {
        Entity self = (Entity)(Object)this;
        
        if (self instanceof LivingEntity living && EntityGlowService.shouldGlow(living)) {
            cir.setReturnValue(true);
        }
    }
}