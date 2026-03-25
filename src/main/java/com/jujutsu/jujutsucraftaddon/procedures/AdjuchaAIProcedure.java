package com.jujutsu.jujutsucraftaddon.procedures;

import com.jujutsu.jujutsucraftaddon.entity.misc.AdjuchaEntity;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;

public class AdjuchaAIProcedure {

    public static void execute(LevelAccessor world, Entity sourceentity) {
        if (!(sourceentity instanceof AdjuchaEntity adjucha)) return;

        if (getForgeDouble(adjucha, "skill") == 2920) return;

        handleCursedTechniqueEffect(adjucha);

        double skill = getForgeDouble(adjucha, "skill");
        if (Math.random() >= 0.8) {
            handleHighRandomBranch(adjucha, skill);
        } else {
            handleLowRandomBranch(adjucha, skill);
        }
    }


    private static void handleHighRandomBranch(Entity entity, double skill) {
        if (skill != 0) return;

        double r = Math.random();
        if (r >= 0.9) {
            setForgeDouble(entity, "skill", randomSkill(1605, 1607));
            tryActivatePressZ(entity, true); // sets cnt5/cnt6
        } else if (r >= 0.8) {
            double skillVal = Math.random() < 0.4
                    ? randomSkill(-99, -98)
                    : randomSkill(1605, 1607);
            setForgeDouble(entity, "skill", skillVal);
            tryActivatePressZ(entity, false);
        } else if (r >= 0.7) {
            setForgeDouble(entity, "skill", randomSkill(-97, -96));
            tryActivatePressZ(entity, false);
        }
    }

    private static void handleLowRandomBranch(Entity entity, double skill) {
        double r = Math.random();
        if (r >= 0.7) {
            if (skill == 0) {
                setForgeDouble(entity, "skill", randomSkill(1605, 1607));
            }
        } else if (r >= 0.9) {
            if (skill == 0) {
                setForgeDouble(entity, "skill", randomSkill(-97, -96));
                ensurePressZSet(entity);
            }
            tryFireIfPressZ(entity, true);
        } else if (r >= 0.85) {
            if (skill == 0) {
                setForgeDouble(entity, "skill", randomSkill(-98, -96));
                ensurePressZSet(entity);
            }
            tryFireIfPressZ(entity, true);
        } else if (r >= 0.2) {
            if (skill == 0) {
                setForgeDouble(entity, "skill", -98);
                ensurePressZSet(entity);
            }
            tryFireIfPressZ(entity, true);
        }
    }

    private static void tryActivatePressZ(Entity entity, boolean withCounters) {
        ensurePressZSet(entity);
        tryFireIfPressZ(entity, withCounters);
    }

    /** Sets PRESS_Z to 1 only if it is currently 0. */
    private static void ensurePressZSet(Entity entity) {
        if (getForgeDouble(entity, "PRESS_Z") == 0) {
            setForgeDouble(entity, "PRESS_Z", 1);
        }
    }

    /** Fires the cursed technique command if PRESS_Z == 1, then resets state. */
    private static void tryFireIfPressZ(Entity entity, boolean withCounters) {
        if (getForgeDouble(entity, "PRESS_Z") != 1) return;

        runCommand(entity, "execute as @s run effect give @s jujutsucraft:cursed_technique 10 2 true");

        if (withCounters) {
            setForgeDouble(entity, "cnt5", 11);
            setForgeDouble(entity, "cnt6", 6);
        }
        setForgeDouble(entity, "PRESS_Z", 0);
    }


    private static void handleCursedTechniqueEffect(Entity entity) {
        if (!(entity instanceof LivingEntity living)) return;
        if (getForgeDouble(entity, "skill") < 1) return;
        if (living.hasEffect(JujutsucraftModMobEffects.CURSED_TECHNIQUE.get())) return;
        if (living.level().isClientSide()) return;

        living.addEffect(new MobEffectInstance(JujutsucraftModMobEffects.CURSED_TECHNIQUE.get(), 1, 1, false, false));
        living.addEffect(new MobEffectInstance(JujutsucraftModMobEffects.COOLDOWN_TIME.get(), 1, 1, false, false));
        setForgeDouble(entity, "PRESS_Z", 0);
    }


    private static double getForgeDouble(Entity entity, String key) {
        CompoundTag tag = new CompoundTag();
        entity.saveWithoutId(tag);
        return tag.getCompound("ForgeData").getDouble(key);
    }

    private static void setForgeDouble(Entity entity, String key, double value) {
        CompoundTag tag = new CompoundTag();
        entity.saveWithoutId(tag);
        tag.getCompound("ForgeData").putDouble(key, value);
        entity.load(tag);
    }


    private static int randomSkill(int min, int max) {
        return Mth.nextInt(RandomSource.create(), min, max);
    }

    private static void runCommand(Entity entity, String command) {
        if (entity.level().isClientSide() || entity.getServer() == null) return;

        entity.getServer().getCommands().performPrefixedCommand(
                new CommandSourceStack(
                        CommandSource.NULL,
                        entity.position(),
                        entity.getRotationVector(),
                        entity.level() instanceof ServerLevel sl ? sl : null,
                        4,
                        entity.getName().getString(),
                        entity.getDisplayName(),
                        entity.level().getServer(),
                        entity),
                command);
    }
}