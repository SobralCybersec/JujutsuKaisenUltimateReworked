package com.jujutsu.jujutsucraftaddon.procedures;

import com.jujutsu.jujutsucraftaddon.entity.clone.CloneEntity;
import com.jujutsu.jujutsucraftaddon.init.mod.JujutsucraftaddonModEntities;
import com.jujutsu.jujutsucraftaddon.network.JujutsucraftaddonModVariables;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;

import static com.jujutsu.jujutsucraftaddon.entity.clone.CloneEntity.ATTACK_MODE;

public class SpawnCloneProcedure {

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null || !(world instanceof ServerLevel serverLevel)) return;

        boolean isMajima = entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .orElse(new JujutsucraftaddonModVariables.PlayerVariables()).Clans.equals("Majima");

        spawnClone(serverLevel, x, y, z, entity, isMajima);
    }

    private static void spawnClone(ServerLevel serverLevel, double x, double y, double z, Entity owner, boolean isMajima) {
        Entity clone = JujutsucraftaddonModEntities.CLONE.get().create(serverLevel, null, null,
                BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED, false, false);

        if (clone == null) return;

        clone.setYRot(serverLevel.getRandom().nextFloat() * 360.0F);

        setupOwnership(clone, owner);
        setupAttributes(clone, owner, isMajima);
        setupEquipment(clone, owner);
        setupEffects(clone);
        setupNBTData(clone, owner);

        serverLevel.addFreshEntity(clone);
    }

    private static void setupOwnership(Entity clone, Entity owner) {
        setNBTString(clone, "OWNER_UUID", owner.getStringUUID());
        setNBTString(clone, "Owner", owner.getStringUUID());

        if (clone instanceof CloneEntity cloneEntity) {
            cloneEntity.setOwnerUUID(owner.getUUID());

            if (owner.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                    .orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCurseTechnique2 == 100) {
                cloneEntity.getEntityData().set(ATTACK_MODE, true);
            }
        }

        clone.getPersistentData().putString("Owner", owner.getStringUUID());
        clone.setCustomName(Component.literal(owner.getDisplayName().getString()));

        if (clone instanceof TamableAnimal tamable && owner instanceof Player player) {
            tamable.tame(player);
        }
    }

    private static void setupAttributes(Entity clone, Entity owner, boolean isMajima) {
        if (!(clone instanceof LivingEntity cloneLiving) || !(owner instanceof LivingEntity ownerLiving)) return;

        copyAttribute(cloneLiving, ownerLiving, Attributes.ATTACK_KNOCKBACK);
        copyAttribute(cloneLiving, ownerLiving, Attributes.ARMOR);
        copyAttribute(cloneLiving, ownerLiving, Attributes.ARMOR_TOUGHNESS);
        copyAttribute(cloneLiving, ownerLiving, Attributes.ATTACK_DAMAGE);
        copyAttribute(cloneLiving, ownerLiving, Attributes.KNOCKBACK_RESISTANCE);

        double maxHealth = getAttributeValue(ownerLiving, Attributes.MAX_HEALTH) * (isMajima ? 40 : 40);
        setAttribute(cloneLiving, Attributes.MAX_HEALTH, maxHealth);
        setAttribute(cloneLiving, Attributes.FOLLOW_RANGE, 256);

        cloneLiving.setHealth(cloneLiving.getMaxHealth());
    }

    private static void setupEquipment(Entity clone, Entity owner) {
        if (!(clone instanceof LivingEntity cloneLiving) || !(owner instanceof LivingEntity ownerLiving)) return;

        ItemStack mainHand = ownerLiving.getMainHandItem().copy();
        mainHand.setCount(1);
        cloneLiving.setItemInHand(InteractionHand.MAIN_HAND, mainHand);

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.ARMOR) {
                cloneLiving.setItemSlot(slot, ownerLiving.getItemBySlot(slot));
            }
        }
    }

    private static void setupEffects(Entity clone) {
        if (!(clone instanceof LivingEntity living) || living.level().isClientSide()) return;

        living.addEffect(new MobEffectInstance(MobEffects.HEAL, 60, 245, false, true));
        living.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 245, false, true));
    }

    private static void setupNBTData(Entity clone, Entity owner) {
        CompoundTag ownerTag = new CompoundTag();
        owner.saveWithoutId(ownerTag);
        CompoundTag ownerForge = ownerTag.getCompound("ForgeData");

        setNBTDouble(clone, "friend_num", ownerForge.getDouble("friend_num"));
        setNBTDouble(clone, "friend_num2", ownerForge.getDouble("friend_num"));
        setNBTDouble(clone, "Tagged", owner.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCurseTechnique);

        clone.getPersistentData().putDouble("Tagged", owner.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCurseTechnique);
        clone.getPersistentData().putString("Owner", owner.getStringUUID());
        clone.getPersistentData().putString("OWNER_UUID", owner.getStringUUID());

        setNBTBoolean(clone, "UseCursedTechnique", true);
        setNBTBoolean(clone, "JujutsuSorcerer", ownerForge.getBoolean("JujutsuSorcerer"));
        setNBTBoolean(clone, "CursedSpirit", ownerForge.getBoolean("CursedSpirit"));
        setNBTBoolean(clone, "CurseUser", ownerForge.getBoolean("CurseUser"));
    }

    // Helper methods
    private static void copyAttribute(LivingEntity target, LivingEntity source, Attribute attribute) {
        if (target.getAttributes().hasAttribute(attribute) && source.getAttributes().hasAttribute(attribute)) {
            target.getAttribute(attribute).setBaseValue(source.getAttribute(attribute).getBaseValue());
        }
    }

    private static void setAttribute(LivingEntity entity, Attribute attribute, double value) {
        if (entity.getAttributes().hasAttribute(attribute)) {
            entity.getAttribute(attribute).setBaseValue(value);
        }
    }

    private static double getAttributeValue(LivingEntity entity, Attribute attribute) {
        return entity.getAttributes().hasAttribute(attribute) ? entity.getAttribute(attribute).getBaseValue() : 0;
    }

    private static void setNBTString(Entity entity, String key, String value) {
        CompoundTag tag = new CompoundTag();
        entity.saveWithoutId(tag);
        tag.getCompound("ForgeData").putString(key, value);
        entity.load(tag);
    }

    private static void setNBTDouble(Entity entity, String key, double value) {
        CompoundTag tag = new CompoundTag();
        entity.saveWithoutId(tag);
        tag.getCompound("ForgeData").putDouble(key, value);
        entity.load(tag);
    }

    private static void setNBTBoolean(Entity entity, String key, boolean value) {
        CompoundTag tag = new CompoundTag();
        entity.saveWithoutId(tag);
        tag.getCompound("ForgeData").putBoolean(key, value);
        entity.load(tag);
    }
}
