package com.jujutsu.jujutsucraftaddon.procedures;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class SetKurourushiProcedure {
    public static void execute(LevelAccessor world, CommandContext<CommandSourceStack> arguments) {
        try {
            Entity player = EntityArgument.getEntity(arguments, "Player");
            player.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                capability.PlayerCurseTechnique2 = 23;
                capability.PlayerCurseTechnique = 23;
                capability.syncPlayerVariables(player);
            });
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
    }
}
