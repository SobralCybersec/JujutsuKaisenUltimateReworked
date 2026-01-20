package com.jujutsu.jujutsucraftaddon.command.pvp;

import com.jujutsu.jujutsucraftaddon.procedures.DisableProcedure;
import com.jujutsu.jujutsucraftaddon.procedures.EnableProcedure;
import com.jujutsu.jujutsucraftaddon.procedures.PVPFlagProcedure;
import com.jujutsu.jujutsucraftaddon.procedures.PVPProcedure;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber
public class PVPMaster {

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("jjkuPVP")
                        .then(Commands.literal("Battle")
                                .then(Commands.argument("Team", BoolArgumentType.bool())
                                        .then(Commands.argument("Player", EntityArgument.player())
                                                .executes(ctx -> {
                                                    PVPProcedure.execute(ctx.getSource().getUnsidedLevel(), ctx, ctx.getSource().getEntity());
                                                    return 0;
                                                })
                                        )
                                )
                        )
                        .then(Commands.literal("Flag")
                                .then(Commands.argument("Team", BoolArgumentType.bool())
                                        .then(Commands.argument("Player", EntityArgument.player())
                                                .executes(ctx -> {
                                                    PVPFlagProcedure.execute(ctx.getSource().getUnsidedLevel(), ctx, ctx.getSource().getEntity());
                                                    return 0;
                                                })
                                        )
                                )
                        )
                        .then(Commands.literal("Enable")
                                .executes(ctx -> {
                                    EnableProcedure.execute(ctx.getSource().getEntity());
                                    return 0;
                                })
                        )
                        .then(Commands.literal("Disable")
                                .executes(ctx -> {
                                    DisableProcedure.execute(ctx.getSource().getEntity());
                                    return 0;
                                })
                        )
        );
    }
}
