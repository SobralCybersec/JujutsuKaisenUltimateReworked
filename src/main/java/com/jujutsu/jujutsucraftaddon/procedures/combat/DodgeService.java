package com.jujutsu.jujutsucraftaddon.procedures.combat;

import com.jujutsu.jujutsucraftaddon.init.mod.JujutsucraftaddonModEnchantments;
import com.jujutsu.jujutsucraftaddon.network.JujutsucraftaddonModVariables;
import com.jujutsu.jujutsucraftaddon.procedures.AnimationDodgeProcedure;
import com.jujutsu.jujutsucraftaddon.procedures.SwapTodoTarget;
import net.mcreator.jujutsucraft.entity.TodoAoiEntity;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.LevelAccessor;

public class DodgeService {

    public static boolean tryTodoSwap(Entity entity, LevelAccessor world, Entity sourceentity) {
        if (entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCurseTechnique == 20) {
            if (entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                    .orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCursePower >= 1000) {
                if (Math.random() < 1.0 / 60.0) {
                    SwapTodoTarget.execute(entity, world, sourceentity);
                    AnimationDodgeProcedure.execute(world, entity);
                    return true;
                }
            }
        } else if (entity instanceof TodoAoiEntity && Math.random() < 1.0 / 60.0) {
            SwapTodoTarget.execute(entity, world, sourceentity);
            return true;
        }
        return false;
    }

    public static boolean tryUltraInstinctDodge(Entity entity, LevelAccessor world) {
        ItemStack chestplate = entity instanceof LivingEntity _entGetArmor 
            ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) 
            : ItemStack.EMPTY;
        
        int enchantLevel = EnchantmentHelper.getItemEnchantmentLevel(
            JujutsucraftaddonModEnchantments.ULTRA_INSTINCT.get(), chestplate);
        
        if (enchantLevel != 0 && Math.random() < enchantLevel / 100.0) {
            AnimationDodgeProcedure.execute(world, entity);
            return true;
        }
        return false;
    }

    public static boolean tryUroDodge(Entity entity, LevelAccessor world) {
        if (entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCurseTechnique != 38) {
            return false;
        }

        String clan = entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .orElse(new JujutsucraftaddonModVariables.PlayerVariables()).Clans;
        
        double chance = "Fujiwara".equals(clan) ? 1.0 / 50.0 : 1.0 / 120.0;
        
        if (Math.random() < chance) {
            executeDodgeWithSkill(entity, world);
            AnimationDodgeProcedure.execute(world, entity);
            return true;
        }
        return false;
    }

    public static boolean tryRejectedZeninDodge(Entity entity, LevelAccessor world) {
        if (entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCurseTechnique != -1) {
            return false;
        }

        if (entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCursePower != 0) {
            return false;
        }

        String clan = entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .orElse(new JujutsucraftaddonModVariables.PlayerVariables()).Clans;
        
        double chance = "Rejected Zenin".equals(clan) ? 1.0 / 40.0 : 1.0 / 80.0;
        
        if (Math.random() < chance) {
            AnimationDodgeProcedure.execute(world, entity);
            return true;
        }
        return false;
    }

    private static void executeDodgeWithSkill(Entity entity, LevelAccessor world) {
        entity.getPersistentData().putDouble("skill", 3810.0);
        entity.getPersistentData().putDouble("cnt6", 4.0);
        
        double yaw = Math.toRadians(entity.getYRot() + 90.0F);
        double pitch = Math.toRadians(entity.getXRot());
        
        entity.getPersistentData().putDouble("x_pos", 
            entity.getX() + Math.cos(yaw) * Math.cos(pitch) * (2.0F + entity.getBbWidth()));
        entity.getPersistentData().putDouble("y_pos", 
            entity.getY() + entity.getBbHeight() * 0.75 + Math.sin(pitch) * -1.0 * (2.0F + entity.getBbWidth()));
        entity.getPersistentData().putDouble("z_pos", 
            entity.getZ() + Math.sin(yaw) * Math.cos(pitch) * (2.0F + entity.getBbWidth()));
    }
}
