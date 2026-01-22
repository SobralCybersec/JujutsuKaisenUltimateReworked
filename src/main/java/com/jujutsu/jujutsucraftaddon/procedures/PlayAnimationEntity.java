package com.jujutsu.jujutsucraftaddon.procedures;

import com.jujutsu.jujutsucraftaddon.entity.sukuna.SukunaFushiguroEntity;
import com.jujutsu.jujutsucraftaddon.init.mod.JujutsucraftaddonModItems;
import net.mcreator.jujutsucraft.entity.FushiguroTojiBugEntity;
import net.mcreator.jujutsucraft.entity.SukunaEntity;
import net.mcreator.jujutsucraft.entity.SukunaPerfectEntity;
import net.mcreator.jujutsucraft.init.JujutsucraftModAttributes;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.mcreator.jujutsucraft.procedures.GetEntityAnimationProcedure;
import net.mcreator.jujutsucraft.procedures.LogicSwordProcedure;
import net.mcreator.jujutsucraft.procedures.PlayAnimationEntity2Procedure;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.eventbus.api.Event;
import software.bernie.geckolib.animatable.GeoEntity;

import javax.annotation.Nullable;

public class PlayAnimationEntity {
    public static void execute(@Nullable Event event, LevelAccessor world, DamageSource damagesource, Entity entity) {
        boolean sword = false;
        double NUM2 = 0.0;
        double NUM3 = 0.0;
        double NUM1 = 0.0;
        double rnd = 0.0;
        String ANIME_NAME = "";
        String STR1 = "";
        if (damagesource != null && entity != null) {
            if (damagesource.is(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("jujutsucraft:start_animation"))) && entity instanceof GeoEntity && entity instanceof LivingEntity) {


                LivingEntity _livingEntity2 = (LivingEntity)entity;
                if (_livingEntity2.getAttributes().hasAttribute((Attribute)JujutsucraftModAttributes.ANIMATION_1.get())) {
                    double var10000;
                    label285: {
                        if (entity instanceof LivingEntity) {
                            LivingEntity _livingEntity3 = (LivingEntity)entity;
                            if (_livingEntity3.getAttributes().hasAttribute((Attribute)JujutsucraftModAttributes.ANIMATION_1.get())) {
                                var10000 = _livingEntity3.getAttribute((Attribute)JujutsucraftModAttributes.ANIMATION_1.get()).getBaseValue();
                                break label285;
                            }
                        }

                        var10000 = (double)0.0F;
                    }

                    label280: {
                        NUM1 = var10000;
                        if (entity instanceof LivingEntity) {
                            LivingEntity _livingEntity4 = (LivingEntity)entity;
                            if (_livingEntity4.getAttributes().hasAttribute((Attribute)JujutsucraftModAttributes.ANIMATION_2.get())) {
                                var10000 = _livingEntity4.getAttribute((Attribute)JujutsucraftModAttributes.ANIMATION_2.get()).getBaseValue();
                                break label280;
                            }
                        }

                        var10000 = (double)0.0F;
                    }

                    NUM2 = var10000;
                    NUM1 += (double)(NUM1 <= (double)-50.0F && NUM1 >= (double)-100.0F ? 100 : 0);
                    NUM3 = NUM1 >= (double)0.0F ? NUM1 % (double)100.0F : (double)100.0F;
                    sword = LogicSwordProcedure.execute(entity);
                    STR1 = GetEntityAnimationProcedure.execute(entity);
                    if (STR1.contains("_right")) {
                        STR1 = "left";
                    } else if (STR1.contains("_left")) {
                        STR1 = "right";
                    } else {
                        STR1 = Math.random() > (double)0.5F ? "right" : "left";
                    }

                    if (NUM3 >= (double)0.0F && NUM3 <= (double)4.0F) {
                        if (sword && NUM2 > (double)0.0F) {
                            if (NUM2 != (double)1.0F && NUM2 != (double)2.0F && NUM2 != (double)3.0F) {
                                if (NUM2 == (double)4.0F || NUM2 == (double)5.0F || NUM2 == (double)6.0F) {
                                    STR1 = "left";
                                }
                            } else {
                                STR1 = "right";
                            }
                        }

                        if (NUM3 != (double)0.0F && NUM3 != (double)2.0F && NUM3 != (double)3.0F) {
                            if (NUM3 == (double)1.0F) {
                                if (sword) {
                                    ANIME_NAME = "sword_to_right";
                                } else {
                                    String var29 = Math.random() > (double)0.75F ? "punch_" : "kick_";
                                    ANIME_NAME = var29 + STR1;
                                }
                            } else if (NUM3 == (double)4.0F) {
                                ANIME_NAME = sword ? "sword_overhead" : "punch_overhead";
                            }
                        } else if (NUM2 != (double)0.0F && !(NUM2 >= (double)100.0F)) {
                            if (sword) {
                                rnd = Math.ceil(Math.random() * (double)3.0F);
                                ANIME_NAME = rnd == (double)1.0F ? "sword_overhead" : "sword_to_" + STR1;
                            } else {
                                ANIME_NAME = "punch_" + STR1;
                            }
                        } else if (sword) {
                            ANIME_NAME = "sword_to_" + STR1;
                        } else {
                            String var28 = Math.random() > (double)0.5F ? "kick_" : "punch_";
                            ANIME_NAME = var28 + STR1;
                        }
                    } else if (NUM3 == (double)20.0F) {
                        if (NUM1 == (double)220.0F) {
                            ANIME_NAME = "red";
                        } else {
                            ANIME_NAME = "domain_expansion1";
                        }
                    } else if (NUM1 < (double)0.0F) {
                        if (NUM1 >= (double)-10.0F) {
                            if (NUM1 == (double)-1.0F) {
                                ANIME_NAME = "backstep";
                            } else if (NUM1 == (double)-2.0F) {
                                ANIME_NAME = "death";
                            } else if (NUM1 == (double)-3.0F) {
                                ANIME_NAME = "right_arm_up";
                            } else if (NUM1 == (double)-4.0F) {
                                ANIME_NAME = "both_arm_front";
                            } else if (NUM1 == (double)-5.0F) {
                                if (NUM2 == (double)0.0F) {
                                    ANIME_NAME = "sword_to_right";
                                } else if (NUM2 == (double)1.0F) {
                                    ANIME_NAME = "sword_to_left";
                                }
                            }

                            if (NUM1 == (double)-6.0F) {
                                ANIME_NAME = "kick_" + STR1;
                            } else if (NUM1 == (double)-7.0F) {
                                ANIME_NAME = "punch_" + STR1;
                            } else if (NUM1 == (double)-8.0F) {
                                if (NUM2 == (double)0.0F) {
                                    ANIME_NAME = "rotation";
                                } else if (NUM2 == (double)1.0F) {
                                    ANIME_NAME = "rotation2";
                                } else if (NUM2 == (double)2.0F) {
                                    ANIME_NAME = "ragnaraku2";
                                }
                            } else if (NUM1 == (double)-9.0F) {
                                ANIME_NAME = "guard";
                            } else if (NUM1 == (double)-10.0F) {
                                ANIME_NAME = "fall1";
                            }
                        } else if (NUM1 >= (double)-15.0F) {
                            ANIME_NAME = "dance" + Math.round(NUM1 + (double)16.0F);
                        } else if (NUM1 >= (double)-20.0F) {
                            if (NUM1 == (double)-16.0F) {
                                ANIME_NAME = NUM2 == (double)0.0F ? "simple_domain1" : "simple_domain2";
                            } else if (NUM1 == (double)-17.0F) {
                                ANIME_NAME = "clap";
                            } else if (NUM1 == (double)-18.0F) {
                                ANIME_NAME = "fly";
                            } else if (NUM1 == (double)-19.0F) {
                                if (NUM2 == (double)0.0F) {
                                    ANIME_NAME = "breath1";
                                } else if (NUM2 == (double)1.0F) {
                                    ANIME_NAME = "breath2";
                                }
                            } else if (NUM1 == (double)-20.0F) {
                                if (NUM2 == (double)0.0F) {
                                    ANIME_NAME = "step_back";
                                } else if (NUM2 == (double)1.0F) {
                                    ANIME_NAME = "step_right";
                                } else if (NUM2 == (double)2.0F) {
                                    ANIME_NAME = "step_left";
                                } else if (NUM2 == (double)3.0F) {
                                    ANIME_NAME = "step_front";
                                }
                            }
                        } else if (NUM1 >= (double)-25.0F && NUM1 == (double)-21.0F) {
                            ANIME_NAME = "invisibility";
                        }

                        if (NUM1 == (double)-49.0F) {
                            ANIME_NAME = "empty";
                        }
                    } else {
                        if (NUM1 == 107.0) {
                            if (entity instanceof SukunaFushiguroEntity || entity instanceof SukunaEntity || entity instanceof SukunaPerfectEntity){
                                if (entity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(JujutsucraftModMobEffects.DOMAIN_EXPANSION.get())) {
                                    ANIME_NAME = "fuga2";
                                } else {
                                    ANIME_NAME = "fuga1";
                                }
                            } else {
                                ANIME_NAME = "fuga";
                            }
                        } else if (NUM1 == 207.0) {
                            if (entity.onGround()) {
                                if (entity instanceof SukunaFushiguroEntity || entity instanceof SukunaEntity || entity instanceof SukunaPerfectEntity){
                                    ANIME_NAME = "sukuna" + Mth.nextInt(RandomSource.create(), 1, 26);
                                } else {
                                    ANIME_NAME = "red";
                                }
                            } else {
                                if (entity instanceof SukunaFushiguroEntity || entity instanceof SukunaEntity || entity instanceof SukunaPerfectEntity){
                                    ANIME_NAME = "dismantleback" + (Mth.nextInt(RandomSource.create(), 1, 2));
                                } else {
                                    ANIME_NAME = "red5";
                                }
                            }
                        } else if (NUM1 == 618.0) {
                            ANIME_NAME = "ten_shadows_technique_mahoraga";
                        }

                        if (NUM1 == 406.0) {
                            ANIME_NAME = "burn_out";
                        } else if (NUM1 == 415.0) {
                            ANIME_NAME = "meteor";
                        }

                        if (NUM1 == 240){
                            ANIME_NAME = "worldslash" + (Mth.nextInt(RandomSource.create(), 2, 4));
                        }

                        if (NUM1 == 1706.0) {
                            ANIME_NAME = "kick_flying";
                        } else if (NUM1 == 1715.0) {
                            ANIME_NAME = "wifi";
                        } else if (NUM1 == 2015.0) {
                            ANIME_NAME = "plus_ultra";
                        }

                        if (NUM1 == 20010.0){
                            ANIME_NAME = "murasaki";
                        }
                    }

                    if (!(((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.MAINHAND) : ItemStack.EMPTY).getItem() == JujutsucraftaddonModItems.WUKONG_STAFF_TRUE.get().asItem()) || ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.MAINHAND) : ItemStack.EMPTY).getItem() == JujutsucraftaddonModItems.HAMMER_WUKONG.get().asItem()) || ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.MAINHAND) : ItemStack.EMPTY).getItem() == JujutsucraftaddonModItems.HALBERD_WUKONG.get().asItem()) || ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.MAINHAND) : ItemStack.EMPTY).getItem() == JujutsucraftaddonModItems.SWORD_WUKONG.get().asItem()) || ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.MAINHAND) : ItemStack.EMPTY).getItem() == JujutsucraftaddonModItems.GREAT_SWORD_WUKONG.get().asItem()))) {
                        if (!ANIME_NAME.isEmpty()) {
                            if (entity instanceof FushiguroTojiBugEntity) {
                                if (!((((FushiguroTojiBugEntity) entity).animationprocedure).equals("playfulrush"))) {
                                    PlayAnimationEntity2Procedure.execute(entity, ANIME_NAME);
                                }
                            } else {
                                PlayAnimationEntity2Procedure.execute(entity, ANIME_NAME);
                            }
                        }
                    }

                    if (entity instanceof FushiguroTojiBugEntity) {
                        String ANIME_NAME2 = "wu" + Mth.nextInt(RandomSource.create(), 1, 17);
                        PlayAnimationEntity2Procedure.execute(entity, ANIME_NAME2);
                    }

                    if (entity instanceof LivingEntity) {
                        LivingEntity _livingEntity5 = (LivingEntity)entity;
                        if (_livingEntity5.getAttributes().hasAttribute((Attribute)JujutsucraftModAttributes.ANIMATION_1.get())) {
                            _livingEntity5.getAttribute((Attribute)JujutsucraftModAttributes.ANIMATION_1.get()).setBaseValue((double)0.0F);
                        }
                    }

                    if (entity instanceof LivingEntity) {
                        LivingEntity _livingEntity6 = (LivingEntity)entity;
                        if (_livingEntity6.getAttributes().hasAttribute((Attribute)JujutsucraftModAttributes.ANIMATION_2.get())) {
                            _livingEntity6.getAttribute((Attribute)JujutsucraftModAttributes.ANIMATION_2.get()).setBaseValue((double)0.0F);
                        }
                    }

                    if (event != null && event.isCancelable()) {
                        event.setCanceled(true);
                    }
                }

               /* LivingEntity _livingEntity2 = (LivingEntity) entity;
                if (_livingEntity2.getAttributes().hasAttribute((Attribute) JujutsucraftModAttributes.ANIMATION_1.get())) {
                    LivingEntity _livingEntity6;
                    double var10000;
                    label248:
                    {
                        if (entity instanceof LivingEntity) {
                            _livingEntity6 = (LivingEntity) entity;
                            if (_livingEntity6.getAttributes().hasAttribute((Attribute) JujutsucraftModAttributes.ANIMATION_1.get())) {
                                var10000 = _livingEntity6.getAttribute((Attribute) JujutsucraftModAttributes.ANIMATION_1.get()).getBaseValue();
                                break label248;
                            }
                        }

                        var10000 = 0.0;
                    }

                    label243:
                    {
                        NUM1 = var10000;
                        if (entity instanceof LivingEntity) {
                            _livingEntity6 = (LivingEntity) entity;
                            if (_livingEntity6.getAttributes().hasAttribute((Attribute) JujutsucraftModAttributes.ANIMATION_2.get())) {
                                var10000 = _livingEntity6.getAttribute((Attribute) JujutsucraftModAttributes.ANIMATION_2.get()).getBaseValue();
                                break label243;
                            }
                        }

                        var10000 = 0.0;
                    }

                    NUM2 = var10000;
                    NUM1 += (double)(NUM1 <= (double)-50.0F && NUM1 >= (double)-100.0F ? 100 : 0);
                    NUM3 = NUM1 >= (double)0.0F ? NUM1 % (double)100.0F : (double)100.0F;
                    sword = LogicSwordProcedure.execute(entity);
                    STR1 = GetEntityAnimationProcedure.execute(entity);
                    if (NUM3 >= 0.0 && NUM3 <= 4.0) {
                        if (sword && NUM2 > 0.0) {
                            if (NUM2 != 1.0 && NUM2 != 2.0 && NUM2 != 3.0) {
                                if (NUM2 == 4.0 || NUM2 == 5.0 || NUM2 == 6.0) {
                                    STR1 = "left";
                                }
                            } else {
                                STR1 = "right";
                            }
                        }

                        String var16;
                        if (NUM3 != 0.0 && NUM3 != 2.0 && NUM3 != 3.0) {
                            if (NUM3 == 1.0) {
                                if (sword) {
                                    ANIME_NAME = "sword_to_right";
                                } else {
                                    var16 = Math.random() > 0.75 ? "punch_" : "kick_";
                                    ANIME_NAME = var16 + STR1;
                                }
                            } else if (NUM3 == 4.0) {
                                ANIME_NAME = sword ? "sword_overhead" : "punch_overhead";
                            }
                        } else if (NUM2 != 0.0 && !(NUM2 >= 100.0)) {
                            if (sword) {
                                rnd = Math.ceil(Math.random() * 3.0);
                                ANIME_NAME = rnd == 1.0 ? "sword_overhead" : "sword_to_" + STR1;
                            } else {
                                ANIME_NAME = "punch_" + STR1;
                            }
                        } else if (sword) {
                            ANIME_NAME = "sword_to_" + STR1;
                        } else {
                            var16 = Math.random() > 0.5 ? "kick_" : "punch_";
                            ANIME_NAME = var16 + STR1;
                        }
                    } else if (NUM3 == 20.0) {
                        if (NUM1 == 220.0) {
                            ANIME_NAME = "red";
                        } else {
                            ANIME_NAME = "domain_expansion1";
                        }
                    } else if (NUM1 < 0.0) {
                        if (NUM1 >= -10.0) {
                            if (NUM1 == -1.0) {
                                ANIME_NAME = "backstep";
                            } else if (NUM1 == -2.0) {
                                ANIME_NAME = "death";
                            } else if (NUM1 == -3.0) {
                                ANIME_NAME = "right_arm_up";
                            } else if (NUM1 == -4.0) {
                                ANIME_NAME = "both_arm_front";
                            } else if (NUM1 == -5.0) {
                                ANIME_NAME = "sword_to_right";
                            }

                            if (NUM1 == -6.0) {
                                ANIME_NAME = "kick_" + STR1;
                            } else if (NUM1 == -7.0) {
                                ANIME_NAME = "punch_" + STR1;
                            } else if (NUM1 == -8.0) {
                                if (NUM2 == 0.0) {
                                    ANIME_NAME = "rotation";
                                } else if (NUM2 == 1.0) {
                                    ANIME_NAME = "rotation2";
                                } else if (NUM2 == 2.0) {
                                    ANIME_NAME = "ragnaraku2";
                                }
                            } else if (NUM1 == -9.0) {
                                ANIME_NAME = "guard";
                            } else if (NUM1 == -10.0) {
                                ANIME_NAME = "fall1";
                            }
                        } else if (NUM1 >= -15.0) {
                            ANIME_NAME = "dance" + Math.round(NUM1 + 16.0);
                        } else if (NUM1 >= -20.0) {
                            if (NUM1 == -16.0) {
                                ANIME_NAME = NUM2 == 0.0 ? "simple_domain1" : "simple_domain2";
                            } else if (NUM1 == -17.0) {
                                ANIME_NAME = "clap";
                            } else if (NUM1 == -18.0) {
                                ANIME_NAME = "fly";
                            }
                        }
                    } else {
                        if (NUM1 == 107.0) {
                            if (entity instanceof SukunaFushiguroEntity || entity instanceof SukunaEntity || entity instanceof SukunaPerfectEntity){
                                if (entity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(JujutsucraftModMobEffects.DOMAIN_EXPANSION.get())) {
                                    ANIME_NAME = "fuga2";
                                } else {
                                    ANIME_NAME = "fuga1";
                                }
                            } else {
                                ANIME_NAME = "fuga";
                            }
                        } else if (NUM1 == 207.0) {
                            if (entity.onGround()) {
                                if (entity instanceof SukunaFushiguroEntity || entity instanceof SukunaEntity || entity instanceof SukunaPerfectEntity){
                                    ANIME_NAME = "sukuna" + Mth.nextInt(RandomSource.create(), 1, 26);
                                } else {
                                    ANIME_NAME = "red";
                                }
                            } else {
                                if (entity instanceof SukunaFushiguroEntity || entity instanceof SukunaEntity || entity instanceof SukunaPerfectEntity){
                                    ANIME_NAME = "dismantleback" + (Mth.nextInt(RandomSource.create(), 1, 2));
                                } else {
                                    ANIME_NAME = "red5";
                                }
                            }
                        } else if (NUM1 == 618.0) {
                            ANIME_NAME = "ten_shadows_technique_mahoraga";
                        }

                        if (NUM1 == 406.0) {
                            ANIME_NAME = "burn_out";
                        } else if (NUM1 == 415.0) {
                            ANIME_NAME = "meteor";
                        }

                        if (NUM1 == 240){
                            ANIME_NAME = "worldslash" + (Mth.nextInt(RandomSource.create(), 2, 4));
                        }

                        if (NUM1 == 1706.0) {
                            ANIME_NAME = "kick_flying";
                        } else if (NUM1 == 1715.0) {
                            ANIME_NAME = "wifi";
                        } else if (NUM1 == 2015.0) {
                            ANIME_NAME = "plus_ultra";
                        }

                        if (NUM1 == 20010.0){
                            ANIME_NAME = "murasaki";
                        }
                    }

                    if (!(((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.MAINHAND) : ItemStack.EMPTY).getItem() == JujutsucraftaddonModItems.WUKONG_STAFF_TRUE.get().asItem()) || ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.MAINHAND) : ItemStack.EMPTY).getItem() == JujutsucraftaddonModItems.HAMMER_WUKONG.get().asItem()) || ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.MAINHAND) : ItemStack.EMPTY).getItem() == JujutsucraftaddonModItems.HALBERD_WUKONG.get().asItem()) || ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.MAINHAND) : ItemStack.EMPTY).getItem() == JujutsucraftaddonModItems.SWORD_WUKONG.get().asItem()) || ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.MAINHAND) : ItemStack.EMPTY).getItem() == JujutsucraftaddonModItems.GREAT_SWORD_WUKONG.get().asItem()))) {
                        if (!ANIME_NAME.isEmpty()) {
                            if (entity instanceof FushiguroTojiBugEntity) {
                                if (!((((FushiguroTojiBugEntity) entity).animationprocedure).equals("playfulrush"))) {
                                    PlayAnimationEntity2Procedure.execute(entity, ANIME_NAME);
                                }
                            } else {
                                PlayAnimationEntity2Procedure.execute(entity, ANIME_NAME);
                            }
                        }
                    } else {
                        String ANIME_NAME2 = "wu" + Mth.nextInt(RandomSource.create(), 1, 17);
                        PlayAnimationEntity2Procedure.execute(entity, ANIME_NAME2);
                    }


                    if (entity instanceof LivingEntity) {
                        _livingEntity6 = (LivingEntity) entity;
                        if (_livingEntity6.getAttributes().hasAttribute((Attribute) JujutsucraftModAttributes.ANIMATION_1.get())) {
                            _livingEntity6.getAttribute((Attribute) JujutsucraftModAttributes.ANIMATION_1.get()).setBaseValue(0.0);
                        }
                    }

                    if (entity instanceof LivingEntity) {
                        _livingEntity6 = (LivingEntity) entity;
                        if (_livingEntity6.getAttributes().hasAttribute((Attribute) JujutsucraftModAttributes.ANIMATION_2.get())) {
                            _livingEntity6.getAttribute((Attribute) JujutsucraftModAttributes.ANIMATION_2.get()).setBaseValue(0.0);
                        }
                    }

                    if (event != null && event.isCancelable()) {
                        event.setCanceled(true);
                    } else if (event != null && event.hasResult()) {
                        event.setResult(Event.Result.DENY);
                    }
                }*/
            }
        }
    }
}
