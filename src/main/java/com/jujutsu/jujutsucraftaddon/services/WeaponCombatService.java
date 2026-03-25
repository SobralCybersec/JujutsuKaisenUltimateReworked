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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public final class WeaponCombatService {

    private static final String ANIMATION_KEY = "jujutsucraftaddon:player_animation";

    private WeaponCombatService() {
    }

    public static void handleNyoiStaff(Entity entity, AbstractClientPlayer player, double currentIndex) {
        int nextIndex = (int) currentIndex >= 9 ? 1 : (int) currentIndex + 1;
        
        if (Math.random() < 0.025) {
            JujutsucraftaddonMod.PACKET_HANDLER.sendToServer(new PacketEffects(2, entity.getUUID()));
        }
        
        updateAnimation(entity, nextIndex);
        playAnimation(player, "nyoi" + nextIndex);
        sendBaseEffect(entity);
    }

    public static void handleHitenSword(Entity entity, AbstractClientPlayer player, double currentIndex) {
        int nextIndex = (int) currentIndex >= 5 ? 1 : (int) currentIndex + 1;
        updateAnimation(entity, nextIndex);
        playAnimation(player, "hiten" + nextIndex);
        sendBaseEffect(entity);
    }

    public static void handleExterminationSword(Entity entity, AbstractClientPlayer player, double currentIndex) {
        int nextIndex = (int) currentIndex >= 5 ? 1 : (int) currentIndex + 1;
        
        if (Math.random() < 0.02) {
            playAnimation(player, "exterminationswordslice");
            JujutsucraftaddonMod.PACKET_HANDLER.sendToServer(new PacketEffects(4, entity.getUUID()));
            sendBaseEffect(entity);
            return;
        }
        
        updateAnimation(entity, nextIndex);
        playAnimation(player, "exterminationsword" + nextIndex);
        sendBaseEffect(entity);
    }

    public static void handleInvertedSpear(Entity entity, AbstractClientPlayer player, double currentIndex) {
        int nextIndex = (int) currentIndex >= 5 ? 1 : (int) currentIndex + 1;
        
        if (Math.random() < 0.02) {
            playAnimation(player, "invertedcut");
            JujutsucraftaddonMod.PACKET_HANDLER.sendToServer(new PacketEffects(3, entity.getUUID()));
            return;
        }
        
        updateAnimation(entity, nextIndex);
        playAnimation(player, "inverted" + nextIndex);
        sendBaseEffect(entity);
    }

    public static void handleKamutoke(Entity entity, AbstractClientPlayer player, double currentIndex) {
        int nextIndex = (int) currentIndex >= 4 ? 1 : (int) currentIndex + 1;
        
        if (Math.random() < 0.025) {
            JujutsucraftaddonMod.PACKET_HANDLER.sendToServer(new PacketEffects(2, entity.getUUID()));
        }
        
        updateAnimation(entity, nextIndex);
        playAnimation(player, "kamutoke" + nextIndex);
        sendBaseEffect(entity);
    }

    public static void handleItadoriArms(Entity entity, AbstractClientPlayer player, double currentIndex, double style) {
        int nextIndex = (int) currentIndex >= 5 ? 1 : (int) currentIndex + 1;
        
        if (Math.random() < 0.01 && style == 7) {
            JujutsucraftaddonMod.PACKET_HANDLER.sendToServer(new PacketEffects(1, entity.getUUID()));
            playAnimation(player, "itadorifistkoku");
            return;
        }
        
        updateAnimation(entity, nextIndex);
        playAnimation(player, "itadorifist" + nextIndex);
        sendBaseEffect(entity);
    }

    public static void handleSplitSoul(Entity entity, AbstractClientPlayer player, double currentIndex) {
        int nextIndex = (int) currentIndex >= 5 ? 1 : (int) currentIndex + 1;
        updateAnimation(entity, nextIndex);
        playAnimation(player, "split" + nextIndex);
        sendBaseEffect(entity);
    }

    public static void handlePlayfulCloud(Entity entity, AbstractClientPlayer player) {
        ModifierLayer animation = getAnimation(player);
        if (animation != null && !animation.isActive()) {
            playAnimation(player, "ab" + Mth.nextInt(RandomSource.create(), 1, 5) + "player");
        }
        sendBaseEffect(entity);
    }

    public static void handleGenericSword(Entity entity, AbstractClientPlayer player, double style) {
        if (Math.random() < 0.025 && style == 5) {
            JujutsucraftaddonMod.PACKET_HANDLER.sendToServer(new PacketEffects(7, entity.getUUID()));
            playAnimation(player, "swordultimate");
            return;
        }
        
        ModifierLayer animation = getAnimation(player);
        if (animation != null && !animation.isActive()) {
            playAnimation(player, "sword" + Mth.nextInt(RandomSource.create(), 1, 24));
        }
        sendBaseEffect(entity);
    }

    public static boolean isWeaponType(LivingEntity entity, String weaponType) {
        ItemStack mainHand = entity.getMainHandItem();
        String itemKey = ForgeRegistries.ITEMS.getKey(mainHand.getItem()).toString();
        return itemKey.contains(weaponType);
    }

    private static void updateAnimation(Entity entity, int index) {
        entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null)
            .ifPresent(capability -> {
                capability.AttackAnimation = index;
                capability.syncPlayerVariables(entity);
            });
    }

    private static void playAnimation(AbstractClientPlayer player, String animationName) {
        ModifierLayer animation = getAnimation(player);
        if (animation != null) {
            animation.setAnimation(new KeyframeAnimationPlayer(
                PlayerAnimationRegistry.getAnimation(new ResourceLocation("jujutsucraftaddon", animationName))
            ));
        }
    }

    private static ModifierLayer getAnimation(AbstractClientPlayer player) {
        return (ModifierLayer) PlayerAnimationAccess
            .getPlayerAssociatedData(player)
            .get(new ResourceLocation(ANIMATION_KEY));
    }

    private static void sendBaseEffect(Entity entity) {
        JujutsucraftaddonMod.PACKET_HANDLER.sendToServer(new PacketEffects(0, entity.getUUID()));
    }
}
