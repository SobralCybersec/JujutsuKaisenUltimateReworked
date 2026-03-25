package com.jujutsu.jujutsucraftaddon.procedures;

import com.jujutsu.jujutsucraftaddon.network.JujutsucraftaddonModVariables;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DisplaySixEyes1Procedure {

    private static final float RAY_DISTANCE = 20f;
    private static final double SEARCH_RADIUS = 4;

    private record TechniqueRange(int min, int max, String name) {}

    private static final List<TechniqueRange> TECHNIQUE_TABLE = List.of(
        new TechniqueRange(1600, 1607, "Mahorga CT"),
        new TechniqueRange(200,  220,  "Gojo CT"),
        new TechniqueRange(2100, 2102, "Itadori CT"),
        new TechniqueRange(600,  620,  "Fushiguro CT"),
        new TechniqueRange(300,  320,  "Inumaki CT"),
        new TechniqueRange(-100, -96,  "Maki CT"),
        new TechniqueRange(500,  520,  "Okkotsu CT"),
        new TechniqueRange(2900, 2920, "Hakari CT"),
        new TechniqueRange(2000, 2020, "Todo CT"),
        new TechniqueRange(1900, 1920, "Naoya CT"),
        new TechniqueRange(2200, 2220, "Jinichi CT"),
        new TechniqueRange(2600, 2620, "Ogi CT"),
        new TechniqueRange(1300, 1320, "Nanami CT"),
        new TechniqueRange(1100, 1120, "Meimei CT"),
        new TechniqueRange(900,  920,  "Tsukumo CT"),
        new TechniqueRange(1800, 1820, "Geto CT"),
        new TechniqueRange(3100, 3105, "Kusakabe CT"),
        new TechniqueRange(2700, 2720, "Higuruma CT"),
        new TechniqueRange(1200, 1220, "Ishigori CT"),
        new TechniqueRange(700,  715,  "Kashimo CT"),
        new TechniqueRange(1700, 1715, "Takaba CT"),
        new TechniqueRange(2800, 2820, "Angel CT"),
        new TechniqueRange(3000, 3005, "Miguel CT"),
        new TechniqueRange(100,  120,  "Sukuna CT"),
        new TechniqueRange(2400, 2420, "Uraume CT"),
        new TechniqueRange(800,  820,  "Dagon CT"),
        new TechniqueRange(1400, 1420, "Hanami CT"),
        new TechniqueRange(1500, 1520, "Mahito CT"),
        new TechniqueRange(400,  420,  "Jogo CT"),
        new TechniqueRange(1000, 1020, "Choso CT"),
        new TechniqueRange(2300, 2320, "Kurourushi CT")
    );

    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null) return;

        LivingEntity target = findLookedAtEntity(entity);
        if (target == null || target == entity) return;

        displayPowerIfStrengthened(entity, target);

        if (target instanceof Player || target instanceof ServerPlayer) {
            displayPlayerTechnique(entity, target);
        } else {
            displayMobTechnique(entity, target);
        }
    }

    private static void displayPowerIfStrengthened(Entity observer, LivingEntity target) {
        if (!target.hasEffect(MobEffects.DAMAGE_BOOST)) return;
        int amplifier = target.getEffect(MobEffects.DAMAGE_BOOST).getAmplifier();
        if (amplifier <= 0) return;

        setCapabilityValue(observer, "Power: " + amplifier, false);
    }

    private static void displayMobTechnique(Entity observer, LivingEntity target) {
        double skill = getForgeDouble(target, "skill");
        if (skill <= 10) return;

        resolveNpcTechnique(skill).ifPresent(name ->
            setCapabilityValue(observer, "Technique: " + name, false)
        );
    }

    private static void displayPlayerTechnique(Entity observer, LivingEntity target) {
        String playerName = target.getDisplayName().getString();
        String playerTechnique = target
            .getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
            .orElse(new JujutsucraftModVariables.PlayerVariables())
            .PlayerSelectCurseTechniqueName;

        setCapabilityValue(observer, playerName + " Technique: " + playerTechnique, true);
    }

    private static Optional<String> resolveNpcTechnique(double skill) {
        for (TechniqueRange range : TECHNIQUE_TABLE) {
            if (skill == Mth.nextInt(RandomSource.create(), range.min(), range.max())) {
                return Optional.of(range.name());
            }
        }
        return Optional.empty();
    }

    private static LivingEntity findLookedAtEntity(Entity entity) {
        Vec3 hitPos = getLookHitPosition(entity);
        AABB searchBox = AABB.ofSize(hitPos, SEARCH_RADIUS, SEARCH_RADIUS, SEARCH_RADIUS);

        return entity.level()
            .getEntitiesOfClass(LivingEntity.class, searchBox, e -> true)
            .stream()
            .min(Comparator.comparingDouble(e -> e.distanceToSqr(hitPos)))
            .orElse(null);
    }

    private static Vec3 getLookHitPosition(Entity entity) {
        Vec3 from = entity.getEyePosition(1f);
        Vec3 to = from.add(entity.getViewVector(1f).scale(RAY_DISTANCE));
        BlockHitResult hit = entity.level().clip(
            new ClipContext(from, to, ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, entity)
        );
        return Vec3.atLowerCornerOf(hit.getBlockPos());
    }

    private static double getForgeDouble(Entity entity, String key) {
        CompoundTag tag = new CompoundTag();
        entity.saveWithoutId(tag);
        return tag.getCompound("ForgeData").getDouble(key);
    }

    private static void setCapabilityValue(Entity entity, String value, boolean isTechnique) {
        entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null)
            .ifPresent(cap -> {
                if (isTechnique) {
                    cap.Technique = value;
                } else {
                    cap.DataSixEyesOne = value;
                }
                cap.syncPlayerVariables(entity);
            });
    }
}