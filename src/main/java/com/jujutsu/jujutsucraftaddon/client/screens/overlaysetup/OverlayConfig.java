package com.jujutsu.jujutsucraftaddon.client.screens.overlaysetup;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.function.Predicate;

public record OverlayConfig(String id, ResourceLocation texture, Predicate<Player> displayCondition, int offsetX, int offsetY) {
    public static OverlayConfig barSetup(int index, Predicate<Player> condition) {
        return new OverlayConfig(
                "bar" + index,
                new ResourceLocation("jujutsucraftaddon:textures/screens/bar" + (index + 1) + ".png"),
                condition,
                -116,
                -2
        );
    }
}
