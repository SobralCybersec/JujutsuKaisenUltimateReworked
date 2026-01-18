package com.jujutsu.jujutsucraftaddon.command.team;


import com.jujutsu.jujutsucraftaddon.procedures.JjkuCreateProcedure;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.Commands;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CreateTeam {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("jjkuTeamCreatePlayer")
                .then(Commands.argument("teamName", StringArgumentType.word())
                        .executes(arguments -> {
                            Level world = arguments.getSource().getUnsidedLevel();
                            JjkuCreateProcedure.execute(world, arguments, arguments.getSource().getEntity());
                            return 0;
                        })));
    }
}
