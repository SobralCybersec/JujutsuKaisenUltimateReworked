package com.jujutsu.jujutsucraftaddon.mixins;

import com.jujutsu.jujutsucraftaddon.procedures.SukunaAttackAnimationsProcedure;
import com.jujutsu.jujutsucraftaddon.procedures.combat.*;
import net.mcreator.jujutsucraft.procedures.WhenEntityTakesDamageProcedure;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.eventbus.api.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = WhenEntityTakesDamageProcedure.class, priority = -10000)
public abstract class WhenEntityAttackedProcedureMixinRefactored {

    @Inject(at = @At("HEAD"), method = "execute", remap = false, cancellable = true)
    private static void execute(Event event, LevelAccessor world, DamageSource damagesource, 
                               Entity entity, Entity immediatesourceentity, Entity sourceentity, 
                               double amount, CallbackInfo ci) {
        if (damagesource == null || entity == null || immediatesourceentity == null || sourceentity == null) {
            return;
        }

        ci.cancel();

        if (damagesource.is(TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("forge:animation")))) {
            return;
        }

        SukunaAttackAnimationsProcedure.execute(sourceentity, entity, world);
        AnimationService.applyRunAnimation(sourceentity);

        if (handleDodgeMechanics(event, world, entity, sourceentity)) {
            return;
        }

        BuffService.applyItadoriAwakeningBuff(sourceentity, entity);

        if (CombatValidationService.shouldCancelDamage(world, entity, sourceentity)) {
            cancelEvent(event);
            return;
        }

        DamageCalculationService.DamageModification damageModification = 
            new DamageCalculationService.DamageModification(amount);

        // Additional damage calculation logic would go here
        // This is a simplified version showing the pattern
    }

    private static boolean handleDodgeMechanics(Event event, LevelAccessor world, Entity entity, Entity sourceentity) {
        if (DodgeService.tryTodoSwap(entity, world, sourceentity)) {
            cancelEvent(event);
            return true;
        }

        if (DodgeService.tryUltraInstinctDodge(entity, world)) {
            cancelEvent(event);
            return true;
        }

        if (DodgeService.tryUroDodge(entity, world)) {
            cancelEvent(event);
            return true;
        }

        if (DodgeService.tryRejectedZeninDodge(entity, world)) {
            cancelEvent(event);
            return true;
        }

        return false;
    }

    private static void cancelEvent(Event event) {
        if (event != null && event.isCancelable()) {
            event.setCanceled(true);
        } else if (event != null && event.hasResult()) {
            event.setResult(Event.Result.DENY);
        }
    }
}
