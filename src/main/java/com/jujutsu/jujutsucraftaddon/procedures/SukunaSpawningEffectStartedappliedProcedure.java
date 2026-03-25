package com.jujutsu.jujutsucraftaddon.procedures;

import com.jujutsu.jujutsucraftaddon.entity.clone.ErroEntity;
import com.jujutsu.jujutsucraftaddon.entity.clone.ErrorEntity;
import com.jujutsu.jujutsucraftaddon.init.mod.JujutsucraftaddonModEntities;
import com.jujutsu.jujutsucraftaddon.init.mod.JujutsucraftaddonModGameRules;
import com.jujutsu.jujutsucraftaddon.init.mod.JujutsucraftaddonModItems;
import com.jujutsu.jujutsucraftaddon.init.mod.JujutsucraftaddonModMobEffects;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class SukunaSpawningEffectStartedappliedProcedure {

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null) return;
        if (!isPossessionEnabled(world)) return;

        int bodyCount = getBodyItemCount(entity);

        if (bodyCount >= 20) {
            spawnHeianSukuna(world, x, y, z, entity);
            return;
        }

        if (bodyCount < 2) {
            spawnEarlyErro(world, x, y, z, entity);
        } else if (bodyCount > 6) {
            if (Math.random() <= 0.5) {
                applyEffect(entity, JujutsucraftaddonModMobEffects.SUKUNA.get(), Mth.nextInt(RandomSource.create(), 3000, 6000), 1);
                spawnMidErro(world, x, y, z, entity, false);
            } else {
                applyEffect(entity, JujutsucraftaddonModMobEffects.SUKUNA.get(), Mth.nextInt(RandomSource.create(), 4000, 6000), 1);
                spawnMidErro(world, x, y, z, entity, true);
            }
        } else {
            applyEffect(entity, JujutsucraftaddonModMobEffects.SUKUNA.get(), Mth.nextInt(RandomSource.create(), 6000, 18000), 1);
            spawnLateErro(world, x, y, z, entity);
        }
    }

    private static void spawnEarlyErro(LevelAccessor world, double x, double y, double z, Entity owner) {
        spawnInServerLevel(world, x, y, z, serverLevel -> {
            Entity clone = createErro(serverLevel, x, y, z, owner);
            if (clone == null) return;

            applyEffect(owner, JujutsucraftaddonModMobEffects.SUKUNA.get(), 6000, 1);
            applyBaseAttributes(clone, owner);
            applySukunaEffect(clone, 4);
            applyOwnerData(clone, owner);
            copyEquipment(clone, owner);
            setForgeBoolean(clone, "UseCursedTechnique", true);
            setForgeBoolean(clone, "CurseUser", true);
            setForgeDouble(clone, "Demon", 2);
            applyEffect(clone, JujutsucraftaddonModMobEffects.SUKUNA_CUTSCENE_1.get(), 600, 1);
            applyEffect(clone, JujutsucraftaddonModMobEffects.SUKUNA_SOUND_1.get(), 300, 1);

            serverLevel.addFreshEntity(clone);
        });
    }

    private static void spawnMidErro(LevelAccessor world, double x, double y, double z, Entity owner, boolean withSound) {
        spawnInServerLevel(world, x, y, z, serverLevel -> {
            Entity clone = createErro(serverLevel, x, y, z, owner);
            if (clone == null) return;

            if (withSound) playSound(world, x, y, z);

            applyBaseAttributes(clone, owner);
            applySukunaEffect(clone, 6);
            applyOwnerData(clone, owner);
            copyEquipment(clone, owner);
            setForgeBoolean(clone, "UseCursedTechnique", true);
            setForgeBoolean(clone, "CurseUser", true);
            clone.getPersistentData().putDouble("Demon", 1);
            clone.getPersistentData().putDouble("God", 1);
            if (withSound) clone.getPersistentData().putDouble("DeathCount", 1);
            applyEffect(owner, MobEffects.DARKNESS, 60, 1);
            applyEffect(clone, JujutsucraftaddonModMobEffects.SUKUNA_CUTSCENE_3.get(), 60, 1);
            if (withSound) applyEffect(clone, JujutsucraftaddonModMobEffects.SUKUNA_HEARY.get(), 60, 1);

            serverLevel.addFreshEntity(clone);
        });
    }

    private static void spawnLateErro(LevelAccessor world, double x, double y, double z, Entity owner) {
        spawnInServerLevel(world, x, y, z, serverLevel -> {
            Entity clone = createErro(serverLevel, x, y, z, owner);
            if (clone == null) return;

            applyBaseAttributes(clone, owner);
            applySukunaEffect(clone, 16);
            applyOwnerData(clone, owner);
            copyEquipment(clone, owner);
            setForgeBoolean(clone, "UseCursedTechnique", true);
            setForgeBoolean(clone, "CurseUser", true);
            clone.getPersistentData().putDouble("Demon", 1);
            clone.getPersistentData().putDouble("God", 1);
            applyEffect(owner, MobEffects.DARKNESS, 60, 1);
            applyEffect(clone, JujutsucraftaddonModMobEffects.SUKUNA_CUTSCENE_3.get(), 60, 1);

            serverLevel.addFreshEntity(clone);
        });
    }

    private static void spawnHeianSukuna(LevelAccessor world, double x, double y, double z, Entity owner) {
        spawnInServerLevel(world, x, y, z, serverLevel -> {
            Entity clone = createError(serverLevel, x, y, z, owner);
            if (clone == null) return;

            applyEffect(owner, JujutsucraftaddonModMobEffects.SUKUNA.get(), -1, 1);
            applyHeianAttributes(clone, owner);
            applySukunaEffect(clone, 19);
            applyOwnerData(clone, owner);
            applyEffect(clone, JujutsucraftaddonModMobEffects.HEIAN_FORM.get(), -1, 0);

            copyEquipmentExceptArmor(clone, owner);
            setArmorSlot(clone, EquipmentSlot.CHEST, new ItemStack(JujutsucraftaddonModItems.SUKUNA_ARMOR_THREE_CHESTPLATE.get()));
            setArmorSlot(clone, EquipmentSlot.HEAD, new ItemStack(JujutsucraftaddonModItems.SUKUNA_ARMOR_THREE_HELMET.get()));

            setForgeBoolean(clone, "UseCursedTechnique", true);
            setForgeBoolean(clone, "CurseUser", true);
            clone.getPersistentData().putDouble("Demon", 2);

            serverLevel.addFreshEntity(clone);
        });
    }

    private static Entity createErro(ServerLevel level, double x, double y, double z, Entity owner) {
        Entity clone = JujutsucraftaddonModEntities.ERRO.get().create(level, null, null,
                BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED, false, false);
        if (clone == null) return null;
        initClone(clone, owner, level);
        return clone;
    }

    private static Entity createError(ServerLevel level, double x, double y, double z, Entity owner) {
        Entity clone = JujutsucraftaddonModEntities.ERROR.get().create(level, null, null,
                BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED, false, false);
        if (clone == null) return null;
        initClone(clone, owner, level);
        return clone;
    }

    private static void initClone(Entity clone, Entity owner, ServerLevel level) {
        clone.setYRot(level.getRandom().nextFloat() * 360f);
        clone.setCustomName(Component.literal(owner.getDisplayName().getString()));
        if (clone instanceof ErroEntity erro) erro.setOwnerUUID(owner.getUUID());
        if (clone instanceof ErrorEntity error) error.setOwnerUUID(owner.getUUID());
    }

    private static void applyBaseAttributes(Entity clone, Entity owner) {
        if (!(clone instanceof LivingEntity living)) return;
        setAttribute(living, Attributes.ARMOR, 30);
        setAttribute(living, Attributes.MAX_HEALTH, 600);
        setAttribute(living, Attributes.MOVEMENT_SPEED, 0.5);
        setAttribute(living, Attributes.FOLLOW_RANGE, 254);
        copyAttribute(living, Attributes.ATTACK_KNOCKBACK, owner);
        copyAttribute(living, Attributes.ATTACK_DAMAGE, owner);
        copyAttribute(living, Attributes.KNOCKBACK_RESISTANCE, owner);
        living.setHealth(living.getMaxHealth());
    }

    private static void applyHeianAttributes(Entity clone, Entity owner) {
        if (!(clone instanceof LivingEntity living)) return;
        setAttribute(living, Attributes.ARMOR, 30);
        setAttribute(living, Attributes.MAX_HEALTH, 1000);
        setAttribute(living, Attributes.MOVEMENT_SPEED, 0.5);
        setAttribute(living, Attributes.FOLLOW_RANGE, 254);
        copyAttribute(living, Attributes.ATTACK_KNOCKBACK, owner);
        copyAttributeMultiplied(living, Attributes.ATTACK_DAMAGE, owner, 3);
        copyAttribute(living, Attributes.KNOCKBACK_RESISTANCE, owner);
        living.setHealth(living.getMaxHealth());
    }

    private static void applySukunaEffect(Entity clone, int amplifierBonus) {
        if (!(clone instanceof LivingEntity living) || living.level().isClientSide()) return;
        int base = living.hasEffect(JujutsucraftModMobEffects.SUKUNA_EFFECT.get())
                ? living.getEffect(JujutsucraftModMobEffects.SUKUNA_EFFECT.get()).getAmplifier()
                : 0;
        living.addEffect(new MobEffectInstance(JujutsucraftModMobEffects.SUKUNA_EFFECT.get(), -1, base + amplifierBonus, false, false));
    }

    private static void applyOwnerData(Entity clone, Entity owner) {
        clone.getPersistentData().putString("Owner", owner.getStringUUID());
        double tagged = getPlayerVar(owner).PlayerCurseTechnique2;
        clone.getPersistentData().putDouble("Tagged", tagged);
        double friendNum = getForgeDouble(owner, "friend_num");
        setForgeDouble(clone, "friend_num", friendNum);
        setForgeDouble(clone, "friend_num2", friendNum);
        setForgeDouble(clone, "Tagged", tagged);
    }

    private static void copyEquipment(Entity clone, Entity owner) {
        if (owner instanceof LivingEntity src && clone instanceof LivingEntity living) {
            ItemStack mainHand = src.getMainHandItem().copy();
            mainHand.setCount(1);
            living.setItemInHand(InteractionHand.MAIN_HAND, mainHand);
            if (living instanceof Player p) p.getInventory().setChanged();
        }
        setArmorSlot(clone, EquipmentSlot.FEET, getArmorSlot(owner, EquipmentSlot.FEET));
        setArmorSlot(clone, EquipmentSlot.LEGS, getArmorSlot(owner, EquipmentSlot.LEGS));
        setArmorSlot(clone, EquipmentSlot.CHEST, getArmorSlot(owner, EquipmentSlot.CHEST));
        setArmorSlot(clone, EquipmentSlot.HEAD, getArmorSlot(owner, EquipmentSlot.HEAD));
    }

    private static void copyEquipmentExceptArmor(Entity clone, Entity owner) {
        if (owner instanceof LivingEntity src && clone instanceof LivingEntity living) {
            ItemStack mainHand = src.getMainHandItem().copy();
            mainHand.setCount(1);
            living.setItemInHand(InteractionHand.MAIN_HAND, mainHand);
            if (living instanceof Player p) p.getInventory().setChanged();
        }
        setArmorSlot(clone, EquipmentSlot.FEET, getArmorSlot(owner, EquipmentSlot.FEET));
        setArmorSlot(clone, EquipmentSlot.LEGS, getArmorSlot(owner, EquipmentSlot.LEGS));
    }

    private static void setArmorSlot(Entity entity, EquipmentSlot slot, ItemStack stack) {
        if (entity instanceof Player player) {
            player.getInventory().armor.set(slot.getIndex(), stack);
            player.getInventory().setChanged();
        } else if (entity instanceof LivingEntity living) {
            living.setItemSlot(slot, stack);
        }
    }

    private static ItemStack getArmorSlot(Entity entity, EquipmentSlot slot) {
        return entity instanceof LivingEntity living ? living.getItemBySlot(slot) : ItemStack.EMPTY;
    }

    private static void setAttribute(LivingEntity entity, Attribute attribute, double value) {
        if (entity.getAttributes().hasAttribute(attribute))
            entity.getAttribute(attribute).setBaseValue(value);
    }

    private static void copyAttribute(LivingEntity target, Attribute attribute, Entity source) {
        if (!target.getAttributes().hasAttribute(attribute)) return;
        double value = source instanceof LivingEntity src && src.getAttributes().hasAttribute(attribute)
                ? src.getAttribute(attribute).getBaseValue() : 0;
        target.getAttribute(attribute).setBaseValue(value);
    }

    private static void copyAttributeMultiplied(LivingEntity target, Attribute attribute, Entity source, double multiplier) {
        if (!target.getAttributes().hasAttribute(attribute)) return;
        double value = source instanceof LivingEntity src && src.getAttributes().hasAttribute(attribute)
                ? src.getAttribute(attribute).getBaseValue() : 0;
        target.getAttribute(attribute).setBaseValue(value * multiplier);
    }

    private static void applyEffect(Entity entity, net.minecraft.world.effect.MobEffect effect, int duration, int amplifier) {
        if (entity instanceof LivingEntity living && !living.level().isClientSide())
            living.addEffect(new MobEffectInstance(effect, duration, amplifier, false, false));
    }

    private static void playSound(LevelAccessor world, double x, double y, double z) {
        if (world instanceof Level level) {
            var sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jujutsucraftaddon:sukunaheart"));
            if (!level.isClientSide()) level.playSound(null, BlockPos.containing(x, y, z), sound, SoundSource.NEUTRAL, 3, 1);
            else level.playLocalSound(x, y, z, sound, SoundSource.NEUTRAL, 3, 1, false);
        }
    }

    private static void spawnInServerLevel(LevelAccessor world, double x, double y, double z, java.util.function.Consumer<ServerLevel> action) {
        if (!(world instanceof ServerLevel serverLevel)) return;
        action.accept(serverLevel);
    }

    private static boolean isPossessionEnabled(LevelAccessor world) {
        return world.getLevelData().getGameRules().getBoolean(JujutsucraftaddonModGameRules.JJKU_SUKUNA_POSSESSION_ENABLED);
    }

    private static int getBodyItemCount(Entity entity) {
        return getPlayerVar(entity).BodyItem.getCount();
    }

    private static JujutsucraftModVariables.PlayerVariables getPlayerVar(Entity entity) {
        return entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .orElse(new JujutsucraftModVariables.PlayerVariables());
    }

    private static double getForgeDouble(Entity entity, String key) {
        var tag = new net.minecraft.nbt.CompoundTag();
        entity.saveWithoutId(tag);
        return tag.getCompound("ForgeData").getDouble(key);
    }

    private static void setForgeDouble(Entity entity, String key, double value) {
        var tag = new net.minecraft.nbt.CompoundTag();
        entity.saveWithoutId(tag);
        tag.getCompound("ForgeData").putDouble(key, value);
        entity.load(tag);
    }

    private static void setForgeBoolean(Entity entity, String key, boolean value) {
        var tag = new net.minecraft.nbt.CompoundTag();
        entity.saveWithoutId(tag);
        tag.getCompound("ForgeData").putBoolean(key, value);
        entity.load(tag);
    }
}