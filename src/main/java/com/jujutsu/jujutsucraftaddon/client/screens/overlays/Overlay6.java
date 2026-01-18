package com.jujutsu.jujutsucraftaddon.client.screens.overlays;

import com.jujutsu.jujutsucraftaddon.procedures.OverlaySixDisplayOverlayIngameProcedure;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


import com.jujutsu.jujutsucraftaddon.client.screens.overlaysetup.BaseOverlay;
import com.jujutsu.jujutsucraftaddon.client.screens.overlaysetup.OverlayConfig;


@Mod.EventBusSubscriber({Dist.CLIENT})
public class Overlay6 extends BaseOverlay {
    private static final OverlayConfig CONFIG = OverlayConfig.fullscreen(
            "kokusen6",
            "textures/screens/kokusen6.png",
            player -> OverlaySixDisplayOverlayIngameProcedure.execute(player.level(), player)
    );

    @Override
    protected OverlayConfig getConfig() {
        return CONFIG;
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void eventHandler(RenderGuiEvent.Pre event) {
        new Overlay6().render(event);
    }
}
