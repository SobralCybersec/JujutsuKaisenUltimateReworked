package com.jujutsu.jujutsucraftaddon.client.screens.overlays;

import com.jujutsu.jujutsucraftaddon.procedures.OverlayZeroUmDisplayOverlayIngameProcedure;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.jujutsu.jujutsucraftaddon.client.screens.overlaysetup.BaseOverlay;
import com.jujutsu.jujutsucraftaddon.client.screens.overlaysetup.OverlayConfig;

@Mod.EventBusSubscriber({Dist.CLIENT})
public class Kokusen1 extends BaseOverlay {
    private static final OverlayConfig CONFIG = OverlayConfig.fullscreen(
            "kokusen01",
            "textures/screens/kokusen01.png",
            player -> OverlayZeroUmDisplayOverlayIngameProcedure.execute(player.level(), player)
    );

    @Override
    protected OverlayConfig getConfig() {
        return CONFIG;
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void eventHandler(RenderGuiEvent.Pre event) {
        new Kokusen1().render(event);
    }
}
