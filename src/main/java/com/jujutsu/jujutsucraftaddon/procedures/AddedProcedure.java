package com.jujutsu.jujutsucraftaddon.procedures;

import com.jujutsu.jujutsucraftaddon.init.mod.JujutsucraftaddonModMobEffects;
import com.jujutsu.jujutsucraftaddon.network.JujutsucraftaddonModVariables;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;

public class AddedProcedure {
    public static void execute(LevelAccessor world, Entity entity, String effect) {
        if (entity == null || effect == null || !(entity instanceof LivingEntity living))
            return;

        if (!effect.toLowerCase().contains("jackpot"))
            return;

        if (living.level().isClientSide())
            return;

        living.addEffect(new MobEffectInstance(JujutsucraftaddonModMobEffects.JACKPOT.get(), 60, 1, false, false));

        int luckAmplifier = living.hasEffect(MobEffects.LUCK) ? living.getEffect(MobEffects.LUCK).getAmplifier() : 0;
        living.addEffect(new MobEffectInstance(MobEffects.LUCK, 5020, luckAmplifier, false, false));

        String clan = entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .orElse(new JujutsucraftaddonModVariables.PlayerVariables()).Clans;

        if ("Hakari".equals(clan)) {
            int jackpotAmplifier = living.hasEffect(JujutsucraftModMobEffects.JACKPOT.get())
                    ? living.getEffect(JujutsucraftModMobEffects.JACKPOT.get()).getAmplifier() : 0;
            living.addEffect(new MobEffectInstance(JujutsucraftModMobEffects.JACKPOT.get(), 5020, jackpotAmplifier + 6, false, false));
        }
    }
}
