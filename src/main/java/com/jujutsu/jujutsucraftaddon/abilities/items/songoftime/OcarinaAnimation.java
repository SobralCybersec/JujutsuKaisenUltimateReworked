package com.jujutsu.jujutsucraftaddon.abilities.items.songoftime;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;

public class OcarinaAnimation {
    
    public static void play(ServerPlayer player) {
        player.swing(player.getUsedItemHand(), true);
        
        for (int i = 0; i < 20; i++) {
            player.level().addParticle(
                ParticleTypes.NOTE, 
                player.getX(), 
                player.getY() + 2.0, 
                player.getZ(), 
                Math.random(), 
                1.0, 
                Math.random()
            );
        }
    }
}
