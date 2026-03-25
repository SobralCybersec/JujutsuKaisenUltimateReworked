package com.jujutsu.jujutsucraftaddon.mixins;

import com.jujutsu.jujutsucraftaddon.procedures.combat.SlotEffectService;
import com.jujutsu.jujutsucraftaddon.procedures.combat.SlotMachineService;
import net.mcreator.jujutsucraft.entity.SlotEntity;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.AISLOTProcedure;
import net.mcreator.jujutsucraft.procedures.LogicOwnerExistProcedure;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.UUID;
import java.util.function.BiFunction;

@Mixin(value = AISLOTProcedure.class, remap = false, priority = -10000)
public abstract class AISlotProcedureMixinRefactored {

    @Overwrite
    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null || !LogicOwnerExistProcedure.execute(world, entity)) {
            if (entity != null && !entity.level().isClientSide()) {
                entity.discard();
            }
            return;
        }

        Entity owner = getOwner(world, entity);
        if (owner == null) {
            if (!entity.level().isClientSide()) {
                entity.discard();
            }
            return;
        }

        if (!isOwnerInDomain(owner)) {
            if (!entity.level().isClientSide()) {
                entity.discard();
            }
            return;
        }

        processSlotLogic(world, entity, owner);
    }

    private static Entity getOwner(LevelAccessor world, Entity entity) {
        return new BiFunction<LevelAccessor, String, Entity>() {
            public Entity apply(LevelAccessor levelAccessor, String uuid) {
                if (levelAccessor instanceof ServerLevel serverLevel) {
                    try {
                        return serverLevel.getEntity(UUID.fromString(uuid));
                    } catch (Exception e) {
                        return null;
                    }
                }
                return null;
            }
        }.apply(world, entity.getPersistentData().getString("OWNER_UUID"));
    }

    private static boolean isOwnerInDomain(Entity owner) {
        return owner instanceof LivingEntity _livEnt && 
               _livEnt.hasEffect(JujutsucraftModMobEffects.DOMAIN_EXPANSION.get()) &&
               !owner.getPersistentData().getBoolean("Failed");
    }

    private static void processSlotLogic(LevelAccessor world, Entity entity, Entity owner) {
        entity.getPersistentData().putDouble("cnt1", entity.getPersistentData().getDouble("cnt1") + 1.0);

        double range = JujutsucraftModVariables.MapVariables.get(world).DomainExpansionRadius * 2.0;
        double x = entity.getX();
        double y = entity.getY() + entity.getBbHeight() * 0.5;
        double z = entity.getZ();

        int slotMode = SlotMachineService.getSlotMode(entity);
        double counter = entity.getPersistentData().getDouble("cnt1");

        if (shouldDecide(slotMode, counter)) {
            handleDecision(world, entity, owner, x, y, z);
        }

        if (entity.getPersistentData().getDouble("cnt2") > 0.0) {
            handleJackpot(world, entity, owner);
        }
    }

    private static boolean shouldDecide(int slotMode, double counter) {
        return switch (slotMode) {
            case 1 -> counter >= 20.0;
            case 2 -> counter >= 30.0;
            case 3 -> counter >= 60.0;
            default -> false;
        };
    }

    private static void handleDecision(LevelAccessor world, Entity entity, Entity owner, double x, double y, double z) {
        SlotEffectService.playDecisionSound(world, x, y, z);
        SlotEffectService.spawnDecisionParticles(world, x, y, z);

        int slotMode = SlotMachineService.getSlotMode(entity);
        int slotNumber = calculateSlotNumberForMode(entity, slotMode, owner);
        
        SlotMachineService.setSlotNumber(entity, slotNumber);
        
        if (entity instanceof SlotEntity) {
            ((SlotEntity) entity).setAnimation("slot" + slotNumber);
        }
    }

    private static int calculateSlotNumberForMode(Entity entity, int slotMode, Entity owner) {
        return switch (slotMode) {
            case 1 -> (int) Math.ceil(Math.random() * 7.0);
            case 2 -> {
                Entity slot1 = getSlotEntity(owner, "SLOT1");
                int previousNumber = SlotMachineService.getSlotNumber(slot1);
                yield previousNumber > 0 ? previousNumber : (int) Math.ceil(Math.random() * 7.0);
            }
            case 3 -> {
                Entity slot1 = getSlotEntity(owner, "SLOT1");
                int previousNumber = SlotMachineService.getSlotNumber(slot1);
                yield SlotMachineService.calculateSlotNumber(entity, previousNumber);
            }
            default -> 0;
        };
    }

    private static void handleJackpot(LevelAccessor world, Entity entity, Entity owner) {
        entity.getPersistentData().putDouble("cnt2", entity.getPersistentData().getDouble("cnt2") + 1.0);
        
        double x = entity.getX();
        double y = entity.getY() + entity.getBbHeight() * 0.5;
        double z = entity.getZ();
        
        SlotEffectService.spawnJackpotParticles(world, x, y, z);

        if (entity.getPersistentData().getDouble("cnt2") >= 50.0) {
            applyJackpotEffect(owner);
            if (!entity.level().isClientSide()) {
                entity.discard();
            }
        }
    }

    private static void applyJackpotEffect(Entity owner) {
        if (!(owner instanceof LivingEntity _entity)) {
            return;
        }

        _entity.addEffect(new MobEffectInstance(JujutsucraftModMobEffects.JACKPOT.get(), 5020, 0, true, true));
        _entity.removeEffect(JujutsucraftModMobEffects.DOMAIN_EXPANSION.get());
    }

    private static Entity getSlotEntity(Entity owner, String slotKey) {
        String uuid = owner.getPersistentData().getString(slotKey);
        if (uuid.isEmpty()) {
            return null;
        }
        
        if (owner.level() instanceof ServerLevel serverLevel) {
            try {
                return serverLevel.getEntity(UUID.fromString(uuid));
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}
