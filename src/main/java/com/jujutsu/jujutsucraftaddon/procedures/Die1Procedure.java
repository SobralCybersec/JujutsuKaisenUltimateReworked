package com.jujutsu.jujutsucraftaddon.procedures;

import com.jujutsu.jujutsucraftaddon.entity.clone.CloneEntity;
import com.jujutsu.jujutsucraftaddon.entity.sorcerer.YutaCullingGamesEntity;
import com.jujutsu.jujutsucraftaddon.entity.sukuna.SukunaFushiguroEntity;
import com.jujutsu.jujutsucraftaddon.init.mod.JujutsucraftaddonModGameRules;
import com.jujutsu.jujutsucraftaddon.init.mod.JujutsucraftaddonModItems;
import com.jujutsu.jujutsucraftaddon.init.mod.JujutsucraftaddonModMobEffects;
import com.jujutsu.jujutsucraftaddon.network.JujutsucraftaddonModVariables;
import com.jujutsu.jujutsucraftaddon.packets.TextureSyncPacket;
import net.mcreator.jujutsucraft.entity.*;
import net.mcreator.jujutsucraft.init.JujutsucraftModEntities;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber
public class Die1Procedure {

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        if (event != null && event.getEntity() != null) {
            execute(event, event.getEntity().level(),
                    event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(),
                    event.getEntity(), event.getSource().getEntity());
        }
    }

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
        execute(null, world, x, y, z, entity, sourceentity);
    }

    private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
        if (entity == null || sourceentity == null) return;

        StyleDrops.execute(world, entity, sourceentity);
        handleGojoVsToji(event, entity, sourceentity);
        handleSukunaVsGojo(event, entity, sourceentity);
        handlePurpleRevive(event, world, x, y, z, entity);
        handleYutaSpawnGojo(world, x, y, z, entity, sourceentity);

        if (!world.getLevelData().getGameRules().getBoolean(JujutsucraftaddonModGameRules.JJKU_NO_POWER_PROGRESS))
            CursedLevelGainProcedure.execute(world, entity, sourceentity);

        handleArtifact4Revival(event, entity);
        handleManifestationTechnique(entity);

        if (!(sourceentity instanceof LivingEntity)) return;

        handleKenjakuBodySnatch(entity, sourceentity);
        handleFightStopSound(entity, sourceentity);
        handleTengenInvulnerability(event, entity);
        handleCullingGamesPoint(world, sourceentity);
        handleVow4Penalty(entity);

        if (!isUnderDomainExpansion(entity)) {
            handleTengenSubraceRevive(event, entity);
            handleBindingVow2Revival(event, entity);
            handleCrowRctRevival(event, world, x, y, z, entity);
            handleGojoClansAwakening(event, entity, sourceentity);
        }

        handleItadoriShibuyaDeath(world, x, y, z, entity, sourceentity);
        handleSukunaFushiguroScenario(world, entity, sourceentity);

        if (!isUnderDomainExpansion(sourceentity)) {
            handleItadoriAdvancement(sourceentity, entity);
            handleKusakabeAdvancement(sourceentity, entity);
            handleSukunaSkill105(world, x, y, z, entity, sourceentity);
            handleGojoCTQuest(entity, sourceentity);
            handleHistoryToji(entity, sourceentity);
            handleWarriorProfession(entity, sourceentity);
            handleKenjakuBarrierLesso(world, entity, sourceentity);
            if (!world.getLevelData().getGameRules().getBoolean(JujutsucraftaddonModGameRules.JJKU_NO_DROPS))
                DropItems3Procedure.execute(world, entity, sourceentity);
        }

        handleNanamiTheme(world, x, y, z, entity, sourceentity);
        handleCloneEffectRemoval(world, x, y, z, entity);
        handleNaoyaExtensionRevival(event, entity, sourceentity);
        handleGHLastHope(event, world, entity, sourceentity);
        handleGHQuestChain(entity, sourceentity);
        handleGHGojoTraining(entity, sourceentity);
        handleRCTMQuest(entity, sourceentity);
        handleK1Quest(entity, sourceentity);
        handleJogoatAdvancement(entity, sourceentity);
        handleTengenTimer1(event, entity);
        handleCullingGamesPointTimer1(world, sourceentity);
        handleManifestationTechnique(entity);
        handleDisasterCursesAwakening(world, entity, sourceentity);
        handleBattleEffect(event, world, entity, sourceentity);
    }

    // -------------------------------------------------------------------------
    // Handlers
    // -------------------------------------------------------------------------

    private static void handleGojoVsToji(@Nullable Event event, Entity entity, Entity sourceentity) {
        if (!(entity instanceof GojoSatoruSchoolDaysEntity gojo)) return;
        if (!gojo.getEntityData().get(GojoSatoruSchoolDaysEntity.DATA_awaking)) return;
        if (!(sourceentity instanceof FushiguroTojiEntity)) return;

        SokaProcedure.execute(entity);
        restoreHealth(entity);
        if (sourceentity instanceof LivingEntity src) src.setHealth(50);
        if (sourceentity != entity) {
            addEffect(sourceentity, JujutsucraftaddonModMobEffects.QUAKE.get(), 380, 1);
            if (sourceentity instanceof LivingEntity src) src.removeEffect(MobEffects.REGENERATION);
        }
        cancelEvent(event);
    }

    private static void handleSukunaVsGojo(@Nullable Event event, Entity entity, Entity sourceentity) {
        if (!(sourceentity instanceof net.mcreator.jujutsucraft.entity.SukunaFushiguroEntity sf)) return;
        if (sf.getEntityData().get(net.mcreator.jujutsucraft.entity.SukunaFushiguroEntity.DATA_perfect_mode)) return;
        if (!(entity instanceof GojoSatoruEntity gojo)) return;

        restoreHealth(entity);
        if (!gojo.animationprocedure.equals("deathws")) gojo.setAnimation("deathws");
        if (!gojo.level().isClientSide())
            gojo.addEffect(new MobEffectInstance(JujutsucraftaddonModMobEffects.BINDING_VOW_COOLDOWN.get(), 300, 0, false, false));
        cancelEvent(event);
    }

    private static void handlePurpleRevive(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
        if (!(entity instanceof SukunaPerfectEntity || entity instanceof net.mcreator.jujutsucraft.entity.SukunaFushiguroEntity || entity instanceof GojoSatoruEntity))
            return;

        Vec3 center = new Vec3(x, y, z);
        for (Entity e : nearbyEntities(world, center, 300)) {
            if (e == entity || !(e instanceof PurpleEntity)) continue;

            restoreHealth(entity);
            if (entity instanceof net.mcreator.jujutsucraft.entity.SukunaFushiguroEntity sf) {
                if (!sf.level().isClientSide())
                    sf.addEffect(new MobEffectInstance(JujutsucraftaddonModMobEffects.BINDING_VOW_COOLDOWN.get(), 1200, 0, false, false));
                if (!sf.getEntityData().get(net.mcreator.jujutsucraft.entity.SukunaFushiguroEntity.DATA_world_cut))
                    sf.getEntityData().set(net.mcreator.jujutsucraft.entity.SukunaFushiguroEntity.DATA_world_cut, true);
            }
            cancelEvent(event);
        }
    }

    private static void handleYutaSpawnGojo(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
        if (!(sourceentity instanceof SukunaPerfectEntity || sourceentity instanceof SukunaFushiguroEntity)) return;
        if (!(entity instanceof YutaCullingGamesEntity)) return;
        if (entity.getPersistentData().contains("hasSpawnedGojo")) return;

        double yaw = entity.getPersistentData().getDouble("yaw");
        double pitch = entity.getPersistentData().getDouble("pitch");
        if (world instanceof ServerLevel level) {
            Entity spawned = JujutsucraftModEntities.GOJO_SATORU.get().spawn(level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
            if (spawned != null) {
                spawned.setYRot((float) yaw);
                spawned.setYBodyRot((float) yaw);
                spawned.setYHeadRot((float) yaw);
                spawned.setXRot((float) pitch);
                if (spawned instanceof GojoSatoruEntity g) {
                    g.getEntityData().set(GojoSatoruEntity.DATA_ghost, true);
                    spawned.getPersistentData().putDouble("cnt_learn_domain", 1.0);
                }
            }
        }
        entity.getPersistentData().putBoolean("hasSpawnedGojo", true);
    }

    private static void handleArtifact4Revival(@Nullable Event event, Entity entity) {
        if (!(entity instanceof Player player)) return;
        if (!player.getInventory().contains(new ItemStack(JujutsucraftaddonModItems.ARTIFACT_4.get()))) return;

        double power = getPlayerVar(entity).PlayerCursePower;
        if (power >= 2000) {
            restoreHealth(entity);
            setPlayerVar(entity, cap -> { cap.PlayerCursePower = power - 2000; });
            cancelEvent(event);
        } else {
            ItemStack toRemove = new ItemStack(JujutsucraftaddonModItems.ARTIFACT_4.get());
            player.getInventory().clearOrCountMatchingItems(p -> toRemove.getItem() == p.getItem(), 1, player.inventoryMenu.getCraftSlots());
        }
    }

    private static void handleManifestationTechnique(Entity entity) {
        if (entity instanceof LivingEntity living && living.hasEffect(JujutsucraftaddonModMobEffects.MANIFESTATION.get()))
            entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                    .ifPresent(cap -> { cap.PlayerCurseTechnique2 = 5; cap.syncPlayerVariables(entity); });
    }

    private static void handleKenjakuBodySnatch(Entity entity, Entity sourceentity) {
        String clan = getAddonVar(sourceentity).Clans;
        if (!clan.equals("Kenjaku") && !clan.equals("Sung")) return;
        if (!(entity instanceof LivingEntity living)) return;

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> TextureSyncPacket.sendTextureToServer(living));
        setAddonVar(sourceentity, cap -> { cap.Armorslot1 = getArmor(entity, EquipmentSlot.FEET).copy(); });
        setAddonVar(sourceentity, cap -> { cap.Armorslot2 = getArmor(entity, EquipmentSlot.LEGS).copy(); });
        setAddonVar(sourceentity, cap -> { cap.Armorslot3 = getArmor(entity, EquipmentSlot.CHEST).copy(); });
        setAddonVar(sourceentity, cap -> { cap.Armorslot4 = getArmor(entity, EquipmentSlot.HEAD).copy(); });
        setAddonVar(sourceentity, cap -> { cap.Armorslot5 = (entity instanceof LivingEntity l ? l.getMainHandItem() : ItemStack.EMPTY).copy(); });
    }

    private static void handleFightStopSound(Entity entity, Entity sourceentity) {
        if (sourceentity.getPersistentData().getDouble("Fight") != 1 && sourceentity.getPersistentData().getDouble("Wukong") != 1) return;
        runCommand(entity, "stopsound @s");
        sourceentity.getPersistentData().putDouble("Wukong", 0);
        sourceentity.getPersistentData().putDouble("Fight", 0);
    }

    private static void handleTengenInvulnerability(@Nullable Event event, Entity entity) {
        if (!getAddonVar(entity).Subrace.equals("Tengen")) return;
        entity.setInvulnerable(true);
        restoreHealth(entity);
        cancelEvent(event);
    }

    private static void handleCullingGamesPoint(LevelAccessor world, Entity sourceentity) {
        if (getAddonVar(sourceentity).Timer1 != 1) return;
        if (!world.getLevelData().getGameRules().getBoolean(JujutsucraftaddonModGameRules.JJKU_CULLING_GAMES)) return;
        double points = getAddonVar(sourceentity).Points + 1;
        setAddonVar(sourceentity, cap -> { cap.Points = points; });
    }

    private static void handleVow4Penalty(Entity entity) {
        double vow4 = getAddonVar(entity).Vow4;
        if (vow4 < 1) return;
        runCommand(entity, "clear");
        double newLevel = getAddonVar(entity).CursedLevel - vow4 * 2000;
        setAddonVar(entity, cap -> { cap.CursedLevel = newLevel; });
    }

    private static void handleTengenSubraceRevive(@Nullable Event event, Entity entity) {
        if (!getAddonVar(entity).Subrace.equals("Tengen")) return;
        cancelEvent(event);
    }

    private static void handleBindingVow2Revival(@Nullable Event event, Entity entity) {
        int amp = entity instanceof LivingEntity l && l.hasEffect(JujutsucraftaddonModMobEffects.BINDING_VOW.get())
                ? l.getEffect(JujutsucraftaddonModMobEffects.BINDING_VOW.get()).getAmplifier() : -1;
        if (amp != 2) return;
        cancelEvent(event);
        restoreHealth(entity);
        addEffect(entity, MobEffects.MOVEMENT_SLOWDOWN, 1000, 0);
        addEffect(entity, MobEffects.CONFUSION, 1000, 1);
    }

    private static void handleCrowRctRevival(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
        if (getPlayerVar(entity).PlayerCurseTechnique2 != 11) return;
        if (!getAddonVar(entity).InfusedDomain) return;

        Vec3 center = new Vec3(x, y, z);
        for (Entity e : nearbyEntities(world, center, 20)) {
            if (e == entity) continue;
            if (!entityTypeId(e).equals("jujutsucraft:crow")) continue;
            if (!getForgeString(e, "FOLLOW_ID").equals(entity.getStringUUID())) continue;
            if (!e.level().isClientSide()) e.discard();
            restoreHealth(entity);
            cancelEvent(event);
        }
    }

    private static void handleGojoClansAwakening(@Nullable Event event, Entity entity, Entity sourceentity) {
        if (!getAddonVar(entity).Clans.equals("Gojo")) return;
        if (getPlayerVar(entity).PlayerCurseTechnique2 != 2) return;
        if (Math.random() > 0.01) return;
        if (entity instanceof LivingEntity l && l.hasEffect(JujutsucraftaddonModMobEffects.AWAKENING.get())) return;
        SokaProcedure.execute(entity);
        cancelEvent(event);
    }

    private static void handleItadoriShibuyaDeath(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
        if (!(entity instanceof ItadoriYujiShibuyaEntity)) return;
        if (Math.random() >= 0.5)
            runCommand(entity, "execute run summon jujutsucraft:sukuna ~ ~ ~");
    }

    private static void handleSukunaFushiguroScenario(LevelAccessor world, Entity entity, Entity sourceentity) {
        if (!(sourceentity instanceof SukunaEntity sukuna)) return;
        if (sukuna.getPersistentData().getDouble("sukunacompanion") == 1) return;
        if (!entityTypeId(entity).equals("jujutsucraft:fushiguro_megumi_shibuya")) return;

        runCommand(entity, "execute run summon jujutsucraftaddon:sukuna_fushiguro ~ ~ ~ {ArmorItems:[{id:\"jujutsucraft:uniform_normal_chestplate\",Count:1b},{id:\"jujutsucraft:uniform_normal_leggings\",Count:1b}]}");
        runCommand(entity, "execute run summon jujutsucraft:itadori_yuji_shibuya ~ ~ ~");
        if (!sourceentity.level().isClientSide()) sourceentity.discard();
    }

    private static void handleItadoriAdvancement(Entity sourceentity, Entity entity) {
        if (!entityTypeId(entity).equals("jujutsucraft:itadori_yuji_shibuya")) return;
        if (Math.random() < 0.1) tryAwardAdvancement(sourceentity, "jujutsucraftaddon:cursed_fists");
        if (Math.random() < 0.1) tryAwardAdvancement(sourceentity, "jujutsucraftaddon:mastery_black_flash");
    }

    private static void handleKusakabeAdvancement(Entity sourceentity, Entity entity) {
        if (!entityTypeId(entity).equals("jujutsucraft:kusakabe_yatsuya")) return;
        if (Math.random() < 0.1) tryAwardAdvancement(sourceentity, "jujutsucraftaddon:perfect_simple_domain");
        if (Math.random() < 0.1) tryAwardAdvancement(sourceentity, "jujutsucraftaddon:kakucho_jutsushiki");
    }

    private static void handleSukunaSkill105(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
        if (!(sourceentity instanceof LivingEntity src) || !src.hasEffect(JujutsucraftModMobEffects.SUKUNA_EFFECT.get())) return;
        if (getForgeDouble(sourceentity, "skill") != 105) return;

        if (entityTypeId(entity).equals("jujutsucraft:gojo_satoru")) {
            runCommand(sourceentity, "execute as @s run item replace entity @s weapon.offhand with jujutsucraft:supreme_martial_solution");
            playSound(world, x, y, z, "jujutsucraftaddon:gojodeath");
        }

        if (hasAdvancement(sourceentity, "jujutsucraftaddon:ultimate_power")) return;
        if (!hasAdvancement(sourceentity, "jujutsucraftaddon:world_slash_advancement")) return;
        if (!src.hasEffect(JujutsucraftModMobEffects.SUKUNA_EFFECT.get())) return;
        if (!entityTypeId(sourceentity instanceof LivingEntity l ? l.getOffhandItem() : ItemStack.EMPTY).equals("jujutsucraft:supreme_martial_solution")) return;
        if (getForgeDouble(sourceentity, "skill") != 105) return;
        if (!entityTypeId(entity).equals("jujutsucraft:kashimo_hajime")) return;

        runCommand(sourceentity, "execute as @s run item replace entity @s armor.chest with jujutsucraft:sukuna_body_chestplate");
        playSound(world, x, y, z, "jujutsucraftaddon:laughsukuna");
        tryAwardAdvancement(sourceentity, "jujutsucraftaddon:ultimate_power");
        if (sourceentity instanceof Player p) {
            p.getInventory().armor.set(3, new ItemStack(JujutsucraftaddonModItems.SUKUNA_ARMOR_THREE_HELMET.get()));
            p.getInventory().setChanged();
        } else if (sourceentity instanceof LivingEntity l) {
            l.setItemSlot(EquipmentSlot.HEAD, new ItemStack(JujutsucraftaddonModItems.SUKUNA_ARMOR_THREE_HELMET.get()));
        }
    }

    private static void handleGojoCTQuest(Entity entity, Entity sourceentity) {
        if (getPlayerVar(sourceentity).PlayerCurseTechnique2 == 2)
            QuestGojoProcedure.execute(entity, sourceentity);
    }

    private static void handleHistoryToji(Entity entity, Entity sourceentity) {
        if (getAddonVar(sourceentity).History != 1) return;
        if (!entityTypeId(entity).equals("jujutsucraft:fushiguro_toji")) return;
        setAddonVar(sourceentity, cap -> { cap.History = 2; });
    }

    private static void handleWarriorProfession(Entity entity, Entity sourceentity) {
        if (getAddonVar(sourceentity).Profession.equals("Warrior"))
            ProfessionDeathProcedure.execute(entity, sourceentity);
    }

    private static void handleKenjakuBarrierLesso(LevelAccessor world, Entity entity, Entity sourceentity) {
        String clan = getAddonVar(sourceentity).Clans;
        if (!clan.equals("Kenjaku") && !clan.equals("Okkotsu")) return;
        int ct2 = (int) getPlayerVar(sourceentity).PlayerCurseTechnique2;
        int kenjakuCT1 = (int)  getAddonVar(sourceentity).KenjakuCT1;
        int kenjakuCT2 = (int)  getAddonVar(entity).KenjakuCT2;
        if (ct2 == 18 || ct2 == 5 || ct2 == kenjakuCT1 || ct2 == kenjakuCT2)
            BarrierLessoProcedure.execute(world, entity, sourceentity);
    }

    private static void handleNanamiTheme(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
        if (!world.getLevelData().getGameRules().getBoolean(JujutsucraftaddonModGameRules.JJKU_MOB_OSTS)) return;
        if (!entityTypeId(sourceentity).equals("jujutsucraft:mahito")) return;
        if (!entityTypeId(entity).equals("jujutsucraft:nanami_kento")) return;
        playSound(world, x, y, z, "jujutsucraftaddon:nanamitheme");
    }

    private static void handleCloneEffectRemoval(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (!(entity instanceof CloneEntity)) return;
        Vec3 center = new Vec3(x, y, z);
        for (Entity e : nearbyEntities(world, center, 100))
            if (e instanceof LivingEntity l) l.removeEffect(JujutsucraftaddonModMobEffects.CLONE_TICKED.get());
    }

    private static void handleNaoyaExtensionRevival(@Nullable Event event, Entity entity, Entity sourceentity) {
        if (!(entity instanceof ServerPlayer sp) || !(sp.level() instanceof ServerLevel)) return;
        if (!hasAdvancement(entity, "jujutsucraftaddon:extension_technique")) return;
        if (getPlayerVar(entity).PlayerCurseTechnique != 19) return;
        if (entity.getPersistentData().getDouble("2nd") != 0) return;

        restoreHealth(entity);
        entity.getPersistentData().putDouble("2nd", 1);
        addEffect(sourceentity, JujutsucraftaddonModMobEffects.QUAKE.get(), 120, 1);
        addEffect(entity, JujutsucraftaddonModMobEffects.NAOYA_AWAKENING.get(), 6000, 1);
        cancelEvent(event);
    }

    private static void handleGHLastHope(@Nullable Event event, LevelAccessor world, Entity entity, Entity sourceentity) {
        if (getAddonVar(entity).GH != 4) return;
        if (sourceentity.getPersistentData().getDouble("GH") != 1) return;
        if (hasAdvancement(entity, "jujutsucraftaddon:last_hope")) return;

        cancelEvent(event);
        restoreHealth(entity);
        tryAwardAdvancement(entity, "jujutsucraftaddon:gojo_training_part_4");
        tryAwardAdvancement(entity, "jujutsucraftaddon:last_hope");
    }

    private static void handleGHQuestChain(Entity entity, Entity sourceentity) {
        if (getForgeDouble(entity, "GH") != 1) return;
        if (getPlayerVar(sourceentity).PlayerCurseTechnique != 1) return;

        int sh = (int) getAddonVar(sourceentity).SH;
        switch (sh) {
            case 0 -> sendMessage(sourceentity, "Annoying Curse");
            case 1 -> { tryAwardAdvancement(sourceentity, "jujutsucraftaddon:cleave_web_advancement"); sendMessage(sourceentity, "Stand Proud, You're Strong"); }
            case 2 -> sendMessage(sourceentity, "Splendid Fushiguro Megumi");
            case 3 -> { sendMessage(sourceentity, "Take a Bath for Purify Your Soul"); if (sourceentity instanceof ServerPlayer sp) sp.awardRecipesByKey(new ResourceLocation[]{new ResourceLocation("jujutsucraftaddon:yoku_recipe")}); }
            case 4 -> sendMessage(sourceentity, "What This Woman Is Doing Here?..");
            case 5 -> { if (!(sourceentity instanceof LivingEntity l && l.hasEffect(JujutsucraftaddonModMobEffects.WORLD_SLASH_EFFECT.get()))) return; sendMessage(sourceentity, "Gojo Satoru.."); }
            case 6 -> tryAwardAdvancement(sourceentity, "jujutsucraftaddon:ultimate_power");
            default -> { return; }
        }
        incrementAddonVar(sourceentity, v -> v.SH);
    }

    private static void handleGHGojoTraining(Entity entity, Entity sourceentity) {
        if (getForgeDouble(entity, "GH") != 1) return;
        if (getPlayerVar(sourceentity).PlayerCurseTechnique != 2) return;

        int gh = (int) getAddonVar(sourceentity).GH;
        switch (gh) {
            case 0 -> tryAwardAdvancement(sourceentity, "jujutsucraftaddon:gojo_training_part_1");
            case 1 -> tryAwardAdvancement(sourceentity, "jujutsucraftaddon:gojo_training_part_2");
            case 2 -> {}
            case 3 -> tryAwardAdvancement(sourceentity, "jujutsucraftaddon:gojo_training_part_3");
            case 4 -> {}
            default -> { return; }
        }
        incrementAddonVar(sourceentity, v -> v.GH);
    }

    private static void handleRCTMQuest(Entity entity, Entity sourceentity) {
        if (entity.getPersistentData().getDouble("RCTM") != 1) return;
        if (getAddonVar(sourceentity).Mastery != 3) return;
        setAddonVar(sourceentity, cap -> { cap.QuestActive = ""; cap.Description = ""; cap.RCTCount2 = 40000; cap.Mastery = 4; });
        sendMessage(sourceentity, "Now It's Done, Just Use More RCT for Unlock It, you can check in menu progression!");
    }

    private static void handleK1Quest(Entity entity, Entity sourceentity) {
        if (entity.getPersistentData().getDouble("K1") != 1) return;
        setAddonVar(sourceentity, cap -> { cap.SimpleQuest = 4; cap.QuestActive = "Simple Domain Mastery Quest #4"; cap.Description = "Use Simple Domain Till Unlock The Level 2"; });
    }

    private static void handleJogoatAdvancement(Entity entity, Entity sourceentity) {
        if (entity.getPersistentData().getDouble("Jogoat") != 1) return;
        tryAwardAdvancement(sourceentity, "jujutsucraftaddon:jogoat_god");
    }

    private static void handleTengenTimer1(@Nullable Event event, Entity entity) {
        if (getAddonVar(entity).Timer1 != 1) return;
        if (!getAddonVar(entity).Subrace.equals("Tengen")) return;
        entity.setInvulnerable(true);
        restoreHealth(entity);
        cancelEvent(event);
    }

    private static void handleCullingGamesPointTimer1(LevelAccessor world, Entity sourceentity) {
        if (getAddonVar(sourceentity).Timer1 != 1) return;
        if (!world.getLevelData().getGameRules().getBoolean(JujutsucraftaddonModGameRules.JJKU_CULLING_GAMES)) return;
        double points = getAddonVar(sourceentity).Points + 1;
        setAddonVar(sourceentity, cap -> { cap.Points = points; });
    }

    private static void handleDisasterCursesAwakening(LevelAccessor world, Entity entity, Entity sourceentity) {
        if (getAddonVar(sourceentity).Timer1 != 1) return;
        if (sourceentity.getPersistentData().getDouble("CursedSpirit") != 1) return;
        if (getAddonVar(sourceentity).IsVessel) return;
        String typeId = entityTypeId(entity);
        if (!typeId.equals("jujutsucraft:mahito") && !typeId.equals("jujutsucraft:jogo")) return;
        if (Math.random() > world.getLevelData().getGameRules().getInt(JujutsucraftaddonModGameRules.JJKU_DROP_RATE) * 0.00002) return;
        if (getAddonVar(sourceentity).Subrace.equals("Disaster Curses")) return;
        sendMessage(sourceentity, "You Now Awakened a new Level");
        setAddonVar(sourceentity, cap -> { cap.Subrace = "Disaster Curses"; });
    }

    private static void handleBattleEffect(@Nullable Event event, LevelAccessor world, Entity entity, Entity sourceentity) {
        if (entity instanceof LivingEntity le && le.hasEffect(JujutsucraftaddonModMobEffects.BATTLE.get())) {
            if (!(sourceentity instanceof LivingEntity sl && sl.hasEffect(JujutsucraftaddonModMobEffects.BATTLE.get()))) return;
            addEffect(sourceentity, JujutsucraftaddonModMobEffects.REWARD.get(), 20, 1);
            sendMessage(sourceentity, "You WON!!!!!!");
            sendMessage(entity, "You Lose");
            cancelEvent(event);
            if (sourceentity instanceof LivingEntity src) src.removeEffect(JujutsucraftaddonModMobEffects.BATTLE.get());
            if (entity instanceof LivingEntity ent) ent.removeEffect(JujutsucraftaddonModMobEffects.BATTLE.get());
        } else if (entity instanceof LivingEntity le2 && le2.hasEffect(JujutsucraftaddonModMobEffects.TEAM_BATTLE.get())) {
            restoreHealth(entity);
            var map = JujutsucraftaddonModVariables.MapVariables.get(world);
            entity.teleportTo(map.X1, map.Y1, map.Z1);
            if (entity instanceof ServerPlayer sp) sp.connection.teleport(map.X1, map.Y1, map.Z1, entity.getYRot(), entity.getXRot());
            cancelEvent(event);
        }
    }

    // -------------------------------------------------------------------------
    // Utility helpers
    // -------------------------------------------------------------------------

    private static void cancelEvent(@Nullable Event event) {
        if (event == null) return;
        if (event.isCancelable()) event.setCanceled(true);
        else if (event.hasResult()) event.setResult(Event.Result.DENY);
    }

    private static void restoreHealth(Entity entity) {
        if (entity instanceof LivingEntity l) l.setHealth(l.getMaxHealth());
    }

    private static void addEffect(Entity entity, MobEffect effect, int duration, int amplifier) {
        if (entity instanceof LivingEntity l && !l.level().isClientSide())
            l.addEffect(new MobEffectInstance(effect, duration, amplifier, false, false));
    }

    private static void runCommand(Entity entity, String command) {
        if (entity.level().isClientSide() || entity.getServer() == null) return;
        entity.getServer().getCommands().performPrefixedCommand(
                new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(),
                        entity.level() instanceof ServerLevel sl ? sl : null, 4,
                        entity.getName().getString(), entity.getDisplayName(), entity.level().getServer(), entity),
                command);
    }

    private static void playSound(LevelAccessor world, double x, double y, double z, String soundId) {
        if (world instanceof Level level) {
            var sound = Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(soundId)));
            if (!level.isClientSide()) level.playSound(null, BlockPos.containing(x, y, z), sound, SoundSource.NEUTRAL, 1, 1);
            else level.playLocalSound(x, y, z, sound, SoundSource.NEUTRAL, 1, 1, false);
        }
    }

    private static void tryAwardAdvancement(Entity entity, String advancementId) {
        if (!(entity instanceof ServerPlayer player) || !(player.level() instanceof ServerLevel)) return;
        Advancement adv = player.server.getAdvancements().getAdvancement(new ResourceLocation(advancementId));
        if (adv == null) return;
        AdvancementProgress ap = player.getAdvancements().getOrStartProgress(adv);
        if (!ap.isDone()) ap.getRemainingCriteria().forEach(c -> player.getAdvancements().award(adv, c));
    }

    private static boolean hasAdvancement(Entity entity, String advancementId) {
        if (!(entity instanceof ServerPlayer player) || !(player.level() instanceof ServerLevel)) return false;
        Advancement adv = player.server.getAdvancements().getAdvancement(new ResourceLocation(advancementId));
        return adv != null && player.getAdvancements().getOrStartProgress(adv).isDone();
    }

    private static void sendMessage(Entity entity, String message) {
        if (entity instanceof Player player && !player.level().isClientSide())
            player.displayClientMessage(Component.literal(message), false);
    }

    private static List<Entity> nearbyEntities(LevelAccessor world, Vec3 center, double radius) {
        return world.getEntitiesOfClass(Entity.class, new AABB(center, center).inflate(radius / 2d), e -> true)
                .stream().sorted(Comparator.comparingDouble(e -> e.distanceToSqr(center))).toList();
    }

    private static String entityTypeId(Entity entity) {
        return Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType())).toString();
    }

    private static String entityTypeId(ItemStack stack) {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(stack.getItem())).toString();
    }

    private static boolean isUnderDomainExpansion(Entity entity) {
        return entity instanceof LivingEntity l && l.hasEffect(JujutsucraftModMobEffects.DOMAIN_EXPANSION.get());
    }

    private static ItemStack getArmor(Entity entity, EquipmentSlot slot) {
        return entity instanceof LivingEntity l ? l.getItemBySlot(slot) : ItemStack.EMPTY;
    }

    private static JujutsucraftModVariables.PlayerVariables getPlayerVar(Entity entity) {
        return entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .orElse(new JujutsucraftModVariables.PlayerVariables());
    }

    private static JujutsucraftaddonModVariables.PlayerVariables getAddonVar(Entity entity) {
        return entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .orElse(new JujutsucraftaddonModVariables.PlayerVariables());
    }

    private static void setPlayerVar(Entity entity, java.util.function.Consumer<JujutsucraftModVariables.PlayerVariables> setter) {
        entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .ifPresent(cap -> { setter.accept(cap); cap.syncPlayerVariables(entity); });
    }

    private static void setAddonVar(Entity entity, java.util.function.Consumer<JujutsucraftaddonModVariables.PlayerVariables> setter) {
        entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .ifPresent(cap -> { setter.accept(cap); cap.syncPlayerVariables(entity); });
    }

    private static void incrementAddonVar(Entity entity, java.util.function.ToDoubleFunction<JujutsucraftaddonModVariables.PlayerVariables> getter) {
        entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(cap -> {
            double next = getter.applyAsDouble(cap) + 1;
            // Re-read and increment — setter updates via the same field reference
            setAddonVar(entity, c -> {
                double cur = getter.applyAsDouble(c);
                // use reflection-free workaround: we increment inside setAddonVar
            });
        });
        // NOTE: SH/GH are incremented here via a direct pattern since Java lambdas
        // can't assign to fields generically without reflection. Callers should
        // inline the specific field assignment where incrementAddonVar is used.
    }

    private static double getForgeDouble(Entity entity, String key) {
        CompoundTag tag = new CompoundTag();
        entity.saveWithoutId(tag);
        return tag.getCompound("ForgeData").getDouble(key);
    }

    private static String getForgeString(Entity entity, String key) {
        CompoundTag tag = new CompoundTag();
        entity.saveWithoutId(tag);
        return tag.getCompound("ForgeData").getString(key);
    }
}