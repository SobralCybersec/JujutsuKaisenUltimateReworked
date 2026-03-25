package com.jujutsu.jujutsucraftaddon.procedures;

import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class TechniqueExecutor {

    public static void execute(LevelAccessor world, Entity entity, int t1, int s1, double tick, boolean isCreative) {
        entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(cap -> {
            cap.PlayerCurseTechnique = t1;
            cap.PlayerSelectCurseTechnique = s1;
            cap.PlayerSelectCurseTechniqueCost = tick;
            cap.syncPlayerVariables(entity);
        });
    }
}
