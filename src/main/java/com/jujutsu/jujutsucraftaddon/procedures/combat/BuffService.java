package com.jujutsu.jujutsucraftaddon.procedures.combat;

import com.jujutsu.jujutsucraftaddon.init.mod.JujutsucraftaddonModMobEffects;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class BuffService {

    public static void applyItadoriAwakeningBuff(Entity sourceentity, Entity entity) {
        if (!(sourceentity instanceof LivingEntity _livEnt && _livEnt.hasEffect(JujutsucraftaddonModMobEffects.ITADORI_AWAKENING.get()))) {
            return;
        }

        if (Math.random() >= 1.0 / 60.0) {
            return;
        }

        sourceentity.getPersistentData().putDouble("skill", 3810.0);
        sourceentity.getPersistentData().putDouble("cnt6", 4.0);

        if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
            _entity.addEffect(new MobEffectInstance(JujutsucraftModMobEffects.CURSED_TECHNIQUE.get(), 60, 1, false, false));
        }

        calculateTargetPosition(sourceentity, entity);

        if (!(sourceentity instanceof LivingEntity _livEnt01 && _livEnt01.hasEffect(JujutsucraftaddonModMobEffects.KOKUSEN_N.get()))) {
            if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                _entity.addEffect(new MobEffectInstance(JujutsucraftaddonModMobEffects.KOKUSEN_N.get(), 20, 1, false, false));
            }
        }
    }

    private static void calculateTargetPosition(Entity sourceentity, Entity entity) {
        double yaw = Math.toRadians(sourceentity.getYRot() + 90.0F);
        double pitch = Math.toRadians(sourceentity.getXRot());
        
        sourceentity.getPersistentData().putDouble("x_pos", 
            entity.getX() + Math.cos(yaw) * Math.cos(pitch) * (2.0F + entity.getBbWidth()));
        sourceentity.getPersistentData().putDouble("y_pos", 
            entity.getY() + entity.getBbHeight() * 0.75 + Math.sin(pitch) * -1.0 * (2.0F + entity.getBbWidth()));
        sourceentity.getPersistentData().putDouble("z_pos", 
            entity.getZ() + Math.sin(yaw) * Math.cos(pitch) * (2.0F + entity.getBbWidth()));
    }
}
