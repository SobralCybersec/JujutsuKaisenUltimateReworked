package com.jujutsu.jujutsucraftaddon.command.misc;

import com.jujutsu.jujutsucraftaddon.procedures.ChangeRaceCurseUser;
import com.jujutsu.jujutsucraftaddon.procedures.ChangeRaceCursedSpirit;
import com.jujutsu.jujutsucraftaddon.procedures.ChangeRaceJujutsuSorcerer;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber
public class ChangeRace {

    @FunctionalInterface
    private interface RaceProcedure {
        void execute(LevelAccessor world, CommandContext<CommandSourceStack> arguments);
    }

    private static final Map<String, RaceProcedure> RACE_PROCEDURES = Map.of(
            "CursedSpirit", ChangeRaceCursedSpirit::execute,
            "JujutsuSorcerer", ChangeRaceJujutsuSorcerer::execute,
            "CurseUser", ChangeRaceCurseUser::execute
    );

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        var baseCommand = Commands.literal("jjkuChangeRace").requires(s -> s.hasPermission(2));

        RACE_PROCEDURES.forEach((raceName, procedure) ->
                baseCommand.then(Commands.literal(raceName)
                        .then(Commands.argument("Player", EntityArgument.player())
                                .executes(ctx -> {
                                    procedure.execute(ctx.getSource().getUnsidedLevel(), ctx);
                                    return 0;
                                })
                        )
                )
        );

        event.getDispatcher().register(baseCommand);
    }
}
