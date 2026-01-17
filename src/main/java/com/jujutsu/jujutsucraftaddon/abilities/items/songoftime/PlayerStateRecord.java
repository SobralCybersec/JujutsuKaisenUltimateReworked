package com.jujutsu.jujutsucraftaddon.abilities.items.songoftime;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerStateRecord {
    public final double x, y, z;
    public final float health;
    public final CompoundTag nbtData;
    public final List<MobEffectInstance> effects;
    public final List<ItemStack> inventory;

    public PlayerStateRecord(LivingEntity entity) {
        this.x = entity.getX();
        this.y = entity.getY();
        this.z = entity.getZ();
        this.health = entity.getHealth();
        
        this.nbtData = new CompoundTag();
        entity.save(nbtData);
        
        this.effects = entity.getActiveEffects().stream()
            .map(MobEffectInstance::new)
            .collect(Collectors.toList());
        
        this.inventory = entity instanceof Player p 
            ? p.getInventory().items.stream().map(ItemStack::copy).collect(Collectors.toList())
            : List.of();
    }
}
