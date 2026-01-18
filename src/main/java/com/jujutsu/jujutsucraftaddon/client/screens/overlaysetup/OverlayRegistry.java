package com.jujutsu.jujutsucraftaddon.client.screens.overlaysetup;

import com.jujutsu.jujutsucraftaddon.procedures.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class OverlayRegistry {
    public static final List<OverlayConfig> OVERLAYS = List.of(    OverlayConfig.barSetup(0, Bar0DisplayOverlayIngameProcedure::execute),
            OverlayConfig.barSetup(1, Bar1DisplayOverlayIngameProcedure::execute),
            OverlayConfig.barSetup(2, Bar2DisplayOverlayIngameProcedure::execute),
            OverlayConfig.barSetup(3, Bar3DisplayOverlayIngameProcedure::execute),
            OverlayConfig.barSetup(4, Bar4DisplayOverlayIngameProcedure::execute),
            OverlayConfig.barSetup(5, Bar5DisplayOverlayIngameProcedure::execute)
    );

    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Pre event) {
        OVERLAYS.forEach(config -> OverlayRenderer.render(event, config));
    }

}
