package com.jujutsu.jujutsucraftaddon.abilities.elemental.fireblast;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Mod.EventBusSubscriber(modid = "jujutsucraftaddon")
public class FireBlastAbility {
    private static final int BLAST_RADIUS = 5;

    public static final List<ScheduledFireRemoval> scheduledRemovals = new ArrayList<>();


    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Iterator<ScheduledFireRemoval> iterator = scheduledRemovals.iterator();
            while (iterator.hasNext()) {
                ScheduledFireRemoval removal = iterator.next();
                removal.ticksLeft--;

                if (removal.ticksLeft <= 0) {
                    for (BlockPos blockPos : removal.blockPositions) {
                        if (removal.level.getBlockState(blockPos).is(Blocks.SOUL_FIRE)) {
                            removal.level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
                        }
                    }
                    iterator.remove();
                }
            }
        }
    }

    public static class ScheduledFireRemoval {
        private final ServerLevel level;
        private final List<BlockPos> blockPositions;
        private int ticksLeft;

        public ScheduledFireRemoval(ServerLevel level, List<BlockPos> blockPositions, int ticksLeft) {
            this.level = level;
            this.blockPositions = blockPositions;
            this.ticksLeft = ticksLeft;
        }
    }
}
