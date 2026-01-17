package com.jujutsu.jujutsucraftaddon.abilities.items.songoftime;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;

public class StateManager {
    private static final Map<LivingEntity, PlayerStateRecord> playerStates = new HashMap<>();
    private static long savedTime;

    public static void saveState(MinecraftServer server) {
        savedTime = server.overworld().getDayTime();
        
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            playerStates.put(player, new PlayerStateRecord(player));
        }
        
        for (ServerLevel level : server.getAllLevels()) {
            for (Entity entity : level.getAllEntities()) {
                if (entity instanceof LivingEntity living) {
                    playerStates.put(living, new PlayerStateRecord(living));
                }
            }
        }
    }

    public static PlayerStateRecord getState(LivingEntity entity) {
        return playerStates.get(entity);
    }

    public static long getSavedTime() {
        return savedTime;
    }

    public static boolean hasState(Entity entity) {
        return playerStates.containsKey(entity);
    }
}
