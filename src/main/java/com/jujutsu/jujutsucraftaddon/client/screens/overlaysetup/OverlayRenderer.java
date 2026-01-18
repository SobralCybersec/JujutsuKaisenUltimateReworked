package com.jujutsu.jujutsucraftaddon.client.screens.overlaysetup;

import com.jujutsu.jujutsucraftaddon.network.JujutsucraftaddonModVariables;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGuiEvent;

public class OverlayRenderer {
    public static void render(RenderGuiEvent.Pre event, OverlayConfig config){
        Player player = Minecraft.getInstance().player;
        if (player == null || !shouldDisplay(player, config)) return;

        int w = event.getWindow().getGuiScaledWidth();
        int h = event.getWindow().getGuiScaledHeight();

        setupRenderState();
        event.getGuiGraphics().blit(config.texture(),
                w + config.offsetX(), h / 2 + config.offsetY(),
                0, 0, 101, 101, 101, 101);
        resetRenderState();
    }

    private static boolean shouldDisplay(Player player, OverlayConfig config) {
        return config.displayCondition().test(player) &&
                player.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                        .orElse(new JujutsucraftaddonModVariables.PlayerVariables()).bar;
    }

    private static void setupRenderState() {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    private static void resetRenderState() {
        RenderSystem.depthMask(true);
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }
}
