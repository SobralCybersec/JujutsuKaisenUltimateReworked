package com.jujutsu.jujutsucraftaddon.services;

import com.jujutsu.jujutsucraftaddon.JujutsucraftaddonMod;
import com.jujutsu.jujutsucraftaddon.network.JujutsucraftaddonModVariables;
import com.jujutsu.jujutsucraftaddon.network.PacketEffects;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;

public final class CombatStyleService {

    private static final String ANIMATION_KEY = "jujutsucraftaddon:player_animation";
    private static final float SOUND_EFFECT_CHANCE = 0.2f;
    private static final float SPECIAL_MOVE_CHANCE_RARE = 0.05f;
    private static final float SPECIAL_MOVE_CHANCE_UNCOMMON = 0.067f;

    private CombatStyleService() {
    }

    public static void handleBasicStyle(Entity entity, AbstractClientPlayer player, double currentIndex) {
        int nextIndex = getNextIndex((int) currentIndex, 4);
        updateAttackAnimation(entity, nextIndex);
        playAnimation(player, "basicstyle" + nextIndex);
        sendEffectPackets(entity, SOUND_EFFECT_CHANCE);
    }

    public static void handleGojoStyle(Entity entity, AbstractClientPlayer player, double currentIndex) {
        int nextIndex = getNextIndex((int) currentIndex, 5);
        
        if (Math.random() < SPECIAL_MOVE_CHANCE_RARE) {
            playAnimation(player, "gojostylebarrage");
            JujutsucraftaddonMod.PACKET_HANDLER.sendToServer(new PacketEffects(9, entity.getUUID()));
            JujutsucraftaddonMod.PACKET_HANDLER.sendToServer(new PacketEffects(0, entity.getUUID()));
            return;
        }
        
        updateAttackAnimation(entity, nextIndex);
        playAnimation(player, "gojostyle" + nextIndex);
        sendEffectPackets(entity, SOUND_EFFECT_CHANCE);
    }

    public static void handleYujiStyle(Entity entity, AbstractClientPlayer player, double currentIndex) {
        int nextIndex = getNextIndex((int) currentIndex, 6);
        
        if (Math.random() < SPECIAL_MOVE_CHANCE_RARE) {
            playAnimation(player, "yujistylecombo");
            sendSpecialEffects(entity);
            return;
        }
        
        if (Math.random() < SPECIAL_MOVE_CHANCE_UNCOMMON) {
            playAnimation(player, "yujistylemanjikick");
            sendSpecialEffects(entity);
            return;
        }
        
        updateAttackAnimation(entity, nextIndex);
        playAnimation(player, "yujistyle" + nextIndex);
        sendEffectPackets(entity, SOUND_EFFECT_CHANCE);
    }

    public static void handleKashimoStyle(Entity entity, AbstractClientPlayer player, double currentIndex) {
        int nextIndex = getNextIndex((int) currentIndex, 5);
        updateAttackAnimation(entity, nextIndex);
        playAnimation(player, "kashimo" + nextIndex);
        sendEffectPackets(entity, SOUND_EFFECT_CHANCE);
    }

    public static void handleJogoStyle(Entity entity, AbstractClientPlayer player, double currentIndex) {
        int nextIndex = getNextIndex((int) currentIndex, 5);
        updateAttackAnimation(entity, nextIndex);
        playAnimation(player, "jogo" + nextIndex);
        sendEffectPackets(entity, SOUND_EFFECT_CHANCE);
    }

    public static void handleMakiStyle(Entity entity, AbstractClientPlayer player, double currentIndex) {
        int nextIndex = getNextIndex((int) currentIndex, 5);
        
        if (Math.random() < SPECIAL_MOVE_CHANCE_RARE) {
            JujutsucraftaddonMod.PACKET_HANDLER.sendToServer(new PacketEffects(5, entity.getUUID()));
        } else if (Math.random() < SPECIAL_MOVE_CHANCE_UNCOMMON) {
            JujutsucraftaddonMod.PACKET_HANDLER.sendToServer(new PacketEffects(6, entity.getUUID()));
        }
        
        updateAttackAnimation(entity, nextIndex);
        playAnimation(player, "maki" + nextIndex);
        sendEffectPackets(entity, SOUND_EFFECT_CHANCE);
    }

    public static void handleSukunaStyle(Entity entity, AbstractClientPlayer player) {
        playAnimation(player, "sukuna" + Mth.nextInt(RandomSource.create(), 1, 26));
        sendEffectPackets(entity, SOUND_EFFECT_CHANCE);
    }

    public static void handleItadoriFistStyle(Entity entity, AbstractClientPlayer player, double currentIndex) {
        int nextIndex = getNextIndex((int) currentIndex, 5);
        
        if (Math.random() < 0.01) {
            JujutsucraftaddonMod.PACKET_HANDLER.sendToServer(new PacketEffects(1, entity.getUUID()));
            playAnimation(player, "itadorifistkoku");
            return;
        }
        
        updateAttackAnimation(entity, nextIndex);
        playAnimation(player, "itadorifist" + nextIndex);
        sendEffectPackets(entity, SOUND_EFFECT_CHANCE);
    }

    private static int getNextIndex(int current, int max) {
        return current >= max ? 1 : current + 1;
    }

    private static void updateAttackAnimation(Entity entity, int index) {
        entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null)
            .ifPresent(capability -> {
                capability.AttackAnimation = index;
                capability.syncPlayerVariables(entity);
            });
    }

    private static void playAnimation(AbstractClientPlayer player, String animationName) {
        ModifierLayer animation = (ModifierLayer) PlayerAnimationAccess
            .getPlayerAssociatedData(player)
            .get(new ResourceLocation(ANIMATION_KEY));
            
        if (animation != null) {
            animation.setAnimation(new KeyframeAnimationPlayer(
                PlayerAnimationRegistry.getAnimation(new ResourceLocation("jujutsucraftaddon", animationName))
            ));
        }
    }

    private static void sendEffectPackets(Entity entity, float soundChance) {
        if (Math.random() < soundChance) {
            JujutsucraftaddonMod.PACKET_HANDLER.sendToServer(new PacketEffects(8, entity.getUUID()));
        }
        JujutsucraftaddonMod.PACKET_HANDLER.sendToServer(new PacketEffects(0, entity.getUUID()));
    }

    private static void sendSpecialEffects(Entity entity) {
        JujutsucraftaddonMod.PACKET_HANDLER.sendToServer(new PacketEffects(9, entity.getUUID()));
        JujutsucraftaddonMod.PACKET_HANDLER.sendToServer(new PacketEffects(0, entity.getUUID()));
    }
}
