package com.jujutsu.jujutsucraftaddon.abilities.items.songoftime;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class StateRestorer {
    
    public static void restorePlayer(ServerPlayer player, PlayerStateRecord state) {
        teleportSmooth(player, new Vec3(state.x, state.y, state.z));
        
        player.removeAllEffects();
        state.effects.forEach(effect -> player.addEffect(new MobEffectInstance(effect)));
        
        player.getInventory().clearContent();
        for (int i = 0; i < state.inventory.size(); i++) {
            player.getInventory().setItem(i, state.inventory.get(i).copy());
        }
        
        player.setHealth(state.health);
        player.load(state.nbtData.copy());
    }

    public static void restoreEntity(Entity entity, PlayerStateRecord state) {
        if (!(entity instanceof LivingEntity living)) return;
        
        teleportSmooth(entity, new Vec3(state.x, state.y, state.z));
        
        living.removeAllEffects();
        state.effects.forEach(effect -> living.addEffect(new MobEffectInstance(effect)));
        
        living.setHealth(state.health);
        entity.load(state.nbtData.copy());
    }

    private static void teleportSmooth(Entity entity, Vec3 target) {
        Vec3 start = entity.position();
        for (int i = 0; i <= 20; i++) {
            Vec3 interpolated = start.lerp(target, i / 20.0);
            entity.setPos(interpolated.x, interpolated.y, interpolated.z);
        }
    }
}
