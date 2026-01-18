package com.jujutsu.jujutsucraftaddon.command.misc;

import com.jujutsu.jujutsucraftaddon.command.cursedenergy.ChangeCursedEnergyFormer;
import com.jujutsu.jujutsucraftaddon.command.setup.BaseCommand;
import com.jujutsu.jujutsucraftaddon.procedures.ChangeCursePowerFormerBaseProcedure;
import com.jujutsu.jujutsucraftaddon.procedures.ProfessionLevelProcedure;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ChangeProfessionLevel extends BaseCommand<Double> {

    @Override
    protected String getName() { return "jjkuChangeProfessionLevel"; }

    @Override
    protected ArgumentType<java.lang.Double> getArgumentType() { return DoubleArgumentType.doubleArg(); }

    @Override
    protected Class<java.lang.Double> getValueClass() { return java.lang.Double.class; }

    @Override
    protected void execute(CommandContext<CommandSourceStack> ctx, java.lang.Double value) {
        ProfessionLevelProcedure.execute( ctx);
    }

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        new ChangeProfessionLevel().register(event);
    }
}
