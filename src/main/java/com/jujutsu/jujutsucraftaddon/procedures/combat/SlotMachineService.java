package com.jujutsu.jujutsucraftaddon.procedures.combat;

import net.mcreator.jujutsucraft.entity.SlotEntity;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class SlotMachineService {

    public static int calculateSlotNumber(Entity entity, double previousNumber) {
        int unluckAmplifier = entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(MobEffects.UNLUCK)
            ? _livEnt.getEffect(MobEffects.UNLUCK).getAmplifier()
            : 0;

        if (unluckAmplifier >= 9) {
            return calculateUnluckyNumber(previousNumber);
        }

        int luckAmplifier = entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(MobEffects.LUCK)
            ? _livEnt.getEffect(MobEffects.LUCK).getAmplifier()
            : 0;

        if (luckAmplifier >= 9) {
            return (int) previousNumber;
        }

        return calculateRandomNumber(entity, previousNumber, luckAmplifier);
    }

    private static int calculateUnluckyNumber(double previousNumber) {
        double result = previousNumber - 1.0;
        if (previousNumber < 1.0) {
            result = 7.0;
        }
        return (int) result;
    }

    private static int calculateRandomNumber(Entity entity, double previousNumber, int luckAmplifier) {
        int iterations = (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(MobEffects.LUCK))
            ? (luckAmplifier + 1) * 2
            : 2;

        double result = 0;
        for (int i = 0; i < iterations; i++) {
            result = Math.ceil(Math.random() * 7.0);
            
            if (result != previousNumber) {
                if (!(entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(MobEffects.LUCK))) {
                    break;
                }
            } else if (Math.random() < 0.35) {
                break;
            }
        }
        return (int) result;
    }

    public static int getSlotMode(Entity entity) {
        if (!(entity instanceof SlotEntity _datEnt)) {
            return 0;
        }
        return _datEnt.getEntityData().get(SlotEntity.DATA_SLOT_MODE);
    }

    public static int getSlotNumber(Entity entity) {
        if (!(entity instanceof SlotEntity _datEnt)) {
            return 0;
        }
        return _datEnt.getEntityData().get(SlotEntity.DATA_SLOT_NUM);
    }

    public static void setSlotNumber(Entity entity, int number) {
        if (entity instanceof SlotEntity _datEnt) {
            _datEnt.getEntityData().set(SlotEntity.DATA_SLOT_NUM, number);
        }
    }

    public static boolean checkJackpot(Entity entity1, Entity entity2, Entity entity3) {
        int num1 = getSlotNumber(entity1);
        int num2 = getSlotNumber(entity2);
        int num3 = getSlotNumber(entity3);

        return num1 > 0 && num1 == num2 && num2 == num3;
    }
}
