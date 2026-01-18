package com.jujutsu.jujutsucraftaddon.command.cursedtechnique;

import com.jujutsu.jujutsucraftaddon.procedures.TPLocationProcedure;
import com.jujutsu.jujutsucraftaddon.procedures.TPPlayerProcedure;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber
public class TechniqueTeleport {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("jjkuTeleport")
                .then(Commands.literal("Location")
                        .then(Commands.argument("X", DoubleArgumentType.doubleArg())
                                .then(Commands.argument("Y", DoubleArgumentType.doubleArg())
                                        .then(Commands.argument("Z", DoubleArgumentType.doubleArg())
                                                .executes(arguments -> {
                                                    var source = arguments.getSource();
                                                    TPLocationProcedure.execute(source.getUnsidedLevel(), arguments, source.getEntity());
                                                    return 0;
                                                })))))
                .then(Commands.literal("Player")
                        .then(Commands.argument("Player", EntityArgument.player())
                                .executes(arguments -> {
                                    var source = arguments.getSource();
                                    TPPlayerProcedure.execute(source.getUnsidedLevel(), arguments, source.getEntity());
                                    return 0;
                                }))));
    }
}
