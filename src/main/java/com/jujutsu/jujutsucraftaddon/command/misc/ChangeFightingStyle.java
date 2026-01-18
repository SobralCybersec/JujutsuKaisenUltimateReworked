package com.jujutsu.jujutsucraftaddon.command.misc;

import com.jujutsu.jujutsucraftaddon.techniques.styles.*;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;

import java.util.Map;

@Mod.EventBusSubscriber
public class ChangeFightingStyle {
    @FunctionalInterface
    private interface StyleProcedure {
        void execute(Level world, CommandContext<CommandSourceStack> arguments);
    }

    private static final Map<String, StyleProcedure> STYLES = Map.of(
            "Maki", SetMakiStyle::execute,
            "Yuji", SetYujiStyle::execute,
            "Sukuna", SetSukunaStyle::execute,
            "Gojo", SetGojoStyle::execute,
            "Kashimo", SetKashimoStyle::execute,
            "Jogo", SetJogoStyle::execute,
            "YujiShinjuku", SetShinjukuYujiStyle::execute,
            "Normal", SetNormalStyle::execute
    );

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        var command = Commands.literal("jjkuFightingStyle").requires(s -> s.hasPermission(2));

        STYLES.forEach((style, procedure) ->
                command.then(Commands.literal(style)
                        .then(Commands.argument("Player", EntityArgument.player())
                                .executes(arguments -> {
                                    procedure.execute(arguments.getSource().getUnsidedLevel(), arguments);
                                    return 0;
                                })))
        );

        event.getDispatcher().register(command);
    }
}
