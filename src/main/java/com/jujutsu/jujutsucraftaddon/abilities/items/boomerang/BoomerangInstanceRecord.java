package com.jujutsu.jujutsucraftaddon.abilities.boomerang;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public record BoomerangInstanceRecord(Player player, ItemEntity boomerang, Vec3 velocity, int ticksTraveled, boolean returning) {
}
