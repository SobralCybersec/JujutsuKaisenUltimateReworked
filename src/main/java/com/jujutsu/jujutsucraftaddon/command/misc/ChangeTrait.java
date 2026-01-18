package com.jujutsu.jujutsucraftaddon.command.misc;

import com.jujutsu.jujutsucraftaddon.procedures.*;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import java.util.Map;

@Mod.EventBusSubscriber
public class ChangeTrait {

    @FunctionalInterface
    private interface TraitProcedure {
        void execute(CommandContext<CommandSourceStack> arguments);
    }

    private static final Map<String, TraitProcedure> TRAIT_PROCEDURES = Map.of(
            "TheHonoredOne", ChangeTraitHonoredOne::execute,
            "TheFallenOne", ChangeTraitTheFallenOne::execute,
            "Gifted", ChangeTraitGiftedProcedure::execute,
            "Cursed", ChangeTraitCursedProcedure::execute,
            "Speedy", ChangeTraitSpeedyProcedure::execute,
            "Strong", ChangeTraitStrongProcedure::execute,
            "Resistant", ChangeTraitResistantProcedure::execute,
            "Healthy", ChangeTraitHealthyProcedure::execute
    );

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        var baseCommand = Commands.literal("jjkuChangeTrait").requires(s -> s.hasPermission(2));

        TRAIT_PROCEDURES.forEach((traitName, procedure) ->
                baseCommand.then(Commands.literal(traitName)
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

