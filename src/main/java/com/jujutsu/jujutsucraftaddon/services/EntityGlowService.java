package com.jujutsu.jujutsucraftaddon.services;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;

public final class EntityGlowService {

    private static final int MONSTER_GLOW_COLOR = 0xFFFF8080;
    private static final int ANIMAL_GLOW_COLOR = 0xFF00FFFF;
    private static final int PLAYER_GLOW_COLOR = 0xFFFFFFFF;

    private EntityGlowService() {
    }

    public static int getGlowColor(LivingEntity entity) {
        if (entity instanceof Monster) {
            return MONSTER_GLOW_COLOR;
        }
        if (entity instanceof Animal) {
            return ANIMAL_GLOW_COLOR;
        }
        if (entity instanceof Player) {
            return PLAYER_GLOW_COLOR;
        }
        return PLAYER_GLOW_COLOR;
    }

    public static boolean shouldGlow(LivingEntity entity) {
        return entity.level().isClientSide && entity.getPersistentData().getBoolean("glowing");
    }
}
