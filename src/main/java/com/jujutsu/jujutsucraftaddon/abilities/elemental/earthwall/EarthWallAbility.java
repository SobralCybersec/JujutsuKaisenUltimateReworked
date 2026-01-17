package com.jujutsu.jujutsucraftaddon.abilities.earthwall;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Mod.EventBusSubscriber(modid = "jujutsucraftaddon")
public class EarthWallAbility {
    public static final List<ScheduledWallRemoval> scheduledRemovals = new ArrayList<>();

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Iterator<ScheduledWallRemoval> iterator = scheduledRemovals.iterator();
            while (iterator.hasNext()) {
                ScheduledWallRemoval removal = iterator.next();
                removal.ticksLeft--;

                if (removal.ticksLeft <= 0) {
                    for (BlockPos blockPos : removal.blockPositions) {
                        if (removal.level.getBlockState(blockPos).is(Blocks.STONE)) {
                            removal.level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
                        }
                    }
                    iterator.remove();
                }
            }
        }
    }

    public static class ScheduledWallRemoval {
        private final ServerLevel level;
        private final List<BlockPos> blockPositions;
        private int ticksLeft;

        public ScheduledWallRemoval(ServerLevel level, List<BlockPos> blockPositions, int ticksLeft) {
            this.level = level;
            this.blockPositions = blockPositions;
            this.ticksLeft = ticksLeft;
        }
    }
}