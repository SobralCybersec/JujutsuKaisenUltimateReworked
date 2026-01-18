package com.jujutsu.jujutsucraftaddon.techniques.hr;

import com.jujutsu.jujutsucraftaddon.mixins.OptionsAccessor;
import com.jujutsu.jujutsucraftaddon.network.JujutsucraftaddonModVariables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = "jujutsucraftaddon", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public abstract class TojiHRClassRenderer {

    private static final Set<LivingEntity> glowSet = new HashSet<>();

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void eventHandler(RenderGuiOverlayEvent event) {
        if (!event.isCancelable()) {
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            if (player == null) return;
            int color = 0xFFFF0000; // white
            int sWidth = event.getWindow().getWidth();
            int sHeight = event.getWindow().getHeight();
            GuiGraphics guiGraphics = event.getGuiGraphics();

            OptionsAccessor accessor = (OptionsAccessor) Minecraft.getInstance().options;
            if (player.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables()).BGM) {
                guiGraphics.fill(sWidth / 2 - 5, sHeight / 2, sWidth / 2 + 5, sHeight / 2 + 1, color);
                guiGraphics.fill(sWidth / 2, sHeight / 2 - 5, sWidth / 2 + 1, sHeight / 2 + 5, color);
                mc.level.getEntitiesOfClass(LivingEntity.class,
                        player.getBoundingBox().inflate(accessor.getRenderDistance().get() * 16),
                        e -> e != player
                ).forEach(e -> {
                    if (!e.isCurrentlyGlowing()) {
                        e.getPersistentData().putBoolean("glowing", true);
                        glowSet.add(e);
                    }
                });
            }
        }
    }
}
