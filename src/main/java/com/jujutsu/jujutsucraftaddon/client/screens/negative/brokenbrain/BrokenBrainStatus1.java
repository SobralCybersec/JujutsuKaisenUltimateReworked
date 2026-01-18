package com.jujutsu.jujutsucraftaddon.client.screens.negative.brokenbrain;

import com.jujutsu.jujutsucraftaddon.client.screens.overlaysetup.BaseBrainStatusOverlay;
import com.jujutsu.jujutsucraftaddon.client.screens.overlaysetup.OverlayConfig;
import com.jujutsu.jujutsucraftaddon.procedures.OverlayBrainDamageDisplayOverlayIngameProcedure;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;



@Mod.EventBusSubscriber({Dist.CLIENT})
public class BrokenBrainStatus1 extends BaseBrainStatusOverlay {
    private static final OverlayConfig CONFIG = OverlayConfig.brainStatus(
            "green",
            "gui.jujutsucraftaddon.overlay_um.label_braindamage",
            OverlayBrainDamageDisplayOverlayIngameProcedure::execute
    );

    @Override
    protected OverlayConfig getConfig() {
        return CONFIG;
    }

    @Override
    protected String getTranslationKey() {
        return "gui.jujutsucraftaddon.overlay_um.label_braindamage";
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void eventHandler(RenderGuiEvent.Pre event) {
        new BrokenBrainStatus1().render(event);
    }
}
