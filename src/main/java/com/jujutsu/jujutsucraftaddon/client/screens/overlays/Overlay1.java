package com.jujutsu.jujutsucraftaddon.client.screens.overlays;

import com.jujutsu.jujutsucraftaddon.procedures.OverlayOneDisplayOverlayIngameProcedure;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.jujutsu.jujutsucraftaddon.client.screens.overlaysetup.BaseOverlay;
import com.jujutsu.jujutsucraftaddon.client.screens.overlaysetup.OverlayConfig;


@Mod.EventBusSubscriber({Dist.CLIENT})
public class Overlay1 extends BaseOverlay {
    private static final OverlayConfig CONFIG = OverlayConfig.fullscreen(
            "kokusen1",
            "textures/screens/kokusen1.png",
            player -> OverlayOneDisplayOverlayIngameProcedure.execute(player.level(), player)
    );

    @Override
    protected OverlayConfig getConfig() {
        return CONFIG;
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void eventHandler(RenderGuiEvent.Pre event) {
        new Overlay1().render(event);
    }
}
