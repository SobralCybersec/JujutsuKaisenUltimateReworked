package com.jujutsu.jujutsucraftaddon.command.cursedenergy.blackflash;

import com.jujutsu.jujutsucraftaddon.command.setup.BaseCommand;
import com.jujutsu.jujutsucraftaddon.procedures.BFSetProcedure;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ChangeBlackFlashMastery extends BaseCommand<Double> {

    @Override
    protected String getName() { return "jjkuChangeBlackFlashMastery"; }

    @Override
    protected ArgumentType<Double> getArgumentType() { return DoubleArgumentType.doubleArg(); }

    @Override
    protected Class<java.lang.Double> getValueClass() { return java.lang.Double.class; }

    @Override
    protected void execute(CommandContext<CommandSourceStack> ctx, java.lang.Double value) {
        BFSetProcedure.execute(ctx);
    }

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        new ChangeBlackFlashMastery().register(event);
    }
}
