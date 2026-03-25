package com.jujutsu.jujutsucraftaddon.procedures;

import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;

public class TechniqueValidator {

    public static boolean canUse(Entity entity, double cost, boolean isCreative) {
        if (isCreative) return true;
        
        double currentPower = entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCursePower;
        
        return currentPower >= cost;
    }

    public static boolean isCreativeMode(Entity entity) {
        if (entity instanceof ServerPlayer player) return player.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
        if (entity.level().isClientSide() && entity instanceof Player player) {
            return Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId()) != null &&
                    Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
        }
        return false;
    }

    public static String getTechniqueName(Entity entity) {
        return entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerSelectCurseTechniqueName;
    }

    public static double getTechniqueCost(Entity entity) {
        return entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerSelectCurseTechniqueCost;
    }

    public static void consumeCursePower(Entity entity, double cost) {
        double current = entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCursePower;
        entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(cap -> {
            cap.PlayerCursePower = current - cost;
            cap.syncPlayerVariables(entity);
        });
    }
}
