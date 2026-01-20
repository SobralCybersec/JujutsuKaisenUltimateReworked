package com.jujutsu.jujutsucraftaddon.command.misc;

import com.jujutsu.jujutsucraftaddon.procedures.*;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber
public class ChangeSubrace {
    @FunctionalInterface
    private interface SubraceProcedure {
        void execute(CommandContext<CommandSourceStack> arguments);
    }

    private static final Map<String, SubraceProcedure> SUBRACES = Map.of(
            "Tengen", SetSubRaceTengen::execute,
            "PerfectVessel", SetSubRaceVessel::execute,
            "DeathPainting", SetSubRaceDeathPainting::execute,
            "Hybrid", SetSubRaceHybrid::execute,
            "DisasterCurse", SetSubRaceDisasterCurse::execute
    );

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        var command = Commands.literal("jjkuChangeSubRace").requires(s -> s.hasPermission(2));

        SUBRACES.forEach((subrace, procedure) ->
                command.then(Commands.literal(subrace)
                        .then(Commands.argument("Player", EntityArgument.player())
                                .executes(arguments -> {
                                    procedure.execute(arguments);
                                    return 0;
                                })))
        );

        event.getDispatcher().register(command);
    }
}
