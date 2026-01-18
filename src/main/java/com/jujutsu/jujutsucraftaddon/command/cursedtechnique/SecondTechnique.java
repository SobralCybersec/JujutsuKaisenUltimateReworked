package com.jujutsu.jujutsucraftaddon.command.cursedtechnique;

import com.jujutsu.jujutsucraftaddon.command.setup.BaseCommand;
import com.jujutsu.jujutsucraftaddon.procedures.TechniqueRandomizerProcedure;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class SecondTechnique extends BaseCommand<Double> {
    @Override
    protected String getName () {
        return "jjkuSecondTechnique";
    }


    @Override
    protected ArgumentType<Double> getArgumentType () {
        return DoubleArgumentType.doubleArg();
    }

    @Override
    protected Class<Double> getValueClass () {
        return Double.class;
    }

    @Override
    protected void execute (CommandContext< CommandSourceStack > ctx, Double value) {
        TechniqueRandomizerProcedure.execute(ctx);
    }

    @SubscribeEvent
    public static void registerCommand (RegisterCommandsEvent event) {
           new SecondTechnique().register(event);
    }
}
