package com.jujutsu.jujutsucraftaddon.abilities.items.boomerang;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class ThrowBoomerang {
    private static final double SPEED = 1.5;

    public static void throwBoomerang(Player player) {
        if (player.level().isClientSide || player.getMainHandItem().isEmpty()) return;

        ServerLevel level = (ServerLevel) player.level();
        Vec3 startPos = player.position().add(0, player.getEyeHeight(), 0);
        Vec3 direction = player.getLookAngle().normalize().scale(SPEED);

        ItemEntity boomerang = new ItemEntity(level, startPos.x, startPos.y, startPos.z,
                player.getMainHandItem().copy());
        boomerang.setNoPickUpDelay();
        boomerang.setDeltaMovement(direction);
        level.addFreshEntity(boomerang);

        player.setItemInHand(player.getUsedItemHand(), ItemStack.EMPTY);

        BoomerangAbility.addBoomerang(new BoomerangInstanceRecord(player, boomerang, direction, 0, false));
    }
}
