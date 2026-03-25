package com.jujutsu.jujutsucraftaddon.procedures;

import net.mcreator.jujutsucraft.init.JujutsucraftModEntities;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import java.util.function.Supplier;

import java.util.Map;

public class MeditationStateEffectStartedappliedProcedure {

    private static final int SPIRIT_MAX_HEALTH = 900;

    private static final Map<Integer, Supplier<? extends EntityType<?>>> TECHNIQUE_ENTITY_MAP =
    Map.ofEntries(
        Map.entry(-1, JujutsucraftModEntities.FUSHIGURO_TOJI),
        Map.entry(1,  JujutsucraftModEntities.SUKUNA),
        Map.entry(2,  JujutsucraftModEntities.GOJO_SATORU),
        Map.entry(3,  JujutsucraftModEntities.INUMAKI_TOGE),
        Map.entry(4,  JujutsucraftModEntities.JOGO),
        Map.entry(5,  JujutsucraftModEntities.OKKOTSU_YUTA_CULLING_GAME),
        Map.entry(6,  JujutsucraftModEntities.FUSHIGURO_MEGUMI_SHIBUYA),
        Map.entry(7,  JujutsucraftModEntities.KASHIMO_HAJIME),
        Map.entry(8,  JujutsucraftModEntities.DAGON),
        Map.entry(9,  JujutsucraftModEntities.TSUKUMO_YUKI),
        Map.entry(10, JujutsucraftModEntities.CHOSO),
        Map.entry(11, JujutsucraftModEntities.MEI_MEI),
        Map.entry(12, JujutsucraftModEntities.ISHIGORI_RYU),
        Map.entry(13, JujutsucraftModEntities.NANAMI_KENTO),
        Map.entry(14, JujutsucraftModEntities.HANAMI),
        Map.entry(15, JujutsucraftModEntities.MAHITO),
        Map.entry(16, JujutsucraftModEntities.EIGHT_HANDLED_SWROD_DIVERGENT_SILA_DIVINE_GENERAL_MAHORAGA),
        Map.entry(17, JujutsucraftModEntities.TAKABA_FUMIHIKO),
        Map.entry(18, JujutsucraftModEntities.GETO_SUGURU_CURSE_USER),
        Map.entry(19, JujutsucraftModEntities.ZENIN_NAOYA),
        Map.entry(20, JujutsucraftModEntities.TODO_AOI),
        Map.entry(21, JujutsucraftModEntities.ITADORI_YUJI_SHINJUKU),
        Map.entry(22, JujutsucraftModEntities.ZENIN_JINICHI),
        Map.entry(24, JujutsucraftModEntities.URAUME),
        Map.entry(25, JujutsucraftModEntities.CURSED_SPIRIT_GRADE_01),
        Map.entry(26, JujutsucraftModEntities.ZENIN_OGI),
        Map.entry(27, JujutsucraftModEntities.HIGURUMA_HIROMI),
        Map.entry(28, JujutsucraftModEntities.KURUSU_HANA),
        Map.entry(29, JujutsucraftModEntities.HAKARI_KINJI),
        Map.entry(30, JujutsucraftModEntities.MIGUEL_DANCER),
        Map.entry(31, JujutsucraftModEntities.KUSAKABE_ATSUYA),
        Map.entry(32, JujutsucraftModEntities.ZENIN_CHOJURO),
        Map.entry(33, JujutsucraftModEntities.YAGA_MASAMICHI),
        Map.entry(34, JujutsucraftModEntities.NOBARA_KUGISAKI),
        Map.entry(35, JujutsucraftModEntities.YOSHINO_JUNPEI),
        Map.entry(36, JujutsucraftModEntities.NISHIMIYA_MOMO),
        Map.entry(37, JujutsucraftModEntities.DHRUV_LAKDAWALLA),
        Map.entry(38, JujutsucraftModEntities.URO_TAKAKO),
        Map.entry(39, JujutsucraftModEntities.YOROZU)
    );

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null)
            return;

        if (entity instanceof Player player && !player.level().isClientSide())
            player.displayClientMessage(Component.literal("Your Training With Your Inner-Spirit Starts Now... Good Luck!"), false);

        if (!(world instanceof ServerLevel serverLevel))
            return;

        int technique = (int) entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
            .orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCurseTechnique2;

        var entityTypeSupplier = TECHNIQUE_ENTITY_MAP.get(technique);
        if (entityTypeSupplier == null)
            return;

        Entity spawned = entityTypeSupplier.get().spawn(serverLevel, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
        if (spawned == null)
            return;

        spawned.setYRot(world.getRandom().nextFloat() * 360.0F);
        setForgeData(spawned, "Spirit", 1);

        if (technique == 6) {
            setForgeData(spawned, "TenShadowsTechnique14", -2);
            setForgeData(spawned, "TenShadowsTechnique13", -2);
        }

        if (spawned instanceof LivingEntity living) {
            if (!living.level().isClientSide())
                living.addEffect(new MobEffectInstance(MobEffects.REGENERATION, -1, 253, false, false));
            living.getAttribute(Attributes.MAX_HEALTH).setBaseValue(SPIRIT_MAX_HEALTH);
            living.setHealth(living.getMaxHealth());
        }

        serverLevel.addFreshEntity(spawned);
    }

    private static void setForgeData(Entity entity, String key, double value) {
        CompoundTag tag = new CompoundTag();
        entity.saveWithoutId(tag);
        tag.getCompound("ForgeData").putDouble(key, value);
        entity.load(tag);
    }
}
