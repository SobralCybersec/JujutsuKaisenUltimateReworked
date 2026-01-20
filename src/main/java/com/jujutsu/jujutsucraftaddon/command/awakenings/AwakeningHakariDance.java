package com.jujutsu.jujutsucraftaddon.command.awakenings;

import com.jujutsu.jujutsucraftaddon.command.setup.BaseCommand;
import com.jujutsu.jujutsucraftaddon.procedures.HakariDanceProcedure;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AwakeningHakariDance extends BaseCommand<Double> {

    @Override
    protected String getName() { return "jjkuAwakeningHakari"; }

    @Override
    protected ArgumentType<java.lang.Double> getArgumentType() { return DoubleArgumentType.doubleArg(); }

    @Override
    protected Class<java.lang.Double> getValueClass() { return java.lang.Double.class; }

    @Override
    protected void execute(CommandContext<CommandSourceStack> ctx, java.lang.Double value) {
        HakariDanceProcedure.execute(ctx.getSource().getEntity());
    }

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        new AwakeningHakariDance().register(event);
    }
}
