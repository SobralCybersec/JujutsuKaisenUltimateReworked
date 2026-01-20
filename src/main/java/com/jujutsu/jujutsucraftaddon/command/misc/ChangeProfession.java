package com.jujutsu.jujutsucraftaddon.command.misc;

import com.jujutsu.jujutsucraftaddon.procedures.SetHealerProcedure;
import com.jujutsu.jujutsucraftaddon.procedures.SetSageProcedure;
import com.jujutsu.jujutsucraftaddon.procedures.SetWarriorProcedure;
import com.jujutsu.jujutsucraftaddon.procedures.SetbmProcedure;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber
public class ChangeProfession {
    @FunctionalInterface
    private interface ProfessionProcedure {
        void execute(CommandContext<CommandSourceStack> arguments);
    }

    private static final Map<String, ProfessionProcedure> PROFESSIONS = Map.of(
            "Healer", SetHealerProcedure::execute,
            "Sage", SetSageProcedure::execute,
            "Warrior", SetWarriorProcedure::execute,
            "Blacksmith", SetbmProcedure::execute
    );

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        var command = Commands.literal("jjkuChangeProfession").requires(s -> s.hasPermission(2));

        PROFESSIONS.forEach((profession, procedure) ->
                command.then(Commands.literal(profession)
                        .then(Commands.argument("Player", EntityArgument.player())
                                .executes(arguments -> {
                                    procedure.execute(arguments);
                                    return 0;
                                })))
        );

        event.getDispatcher().register(command);
    }
}
