package com.jujutsu.jujutsucraftaddon.mixins;

import com.jujutsu.jujutsucraftaddon.init.mod.JujutsucraftaddonModEnchantments;
import com.jujutsu.jujutsucraftaddon.init.mod.JujutsucraftaddonModItems;
import com.jujutsu.jujutsucraftaddon.init.mod.JujutsucraftaddonModMobEffects;
import com.jujutsu.jujutsucraftaddon.network.JujutsucraftaddonModVariables;
import com.jujutsu.jujutsucraftaddon.procedures.AnimationDodgeProcedure;
import com.jujutsu.jujutsucraftaddon.procedures.SukunaAttackAnimationsProcedure;
import com.jujutsu.jujutsucraftaddon.procedures.SwapTodoTarget;
import net.mcreator.jujutsucraft.entity.*;
import net.mcreator.jujutsucraft.init.JujutsucraftModGameRules;
import net.mcreator.jujutsucraft.init.JujutsucraftModItems;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.mcreator.jujutsucraft.init.JujutsucraftModParticleTypes;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib.animatable.GeoEntity;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.BiFunction;

@Mixin(value = WhenEntityTakesDamageProcedure.class, priority = -10000)
public abstract class WhenEntityAttackedProcedureMixin {
    public WhenEntityAttackedProcedureMixin() {
    }

    /**
     * @author Satushi
     * @reason Change The Logic from attacks in jujutsucraft + add animations for attacks ( also that gives the mahoraga adaptation too )
     */

    @Inject(at = @At("HEAD"), method = "execute(Lnet/minecraftforge/eventbus/api/Event;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/damagesource/DamageSource;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity;D)V", remap = false, cancellable = true)
    private static void execute(Event event, LevelAccessor world, DamageSource damagesource, Entity entity, Entity immediatesourceentity, Entity sourceentity, double amount, CallbackInfo ci) {
        ci.cancel();
        if (damagesource != null && entity != null && immediatesourceentity != null && sourceentity != null) {
            ItemStack mainHandItem = ItemStack.EMPTY;
            Entity entity_a = null;
            double old_health = (double)0.0F;
            double NUM2 = (double)0.0F;
            double old_cooldown = (double)0.0F;
            double NUM1 = (double)0.0F;
            double Damage_amount = (double)0.0F;
            boolean cancel = false;
            boolean changeDamage = false;
            boolean logic_a = false;
            boolean guard = false;
            boolean critical_nanami = false;
            boolean use_curse = false;
            boolean critical_ice = false;
            if (!damagesource.is(TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("forge:animation")))) {

                SukunaAttackAnimationsProcedure.execute(sourceentity, entity, world);

                if (sourceentity instanceof FushiguroTojiBugEntity && amount >= 100) {
                    if (!((((FushiguroTojiBugEntity) sourceentity).animationprocedure).equals("playful" + Mth.nextInt(RandomSource.create(), 1, 4)))) {
                        ((FushiguroTojiBugEntity) sourceentity).setAnimation("playful" + Mth.nextInt(RandomSource.create(), 1, 4));
                    }
                }

                if (sourceentity instanceof FushiguroTojiEntity && amount >= 100 && ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == JujutsucraftModItems.PLAYFUL_CLOUD.get())) {
                    if (!((((FushiguroTojiEntity) sourceentity).animationprocedure).equals("playful" + Mth.nextInt(RandomSource.create(), 1, 4)))) {
                        ((FushiguroTojiEntity) sourceentity).setAnimation("playful" + Mth.nextInt(RandomSource.create(), 1, 4));
                    }
                }


                // Normal Player Attack Animations
                if ((sourceentity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).Run == 1) {
                    if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
                        if (!(sourceentity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(JujutsucraftaddonModMobEffects.ANIMATION.get()))) {
                            _entity.addEffect(new MobEffectInstance(JujutsucraftaddonModMobEffects.ANIMATION.get(), 10, 1, false, false));
                        }

                }

                if ((entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables())).PlayerCurseTechnique == 20) {
                    if ((entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables())).PlayerCursePower >= 1000) {
                        if (Math.random() < (1) / ((float) 60)) {
                            SwapTodoTarget.execute(entity, world, sourceentity);
                            AnimationDodgeProcedure.execute(world, entity);
                            if (event != null && event.isCancelable()) {
                                event.setCanceled(true);
                            } else if (event != null && event.hasResult()) {
                                event.setResult(Event.Result.DENY);
                            }
                        }
                    }
                } else if (entity instanceof TodoAoiEntity) {
                    if (Math.random() < (1) / ((float) 60)) {
                        SwapTodoTarget.execute(entity, world, sourceentity);
                        if (event != null && event.isCancelable()) {
                            event.setCanceled(true);
                        } else if (event != null && event.hasResult()) {
                            event.setResult(Event.Result.DENY);
                        }
                    }
                }


                // Ultra Instinct
                if (EnchantmentHelper.getItemEnchantmentLevel(JujutsucraftaddonModEnchantments.ULTRA_INSTINCT.get(), (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY)) != 0) {
                    if (Math.random() < ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getEnchantmentLevel(JujutsucraftaddonModEnchantments.ULTRA_INSTINCT.get())) / ((float) 100)) {
                        AnimationDodgeProcedure.execute(world, entity);
                        if (event != null && event.isCancelable()) {
                            event.setCanceled(true);
                        } else if (event != null && event.hasResult()) {
                            event.setResult(Event.Result.DENY);
                        }
                    }
                }

                // Itadori Awakening Buff
                if (sourceentity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(JujutsucraftaddonModMobEffects.ITADORI_AWAKENING.get())) {
                    if (Math.random() < (1) / ((float) 60)) {
                        sourceentity.getPersistentData().putDouble("skill", 3810.0);
                        sourceentity.getPersistentData().putDouble("cnt6", 4.0);
                        if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
                            _entity.addEffect(new MobEffectInstance(JujutsucraftModMobEffects.CURSED_TECHNIQUE.get(), 60, 1, false, false));
                        double yaw = Math.toRadians(sourceentity.getYRot() + 90.0F);
                        double pitch = Math.toRadians(sourceentity.getXRot());
                        sourceentity.getPersistentData().putDouble("x_pos", entity.getX() + Math.cos(yaw) * Math.cos(pitch) * (double) (2.0F + entity.getBbWidth()));
                        sourceentity.getPersistentData().putDouble("y_pos", entity.getY() + (double) entity.getBbHeight() * 0.75 + Math.sin(pitch) * -1.0 * (double) (2.0F + entity.getBbWidth()));
                        sourceentity.getPersistentData().putDouble("z_pos", entity.getZ() + Math.sin(yaw) * Math.cos(pitch) * (double) (2.0F + entity.getBbWidth()));
                        if (!(sourceentity instanceof LivingEntity _livEnt01 && _livEnt01.hasEffect(JujutsucraftaddonModMobEffects.KOKUSEN_N.get()))) {
                            if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
                                _entity.addEffect(new MobEffectInstance(JujutsucraftaddonModMobEffects.KOKUSEN_N.get(), 20, 1, false, false));
                        }
                    }
                }

                // Uro Buff
                if ((entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables())).PlayerCurseTechnique == 38) {
                    if (((entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).Clans).equals("Fujiwara")) {
                        if (Math.random() < (1) / ((float) 50)) {
                            entity.getPersistentData().putDouble("skill", 3810.0);
                            entity.getPersistentData().putDouble("cnt6", 4.0);
                            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
                                _entity.addEffect(new MobEffectInstance(JujutsucraftModMobEffects.CURSED_TECHNIQUE.get(), 60, 1, false, false));
                            double yaw = Math.toRadians(entity.getYRot() + 90.0F);
                            double pitch = Math.toRadians(entity.getXRot());
                            entity.getPersistentData().putDouble("x_pos", entity.getX() + Math.cos(yaw) * Math.cos(pitch) * (double) (2.0F + entity.getBbWidth()));
                            entity.getPersistentData().putDouble("y_pos", entity.getY() + (double) entity.getBbHeight() * 0.75 + Math.sin(pitch) * -1.0 * (double) (2.0F + entity.getBbWidth()));
                            entity.getPersistentData().putDouble("z_pos", entity.getZ() + Math.sin(yaw) * Math.cos(pitch) * (double) (2.0F + entity.getBbWidth()));
                            AnimationDodgeProcedure.execute(world, entity);
                            if (event != null && event.isCancelable()) {
                                event.setCanceled(true);
                            } else if (event != null && event.hasResult()) {
                                event.setResult(Event.Result.DENY);
                            }
                        }
                    } else {
                        if (Math.random() < (1) / ((float) 120)) {
                            entity.getPersistentData().putDouble("skill", 3810.0);
                            entity.getPersistentData().putDouble("cnt6", 4.0);
                            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
                                _entity.addEffect(new MobEffectInstance(JujutsucraftModMobEffects.CURSED_TECHNIQUE.get(), 60, 1, false, false));
                            double yaw = Math.toRadians(entity.getYRot() + 90.0F);
                            double pitch = Math.toRadians(entity.getXRot());
                            entity.getPersistentData().putDouble("x_pos", entity.getX() + Math.cos(yaw) * Math.cos(pitch) * (double) (2.0F + entity.getBbWidth()));
                            entity.getPersistentData().putDouble("y_pos", entity.getY() + (double) entity.getBbHeight() * 0.75 + Math.sin(pitch) * -1.0 * (double) (2.0F + entity.getBbWidth()));
                            entity.getPersistentData().putDouble("z_pos", entity.getZ() + Math.sin(yaw) * Math.cos(pitch) * (double) (2.0F + entity.getBbWidth()));
                            AnimationDodgeProcedure.execute(world, entity);
                            if (event != null && event.isCancelable()) {
                                event.setCanceled(true);
                            } else if (event != null && event.hasResult()) {
                                event.setResult(Event.Result.DENY);
                            }
                        }
                    }
                }

                // Rejected Zenin and Toji Buff
                if ((entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables())).PlayerCurseTechnique == -1) {
                    if ((entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables())).PlayerCursePower == 0) {
                        if (((entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).Clans).equals("Rejected Zenin")) {
                            if (Math.random() < (1) / ((float) 40)) {
                                AnimationDodgeProcedure.execute(world, entity);
                                if (event != null && event.isCancelable()) {
                                    event.setCanceled(true);
                                } else if (event != null && event.hasResult()) {
                                    event.setResult(Event.Result.DENY);
                                }
                            }
                        } else {
                            if (Math.random() < (1) / ((float) 80)) {
                                AnimationDodgeProcedure.execute(world, entity);
                                if (event != null && event.isCancelable()) {
                                    event.setCanceled(true);
                                } else if (event != null && event.hasResult()) {
                                    event.setResult(Event.Result.DENY);
                                }
                            }
                        }
                    }
                }

                if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == JujutsucraftaddonModItems.WUKONG_SET_BOOTS.get().asItem()) {
                    if (Math.random() < (1) / ((float) 100)) {
                        if (!(entity instanceof GeoEntity)) {
                            AnimationDodgeProcedure.execute(world, entity);
                            if (event != null && event.isCancelable()) {
                                event.setCanceled(true);
                            } else if (event != null && event.hasResult()) {
                                event.setResult(Event.Result.DENY);
                            }
                        }
                    }
                }

                if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == JujutsucraftaddonModItems.WUKONG_SET_CHESTPLATE.get().asItem()) {
                    if (Math.random() < (1) / ((float) 20)) {
                        sourceentity.hurt(damagesource, (float) amount / 10);
                    }
                }

                if (sourceentity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:ranged_ammo"))) && !sourceentity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:ranged_ammo_no_move"))) && LogicStartPassiveProcedure.execute(entity)) {
                    label865: {
                        NUM1 = ((JujutsucraftModVariables.PlayerVariables)entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, (Direction)null).orElse(new JujutsucraftModVariables.PlayerVariables())).PlayerCurseTechnique;
                        NUM2 = ((JujutsucraftModVariables.PlayerVariables)entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, (Direction)null).orElse(new JujutsucraftModVariables.PlayerVariables())).PlayerCurseTechnique2;
                        if (NUM1 == (double)38.0F || NUM2 == (double)38.0F || entity instanceof UroTakakoEntity) {
                            label863: {
                                if (entity instanceof LivingEntity) {
                                    LivingEntity _livEnt4 = (LivingEntity)entity;
                                    if (_livEnt4.hasEffect((MobEffect)JujutsucraftModMobEffects.NEUTRALIZATION.get())) {
                                        break label863;
                                    }
                                }

                                if (sourceentity.getBbWidth() + sourceentity.getBbHeight() <= (entity.getBbWidth() + entity.getBbHeight()) * 4.0F && entity instanceof LivingEntity) {
                                    LivingEntity _livEnt9 = (LivingEntity)entity;
                                    if (_livEnt9.hasEffect((MobEffect)JujutsucraftModMobEffects.GUARD.get())) {
                                        cancel = true;
                                    }
                                }
                            }
                        }

                        if (entity instanceof LivingEntity) {
                            LivingEntity _livEnt10 = (LivingEntity)entity;
                            if (_livEnt10.hasEffect((MobEffect)JujutsucraftModMobEffects.NEUTRALIZATION.get())) {
                                break label865;
                            }
                        }

                        if (entity instanceof LivingEntity) {
                            LivingEntity _livEnt11 = (LivingEntity)entity;
                            if (_livEnt11.hasEffect((MobEffect)JujutsucraftModMobEffects.PRAYER_SONG.get())) {
                                int var10000;
                                label784: {
                                    if (entity instanceof LivingEntity) {
                                        LivingEntity _livEnt = (LivingEntity)entity;
                                        if (_livEnt.hasEffect((MobEffect)JujutsucraftModMobEffects.GUARD.get())) {
                                            var10000 = _livEnt.getEffect((MobEffect)JujutsucraftModMobEffects.GUARD.get()).getAmplifier();
                                            break label784;
                                        }
                                    }

                                    var10000 = 0;
                                }

                                if (var10000 > 0) {
                                    cancel = true;
                                }
                            }
                        }
                    }
                }

                if (cancel) {
                    if (event != null && event.isCancelable()) {
                        event.setCanceled(true);
                    }
                } else if (!damagesource.is(TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("forge:curse"))) && !damagesource.is(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("jujutsucraft:damage_combat")))) {
                    cancel = false;
                    LivingEntity var119;
                    if (sourceentity instanceof Mob) {
                        Mob _mobEnt = (Mob)sourceentity;
                        var119 = _mobEnt.getTarget();
                    } else {
                        var119 = null;
                    }

                    if (var119 instanceof LivingEntity) {
                        if (sourceentity instanceof Mob) {
                            Mob _mobEnt = (Mob)sourceentity;
                            var119 = _mobEnt.getTarget();
                        } else {
                            var119 = null;
                        }

                        LivingEntity var10001;
                        if (entity instanceof Mob) {
                            Mob _mobEnt = (Mob)entity;
                            var10001 = _mobEnt.getTarget();
                        } else {
                            var10001 = null;
                        }

                        if (var119 == var10001) {
                            cancel = true;
                        }
                    }

                    if (!cancel) {
                        label842: {
                            label814: {
                                if (entity instanceof TamableAnimal) {
                                    TamableAnimal _tamEnt = (TamableAnimal)entity;
                                    if (_tamEnt.isTame()) {
                                        break label814;
                                    }
                                }

                                if (!(sourceentity instanceof TamableAnimal)) {
                                    break label842;
                                }

                                TamableAnimal _tamEnt = (TamableAnimal)sourceentity;
                                if (!_tamEnt.isTame()) {
                                    break label842;
                                }
                            }

                            if (entity instanceof TamableAnimal) {
                                TamableAnimal _tamEnt = (TamableAnimal)entity;
                                var119 = _tamEnt.getOwner();
                            } else {
                                var119 = null;
                            }

                            if (var119 != sourceentity) {
                                if (sourceentity instanceof TamableAnimal) {
                                    TamableAnimal _tamEnt = (TamableAnimal)sourceentity;
                                    var119 = _tamEnt.getOwner();
                                } else {
                                    var119 = null;
                                }

                                if (var119 != entity) {
                                    if (entity instanceof TamableAnimal) {
                                        TamableAnimal _tamEnt = (TamableAnimal)entity;
                                        var119 = _tamEnt.getOwner();
                                    } else {
                                        var119 = null;
                                    }

                                    LivingEntity var140;
                                    if (sourceentity instanceof TamableAnimal) {
                                        TamableAnimal _tamEnt = (TamableAnimal)sourceentity;
                                        var140 = _tamEnt.getOwner();
                                    } else {
                                        var140 = null;
                                    }

                                    if (var119 != var140) {
                                        break label842;
                                    }
                                }
                            }

                            cancel = true;
                        }
                    }

                    if (!world.getLevelData().getGameRules().getBoolean(JujutsucraftModGameRules.JUJUTSUPVP) && (sourceentity instanceof Player || sourceentity.getPersistentData().getBoolean("Player")) && (entity instanceof Player || entity.getPersistentData().getBoolean("Player"))) {
                        label868: {
                            label817: {
                                cancel = true;
                                if (entity instanceof LivingEntity) {
                                    LivingEntity _livEnt34 = (LivingEntity)entity;
                                    if (_livEnt34.hasEffect((MobEffect)JujutsucraftModMobEffects.SUKUNA_EFFECT.get())) {
                                        break label817;
                                    }
                                }

                                if (!(sourceentity instanceof LivingEntity)) {
                                    break label868;
                                }

                                LivingEntity _livEnt35 = (LivingEntity)sourceentity;
                                if (!_livEnt35.hasEffect((MobEffect)JujutsucraftModMobEffects.SUKUNA_EFFECT.get())) {
                                    break label868;
                                }
                            }

                            cancel = false;
                        }
                    }

                    if (entity instanceof Mob) {
                        Mob _mobEnt = (Mob)entity;
                        var119 = _mobEnt.getTarget();
                    } else {
                        var119 = null;
                    }

                    label870: {
                        if (var119 != sourceentity || !(entity.getPersistentData().getDouble("cnt_target") > (double)6.0F)) {
                            if (sourceentity instanceof Mob) {
                                Mob _mobEnt = (Mob)sourceentity;
                                var119 = _mobEnt.getTarget();
                            } else {
                                var119 = null;
                            }

                            if (var119 != entity || !(sourceentity.getPersistentData().getDouble("cnt_target") > (double)6.0F)) {
                                break label870;
                            }
                        }

                        cancel = false;
                    }

                    if (!cancel) {
                        if (sourceentity.getPersistentData().getDouble("friend_num") != (double)0.0F && sourceentity.getPersistentData().getDouble("friend_num") == entity.getPersistentData().getDouble("friend_num")) {
                            cancel = true;
                        }

                        if (damagesource.is(DamageTypes.MOB_ATTACK) || damagesource.is(DamageTypes.PLAYER_ATTACK)) {
                            label864: {
                                label821: {
                                    if (sourceentity instanceof LivingEntity) {
                                        LivingEntity _livEnt47 = (LivingEntity)sourceentity;
                                        if (_livEnt47.hasEffect((MobEffect)JujutsucraftModMobEffects.COOLDOWN_TIME_COMBAT.get()) && sourceentity instanceof LivingEntity) {
                                            LivingEntity _livEnt48 = (LivingEntity)sourceentity;
                                            if (_livEnt48.hasEffect((MobEffect)JujutsucraftModMobEffects.CURSED_TECHNIQUE.get())) {
                                                break label821;
                                            }
                                        }
                                    }
                                    double var126;
                                    label702: {
                                        if (sourceentity instanceof LivingEntity) {
                                            LivingEntity _livEnt = (LivingEntity)sourceentity;
                                            if (_livEnt.hasEffect((MobEffect)JujutsucraftModMobEffects.COOLDOWN_TIME_COMBAT.get())) {
                                                var126 = _livEnt.getEffect((MobEffect)JujutsucraftModMobEffects.COOLDOWN_TIME_COMBAT.get()).getAmplifier();
                                                break label702;
                                            }
                                        }

                                        var126 = 0;
                                    }

                                    if (var126 <= 0 && !(sourceentity.getPersistentData().getDouble("skill") <= (double)-999.0F)) {
                                        break label864;
                                    }
                                }

                                cancel = true;
                            }
                        }

                        label694: {
                            if (entity instanceof LivingEntity) {
                                LivingEntity _livEnt51 = (LivingEntity)entity;
                                if (_livEnt51.hasEffect((MobEffect)JujutsucraftModMobEffects.NEUTRALIZATION.get())) {
                                    break label694;
                                }
                            }
                            double var127;
                            if (entity instanceof LivingEntity) {
                                LivingEntity _livEnt52 = (LivingEntity)entity;
                                if (_livEnt52.hasEffect((MobEffect)JujutsucraftModMobEffects.PRAYER_SONG.get())) {
                                    label687: {
                                        if (entity instanceof LivingEntity) {
                                            LivingEntity _livEnt = (LivingEntity)entity;
                                            if (_livEnt.hasEffect((MobEffect)JujutsucraftModMobEffects.GUARD.get())) {
                                                var127 = _livEnt.getEffect((MobEffect)JujutsucraftModMobEffects.GUARD.get()).getAmplifier();
                                                break label687;
                                            }
                                        }

                                        var127 = 0;
                                    }

                                    if (var127 > 0) {
                                        cancel = true;
                                    }
                                }
                            }
                        }

                        if (immediatesourceentity instanceof BulletBallProjectileEntity) {
                            cancel = true;
                        }

                        if (entity instanceof DomainExpansionEntityEntity) {
                            cancel = true;
                        }
                    }

                    if (cancel) {
                        if (event != null && event.isCancelable()) {
                            event.setCanceled(true);
                        }
                    } else {
                        double var128;
                        if (immediatesourceentity instanceof Projectile) {
                            Projectile _projEnt = (Projectile)immediatesourceentity;
                            var128 = _projEnt.getDeltaMovement().length();
                        } else {
                            var128 = (double)0.0F;
                        }

                        if (var128 == (double)0.0F) {
                            logic_a = sourceentity.getPersistentData().getBoolean("attack");
                            sourceentity.getPersistentData().putBoolean("attack", true);
                            CursedToolsAbilityProcedure.execute(sourceentity, entity);
                            sourceentity.getPersistentData().putBoolean("attack", logic_a);
                        }
                    }
                }
            }

            if (ModList.get().isLoaded("minepiece") && damagesource.is(TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("forge:curse")))) {
                cancel = true;
            }

            if (!cancel && !damagesource.is(TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("forge:animation"))) && !damagesource.is(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("jujutsucraft:damage_combat")))) {
                Damage_amount = amount;
                ItemStack var129;
                if (sourceentity instanceof LivingEntity) {
                    LivingEntity _livEnt = (LivingEntity)sourceentity;
                    var129 = _livEnt.getMainHandItem();
                } else {
                    var129 = ItemStack.EMPTY;
                }

                mainHandItem = var129.copy();
                if (!sourceentity.getPersistentData().getString("OWNER_UUID").isEmpty() && (sourceentity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:ranged_ammo"))) || sourceentity.getPersistentData().getDouble("NameRanged_ranged") != (double)0.0F)) {
                    entity_a = (new BiFunction<LevelAccessor, String, Entity>() {
                        public Entity apply(LevelAccessor levelAccessor, String uuid) {
                            if (levelAccessor instanceof ServerLevel serverLevel) {
                                try {
                                    return serverLevel.getEntity(UUID.fromString(uuid));
                                } catch (Exception var5) {
                                }
                            }

                            return null;
                        }
                    }).apply(world, sourceentity.getPersistentData().getString("OWNER_UUID"));
                }

                if (!(entity_a instanceof LivingEntity)) {
                    entity_a = sourceentity;
                }

                CounterProcedure.execute(entity);
                if (sourceentity instanceof LivingEntity) {
                    LivingEntity _livEnt = (LivingEntity)sourceentity;
                    var129 = _livEnt.getMainHandItem();
                } else {
                    var129 = ItemStack.EMPTY;
                }

                SetAttributeMainhandProcedure.execute(var129);
                if ((damagesource.is(TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("forge:curse"))) || damagesource.is(TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("forge:combat")))) && !sourceentity.getPersistentData().getBoolean("attack")) {
                    if (mainHandItem.getItem() != ItemStack.EMPTY.getItem() && mainHandItem.getOrCreateTag().contains("CursePower") && mainHandItem.getOrCreateTag().getDouble("CursePower") != (double)0.0F) {
                        use_curse = true;
                    }
                } else if (!damagesource.is(DamageTypes.MOB_PROJECTILE)) {
                    label656: {
                        if (entity instanceof LivingEntity) {
                            LivingEntity _livEnt76 = (LivingEntity)entity;
                            if (_livEnt76.hasEffect((MobEffect)JujutsucraftModMobEffects.DAMAGE_EFFECT.get())) {
                                break label656;
                            }
                        }

                        CursedToolsAbilityProcedure.execute(sourceentity, entity);
                        EffectAttackProcedure.execute(world, entity, sourceentity);
                    }

                    if (mainHandItem.is(ItemTags.create(new ResourceLocation("forge:cursed_tool")))) {
                        if (mainHandItem.getItem() == JujutsucraftModItems.EXECUTIONERS_SWORD.get()) {
                            Damage_amount = (double)1.0F;
                            changeDamage = true;
                        }

                        if (mainHandItem.getItem() == JujutsucraftModItems.FESTER_LIFE_BLADE.get()) {
                            changeDamage = true;
                        }

                        if (mainHandItem.getItem() == JujutsucraftModItems.PLAYFUL_CLOUD.get()) {
                            Damage_amount *= (double)1.25F;
                            changeDamage = true;
                        }
                    }

                    if (mainHandItem.getItem() != ItemStack.EMPTY.getItem() && mainHandItem.getOrCreateTag().contains("CursePower")) {
                        if (mainHandItem.getOrCreateTag().getDouble("CursePower") > (double)0.0F) {
                            Damage_amount *= (double)1.0F + Math.abs(mainHandItem.getOrCreateTag().getDouble("CursePower")) * 0.02;
                            changeDamage = true;
                            use_curse = true;
                        } else if (mainHandItem.getOrCreateTag().getDouble("CursePower") < (double)0.0F && entity.getPersistentData().getBoolean("CursedSpirit")) {
                            label644: {
                                if (mainHandItem.getItem() == JujutsucraftModItems.SWORD_OF_EXTERMINATION.get() && sourceentity instanceof Player) {
                                    Player _plrCldCheck89 = (Player)sourceentity;
                                    if (_plrCldCheck89.getCooldowns().isOnCooldown(mainHandItem.getItem())) {
                                        break label644;
                                    }
                                }

                                Damage_amount *= (double)1.0F + Math.abs(mainHandItem.getOrCreateTag().getDouble("CursePower")) * 0.2;
                            }

                            changeDamage = true;
                            use_curse = true;
                        }
                    }
                    double var131;
                    label638: {
                        if (sourceentity instanceof LivingEntity) {
                            LivingEntity _livEnt = (LivingEntity)sourceentity;
                            if (_livEnt.hasEffect((MobEffect)JujutsucraftModMobEffects.SPECIAL.get())) {
                                var131 = _livEnt.getEffect((MobEffect)JujutsucraftModMobEffects.SPECIAL.get()).getAmplifier();
                                break label638;
                            }
                        }

                        var131 = 0;
                    }

                    if (var131 >= 1 && entity instanceof LivingEntity) {
                        LivingEntity _livEnt92 = (LivingEntity)entity;
                        if (_livEnt92.hasEffect((MobEffect)JujutsucraftModMobEffects.SPECIAL.get())) {
                            if (entity instanceof LivingEntity) {
                                LivingEntity _entity = (LivingEntity)entity;
                                _entity.removeEffect((MobEffect)JujutsucraftModMobEffects.SPECIAL.get());
                            }

                            Damage_amount *= (double)2.0F;
                            critical_nanami = true;
                            changeDamage = true;
                        }
                    }

                    if (entity.getPercentFrozen() * 100.0F >= 5.0F) {
                        entity.setTicksFrozen(0);
                        Damage_amount *= (double)1.5F;
                        critical_ice = true;
                        changeDamage = true;
                    }
                }

                if (!damagesource.is(TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("forge:combat"))) && LogicGuardSuccessProcedure.execute(entity, sourceentity)) {
                    Damage_amount = Math.max(Damage_amount - entity.getPersistentData().getDouble("Damage"), 0.01);
                    changeDamage = true;
                    guard = true;
                }
                double var132;
                double var136;
                double var137;
                if (entity_a instanceof LivingEntity) {
                    LivingEntity _livEnt102 = (LivingEntity)entity_a;
                    if (_livEnt102.hasEffect((MobEffect)JujutsucraftModMobEffects.DOMAIN_EXPANSION.get()) && entity instanceof LivingEntity) {
                        LivingEntity _livEnt103 = (LivingEntity)entity;
                        if (_livEnt103.hasEffect((MobEffect)JujutsucraftModMobEffects.FALLING_BLOSSOM_EMOTION.get())) {
                            label627: {
                                if (entity instanceof LivingEntity) {
                                    LivingEntity _livEnt = (LivingEntity)entity;
                                    if (_livEnt.hasEffect((MobEffect)JujutsucraftModMobEffects.NEUTRALIZATION.get())) {
                                        var132 = _livEnt.getEffect((MobEffect)JujutsucraftModMobEffects.NEUTRALIZATION.get()).getAmplifier();
                                        break label627;
                                    }
                                }

                                var132 = 0;
                            }

                            if (var132 > 0) {
                                int var10002;
                                label621: {
                                    if (entity instanceof LivingEntity) {
                                        LivingEntity _livEnt = (LivingEntity)entity;
                                        if (_livEnt.hasEffect(MobEffects.DAMAGE_BOOST)) {
                                            var10002 = _livEnt.getEffect(MobEffects.DAMAGE_BOOST).getAmplifier();
                                            break label621;
                                        }
                                    }

                                    var10002 = 0;
                                }

                                NUM1 = (double)15.0F * ((double)1.0F + (double)var10002 * 0.33);
                                if (sourceentity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:ranged_ammo"))) || sourceentity.getPersistentData().getBoolean("DomainAttack")) {
                                    Damage_amount = Math.max(Damage_amount - NUM1, (double)0.0F);
                                }

                                if (sourceentity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:ranged_ammo")))) {
                                    sourceentity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("jujutsucraft:damage_curse"))), entity), (float)NUM1);
                                }
                            }
                        }
                    }
                }

                if (entity.getPersistentData().getBoolean("CursedSpirit") && !use_curse) {
                    label853: {
                        if (sourceentity instanceof Player) {
                            if (!(((JujutsucraftModVariables.PlayerVariables)sourceentity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, (Direction)null).orElse(new JujutsucraftModVariables.PlayerVariables())).PlayerCursePowerFormer < (double)100.0F) || LogicUseMinePieceProcedure.execute(sourceentity)) {
                                break label853;
                            }
                        } else if (!sourceentity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:no_curse_power")))) {
                            break label853;
                        }

                        Damage_amount = 0.01;
                        changeDamage = true;
                    }
                }

                label828: {
                    if (entity instanceof Player) {
                        if (((JujutsucraftModVariables.PlayerVariables)entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, (Direction)null).orElse(new JujutsucraftModVariables.PlayerVariables())).PlayerCurseTechnique != (double)15.0F && ((JujutsucraftModVariables.PlayerVariables)entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, (Direction)null).orElse(new JujutsucraftModVariables.PlayerVariables())).PlayerCurseTechnique2 != (double)15.0F) {
                            break label828;
                        }
                    } else if (!(entity instanceof MahitoEntity)) {
                        break label828;
                    }

                    if (entity instanceof LivingEntity) {
                        LivingEntity _livEnt116 = (LivingEntity)entity;
                        if (_livEnt116.hasEffect((MobEffect)JujutsucraftModMobEffects.UNSTABLE.get())) {
                            break label828;
                        }
                    }

                    if (entity instanceof LivingEntity) {
                        LivingEntity _livEnt117 = (LivingEntity)entity;
                        if (_livEnt117.hasEffect((MobEffect)JujutsucraftModMobEffects.DOMAIN_AMPLIFICATION.get())) {
                            break label828;
                        }
                    }

                    if (entity_a instanceof Player) {
                        if (entity_a instanceof ServerPlayer) {
                            ServerPlayer _plr119 = (ServerPlayer)entity_a;
                            if (_plr119.level() instanceof ServerLevel && _plr119.getAdvancements().getOrStartProgress(_plr119.server.getAdvancements().getAdvancement(new ResourceLocation("jujutsucraft:observation_of_the_soul"))).isDone()) {
                                break label828;
                            }
                        }
                    } else if (entity_a.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:soul_perception")))) {
                        break label828;
                    }

                    if (!sourceentity.getPersistentData().getBoolean("ignore")) {
                        Damage_amount *= (double)0.5F;
                        changeDamage = true;
                    }
                }

                if (sourceentity.getPersistentData().getBoolean("ignore")) {
                    changeDamage = true;
                }

                if (!damagesource.is(TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("forge:combat"))) && entity instanceof Player && (entity_a.getPersistentData().getBoolean("jjkChara") || entity_a instanceof Player)) {
                    Damage_amount *= GetDifficultyLevelProcedure.execute(world);
                    changeDamage = true;
                }

                if (sourceentity.getPersistentData().getBoolean("ignore") && entity instanceof LivingEntity) {
                    LivingEntity _entity = (LivingEntity)entity;
                    if (!_entity.level().isClientSide()) {
                        _entity.addEffect(new MobEffectInstance((MobEffect)JujutsucraftModMobEffects.IGNORE_GUARD.get(), 1, 0, false, false));
                    }
                }

                double var133;
                if (entity instanceof LivingEntity) {
                    LivingEntity _livEnt = (LivingEntity)entity;
                    var133 = (double)_livEnt.getHealth();
                } else {
                    var133 = (double)-1.0F;
                }

                old_health = var133;
                if (Damage_amount > (double)0.0F) {
                    if (ModList.get().isLoaded("minepiece")) {
                        entity.hurt(damagesource.is(TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("forge:combat"))) ? new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("jujutsucraft:damage_combat"))), entity_a) : new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("jujutsucraft:damage_curse"))), entity_a), (float)Damage_amount);
                    } else {
                        entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("jujutsucraft:damage_combat"))), entity_a), (float)Damage_amount);
                    }
                }

                old_cooldown = entity.getPersistentData().getDouble("COOLDOWN_TICKS");
                float var134;
                if (entity instanceof LivingEntity) {
                    LivingEntity _livEnt = (LivingEntity)entity;
                    var134 = _livEnt.getHealth();
                } else {
                    var134 = -1.0F;
                }

                if ((double)var134 != old_health) {
                    float var141;
                    if (entity instanceof LivingEntity) {
                        LivingEntity _livEnt = (LivingEntity)entity;
                        var141 = _livEnt.getHealth();
                    } else {
                        var141 = -1.0F;
                    }

                    double var135 = old_health - (double)var141;
                    double var142;
                    if (entity instanceof LivingEntity) {
                        LivingEntity _livEnt = (LivingEntity)entity;
                        var142 = (double)_livEnt.getMaxHealth();
                    } else {
                        var142 = (double)-1.0F;
                    }

                    label553: {
                        NUM1 = var135 / Math.max(var142, 0.1) * (entity instanceof Player ? (double)0.75F : (double)1.5F);
                        if (sourceentity instanceof LivingEntity) {
                            LivingEntity _livEnt = (LivingEntity)sourceentity;
                            if (_livEnt.hasEffect((MobEffect)JujutsucraftModMobEffects.ATTACKING.get())) {
                                var136 = _livEnt.getEffect((MobEffect)JujutsucraftModMobEffects.ATTACKING.get()).getAmplifier();
                                break label553;
                            }
                        }

                        var136 = 0;
                    }

                    if (var136 >= 1) {
                        NUM1 += (double)0.5F;
                    }

                    label831: {
                        label546: {
                            if (entity instanceof LivingEntity) {
                                LivingEntity _livEnt143 = (LivingEntity)entity;
                                if (_livEnt143.hasEffect((MobEffect)JujutsucraftModMobEffects.GUARD.get())) {
                                    break label546;
                                }
                            }

                            if (!guard) {
                                break label831;
                            }
                        }

                        label539: {
                            NUM1 *= (double)0.5F;
                            if (entity instanceof LivingEntity) {
                                LivingEntity _livEnt = (LivingEntity)entity;
                                if (_livEnt.hasEffect((MobEffect)JujutsucraftModMobEffects.GUARD.get())) {
                                    var137 = _livEnt.getEffect((MobEffect)JujutsucraftModMobEffects.GUARD.get()).getAmplifier();
                                    break label539;
                                }
                            }

                            var137 = 0;
                        }

                        if (var137 > 0) {
                            NUM1 *= (double)0.5F;
                        }
                    }

                    if (!entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:not_living"))) && !entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:ranged_ammo")))) {
                        label878: {
                            if (entity instanceof LivingEntity) {
                                LivingEntity _livEnt147 = (LivingEntity)entity;
                                if (_livEnt147.hasEffect((MobEffect)JujutsucraftModMobEffects.COMEDIAN.get())) {
                                    break label878;
                                }
                            }

                            if (entity instanceof LivingEntity) {
                                LivingEntity _livEnt148 = (LivingEntity)entity;
                                if (_livEnt148.hasEffect((MobEffect)JujutsucraftModMobEffects.EFFECT_KIRIN.get())) {
                                    break label878;
                                }
                            }

                            if ((!damagesource.is(TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("forge:curse"))) && !damagesource.is(TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("forge:combat"))) || sourceentity.getPersistentData().getBoolean("attack")) && entity instanceof LivingEntity) {
                                LivingEntity _entity = (LivingEntity)entity;
                                if (!_entity.level().isClientSide()) {
                                    _entity.addEffect(new MobEffectInstance((MobEffect)JujutsucraftModMobEffects.COOLDOWN_TIME.get(), 4, 0, false, false));
                                }
                            }

                            if (NUM1 >= 0.05) {
                                if (entity instanceof LivingEntity) {
                                    LivingEntity _entity = (LivingEntity)entity;
                                    if (!_entity.level().isClientSide()) {
                                        _entity.addEffect(new MobEffectInstance((MobEffect)JujutsucraftModMobEffects.STUN.get(), 10, 0, false, false));
                                    }
                                }

                                if (NUM1 >= 0.1) {
                                    if (entity instanceof LivingEntity) {
                                        LivingEntity _entity = (LivingEntity)entity;
                                        if (!_entity.level().isClientSide()) {
                                            _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int)Math.round(Math.min(NUM1 * (double)75.0F, (double)60.0F)), (int)Math.round(NUM1 * (double)5.0F), false, false));
                                        }
                                    }

                                    if (entity instanceof LivingEntity) {
                                        LivingEntity _entity = (LivingEntity)entity;
                                        if (!_entity.level().isClientSide()) {
                                            _entity.addEffect(new MobEffectInstance((MobEffect)JujutsucraftModMobEffects.COOLDOWN_TIME.get(), (int)Math.round(Math.min(NUM1 * (double)75.0F, (double)60.0F)), 0, false, false));
                                        }
                                    }

                                    if (NUM1 >= 0.2) {
                                        if (entity instanceof LivingEntity) {
                                            LivingEntity _entity = (LivingEntity)entity;
                                            if (!_entity.level().isClientSide()) {
                                                _entity.addEffect(new MobEffectInstance((MobEffect)JujutsucraftModMobEffects.STUN.get(), (int)Math.round(Math.min(NUM1 * (double)75.0F, (double)60.0F)), 1, false, false));
                                            }
                                        }

                                        label507: {
                                            if (entity instanceof LivingEntity) {
                                                LivingEntity _livEnt = (LivingEntity)entity;
                                                if (_livEnt.hasEffect((MobEffect)JujutsucraftModMobEffects.DOMAIN_EXPANSION.get())) {
                                                    var135 = (double)_livEnt.getEffect((MobEffect)JujutsucraftModMobEffects.DOMAIN_EXPANSION.get()).getDuration();
                                                    break label507;
                                                }
                                            }

                                            var135 = (double)0.0F;
                                        }

                                        Damage_amount = var135;
                                        if (Damage_amount > (double)0.0F && Damage_amount <= (double)600.0F && entity instanceof LivingEntity) {
                                            LivingEntity _entity = (LivingEntity)entity;
                                            _entity.removeEffect((MobEffect)JujutsucraftModMobEffects.DOMAIN_EXPANSION.get());
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (critical_nanami) {
                        critical_nanami = false;
                        if (world instanceof Level) {
                            Level _level = (Level)world;
                            if (!_level.isClientSide()) {
                                _level.playSound((Player)null, BlockPos.containing(entity.getX(), entity.getY() + (double)entity.getBbHeight() * (double)0.5F, entity.getZ()), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jujutsucraft:crush")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                            } else {
                                _level.playLocalSound(entity.getX(), entity.getY() + (double)entity.getBbHeight() * (double)0.5F, entity.getZ(), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jujutsucraft:crush")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                            }
                        }

                        if (world instanceof ServerLevel) {
                            ServerLevel _level = (ServerLevel)world;
                            _level.sendParticles((SimpleParticleType)JujutsucraftModParticleTypes.PARTICLE_NANAMI_2.get(), entity.getX(), entity.getY() + (double)entity.getBbHeight() * (double)0.5F, entity.getZ(), 1, (double)0.5F, (double)0.5F, (double)0.5F, (double)0.25F);
                        }

                        if (world instanceof ServerLevel) {
                            ServerLevel _level = (ServerLevel)world;
                            _level.sendParticles(ParticleTypes.CRIT, entity.getX(), entity.getY() + (double)entity.getBbHeight() * (double)0.5F, entity.getZ(), 25, (double)0.25F, (double)0.25F, (double)0.25F, (double)0.5F);
                        }
                    }

                    if (critical_ice) {
                        critical_ice = false;
                        if (world instanceof Level) {
                            Level _level = (Level)world;
                            if (!_level.isClientSide()) {
                                _level.playSound((Player)null, BlockPos.containing(entity.getX(), entity.getY() + (double)entity.getBbHeight() * (double)0.5F, entity.getZ()), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jujutsucraft:glass_crash")), SoundSource.NEUTRAL, 1.0F, 1.2F);
                            } else {
                                _level.playLocalSound(entity.getX(), entity.getY() + (double)entity.getBbHeight() * (double)0.5F, entity.getZ(), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jujutsucraft:glass_crash")), SoundSource.NEUTRAL, 1.0F, 1.2F, false);
                            }
                        }

                        world.levelEvent(2001, BlockPos.containing(entity.getX(), entity.getY() + (double)entity.getBbHeight() * (double)0.5F, entity.getZ()), Block.getId(Blocks.ICE.defaultBlockState()));
                    }
                }

                if (entity instanceof LivingEntity) {
                    LivingEntity _livEnt = (LivingEntity)entity;
                    var134 = _livEnt.getHealth();
                } else {
                    var134 = -1.0F;
                }

                label836: {
                    if ((double)var134 == old_health && entity instanceof LivingEntity) {
                        LivingEntity _livEnt185 = (LivingEntity)entity;
                        if (_livEnt185.hasEffect((MobEffect)JujutsucraftModMobEffects.DAMAGE_EFFECT.get())) {
                            break label836;
                        }
                    }

                    if (entity instanceof LivingEntity) {
                        LivingEntity _livEnt186 = (LivingEntity)entity;
                        if (_livEnt186.hasEffect((MobEffect)JujutsucraftModMobEffects.INFINITY_EFFECT.get())) {
                            if (!(entity instanceof LivingEntity)) {
                                break label836;
                            }

                            LivingEntity _livEnt187 = (LivingEntity)entity;
                            if (!_livEnt187.hasEffect((MobEffect)JujutsucraftModMobEffects.NEUTRALIZATION.get())) {
                                break label836;
                            }
                        }
                    }

                    if (entity instanceof LivingEntity) {
                        LivingEntity _entity = (LivingEntity)entity;
                        if (!_entity.level().isClientSide()) {
                            _entity.addEffect(new MobEffectInstance((MobEffect)JujutsucraftModMobEffects.DAMAGE_EFFECT.get(), 3, 0, false, false));
                        }
                    }

                    if (entity instanceof LivingEntity) {
                        LivingEntity _entity = (LivingEntity)entity;
                        if (!_entity.level().isClientSide()) {
                            _entity.addEffect(new MobEffectInstance((MobEffect)JujutsucraftModMobEffects.COOLDOWN_TIME_GUARD.get(), 10, 0, false, false));
                        }
                    }

                    if (entity instanceof LivingEntity) {
                        LivingEntity _livingEntity = (LivingEntity)entity;
                        _livingEntity.addEffect(new MobEffectInstance((MobEffect)JujutsucraftModMobEffects.DAMAGE_EFFECT.get(), 3, 0, false, false));
                    }

                    if (entity instanceof LivingEntity) {
                        LivingEntity _livingEntity = (LivingEntity)entity;
                        _livingEntity.addEffect(new MobEffectInstance((MobEffect)JujutsucraftModMobEffects.COOLDOWN_TIME_GUARD.get(), 10, 0, false, false));
                    }

                    if (!damagesource.is(TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("forge:combat")))) {
                        if (guard) {
                            GuardEffectProcedureProcedure.execute(world, entity.getX() + (double)entity.getBbWidth() * (Math.random() - (double)0.5F), entity.getY() + (double)entity.getBbHeight() * Math.random(), entity.getZ() + (double)entity.getBbWidth() * (Math.random() - (double)0.5F), sourceentity, entity);
                        }

                        if (!damagesource.is(TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("forge:curse"))) && !damagesource.is(TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("forge:combat"))) || sourceentity.getPersistentData().getBoolean("attack")) {
                            DamageEffectProcedureProcedure.execute(world, sourceentity, entity, amount);
                        }
                    }
                }

                entity.getPersistentData().putDouble("COOLDOWN_TICKS", old_cooldown);
                if (event != null && event.isCancelable()) {
                    event.setCanceled(true);
                }
            }

        }

    }
}

/*SukunaAttackAnimationsProcedure.execute(sourceentity, entity, world);

                if (sourceentity instanceof FushiguroTojiBugEntity && amount >= 100) {
                    if (!((((FushiguroTojiBugEntity) sourceentity).animationprocedure).equals("playful" + Mth.nextInt(RandomSource.create(), 1, 4)))) {
                        ((FushiguroTojiBugEntity) sourceentity).setAnimation("playful" + Mth.nextInt(RandomSource.create(), 1, 4));
                    }
                }

                if (sourceentity instanceof FushiguroTojiEntity && amount >= 100 && ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == JujutsucraftModItems.PLAYFUL_CLOUD.get())) {
                    if (!((((FushiguroTojiEntity) sourceentity).animationprocedure).equals("playful" + Mth.nextInt(RandomSource.create(), 1, 4)))) {
                        ((FushiguroTojiEntity) sourceentity).setAnimation("playful" + Mth.nextInt(RandomSource.create(), 1, 4));
                    }
                }


                // Normal Player Attack Animations
                if ((sourceentity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).Run == 1) {
                    if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
                        if (!(sourceentity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(JujutsucraftaddonModMobEffects.ANIMATION.get()))) {
                            _entity.addEffect(new MobEffectInstance(JujutsucraftaddonModMobEffects.ANIMATION.get(), 10, 1, false, false));
                        }

                }

                if ((entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables())).PlayerCurseTechnique == 20) {
                    if ((entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables())).PlayerCursePower >= 1000) {
                        if (Math.random() < (1) / ((float) 60)) {
                            SwapTodoTarget.execute(entity, world, sourceentity);
                            AnimationDodgeProcedure.execute(world, entity);
                            if (event != null && event.isCancelable()) {
                                event.setCanceled(true);
                            } else if (event != null && event.hasResult()) {
                                event.setResult(Event.Result.DENY);
                            }
                        }
                    }
                } else if (entity instanceof TodoAoiEntity) {
                    if (Math.random() < (1) / ((float) 60)) {
                        SwapTodoTarget.execute(entity, world, sourceentity);
                        if (event != null && event.isCancelable()) {
                            event.setCanceled(true);
                        } else if (event != null && event.hasResult()) {
                            event.setResult(Event.Result.DENY);
                        }
                    }
                }


                // Ultra Instinct
                if (EnchantmentHelper.getItemEnchantmentLevel(JujutsucraftaddonModEnchantments.ULTRA_INSTINCT.get(), (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY)) != 0) {
                    if (Math.random() < ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getEnchantmentLevel(JujutsucraftaddonModEnchantments.ULTRA_INSTINCT.get())) / ((float) 100)) {
                        AnimationDodgeProcedure.execute(world, entity);
                        if (event != null && event.isCancelable()) {
                            event.setCanceled(true);
                        } else if (event != null && event.hasResult()) {
                            event.setResult(Event.Result.DENY);
                        }
                    }
                }

                // Itadori Awakening Buff
                if (sourceentity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(JujutsucraftaddonModMobEffects.ITADORI_AWAKENING.get())) {
                    if (Math.random() < (1) / ((float) 60)) {
                        sourceentity.getPersistentData().putDouble("skill", 3810.0);
                        sourceentity.getPersistentData().putDouble("cnt6", 4.0);
                        if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
                            _entity.addEffect(new MobEffectInstance(JujutsucraftModMobEffects.CURSED_TECHNIQUE.get(), 60, 1, false, false));
                        double yaw = Math.toRadians(sourceentity.getYRot() + 90.0F);
                        double pitch = Math.toRadians(sourceentity.getXRot());
                        sourceentity.getPersistentData().putDouble("x_pos", entity.getX() + Math.cos(yaw) * Math.cos(pitch) * (double) (2.0F + entity.getBbWidth()));
                        sourceentity.getPersistentData().putDouble("y_pos", entity.getY() + (double) entity.getBbHeight() * 0.75 + Math.sin(pitch) * -1.0 * (double) (2.0F + entity.getBbWidth()));
                        sourceentity.getPersistentData().putDouble("z_pos", entity.getZ() + Math.sin(yaw) * Math.cos(pitch) * (double) (2.0F + entity.getBbWidth()));
                        if (!(sourceentity instanceof LivingEntity _livEnt01 && _livEnt01.hasEffect(JujutsucraftaddonModMobEffects.KOKUSEN_N.get()))) {
                            if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
                                _entity.addEffect(new MobEffectInstance(JujutsucraftaddonModMobEffects.KOKUSEN_N.get(), 20, 1, false, false));
                        }
                    }
                }

                // Uro Buff
                if ((entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables())).PlayerCurseTechnique == 38) {
                    if (((entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).Clans).equals("Fujiwara")) {
                        if (Math.random() < (1) / ((float) 50)) {
                            entity.getPersistentData().putDouble("skill", 3810.0);
                            entity.getPersistentData().putDouble("cnt6", 4.0);
                            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
                                _entity.addEffect(new MobEffectInstance(JujutsucraftModMobEffects.CURSED_TECHNIQUE.get(), 60, 1, false, false));
                            double yaw = Math.toRadians(entity.getYRot() + 90.0F);
                            double pitch = Math.toRadians(entity.getXRot());
                            entity.getPersistentData().putDouble("x_pos", entity.getX() + Math.cos(yaw) * Math.cos(pitch) * (double) (2.0F + entity.getBbWidth()));
                            entity.getPersistentData().putDouble("y_pos", entity.getY() + (double) entity.getBbHeight() * 0.75 + Math.sin(pitch) * -1.0 * (double) (2.0F + entity.getBbWidth()));
                            entity.getPersistentData().putDouble("z_pos", entity.getZ() + Math.sin(yaw) * Math.cos(pitch) * (double) (2.0F + entity.getBbWidth()));
                            AnimationDodgeProcedure.execute(world, entity);
                            if (event != null && event.isCancelable()) {
                                event.setCanceled(true);
                            } else if (event != null && event.hasResult()) {
                                event.setResult(Event.Result.DENY);
                            }
                        }
                    } else {
                        if (Math.random() < (1) / ((float) 120)) {
                            entity.getPersistentData().putDouble("skill", 3810.0);
                            entity.getPersistentData().putDouble("cnt6", 4.0);
                            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
                                _entity.addEffect(new MobEffectInstance(JujutsucraftModMobEffects.CURSED_TECHNIQUE.get(), 60, 1, false, false));
                            double yaw = Math.toRadians(entity.getYRot() + 90.0F);
                            double pitch = Math.toRadians(entity.getXRot());
                            entity.getPersistentData().putDouble("x_pos", entity.getX() + Math.cos(yaw) * Math.cos(pitch) * (double) (2.0F + entity.getBbWidth()));
                            entity.getPersistentData().putDouble("y_pos", entity.getY() + (double) entity.getBbHeight() * 0.75 + Math.sin(pitch) * -1.0 * (double) (2.0F + entity.getBbWidth()));
                            entity.getPersistentData().putDouble("z_pos", entity.getZ() + Math.sin(yaw) * Math.cos(pitch) * (double) (2.0F + entity.getBbWidth()));
                            AnimationDodgeProcedure.execute(world, entity);
                            if (event != null && event.isCancelable()) {
                                event.setCanceled(true);
                            } else if (event != null && event.hasResult()) {
                                event.setResult(Event.Result.DENY);
                            }
                        }
                    }
                }

                // Rejected Zenin and Toji Buff
                if ((entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables())).PlayerCurseTechnique == -1) {
                    if ((entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables())).PlayerCursePower == 0) {
                        if (((entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).Clans).equals("Rejected Zenin")) {
                            if (Math.random() < (1) / ((float) 40)) {
                                AnimationDodgeProcedure.execute(world, entity);
                                if (event != null && event.isCancelable()) {
                                    event.setCanceled(true);
                                } else if (event != null && event.hasResult()) {
                                    event.setResult(Event.Result.DENY);
                                }
                            }
                        } else {
                            if (Math.random() < (1) / ((float) 80)) {
                                AnimationDodgeProcedure.execute(world, entity);
                                if (event != null && event.isCancelable()) {
                                    event.setCanceled(true);
                                } else if (event != null && event.hasResult()) {
                                    event.setResult(Event.Result.DENY);
                                }
                            }
                        }
                    }
                }

                if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == JujutsucraftaddonModItems.WUKONG_SET_BOOTS.get().asItem()) {
                    if (Math.random() < (1) / ((float) 100)) {
                        if (!(entity instanceof GeoEntity)) {
                            AnimationDodgeProcedure.execute(world, entity);
                            if (event != null && event.isCancelable()) {
                                event.setCanceled(true);
                            } else if (event != null && event.hasResult()) {
                                event.setResult(Event.Result.DENY);
                            }
                        }
                    }
                }

                if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == JujutsucraftaddonModItems.WUKONG_SET_CHESTPLATE.get().asItem()) {
                    if (Math.random() < (1) / ((float) 20)) {
                        sourceentity.hurt(damagesource, (float) amount / 10);
                    }
                }*/
