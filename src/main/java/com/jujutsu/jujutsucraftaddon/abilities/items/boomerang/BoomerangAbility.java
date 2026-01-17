package com.jujutsu.jujutsucraftaddon.abilities.boomerang;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = "jujutsucraftaddon")
public class BoomerangAbility {
    public static final List<BoomerangTick> activeBoomerangs = new ArrayList<>();

    public static void addBoomerang(BoomerangInstanceRecord record) {
        activeBoomerangs.add(new BoomerangTick(record));
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            activeBoomerangs.removeIf(b -> { b.tick(); return b.isComplete(); });
        }
    }

    public static void throwBoomerang(Player player) {
        ThrowBoomerang.throwBoomerang(player);
    }

}
