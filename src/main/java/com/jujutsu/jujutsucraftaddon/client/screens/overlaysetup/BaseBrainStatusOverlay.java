package com.jujutsu.jujutsucraftaddon.client.screens.overlaysetup;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGuiEvent;

public abstract class BaseBrainStatusOverlay {
    protected abstract OverlayConfig getConfig();
    protected abstract String getTranslationKey();

    protected void render(RenderGuiEvent.Pre event) {
        Player entity = Minecraft.getInstance().player;
        if (entity == null) return;

        OverlayConfig config = getConfig();
        if (!config.displayCondition().test(entity)) return;

        int w = event.getWindow().getGuiScaledWidth();
        int h = event.getWindow().getGuiScaledHeight();

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO);
        RenderSystem.setShaderColor(1, 1, 1, 1);

        event.getGuiGraphics().blit(config.texture(), w / 2 + config.offsetX(), h / 2 + config.offsetY(), 0, 0, 128, 128, 128, 128);
        event.getGuiGraphics().drawString(Minecraft.getInstance().font, Component.translatable(getTranslationKey()), w / 2 + -195, h / 2 + 52, -1, false);

        RenderSystem.depthMask(true);
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }
}
