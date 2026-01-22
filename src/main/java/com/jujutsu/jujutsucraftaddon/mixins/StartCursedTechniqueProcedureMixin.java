package com.jujutsu.jujutsucraftaddon.mixins;

import com.jujutsu.jujutsucraftaddon.init.mod.JujutsucraftaddonModGameRules;
import com.jujutsu.jujutsucraftaddon.init.mod.JujutsucraftaddonModMobEffects;
import com.jujutsu.jujutsucraftaddon.network.JujutsucraftaddonModVariables;
import com.jujutsu.jujutsucraftaddon.procedures.BurnoutKeyOnKeyPressedProcedure;
import com.jujutsu.jujutsucraftaddon.procedures.CloneMeteor;
import com.jujutsu.jujutsucraftaddon.procedures.TechniqueRegistry;
import net.mcreator.jujutsucraft.init.JujutsucraftModGameRules;
import net.mcreator.jujutsucraft.init.JujutsucraftModItems;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.*;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;

@Mixin(value = StartCursedTechniqueProcedure.class, priority = -10000)
public abstract class StartCursedTechniqueProcedureMixin {
    public StartCursedTechniqueProcedureMixin() {

    }


    /**
     * @author Satushi
     * @reason Modify the cursed techniques numbers for change the utility, has extensions and more
     */


    @Inject(at = @At("HEAD"), method = "execute", remap = false, cancellable = true)
    private static void execute(LevelAccessor world, double x, double y, double z, Entity entity, CallbackInfo ci) {
        ci.cancel();

        if (entity != null) {
            String STR1 = "";
            boolean logic_a = false;
            boolean CREATIVE = false;
            boolean item_use = false;
            boolean noUseCursePower = false;
            ItemStack item_a = ItemStack.EMPTY;
            double Level = 0.0;
            double Tick = 0.0;
            double NUM1 = 0.0;
            double skill = 0.0;
            double T1 = 0.0;
            double S1 = 0.0;
            double T2 = 0.0;
            double cost = 0.0;
            ItemStack var10000;
            LivingEntity _livEnt11;
            if (entity instanceof LivingEntity) {
                _livEnt11 = (LivingEntity)entity;
                var10000 = _livEnt11.getMainHandItem();
            } else {
                var10000 = ItemStack.EMPTY;
            }

            LivingEntity _livEnt12;
            LivingEntity _livEnt13;
            if (var10000.getOrCreateTag().contains("used_item")) {
                if (entity instanceof LivingEntity) {
                    _livEnt12 = (LivingEntity)entity;
                    var10000 = _livEnt12.getMainHandItem();
                } else {
                    var10000 = ItemStack.EMPTY;
                }

                if (var10000.getOrCreateTag().getBoolean("used_item")) {
                    if (entity instanceof LivingEntity) {
                        _livEnt13 = (LivingEntity)entity;
                        var10000 = _livEnt13.getMainHandItem();
                    } else {
                        var10000 = ItemStack.EMPTY;
                    }

                    var10000.getOrCreateTag().putBoolean("used_item", false);
                    if (entity instanceof LivingEntity) {
                        _livEnt13 = (LivingEntity)entity;
                        var10000 = _livEnt13.getMainHandItem();
                    } else {
                        var10000 = ItemStack.EMPTY;
                    }

                    item_a = var10000.copy();
                    item_use = true;
                }
            }

            if (item_use) {
                STR1 = item_a.getDisplayName().getString();
                skill = item_a.getOrCreateTag().getDouble("skill");
                cost = item_a.getOrCreateTag().getDouble("COOLDOWN_TICKS");
                if (item_a.getItem() == JujutsucraftModItems.SWORD_OKKOTSU_YUTA.get()) {
                    noUseCursePower = true;
                }
            } else {
                boolean _setval = true;
                entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent((capability) -> {
                    capability.noChangeTechnique = _setval;
                    capability.syncPlayerVariables(entity);
                });
                TechniqueRegistry.TechniqueConfig config = TechniqueRegistry.getByName(STR1);

                if (config == null) {
                    KeyChangeTechniqueOnKeyPressedProcedure.execute(world, x, y, z, entity);
                }

            }


            Player _player;
            label359: {
                if (item_use) {
                    label370: {
                        if (entity instanceof LivingEntity) {
                            _livEnt11 = (LivingEntity)entity;
                            if (_livEnt11.hasEffect((MobEffect)JujutsucraftModMobEffects.CURSED_TECHNIQUE.get())) {
                                break label359;
                            }
                        }

                        label371: {
                            if (entity instanceof LivingEntity) {
                                _livEnt12 = (LivingEntity)entity;
                                if (_livEnt12.hasEffect((MobEffect)JujutsucraftModMobEffects.COOLDOWN_TIME.get())) {
                                    break label371;
                                }
                            }

                            if (!(entity instanceof LivingEntity)) {
                                break label370;
                            }

                            _livEnt13 = (LivingEntity)entity;
                            if (!_livEnt13.hasEffect((MobEffect)JujutsucraftModMobEffects.UNSTABLE.get())) {
                                break label370;
                            }
                        }

                        if (!noUseCursePower) {
                            break label359;
                        }
                    }
                } else if (!LogicStartProcedure.execute(entity)) {
                    break label359;
                }

                if (!((new Object() {
                    public boolean checkGamemode(Entity _ent) {
                        if (_ent instanceof ServerPlayer _serverPlayer) {
                            return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.SPECTATOR;
                        } else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
                            return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null && Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.SPECTATOR;
                        } else {
                            return false;
                        }
                    }
                })).checkGamemode(entity)) {
                    CREATIVE = ((new Object() {
                        public boolean checkGamemode(Entity _ent) {
                            if (_ent instanceof ServerPlayer _serverPlayer) {
                                return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
                            } else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
                                return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null && Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
                            } else {
                                return false;
                            }
                        }
                    })).checkGamemode(entity);
                    if (noUseCursePower) {
                        NUM1 = ((JujutsucraftModVariables.PlayerVariables) entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, (Direction) null).orElse(new JujutsucraftModVariables.PlayerVariables())).PlayerCursePower - 0.0;
                    } else {
                        NUM1 = ((JujutsucraftModVariables.PlayerVariables) entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, (Direction) null).orElse(new JujutsucraftModVariables.PlayerVariables())).PlayerCursePower - (item_use ? cost : ((JujutsucraftModVariables.PlayerVariables) entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, (Direction) null).orElse(new JujutsucraftModVariables.PlayerVariables())).PlayerSelectCurseTechniqueCost);
                    }

                    if (!(NUM1 >= 0.0) && !CREATIVE) {
                        if (entity instanceof Player) {
                            _player = (Player) entity;
                            if (!_player.level().isClientSide()) {
                                _player.displayClientMessage(Component.literal(Component.translatable("jujutsu.message.dont_use").getString()), true);
                            }

                            return;
                        }

                        return;
                    } else {
                        double _setval;
                        if (!CREATIVE) {
                            _setval = NUM1;
                            double final_setval = _setval;
                            entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, (Direction) null).ifPresent((capability) -> {
                                capability.PlayerCursePower = final_setval;
                                capability.syncPlayerVariables(entity);
                            });
                        }

                        var playerVars = entity.getCapability(
                                JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null
                        ).orElse(null);

                        var addonVars = entity.getCapability(
                                JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null
                        ).orElse(null);

                        if (playerVars == null || addonVars == null) return;

                        String techName = playerVars.PlayerSelectCurseTechniqueName;

                        if (item_use) {

                            T1 = Math.floor(skill / 100.0);
                            S1 = Math.floor(skill % 100.0);

                        } else if (entity instanceof LivingEntity living &&
                                living.hasEffect(JujutsucraftaddonModMobEffects.MURASAKI_EFFECT.get())) {

                            T1 = 2;
                            S1 = 15;

                        } else if (entity instanceof LivingEntity living &&
                                living.hasEffect(JujutsucraftaddonModMobEffects.WORLD_CUT.get())) {

                            T1 = 1;
                            S1 = 5;

                        } else {

                            if (playerVars.PlayerCurseTechnique2 == 18 && addonVars.InfusedDomain) {

                                if (techName.contains("Cursed Spirit Grade Semi 1 (Mammoth)")) {
                                    T1 = 1;
                                    S1 = 50;

                                } else if (techName.contains("Cursed Spirit Grade Semi 1 (Slug)")) {
                                    T1 = 1;
                                    S1 = 59;

                                } else if (techName.contains("Cursed Spirit Grade 1 (Blindness)")) {

                                    if (entity.isShiftKeyDown()) {
                                        T1 = 23;
                                        S1 = 5;
                                    } else {
                                        T1 = 1;
                                        S1 = Mth.nextInt(RandomSource.create(), 51, 52);
                                    }

                                } else if (techName.contains("Samurai")) {

                                    if (entity.isShiftKeyDown()) {
                                        T1 = 22;
                                        S1 = 4;
                                    } else {
                                        T1 = 15;
                                        S1 = 16;
                                    }

                                } else if (techName.contains("Tamamo-no-Mae Incarnate")) {

                                    T1 = 1;
                                    S1 = entity.isShiftKeyDown() ? 55 : 56;

                                } else if (techName.contains("Ganesha") && !entity.isShiftKeyDown()) {

                                    T1 = 1;
                                    S1 = 60;

                                } else if (techName.contains("Cursed Spirit Grade Semi 1 (Teruteru Bozu)") && !entity.isShiftKeyDown()) {

                                    T1 = 10;
                                    S1 = 5;

                                } else if (techName.contains("Cursed Spirit Grade 1 (Kuchisake-Onna)") && !entity.isShiftKeyDown()) {

                                    T1 = 1;
                                    S1 = 53;

                                } else if (techName.contains("Cursed Spirit Grade 1 (Forest)") && !entity.isShiftKeyDown()) {

                                    T1 = 1;
                                    S1 = 54;

                                } else if (techName.contains("Jogo")) {

                                    T1 = 4;
                                    S1 = entity.isShiftKeyDown() ? 7 : 5;

                                } else if (techName.contains("Mahito")) {

                                    T1 = 15;
                                    S1 = entity.isShiftKeyDown() ? 9 : 5;

                                } else if (techName.contains("Dagon")) {

                                    T1 = 8;
                                    S1 = entity.isShiftKeyDown() ? 10 : 9;

                                } else if (techName.contains("Zenin Naoya")) {

                                    T1 = 19;
                                    S1 = entity.isShiftKeyDown() ? 10 : 5;

                                } else if (techName.equals(Component.translatable("jujutsu.technique.attack1").getString())) {

                                    entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(cap -> {
                                        cap.PlayerSelectCurseTechniqueName = Component.translatable("jujutsu.technique.kaori1").getString();
                                        cap.syncPlayerVariables(entity);
                                    });

                                    T1 = 41;
                                    S1 = 10;

                                } else if (techName.equals(Component.translatable("jujutsu.technique.attack2").getString())) {

                                    entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(cap -> {
                                        cap.PlayerSelectCurseTechniqueName = "Anti-Gravity Push";
                                        cap.syncPlayerVariables(entity);
                                    });

                                    T1 = 1;
                                    S1 = 60;
                                }

                                STR1 = playerVars.PlayerSelectCurseTechniqueName;
                                T2 = playerVars.PlayerCurseTechnique2;

                            } else {

                                TechniqueRegistry.TechniqueConfig config =
                                        TechniqueRegistry.findByDisplayName(techName);

                                if (config != null) {

                                    T1 = config.t1();
                                    S1 = config.s1();

                                } else if ((techName.contains(Component.translatable("entity.jujutsucraft.divine_dog_white").getString())
                                        || techName.contains(Component.translatable("entity.jujutsucraft.divine_dog_black").getString()))
                                        && addonVars.InfusedDomain) {

                                    AttackBeastProcedure.execute(world, x, y, z, entity);

                                } else if (techName.contains(Component.translatable("entity.jujutsucraft.nue").getString())
                                        && addonVars.InfusedDomain) {

                                    T1 = 7;
                                    S1 = 5;

                                } else if (techName.contains(Component.translatable("entity.jujutsucraft.piercing_ox").getString())
                                        && addonVars.InfusedDomain) {

                                    AttackPiecingOxProcedure.execute(world, x, y, z, entity);

                                } else if (techName.contains(Component.translatable("entity.jujutsucraft.max_elephant").getString())
                                        && addonVars.InfusedDomain) {

                                    T1 = 8;
                                    S1 = 5;

                                } else {

                                    STR1 = techName;

                                    T1 = playerVars.SecondTechnique
                                            ? playerVars.PlayerCurseTechnique2
                                            : playerVars.PlayerCurseTechnique;

                                    S1 = playerVars.PlayerSelectCurseTechnique;
                                }
                            }
                        }


                        if (playerVars == null || addonVars == null) return;

                        LivingEntity living = entity instanceof LivingEntity ? (LivingEntity) entity : null;
                        ItemStack mainHand = living != null ? living.getMainHandItem() : ItemStack.EMPTY;


                        if (playerVars.PlayerSelectCurseTechniqueName.contains("Divine Meteor")) {

                            entity.getPersistentData().putDouble("skill", 415);
                            CloneMeteor.execute(world, x, y, z, entity);

                        } else {

                            entity.getPersistentData().putDouble(
                                    "skill",
                                    item_use ? skill : (T1 * 100.0 + S1)
                            );
                        }

                        ResetCounterProcedure.execute(entity);


                        if (living != null && !living.level().isClientSide()) {

                            living.addEffect(new MobEffectInstance(
                                    JujutsucraftModMobEffects.CURSED_TECHNIQUE.get(),
                                    Integer.MAX_VALUE, 0, false, false
                            ));
                        }


                        if ((T1 != 2 || S1 != 5) &&
                                (T1 != 9 || S1 != 10) &&
                                !(T1 == 18 && S1 >= 10 && S1 < 15) &&
                                !CREATIVE) {

                            if (S1 == 20) {

                                Tick = 20;

                            } else {

                                Tick = Math.max(
                                        item_use ? cost : playerVars.PlayerSelectCurseTechniqueCostOrgin / 2.0,
                                        20
                                );

                                String tech = STR1;

                                if (tech.equals(Component.translatable("jujutsu.technique.attack1").getString()))
                                    Tick = 5;
                                else if (tech.equals(Component.translatable("jujutsu.technique.attack2").getString()))
                                    Tick = 15;
                                else if (tech.equals(Component.translatable("jujutsu.technique.attack3").getString()))
                                    Tick = 20;
                                else if (tech.equals(Component.translatable("jujutsu.technique.attack4").getString()))
                                    Tick = 100;
                                else if (tech.equals(Component.translatable("jujutsu.technique.attack5").getString()))
                                    Tick = 200;
                                else if (tech.equals(Component.translatable("jujutsu.technique.attack6").getString())
                                        || tech.equals(Component.translatable("jujutsu.technique.flying_kick").getString()))
                                    Tick = 100;
                                else if (tech.equals(Component.translatable("jujutsu.technique.attack7").getString()))
                                    Tick = 100;

                                if (T1 == 5 && S1 == 5) Tick = 20;

                                if (T1 == 6 && S1 >= 5 && S1 < 20 &&
                                        !tech.equals(Component.translatable("jujutsu.technique.choso3").getString())) {
                                    Tick = 5;
                                }

                                if (T1 == 7 && (S1 == 5 || S1 == 10)) Tick = 20;

                                if (T1 == 19 && S1 < 5 && LogicStartPassiveProcedure.execute(entity)) Tick = 5;

                                if (T1 == 39 && S1 >= 8) Tick = 20;

                                if (T1 == 40 && (S1 == 6 || S1 == 7)) Tick = 20;
                            }

                        } else {

                            Tick = 1;
                        }


                        if (world.getLevelData().getGameRules()
                                .getBoolean(JujutsucraftModGameRules.JUJUTSU_GAIN_FAME)) {

                            double newValue = playerVars.PlayerTechniqueUsedNumber + Math.round(Tick);

                            entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                                    .ifPresent(cap -> {
                                        cap.PlayerTechniqueUsedNumber = newValue;
                                        cap.syncPlayerVariables(entity);
                                    });

                            if (entity instanceof ServerPlayer player) {

                                int diff = world.getLevelData()
                                        .getGameRules()
                                        .getInt(JujutsucraftModGameRules.JUJUTSUUPGRADEDIFFICULTY);

                                boolean special = (T1 == 27 || T2 == 27);

                                if (newValue > (special ? 2000 : 4000) * (1 + diff / 10.0)) {
                                    grantAdvancement(player, "jujutsucraft:mastery_simple_domain");
                                }

                                if (newValue > (special ? 100 : 12000) * (1 + diff / 10.0)) {
                                    grantAdvancement(player, "jujutsucraft:mastery_domain_expansion");
                                }
                            }
                        }


                        if (mainHand.getItem() == JujutsucraftModItems.LOUDSPEAKER.get()) {
                            mainHand.getOrCreateTag().putBoolean("Used", true);
                        }


                        if (noUseCursePower) {

                            entity.getPersistentData().putDouble(
                                    "COOLDOWN_TICKS",
                                    Math.max(entity.getPersistentData().getDouble("COOLDOWN_TICKS"),
                                            Math.round(Tick))
                            );
                            return;
                        }

                        boolean sage = addonVars.Profession.equals("Sage")
                                || addonVars.Subrace.equals("Disaster Curses");

                        boolean noCooldown = world.getLevelData()
                                .getGameRules()
                                .getBoolean(JujutsucraftaddonModGameRules.JJKU_NO_COOLDOWN);

                        if (living == null || living.level().isClientSide() || noCooldown) return;


                        if (playerVars.PhysicalAttack && !item_use) {

                            if (S1 >= 0 && S1 <= 2) {

                                ItemStack chest = living.getItemBySlot(EquipmentSlot.CHEST);

                                if (chest.getItem() == JujutsucraftModItems.SUKUNA_BODY_CHESTPLATE.get()) {
                                    Tick *= 0.5;
                                }

                                if (living.getAttributes().hasAttribute(Attributes.ATTACK_SPEED)) {
                                    double speed = living.getAttribute(Attributes.ATTACK_SPEED).getValue();
                                    Tick += 20 * Math.max(1.7 - speed, 0);
                                }

                                living.addEffect(new MobEffectInstance(
                                        JujutsucraftModMobEffects.COOLDOWN_TIME_COMBAT.get(),
                                        (int) Math.round(sage ? Tick : Tick / 2),
                                        1, false, false
                                ));

                            } else {

                                living.addEffect(new MobEffectInstance(
                                        JujutsucraftModMobEffects.COOLDOWN_TIME_COMBAT.get(),
                                        (int) Math.round(sage ? Tick / 2 : Tick),
                                        0, false, false
                                ));
                            }

                            return;
                        }


                        living.addEffect(new MobEffectInstance(
                                JujutsucraftModMobEffects.COOLDOWN_TIME.get(),
                                (int) Math.round(sage ? Tick / 2 : Tick),
                                0, false, false
                        ));

                        if (addonVars.BurnOutRCT) {
                            BurnoutKeyOnKeyPressedProcedure.execute(entity);
                        }

                    }
                }
            }

            if (entity instanceof Player) {
                _player = (Player)entity;
                if (!_player.level().isClientSide()) {
                    _player.displayClientMessage(Component.literal(Component.translatable("jujutsu.message.dont_use").getString()), true);
                }
            }

        }
    }

    private static void grantAdvancement(ServerPlayer player, String id) {

        Advancement adv = player.server.getAdvancements()
                .getAdvancement(new ResourceLocation(id));

        if (adv == null) return;

        AdvancementProgress progress =
                player.getAdvancements().getOrStartProgress(adv);

        if (progress.isDone()) return;

        for (String crit : progress.getRemainingCriteria()) {
            player.getAdvancements().award(adv, crit);
        }
    }

}

