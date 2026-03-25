package com.jujutsu.jujutsucraftaddon.procedures.combat;

import net.mcreator.jujutsucraft.init.JujutsucraftModGameRules;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;

public class CombatValidationService {

    public static boolean shouldCancelDamage(LevelAccessor world, Entity entity, Entity sourceentity) {
        if (isSameTarget(entity, sourceentity)) return true;
        if (isSameTamedOwner(entity, sourceentity)) return true;
        if (isPvPDisabled(world, entity, sourceentity)) return true;
        if (isTargetLocked(entity, sourceentity)) return false;
        if (isSameFriend(entity, sourceentity)) return true;
        
        return false;
    }

    private static boolean isSameTarget(Entity entity, Entity sourceentity) {
        LivingEntity sourceTarget = sourceentity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null;
        LivingEntity entityTarget = entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null;
        
        return sourceTarget instanceof LivingEntity && sourceTarget == entityTarget;
    }

    private static boolean isSameTamedOwner(Entity entity, Entity sourceentity) {
        if (!(entity instanceof TamableAnimal _tamEnt && _tamEnt.isTame()) && 
            !(sourceentity instanceof TamableAnimal _tamEnt2 && _tamEnt2.isTame())) {
            return false;
        }

        LivingEntity entityOwner = entity instanceof TamableAnimal _tamEnt ? _tamEnt.getOwner() : null;
        LivingEntity sourceOwner = sourceentity instanceof TamableAnimal _tamEnt ? _tamEnt.getOwner() : null;

        return entityOwner == sourceentity || sourceOwner == entity || entityOwner == sourceOwner;
    }

    private static boolean isPvPDisabled(LevelAccessor world, Entity entity, Entity sourceentity) {
        if (world.getLevelData().getGameRules().getBoolean(JujutsucraftModGameRules.JUJUTSUPVP)) {
            return false;
        }

        boolean sourceIsPlayer = sourceentity instanceof Player || sourceentity.getPersistentData().getBoolean("Player");
        boolean entityIsPlayer = entity instanceof Player || entity.getPersistentData().getBoolean("Player");

        if (!sourceIsPlayer || !entityIsPlayer) {
            return false;
        }

        boolean hasSukunaEffect = (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(JujutsucraftModMobEffects.SUKUNA_EFFECT.get())) ||
                                  (sourceentity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(JujutsucraftModMobEffects.SUKUNA_EFFECT.get()));

        return !hasSukunaEffect;
    }

    private static boolean isTargetLocked(Entity entity, Entity sourceentity) {
        LivingEntity entityTarget = entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null;
        LivingEntity sourceTarget = sourceentity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null;

        boolean entityLocked = entityTarget == sourceentity && entity.getPersistentData().getDouble("cnt_target") > 6.0;
        boolean sourceLocked = sourceTarget == entity && sourceentity.getPersistentData().getDouble("cnt_target") > 6.0;

        return entityLocked || sourceLocked;
    }

    private static boolean isSameFriend(Entity entity, Entity sourceentity) {
        double friendNum = sourceentity.getPersistentData().getDouble("friend_num");
        return friendNum != 0.0 && friendNum == entity.getPersistentData().getDouble("friend_num");
    }
}
