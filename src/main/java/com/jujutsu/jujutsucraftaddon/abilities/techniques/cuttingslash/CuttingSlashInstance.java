package com.jujutsu.jujutsucraftaddon.abilities.techniques.cuttingslash;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class CuttingSlashInstance {
    private static final double SLASH_LENGTH = 15.0;
    private static final double RADIUS = 5.0;
    private static final double DAMAGE = 20.0;

    public static class Instance {
        private final Player player;
        private final Vec3 startPos;
        private final Vec3 direction;

        private boolean completed = false;

        public Instance(Player player, Vec3 startPos, Vec3 direction) {
            this.player = player;
            this.startPos = startPos;
            this.direction = direction;
        }

        public void tick () {
            if (completed) return;

            ServerLevel level = (ServerLevel) player.level();
            Vec3 endPos = startPos.add(direction.scale(SLASH_LENGTH));


            AABB slashArea = new AABB(startPos, endPos).inflate(RADIUS);
            List<Entity> entitiesInRange = level.getEntities(null, slashArea);


            for (Entity entity : entitiesInRange) {
                if (entity instanceof LivingEntity && entity != player) {
                    ((LivingEntity) entity).hurt(player.damageSources().magic(), (float) DAMAGE);
                }
                if (entity instanceof ItemEntity) {
                    ItemEntity itemEntity = (ItemEntity) entity;
                    BlockPos pos = new BlockPos((int) itemEntity.getX(), (int) itemEntity.getY(), (int) itemEntity.getZ());
                    level.destroyBlock(BlockPos.containing(itemEntity.getX(), itemEntity.getY(), itemEntity.getZ()), false);
                }
            }

            completed = true;
        }

        public boolean isComplete () {
            return completed;
        }
    }
}
