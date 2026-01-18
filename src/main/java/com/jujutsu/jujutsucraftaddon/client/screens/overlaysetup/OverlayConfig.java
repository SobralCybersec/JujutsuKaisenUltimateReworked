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

    public static OverlayConfig fullscreen(String id, String texturePath, Predicate<Player> condition) {
        return new OverlayConfig(
                id,
                new ResourceLocation("jujutsucraftaddon", texturePath),
                condition,
                0,
                0
        );
    }

    public static OverlayConfig brainStatus(String color, String translationKey, Predicate<Player> condition) {
        return new OverlayConfig(
                "brain_" + color,
                new ResourceLocation("jujutsucraftaddon", "textures/screens/" + color + ".png"),
                condition,
                -207,
                -8
        );
    }


}
