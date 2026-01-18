package com.jujutsu.jujutsucraftaddon.command.setup;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraftforge.event.RegisterCommandsEvent;

public abstract class BaseCommand<T> {

    protected abstract String getName();
    protected abstract ArgumentType<T> getArgumentType();
    protected abstract void execute(CommandContext<CommandSourceStack> context, T value);

    public void register(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal(getName())
                        .requires(s -> s.hasPermission(2))
                        .then(Commands.argument("value", getArgumentType())
                                .then(Commands.argument("player", EntityArgument.player())
                                        .executes(ctx -> {
                                            execute(ctx, ctx.getArgument("value", getValueClass()));
                                            return 0;
                                        })
                                )
                        )
        );
    }

    protected abstract Class<T> getValueClass();
}

