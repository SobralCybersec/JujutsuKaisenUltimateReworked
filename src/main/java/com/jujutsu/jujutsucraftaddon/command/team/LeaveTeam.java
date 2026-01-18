package com.jujutsu.jujutsucraftaddon.command.team;

import com.jujutsu.jujutsucraftaddon.command.setup.BaseCommand;
import com.jujutsu.jujutsucraftaddon.procedures.IniviteTeamProcedure;
import com.jujutsu.jujutsucraftaddon.procedures.LeaveTeamProcedure;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class LeaveTeam extends BaseCommand<Double> {

    @Override
    protected String getName () {
        return "jjkuTeamLeavePlayer";
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
    protected void execute (CommandContext<CommandSourceStack> ctx, Double value){
        LeaveTeamProcedure.execute(ctx.getSource().getUnsidedLevel(), ctx.getSource().getEntity());
    }

    @SubscribeEvent
    public static void registerCommand (RegisterCommandsEvent event){
        new LeaveTeam().register(event);
    }
}
