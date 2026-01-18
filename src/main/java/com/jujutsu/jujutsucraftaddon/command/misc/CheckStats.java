package com.jujutsu.jujutsucraftaddon.command.misc;

import com.jujutsu.jujutsucraftaddon.procedures.CellphoneRightclickedProcedure;
import com.jujutsu.jujutsucraftaddon.procedures.StatusProcedure;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


import java.util.Map;
import java.util.function.Consumer;

@Mod.EventBusSubscriber
public class CheckStats {
    private static final Map<String, Consumer<Entity>> COMMANDS = Map.of(
            "jjkuCheckStats", CellphoneRightclickedProcedure::execute,
            "jjkuCheckStatsBase", StatusProcedure::execute
    );

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        COMMANDS.forEach((name, procedure) ->
                event.getDispatcher().register(Commands.literal(name)
                        .executes(arguments -> {
                            procedure.accept(arguments.getSource().getEntity());
                            return 0;
                        }))
        );
    }
}
