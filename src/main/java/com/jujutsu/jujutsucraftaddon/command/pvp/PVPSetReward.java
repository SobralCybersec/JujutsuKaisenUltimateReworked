package com.jujutsu.jujutsucraftaddon.command.pvp;

import com.jujutsu.jujutsucraftaddon.procedures.*;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber
public class PVPSetReward {

    @FunctionalInterface
    private interface RewardProcedure {
        void execute(Level world, CommandContext<CommandSourceStack> arguments, Entity entity);
    }

    private static final Map<String, RewardProcedure> REWARD_PROCEDURES = Map.of(
            "Reward1", PVPRewardProcedure::execute,
            "Reward2", PVPReward2Procedure::execute,
            "Reward3", PVPReward3Procedure::execute,
            "Reward4", PVPReward4Procedure::execute,
            "Reward5", PVPReward5Procedure::execute
    );

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        var baseCommand = Commands.literal("jjkuSetPVPReward").requires(s -> s.hasPermission(2));

        REWARD_PROCEDURES.forEach((rewardName, procedure) ->
                baseCommand.then(Commands.literal(rewardName)
                        .then(Commands.argument("Rate", DoubleArgumentType.doubleArg())
                                .then(Commands.argument("Amount", DoubleArgumentType.doubleArg())
                                        .executes(ctx -> {
                                            procedure.execute(ctx.getSource().getUnsidedLevel(), ctx, ctx.getSource().getEntity());
                                            return 0;
                                        })
                                )
                        )
                )
        );

        event.getDispatcher().register(baseCommand);
    }
}
