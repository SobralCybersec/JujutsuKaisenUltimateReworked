package com.jujutsu.jujutsucraftaddon.abilities.items.songoftime;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

public class SongOfTime {
    private static final int RADIUS = 300;

    public static void activate(MinecraftServer server, ServerPlayer player) {
        OcarinaAnimation.play(player);
        
        // Restore all players
        server.getPlayerList().getPlayers().forEach(p -> {
            if (StateManager.hasState(p)) {
                StateRestorer.restorePlayer(p, StateManager.getState(p));
            }
        });
        
        // Restore nearby entities
        Vec3 center = player.position();
        List<Entity> entities = player.level()
            .getEntitiesOfClass(Entity.class, new AABB(center, center).inflate(RADIUS), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(e -> e.distanceToSqr(center)))
            .toList();
        
        entities.forEach(entity -> {
            if (StateManager.hasState(entity)) {
                StateRestorer.restoreEntity(entity, StateManager.getState((LivingEntity) entity));
            }
        });
        
        server.overworld().setDayTime(StateManager.getSavedTime());
    }
}
