package com.jujutsu.jujutsucraftaddon.client.screens.kokusen;

import com.jujutsu.jujutsucraftaddon.client.screens.overlaysetup.BaseOverlay;
import com.jujutsu.jujutsucraftaddon.client.screens.overlaysetup.OverlayConfig;
import com.jujutsu.jujutsucraftaddon.procedures.ImpactFrameOverlayCondition5;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber({Dist.CLIENT})
public class ImpactFrameKokusen5 extends BaseOverlay {
    private static final OverlayConfig CONFIG = OverlayConfig.fullscreen(
            "kokusen08",
            "textures/screens/kokusen08.png",
            player -> ImpactFrameOverlayCondition5.execute(player.level(), player)
    );

    @Override
    protected OverlayConfig getConfig() {
        return CONFIG;
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void eventHandler(RenderGuiEvent.Pre event) {
        new ImpactFrameKokusen5().render(event);
    }
}