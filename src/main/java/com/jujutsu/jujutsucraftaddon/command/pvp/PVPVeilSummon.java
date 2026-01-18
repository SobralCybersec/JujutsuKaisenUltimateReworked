package com.jujutsu.jujutsucraftaddon.command.pvp;

import com.jujutsu.jujutsucraftaddon.procedures.VeilSpawnedProcedure;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.minecraft.commands.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PVPVeilSummon {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("jjkuPVPVeilSummon")
                .then(Commands.argument("VeilSize", DoubleArgumentType.doubleArg())
                        .then(Commands.argument("VeilDurationInMinutes", DoubleArgumentType.doubleArg())
                                .then(Commands.argument("LetPeoplePass", BoolArgumentType.bool())
                                        .executes(arguments -> {
                                            var source = arguments.getSource();
                                            var pos = source.getPosition();
                                            VeilSpawnedProcedure.execute(
                                                    source.getUnsidedLevel(),
                                                    pos.x(), pos.y(), pos.z(),
                                                    arguments,
                                                    source.getEntity()
                                            );
                                            return 0;
                                        })))));
    }
}
