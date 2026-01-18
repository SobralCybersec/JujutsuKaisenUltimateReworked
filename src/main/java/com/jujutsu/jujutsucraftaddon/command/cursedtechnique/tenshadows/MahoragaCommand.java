package com.jujutsu.jujutsucraftaddon.command.cursedtechnique.tenshadows;

import com.jujutsu.jujutsucraftaddon.procedures.MahoragaDisableProcedure;
import com.jujutsu.jujutsucraftaddon.procedures.MahoragaEnableProcedure;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;


import java.util.Map;

@Mod.EventBusSubscriber
public class MahoragaCommand {

    @FunctionalInterface
    private interface MahoragaProcedure {
        void execute(CommandContext<CommandSourceStack> arguments);
    }

    private static final Map<String, MahoragaProcedure> MAHORAGA_PROCEDURES = Map.of(
            "Enable", MahoragaEnableProcedure::execute,
            "Disable", MahoragaDisableProcedure::execute
    );

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        var baseCommand = Commands.literal("jjkuTenShadowsMahoraga").requires(s -> s.hasPermission(2));

        MAHORAGA_PROCEDURES.forEach((action, procedure) ->
                baseCommand.then(Commands.literal(action)
                        .then(Commands.argument("Player", EntityArgument.player())
                                .executes(ctx -> {
                                    procedure.execute(ctx);
                                    return 0;
                                })
                        )
                )
        );

        event.getDispatcher().register(baseCommand);
    }
}
