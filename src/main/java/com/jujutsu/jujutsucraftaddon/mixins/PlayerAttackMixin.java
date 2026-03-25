package com.jujutsu.jujutsucraftaddon.mixins;

import com.jujutsu.jujutsucraftaddon.network.JujutsucraftaddonModVariables;
import com.jujutsu.jujutsucraftaddon.services.CombatStyleService;
import com.jujutsu.jujutsucraftaddon.services.WeaponCombatService;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class PlayerAttackMixin {
    
    @Inject(method = "startAttack", at = @At("HEAD"))
    private void onPlayerAttack(CallbackInfoReturnable<Boolean> cir) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) {
            return;
        }

        Entity entity = mc.player;
        AbstractClientPlayer player = mc.player;
        
        if (!isRunning(entity) || !isEmptyHanded(entity)) {
            handleWeaponAttack(entity, player);
            return;
        }

        handleUnarmedAttack(entity, player);
    }

    private boolean isRunning(Entity entity) {
        return getPlayerVariable(entity).Run == 1;
    }

    private boolean isEmptyHanded(Entity entity) {
        return entity instanceof LivingEntity living && living.getMainHandItem().isEmpty();
    }

    private void handleUnarmedAttack(Entity entity, AbstractClientPlayer player) {
        int style =  (int) getPlayerVariable(entity).Style;
        int currentIndex = (int) getPlayerVariable(entity).AttackAnimation;

        switch (style) {
            case 0 -> CombatStyleService.handleBasicStyle(entity, player, currentIndex);
            case 1 -> CombatStyleService.handleGojoStyle(entity, player, currentIndex);
            case 2 -> CombatStyleService.handleYujiStyle(entity, player, currentIndex);
            case 3 -> CombatStyleService.handleKashimoStyle(entity, player, currentIndex);
            case 4 -> CombatStyleService.handleJogoStyle(entity, player, currentIndex);
            case 5 -> CombatStyleService.handleMakiStyle(entity, player, currentIndex);
            case 6 -> CombatStyleService.handleSukunaStyle(entity, player);
            case 7 -> CombatStyleService.handleItadoriFistStyle(entity, player, currentIndex);
        }
    }

    private void handleWeaponAttack(Entity entity, AbstractClientPlayer player) {
        if (!(entity instanceof LivingEntity living)) {
            return;
        }

        int currentIndex = (int) getPlayerVariable(entity).AttackAnimation;
        int style = (int) getPlayerVariable(entity).Style;

        if (WeaponCombatService.isWeaponType(living, "nyoi")) {
            WeaponCombatService.handleNyoiStaff(entity, player, currentIndex);
        } else if (WeaponCombatService.isWeaponType(living, "hiten")) {
            WeaponCombatService.handleHitenSword(entity, player, currentIndex);
        } else if (WeaponCombatService.isWeaponType(living, "sword_of_extermination")) {
            WeaponCombatService.handleExterminationSword(entity, player, currentIndex);
        } else if (WeaponCombatService.isWeaponType(living, "inverted_spear_of_heaven")) {
            WeaponCombatService.handleInvertedSpear(entity, player, currentIndex);
        } else if (WeaponCombatService.isWeaponType(living, "supreme_martial_solution")) {
            WeaponCombatService.handleKamutoke(entity, player, currentIndex);
        } else if (WeaponCombatService.isWeaponType(living, "itadori_arm")) {
            WeaponCombatService.handleItadoriArms(entity, player, currentIndex, style);
        } else if (WeaponCombatService.isWeaponType(living, "split")) {
            WeaponCombatService.handleSplitSoul(entity, player, currentIndex);
        } else if (WeaponCombatService.isWeaponType(living, "playful")) {
            WeaponCombatService.handlePlayfulCloud(entity, player);
        } else {
            WeaponCombatService.handleGenericSword(entity, player, style);
        }
    }

    private JujutsucraftaddonModVariables.PlayerVariables getPlayerVariable(Entity entity) {
        return entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null)
            .orElse(new JujutsucraftaddonModVariables.PlayerVariables());
    }
}
