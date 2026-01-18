package com.jujutsu.jujutsucraftaddon.misc.mechanics;

import com.jujutsu.jujutsucraftaddon.init.mod.JujutsucraftaddonModKeyMappings;
import com.jujutsu.jujutsucraftaddon.procedures.LockOnHandler;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class MovementLockOnBaseMechanic {

    @SubscribeEvent
    public static void onInput(InputEvent event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        KeyMapping keyMapping = JujutsucraftaddonModKeyMappings.Z_TARGET_KEY;  // Assuming you have a key mapping for this

        if (keyMapping.isDown()) {
            LockOnHandler.handleLockOn(player);
        }

    }
}
