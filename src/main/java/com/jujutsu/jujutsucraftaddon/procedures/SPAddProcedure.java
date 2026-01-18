package com.jujutsu.jujutsucraftaddon.procedures;

import com.jujutsu.jujutsucraftaddon.network.JujutsucraftaddonModVariables;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;


public class SPAddProcedure {
    public static void execute(CommandContext<CommandSourceStack> arguments, Entity entity) {
        if (entity == null) return;

        try {
            Entity targetPlayer = EntityArgument.getEntity(arguments, "Player");
            double pointsToAdd = DoubleArgumentType.getDouble(arguments, "Points");

            targetPlayer.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                capability.sp += pointsToAdd;
                capability.syncPlayerVariables(targetPlayer);
            });

            if (entity instanceof Player player && !player.level().isClientSide()) {
                player.displayClientMessage(
                        Component.literal("Added " + pointsToAdd + " Points To: " + targetPlayer.getDisplayName().getString()),
                        false
                );
            }
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
    }
}
