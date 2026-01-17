package com.jujutsu.jujutsucraftaddon.abilities.items.songoftime;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "jujutsucraftaddon")
public class SongOfTimeAbility {
    private static final int SAVE_INTERVAL = 200; // Every 10 seconds

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.getServer().getTickCount() % SAVE_INTERVAL == 0) {
            StateManager.saveState(event.getServer());
        }
    }

    public static void activateSongOfTime(MinecraftServer server, ServerPlayer player) {
        SongOfTime.activate(server, player);
    }

    public static void activateSongOfStorms(MinecraftServer server, ServerPlayer player) {
        OtherSongs.activateStorms(server, player);
    }

    public static void activateSongOfSnow(MinecraftServer server, ServerPlayer player) {
        OtherSongs.activateSnow(server, player);
    }

    public static void activateSunsSong(MinecraftServer server, ServerPlayer player) {
        OtherSongs.activateSun(server, player);
    }

    public static void activateSongOfHealing(ServerPlayer player) {
        OtherSongs.activateHealing(player);
    }

    public static void activateBoleroOfFire(ServerPlayer player) {
        OtherSongs.activateFire(player);
    }

    public static void activateSerenadeOfWater(ServerPlayer player) {
        OtherSongs.activateWater(player);
    }

    public static void activateNocturneOfShadow(ServerPlayer player) {
        OtherSongs.activateShadow(player);
    }

    public static void activateEponasSong(ServerPlayer player) {
        OtherSongs.activateEpona(player);
    }
}
