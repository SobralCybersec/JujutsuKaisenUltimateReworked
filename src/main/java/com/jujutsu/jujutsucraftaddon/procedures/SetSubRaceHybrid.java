package com.jujutsu.jujutsucraftaddon.procedures;

import com.jujutsu.jujutsucraftaddon.network.JujutsucraftaddonModVariables;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;

public class SetSubRaceHybrid {
    public static void execute(CommandContext<CommandSourceStack> arguments) {
        try {
            Entity entity = EntityArgument.getEntity(arguments, "Player");
            entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                capability.IsJujutsuSorcerer = true;
                capability.IsCursedSpirit = true;
                capability.Subrace = "Hybrid";
                capability.syncPlayerVariables(entity);
            });
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
    }
}

