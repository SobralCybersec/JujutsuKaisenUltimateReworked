package com.jujutsu.jujutsucraftaddon.procedures.combat;

import com.jujutsu.jujutsucraftaddon.init.mod.JujutsucraftaddonModMobEffects;
import com.jujutsu.jujutsucraftaddon.network.JujutsucraftaddonModVariables;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class AnimationService {

    public static void applyRunAnimation(Entity entity) {
        if (entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .orElse(new JujutsucraftaddonModVariables.PlayerVariables()).Run != 1) {
            return;
        }

        if (!(entity instanceof LivingEntity _entity) || _entity.level().isClientSide()) {
            return;
        }

        if (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(JujutsucraftaddonModMobEffects.ANIMATION.get())) {
            return;
        }

        _entity.addEffect(new MobEffectInstance(JujutsucraftaddonModMobEffects.ANIMATION.get(), 10, 1, false, false));
    }
}
