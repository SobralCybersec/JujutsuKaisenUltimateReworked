package com.jujutsu.jujutsucraftaddon.command.pvp;

import com.jujutsu.jujutsucraftaddon.procedures.SetPVPBase2Procedure;
import com.jujutsu.jujutsucraftaddon.procedures.SetPVPBaseProcedure;
import net.minecraft.commands.Commands;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.util.Map;

@Mod.EventBusSubscriber
public class PVPBases {
    @FunctionalInterface
    private interface BaseProcedure {
        void execute(Level world, net.minecraft.world.entity.Entity entity);
    }

    private static final Map<String, BaseProcedure> BASES = Map.of(
            "Team1", SetPVPBaseProcedure::execute,
            "Team2", SetPVPBase2Procedure::execute
    );

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        var command = Commands.literal("jjkuPVPSetSpawnTeam").requires(s -> s.hasPermission(2));

        BASES.forEach((team, procedure) ->
                command.then(Commands.literal(team).executes(arguments -> {
                    procedure.execute(arguments.getSource().getUnsidedLevel(), arguments.getSource().getEntity());
                    return 0;
                }))
        );

        event.getDispatcher().register(command);
    }
}

