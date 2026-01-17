package com.jujutsu.jujutsucraftaddon.abilities.earthwall;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;

import static com.jujutsu.jujutsucraftaddon.abilities.earthwall.EarthWallAbility.scheduledRemovals;
import static com.jujutsu.jujutsucraftaddon.abilities.earthwall.EarthWallKnockback.knockbackEntities;

public class EarthWallCreation {
    public static final int WALL_WIDTH = 20;
    public static final int WALL_HEIGHT = 10;
    public static final int WALL_LIFETIME = 100;

    public static void createEarthWall(LivingEntity player) {
        if (player.level().isClientSide) return;

        ServerLevel serverLevel = (ServerLevel) player.level();
        float yaw = player.getYRot();

        int dirX = (int) Math.round(-Math.sin(Math.toRadians(yaw)));
        int dirZ = (int) Math.round(Math.cos(Math.toRadians(yaw)));


        BlockPos startPos = player.blockPosition().offset(dirX * 3, 0, dirZ * 3);

        List<BlockPos> placedBlocks = new ArrayList<>();

        for (int x = -WALL_WIDTH / 2; x <= WALL_WIDTH / 2; x++) {
            for (int y = 0; y < WALL_HEIGHT; y++) {
                BlockPos pos;
                if (Math.abs(dirX) > Math.abs(dirZ)) {

                    pos = startPos.offset(0, y, x);
                } else {

                    pos = startPos.offset(x, y, 0);
                }

                if (serverLevel.getBlockState(pos).isAir()) { // Only place if air
                    serverLevel.setBlock(pos, Blocks.STONE.defaultBlockState(), 3);
                    placedBlocks.add(pos);
                }
            }
        }

        knockbackEntities(serverLevel, startPos, dirX, dirZ);

        scheduledRemovals.add(new EarthWallAbility.ScheduledWallRemoval(serverLevel, placedBlocks, WALL_LIFETIME));
    }
}
