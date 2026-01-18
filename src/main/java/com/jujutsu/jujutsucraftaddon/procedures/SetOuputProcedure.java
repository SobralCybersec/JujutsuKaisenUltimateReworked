package com.jujutsu.jujutsucraftaddon.procedures;

import com.jujutsu.jujutsucraftaddon.network.JujutsucraftaddonModVariables;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;

public class SetOuputProcedure {
    public static void execute(CommandContext<CommandSourceStack> arguments) {
        try {
            Entity player = EntityArgument.getEntity(arguments, "Player");
            double outputValue = DoubleArgumentType.getDouble(arguments, "Output");

            player.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                capability.Output = outputValue;
                capability.syncPlayerVariables(player);
            });
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
    }
}

