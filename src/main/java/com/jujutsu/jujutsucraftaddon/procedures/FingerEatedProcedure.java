package com.jujutsu.jujutsucraftaddon.procedures;

import com.jujutsu.jujutsucraftaddon.init.mod.JujutsucraftaddonModGameRules;
import com.jujutsu.jujutsucraftaddon.network.JujutsucraftaddonModVariables;
import net.mcreator.jujutsucraft.init.JujutsucraftModItems;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.GetSukunaLevelProcedure;
import net.mcreator.jujutsucraft.procedures.WhenRespawnProcedure;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.Comparator;
import java.util.List;

public class FingerEatedProcedure {

    private record EligibilityResult(boolean success, double levelControl) {}

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        boolean isCursedSpirit = entity.getPersistentData().getBoolean("CursedSpirit");

        if (isCursedSpirit) {
            processFingerEaten(world, x, y, z, entity, true, 0.0);
            return;
        }

        boolean onlyOneSukunaEnabled = world.getLevelData().getGameRules().getBoolean(JujutsucraftaddonModGameRules.JJKU_ONLY_ONE_SUKUNA);

        if (onlyOneSukunaEnabled) {
            if (JujutsucraftaddonModVariables.MapVariables.get(world).Sukuna != 0) return;
            if (getPlayerVar(entity).BodyItem.getCount() > 0) return;
        } else {
            if (getPlayerVar(entity).BodyItem.getCount() <= 0) return;
        }

        EligibilityResult eligibility = checkEligibility(entity);

        if (!eligibility.success()) {
            rejectFinger(entity);
            return;
        }

        if (entity.isAlive())
            processFingerEaten(world, x, y, z, entity, false, eligibility.levelControl());
    }

    private static EligibilityResult checkEligibility(Entity entity) {
        JujutsucraftModVariables.PlayerVariables vars = getPlayerVar(entity);
        double ct = vars.PlayerCurseTechnique;
        double ct2 = vars.PlayerCurseTechnique2;

        if (vars.PlayerCursePowerFormer == 0.0) return new EligibilityResult(true, 2.0);

        if (ct == 21.0 || ct2 == 21.0 || vars.BodyItem.getItem() == JujutsucraftModItems.SUKUNA_FINGER.get())
            return new EligibilityResult(true, 1.0);

        boolean isCreative = entity instanceof Player p && p.getAbilities().instabuild;
        boolean hasSukunaEffect = entity instanceof LivingEntity l && l.hasEffect((MobEffect) JujutsucraftModMobEffects.SUKUNA_EFFECT.get());
        boolean hasValidCT = ct == 1.0 || ct2 == 1.0 || ct == 6.0 || ct2 == 6.0;

        if (isCreative || hasSukunaEffect || hasValidCT) return new EligibilityResult(true, 0.0);

        return new EligibilityResult(false, 0.0);
    }

    private static void rejectFinger(Entity entity) {
        boolean isCreative = entity instanceof Player p && p.getAbilities().instabuild;
        if (!isCreative && entity instanceof Player player) {
            ItemStack finger = new ItemStack(JujutsucraftModItems.SUKUNA_FINGER.get());
            finger.setCount(1);
            ItemHandlerHelper.giveItemToPlayer(player, finger);
        }
        if (entity instanceof LivingEntity living) {
            living.removeEffect((MobEffect) JujutsucraftModMobEffects.INFINITY_EFFECT.get());
            living.removeEffect((MobEffect) JujutsucraftModMobEffects.ZONE.get());
        }
        runCommand(entity, "kill @s");
    }

    private static void processFingerEaten(LevelAccessor world, double x, double y, double z, Entity entity, boolean isCursedSpirit, double levelControl) {
        double fingerCount = 0;

        if (!isCursedSpirit && levelControl < 2.0) {
            fingerCount = incrementFingerCount(entity);
            notifyFingerCount(entity);
        }

        if (fingerCount > 0 || isCursedSpirit) {
            runCommand(entity, "advancement revoke @s only jujutsucraft:update");
            awardAdvancement(entity, "jujutsucraft:update");

            if (isCursedSpirit) {
                double newPowerChange = getPlayerVar(entity).PlayerCursePowerChange + 1000.0;
                entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                        .ifPresent(cap -> { cap.PlayerCursePowerChange = newPowerChange; cap.syncPlayerVariables(entity); });
            } else if (!(entity instanceof LivingEntity l && l.hasEffect((MobEffect) JujutsucraftModMobEffects.SUKUNA_EFFECT.get()))) {
                SukunaEffectResult result = computeSukunaEffect(entity, fingerCount, levelControl);
                applyMarkerEntity(world, entity, result);
            }
        }

        if (!isCursedSpirit) {
            awardAdvancement(entity, "jujutsucraft:observation_of_the_soul");
            if (levelControl == 0.0)
                entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                        .ifPresent(cap -> { cap.flag_sukuna = true; cap.syncPlayerVariables(entity); });
        }

        WhenRespawnProcedure.execute(world, x, y, z, entity);
        JujutsucraftaddonModVariables.MapVariables.get(world).Sukuna = 1;
        JujutsucraftaddonModVariables.MapVariables.get(world).syncData(world);
    }

    private record SukunaEffectResult(boolean shouldApplySukuna, double tick, double unluckLevel) {}

    private static SukunaEffectResult computeSukunaEffect(Entity entity, double fingerCount, double levelControl) {
        int unluckAmplifier = 0;
        if (entity instanceof LivingEntity living && living.hasEffect(MobEffects.UNLUCK))
            unluckAmplifier = living.getEffect(MobEffects.UNLUCK).getAmplifier() + 1;
        double unluck = Math.min(unluckAmplifier, 9.0);

        boolean hasSukunaEffect = entity instanceof LivingEntity l && l.hasEffect((MobEffect) JujutsucraftModMobEffects.SUKUNA_EFFECT.get());

        if (levelControl == 2.0) return new SukunaEffectResult(false, 300.0, unluck);
        if (levelControl == 0.0) return new SukunaEffectResult(true, 1_000_000.0, unluck);

        boolean triggersOnFirstFinger = fingerCount == 1.0 || unluck >= 9.0 || hasSukunaEffect;
        return new SukunaEffectResult(triggersOnFirstFinger, 300.0, unluck);
    }

    private static void applyMarkerEntity(LevelAccessor world, Entity entity, SukunaEffectResult result) {
        double markerY = -300.0 - Math.random();
        Vec3 markerPos = new Vec3(entity.getX(), markerY, entity.getZ());

        if (result.shouldApplySukuna() && world instanceof ServerLevel level) {
            Entity pig = EntityType.PIG.spawn(level, BlockPos.containing(entity.getX(), markerY, entity.getZ()), MobSpawnType.MOB_SUMMONED);
            if (pig != null) pig.setYRot(world.getRandom().nextFloat() * 360f);
        }

        boolean foundPig = killFirstMatchingEntity(world, markerPos, Pig.class);
        if (foundPig) {
            if (entity instanceof LivingEntity living) living.removeEffect(MobEffects.UNLUCK);
            applySukunaEffectCommand(entity, result.tick());
        }

        if (result.unluckLevel() > 0 && world instanceof ServerLevel level) {
            Entity cow = EntityType.COW.spawn(level, BlockPos.containing(entity.getX(), markerY, entity.getZ()), MobSpawnType.MOB_SUMMONED);
            if (cow != null) cow.setYRot(world.getRandom().nextFloat() * 360f);
        }

        boolean foundCow = killFirstMatchingEntity(world, markerPos, Cow.class);
        if (foundCow)
            runCommand(entity, "effect give @s minecraft:unluck 180 " + Math.round(result.unluckLevel()) + " true");
    }

    private static <T extends Entity> boolean killFirstMatchingEntity(LevelAccessor world, Vec3 center, Class<T> type) {
        List<Entity> nearby = world.getEntitiesOfClass(Entity.class, new AABB(center, center).inflate(0.5), e -> true)
                .stream().sorted(Comparator.comparingDouble(e -> e.distanceToSqr(center))).toList();
        for (Entity e : nearby) {
            if (type.isInstance(e) && e.isAlive()) {
                runCommand(e, "kill @s");
                if (!e.level().isClientSide()) e.discard();
                return true;
            }
        }
        return false;
    }

    private static void applySukunaEffectCommand(Entity entity, double tick) {
        if (entity.level().isClientSide() || entity.getServer() == null) return;
        String duration = tick > 10000.0 ? "infinite" : String.valueOf(Math.round(Math.max(tick, computeCurrentSukunaFloor(entity))));
        long level = Math.round(Math.min(Math.max(GetSukunaLevelProcedure.execute(entity), 1.0), 20.0) - 1.0);
        runCommand(entity, "effect give @s jujutsucraft:sukuna_effect " + duration + " " + level + " true");
    }

    private static double computeCurrentSukunaFloor(Entity entity) {
        if (entity instanceof LivingEntity l && l.hasEffect((MobEffect) JujutsucraftModMobEffects.SUKUNA_EFFECT.get()))
            return Math.floor(l.getEffect((MobEffect) JujutsucraftModMobEffects.SUKUNA_EFFECT.get()).getAmplifier() / 20.0);
        return 0;
    }

    private static double incrementFingerCount(Entity entity) {
        JujutsucraftModVariables.PlayerVariables vars = getPlayerVar(entity);
        if (vars.BodyItem.getItem() == JujutsucraftModItems.DEATH_PAINTING.get() && entity instanceof Player p && !p.level().isClientSide())
            p.displayClientMessage(Component.literal(Component.translatable("jujutsu.message.death_painting").getString()), false);

        if (vars.BodyItem.getItem() == JujutsucraftModItems.SUKUNA_FINGER.get()) {
            vars.BodyItem.setCount(Math.min(vars.BodyItem.getCount() + 1, 20));
        } else {
            ItemStack finger = new ItemStack(JujutsucraftModItems.SUKUNA_FINGER.get());
            entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                    .ifPresent(cap -> { cap.BodyItem = finger.copy(); cap.syncPlayerVariables(entity); });
        }
        return getPlayerVar(entity).BodyItem.getCount();
    }

    private static void notifyFingerCount(Entity entity) {
        if (entity instanceof Player player && !player.level().isClientSide()) {
            String name = Component.translatable("item.jujutsucraft.sukuna_finger").getString();
            int count = Math.round(getPlayerVar(entity).BodyItem.getCount());
            player.displayClientMessage(Component.literal(name + ": " + count), false);
        }
    }

    private static void awardAdvancement(Entity entity, String advancementId) {
        if (!(entity instanceof ServerPlayer player)) return;
        Advancement adv = player.server.getAdvancements().getAdvancement(new ResourceLocation(advancementId));
        if (adv == null) return;
        AdvancementProgress ap = player.getAdvancements().getOrStartProgress(adv);
        if (!ap.isDone()) ap.getRemainingCriteria().forEach(c -> player.getAdvancements().award(adv, c));
    }

    private static void runCommand(Entity entity, String command) {
        if (entity.level().isClientSide() || entity.getServer() == null) return;
        entity.getServer().getCommands().performPrefixedCommand(
                new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(),
                        entity.level() instanceof ServerLevel sl ? sl : null, 4,
                        entity.getName().getString(), entity.getDisplayName(), entity.level().getServer(), entity),
                command);
    }

    private static JujutsucraftModVariables.PlayerVariables getPlayerVar(Entity entity) {
        return entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .orElse(new JujutsucraftModVariables.PlayerVariables());
    }
}