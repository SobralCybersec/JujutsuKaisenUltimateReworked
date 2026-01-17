package com.jujutsu.jujutsucraftaddon.abilities.techniques.cuttingslash;


import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Mod.EventBusSubscriber(modid = "jujutsucraftaddon")
public class CuttingSlashAbility {
    private static final List<CuttingSlashInstance.Instance> activeSlashes = new ArrayList<>();

    public static void useCuttingSlash(Player player) {
        if (player.level().isClientSide) return; // Server-side only

        Vec3 startPos = player.position().add(0, player.getEyeHeight(), 0);
        Vec3 direction = player.getLookAngle().normalize();

        activeSlashes.add(new CuttingSlashInstance.Instance(player, startPos, direction));
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Iterator<CuttingSlashInstance.Instance> iterator = activeSlashes.iterator();
            while (iterator.hasNext()) {
                CuttingSlashInstance.Instance instance = iterator.next();
                instance.tick();

                if (instance.isComplete()) {
                    iterator.remove();
                }
            }
        }
    }
}
