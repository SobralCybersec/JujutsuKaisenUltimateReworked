package com.jujutsu.jujutsucraftaddon.abilities.fireblast;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

import static com.jujutsu.jujutsucraftaddon.abilities.fireblast.FireBlastAbility.scheduledRemovals;

public class FireBlastCreate {
    private static final int FIRE_DURATION = 80;
    private static final int FIRE_LIFETIME = 60;
    private static final int BLAST_RADIUS = 5;

    public static void createFireBlast(Player player) {

        if (player.level().isClientSide) return;

        ServerLevel serverLevel = (ServerLevel) player.level();
        BlockPos centerPos = player.blockPosition();
        List<BlockPos> fireBlocks = new ArrayList<>();

        BlockPos.betweenClosedStream(
                centerPos.offset(-BLAST_RADIUS, 0, -BLAST_RADIUS),
                centerPos.offset(BLAST_RADIUS, 0, BLAST_RADIUS)
        ).forEach(pos -> {
            BlockPos immutablePos = pos.immutable();
            if (serverLevel.getBlockState(immutablePos).isAir()) {
                serverLevel.setBlock(immutablePos, Blocks.SOUL_FIRE.defaultBlockState(), 3);
                fireBlocks.add(immutablePos);
            }
        });

        igniteEntities(serverLevel, centerPos);
        scheduledRemovals.add(new FireBlastAbility.ScheduledFireRemoval(serverLevel, fireBlocks, FIRE_LIFETIME));

    }

    public static void igniteEntities(ServerLevel level, BlockPos centerPos) {
        List<Entity> nearbyEntities = level.getEntities(null, new AABB(
                centerPos.getX() - BLAST_RADIUS, centerPos.getY(), centerPos.getZ() - BLAST_RADIUS,
                centerPos.getX() + BLAST_RADIUS, centerPos.getY() + 2, centerPos.getZ() + BLAST_RADIUS
        ));

        for (Entity entity : nearbyEntities) {
            if (entity instanceof LivingEntity && !(entity instanceof Player)) {
                entity.setSecondsOnFire(FIRE_DURATION / 20);
            }
        }
    }
}
