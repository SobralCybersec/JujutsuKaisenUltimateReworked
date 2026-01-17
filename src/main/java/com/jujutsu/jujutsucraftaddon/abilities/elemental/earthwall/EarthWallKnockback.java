package com.jujutsu.jujutsucraftaddon.abilities.earthwall;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.List;

import static com.jujutsu.jujutsucraftaddon.abilities.earthwall.EarthWallCreation.WALL_HEIGHT;
import static com.jujutsu.jujutsucraftaddon.abilities.earthwall.EarthWallCreation.WALL_WIDTH;

public class EarthWallKnockback {
    private static final double KNOCKBACK_FORCE = 1.5;

    public static void knockbackEntities(ServerLevel level, BlockPos wallPos, int dirX, int dirZ) {
        double range = WALL_WIDTH / 2.0 + 2.0; // Slightly wider than the wall
        List<Entity> nearbyEntities = level.getEntities(null,
                new net.minecraft.world.phys.AABB(
                        wallPos.getX() - range, wallPos.getY(), wallPos.getZ() - range,
                        wallPos.getX() + range, wallPos.getY() + WALL_HEIGHT, wallPos.getZ() + range
                )
        );

        for (Entity entity : nearbyEntities) {
            if (entity instanceof LivingEntity && !(entity instanceof Player)) { // Exclude the player
                double knockbackX = dirX * KNOCKBACK_FORCE;
                double knockbackZ = dirZ * KNOCKBACK_FORCE;
                entity.setDeltaMovement(knockbackX, 0.5, knockbackZ); // Apply force upwards slightly
                if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:ranged_ammo")))) {
                    entity.discard();
                }
                entity.hurtMarked = true; // Ensure motion syncs with the client
            }
        }
    }
}
