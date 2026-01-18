package com.jujutsu.jujutsucraftaddon.procedures;

import com.jujutsu.jujutsucraftaddon.network.JujutsucraftaddonModVariables;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.mcreator.jujutsucraft.init.JujutsucraftModItems;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.List;

public class ClanMajimaProcedure {
    private static final List<Item> MAJIMA_ITEMS = List.of(
            JujutsucraftModItems.KNIFE.get()
    );

    public static void execute(CommandContext<CommandSourceStack> arguments) {
        try {
            Entity player = EntityArgument.getEntity(arguments, "Player");

            player.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                capability.Clans = "Majima";
                capability.syncPlayerVariables(player);
            });

            if (player instanceof Player _player) {
                MAJIMA_ITEMS.forEach(item ->
                        ItemHandlerHelper.giveItemToPlayer(_player, new ItemStack(item))
                );
            }

        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
    }
}
