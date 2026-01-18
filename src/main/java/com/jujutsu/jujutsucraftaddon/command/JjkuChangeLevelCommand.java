package com.jujutsu.jujutsucraftaddon.command;

import com.jujutsu.jujutsucraftaddon.command.setup.BaseCommand;
import com.jujutsu.jujutsucraftaddon.procedures.ChangeLevelProcedure;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;

@Mod.EventBusSubscriber
public class JjkuChangeLevelCommand extends BaseCommand<Double> {

    @Override
    protected String getName() { return "jjkuChangePlayerLevel"; }

    @Override
    protected ArgumentType<Double> getArgumentType() { return DoubleArgumentType.doubleArg(); }

    @Override
    protected Class<Double> getValueClass() { return Double.class; }

    @Override
    protected void execute(CommandContext<CommandSourceStack> ctx, Double value) {
        ChangeLevelProcedure.execute(ctx.getSource().getUnsidedLevel(), ctx);
    }

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        new JjkuChangeLevelCommand().register(event);
    }
}

