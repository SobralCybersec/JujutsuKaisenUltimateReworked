package com.jujutsu.jujutsucraftaddon.client.screens.negative.brokenbrain;

import com.jujutsu.jujutsucraftaddon.client.screens.overlaysetup.BaseBrainStatusOverlay;
import com.jujutsu.jujutsucraftaddon.client.screens.overlaysetup.OverlayConfig;
import com.jujutsu.jujutsucraftaddon.procedures.Overlay3Procedure;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
@Mod.EventBusSubscriber({Dist.CLIENT})
public class BrokenBrainStatus5 extends BaseBrainStatusOverlay {
    private static final OverlayConfig CONFIG = OverlayConfig.brainStatus(
            "redd",
            "gui.jujutsucraftaddon.overlay_braiin_5.label_braindamage",
            Overlay3Procedure::execute
    );

    @Override
    protected OverlayConfig getConfig() {
        return CONFIG;
    }

    @Override
    protected String getTranslationKey() {
        return "gui.jujutsucraftaddon.overlay_braiin_5.label_braindamage";
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void eventHandler(RenderGuiEvent.Pre event) {
        new BrokenBrainStatus5().render(event);
    }
}
