package com.jujutsu.jujutsucraftaddon.abilities.items.songoftime;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.Horse;

public class OtherSongs {
    
    public static void activateStorms(MinecraftServer server, ServerPlayer player) {
        OcarinaAnimation.play(player);
        server.overworld().setWeatherParameters(0, 6000, true, true);
    }

    public static void activateSnow(MinecraftServer server, ServerPlayer player) {
        OcarinaAnimation.play(player);
        server.overworld().setWeatherParameters(0, 6000, true, false);
    }

    public static void activateSun(MinecraftServer server, ServerPlayer player) {
        OcarinaAnimation.play(player);
        long time = server.overworld().getDayTime();
        server.overworld().setDayTime(time < 12000 ? 18000 : 0);
    }

    public static void activateHealing(ServerPlayer player) {
        OcarinaAnimation.play(player);
        player.setHealth(player.getMaxHealth());
        player.removeAllEffects();
    }

    public static void activateFire(ServerPlayer player) {
        OcarinaAnimation.play(player);
        player.setSecondsOnFire(5);
    }

    public static void activateWater(ServerPlayer player) {
        OcarinaAnimation.play(player);
        player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 6000, 1));
        player.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 6000, 1));
    }

    public static void activateShadow(ServerPlayer player) {
        OcarinaAnimation.play(player);
        player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 6000, 1));
    }

    public static void activateEpona(ServerPlayer player) {
        OcarinaAnimation.play(player);
        Horse horse = EntityType.HORSE.create(player.level());
        horse.setPos(player.getX(), player.getY(), player.getZ());
        horse.setTamed(true);
        horse.setOwnerUUID(player.getUUID());
        horse.setCustomName(player.getName());
        horse.setSpeed(0.3375F);
        player.level().addFreshEntity(horse);
        player.startRiding(horse);
    }
}
