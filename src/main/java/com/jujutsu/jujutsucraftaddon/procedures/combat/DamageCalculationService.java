package com.jujutsu.jujutsucraftaddon.procedures.combat;

import net.mcreator.jujutsucraft.init.JujutsucraftModItems;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class DamageCalculationService {

    public static class DamageModification {
        public double damage;
        public boolean changed;
        public boolean criticalNanami;
        public boolean criticalIce;

        public DamageModification(double damage) {
            this.damage = damage;
            this.changed = false;
            this.criticalNanami = false;
            this.criticalIce = false;
        }
    }

    public static void applyCursedToolDamage(ItemStack mainHandItem, DamageModification mod) {
        if (!mainHandItem.is(ItemTags.create(new ResourceLocation("forge:cursed_tool")))) {
            return;
        }

        if (mainHandItem.getItem() == JujutsucraftModItems.EXECUTIONERS_SWORD.get()) {
            mod.damage = 1.0;
            mod.changed = true;
        } else if (mainHandItem.getItem() == JujutsucraftModItems.FESTER_LIFE_BLADE.get()) {
            mod.changed = true;
        } else if (mainHandItem.getItem() == JujutsucraftModItems.PLAYFUL_CLOUD.get()) {
            mod.damage *= 1.25;
            mod.changed = true;
        }
    }

    public static void applyCursePowerDamage(ItemStack mainHandItem, Entity entity, DamageModification mod) {
        if (mainHandItem.getItem() == ItemStack.EMPTY.getItem() || !mainHandItem.getOrCreateTag().contains("CursePower")) {
            return;
        }

        double cursePower = mainHandItem.getOrCreateTag().getDouble("CursePower");

        if (cursePower > 0.0) {
            mod.damage *= 1.0 + Math.abs(cursePower) * 0.02;
            mod.changed = true;
        } else if (cursePower < 0.0 && entity.getPersistentData().getBoolean("CursedSpirit")) {
            mod.damage *= 1.0 + Math.abs(cursePower) * 0.2;
            mod.changed = true;
        }
    }

    public static void applyNanamiCritical(Entity sourceentity, Entity entity, DamageModification mod) {
        int specialAmplifier = sourceentity instanceof LivingEntity _livEnt && _livEnt.hasEffect(JujutsucraftModMobEffects.SPECIAL.get())
            ? _livEnt.getEffect(JujutsucraftModMobEffects.SPECIAL.get()).getAmplifier()
            : 0;

        if (specialAmplifier < 1) {
            return;
        }

        if (!(entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(JujutsucraftModMobEffects.SPECIAL.get()))) {
            return;
        }

        if (entity instanceof LivingEntity _entity) {
            _entity.removeEffect(JujutsucraftModMobEffects.SPECIAL.get());
        }

        mod.damage *= 2.0;
        mod.criticalNanami = true;
        mod.changed = true;
    }

    public static void applyFrozenCritical(Entity entity, DamageModification mod) {
        if (entity.getPercentFrozen() * 100.0F < 5.0F) {
            return;
        }

        entity.setTicksFrozen(0);
        mod.damage *= 1.5;
        mod.criticalIce = true;
        mod.changed = true;
    }

    public static void applyGuardReduction(Entity entity, Entity sourceentity, DamageModification mod) {
        double guardDamage = entity.getPersistentData().getDouble("Damage");
        mod.damage = Math.max(mod.damage - guardDamage, 0.01);
        mod.changed = true;
    }
}
