package com.jujutsu.jujutsucraftaddon.procedures;

import com.jujutsu.jujutsucraftaddon.init.mod.JujutsucraftaddonModItems;
import com.jujutsu.jujutsucraftaddon.network.JujutsucraftaddonModVariables;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;

import net.minecraft.world.item.Item;

import java.util.List;

public class ClanWukongProcedure {

    private static final List<Item> WUKONG_ITEMS = List.of(
            JujutsucraftaddonModItems.WUKONG_STAFF_TRUE.get(),
            JujutsucraftaddonModItems.HALBERD_WUKONG.get(),
            JujutsucraftaddonModItems.GREAT_SWORD_WUKONG.get(),
            JujutsucraftaddonModItems.SWORD_WUKONG.get(),
            JujutsucraftaddonModItems.HAMMER_WUKONG.get(),
            JujutsucraftaddonModItems.WUKONG_HAT.get(),
            JujutsucraftaddonModItems.WUKONG_SET_CHESTPLATE.get(),
            JujutsucraftaddonModItems.WUKONG_SET_LEGGINGS.get(),
            JujutsucraftaddonModItems.WUKONG_SET_BOOTS.get()
    );

    public static void execute(CommandContext<CommandSourceStack> arguments) {
        try {
            Entity player = EntityArgument.getEntity(arguments, "Player");

            player.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                capability.Clans = "Wukong";
                capability.syncPlayerVariables(player);
            });

            if (player instanceof Player _player) {
                WUKONG_ITEMS.forEach(item ->
                        ItemHandlerHelper.giveItemToPlayer(_player, new ItemStack(item))
                );
            }
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
    }
}

