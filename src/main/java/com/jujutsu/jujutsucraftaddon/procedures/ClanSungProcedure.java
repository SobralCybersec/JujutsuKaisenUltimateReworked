package com.jujutsu.jujutsucraftaddon.procedures;

import com.jujutsu.jujutsucraftaddon.init.mod.JujutsucraftaddonModItems;
import com.jujutsu.jujutsucraftaddon.network.JujutsucraftaddonModVariables;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.List;

public class ClanSungProcedure {

    private static final List<Item> SUNG_ITEMS = List.of(
            JujutsucraftaddonModItems.SWORD_JIN_WOO.get(),
            JujutsucraftaddonModItems.SWORD_JIN_WOO_TWO.get(),
            JujutsucraftaddonModItems.SUNG_JIN_WOO_CHESTPLATE.get(),
            JujutsucraftaddonModItems.SUNG_JIN_WOO_LEGGINGS.get()
    );

    public static void execute(CommandContext<CommandSourceStack> arguments) {
        try {
            Entity player = EntityArgument.getEntity(arguments, "Player");

            player.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                capability.Clans = "Sung";
                capability.syncPlayerVariables(player);
            });

            player.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                capability.ShadowName = "";
                capability.syncPlayerVariables(player);
            });

            player.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                capability.Shadow = "";
                capability.syncPlayerVariables(player);
            });

            if (player instanceof Player _player) {
                SUNG_ITEMS.forEach(item ->
                        ItemHandlerHelper.giveItemToPlayer(_player, new ItemStack(item))
                );
            }

        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
    }
}
