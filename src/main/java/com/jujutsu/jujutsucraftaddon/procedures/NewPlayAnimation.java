package com.jujutsu.jujutsucraftaddon.procedures;

import com.jujutsu.jujutsucraftaddon.init.mod.JujutsucraftaddonModMobEffects;
import com.jujutsu.jujutsucraftaddon.network.JujutsucraftaddonModVariables;
import com.jujutsu.jujutsucraftaddon.procedures.setup.AnimationsJJKUR;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.mcreator.jujutsucraft.JujutsucraftMod;
import net.mcreator.jujutsucraft.init.JujutsucraftModAttributes;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.LogicSwordProcedure;
import net.mcreator.jujutsucraft.procedures.SetupAnimationsProcedure;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.network.PacketDistributor;
import software.bernie.geckolib.core.animation.Animation;

import javax.annotation.Nullable;

public class NewPlayAnimation {
    public static void execute(@Nullable Event event, LevelAccessor world, DamageSource damagesource, Entity entity) {
        if (damagesource.is(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("jujutsucraft:start_animation"))) && entity instanceof Player)  {
            boolean sword = false;
            Entity entiry_a;
            entiry_a = entity;
            double rnd = 0.0;
            ModifierLayer animation;
            AbstractClientPlayer player;
            double NUM2 = (double) 0.0F;
            double NUM3 = (double) 0.0F;
            double NUM1 = (double) 0.0F;
            String STR1 = "";
            String animation_name = "";

            if (world.isClientSide() && entiry_a instanceof AbstractClientPlayer) {
                AnimationsJJKUR.playAnimationsJJKUR(event, world, damagesource, entity);
            }

            if (!(((LivingEntity) entiry_a).hasEffect(JujutsucraftaddonModMobEffects.ANIMATION.get())) || (((LivingEntity) entiry_a).hasEffect(JujutsucraftaddonModMobEffects.SOKA_MONA.get())) || (((LivingEntity) entiry_a).hasEffect(JujutsucraftaddonModMobEffects.ANIMATION_TWO.get())) || (((LivingEntity) entiry_a).hasEffect(JujutsucraftaddonModMobEffects.ANIMATION_HEIAN.get()) || ((LivingEntity) entiry_a).hasEffect(JujutsucraftaddonModMobEffects.JACKPOT.get()) || ((LivingEntity) entiry_a).hasEffect(JujutsucraftaddonModMobEffects.DODGE.get()) || ((LivingEntity) entiry_a).hasEffect(JujutsucraftaddonModMobEffects.WORLD_CUT.get()))) {
                double var10000;
                label275: {
                    if (entity instanceof LivingEntity) {
                        LivingEntity _livingEntity2 = (LivingEntity)entity;
                        if (_livingEntity2.getAttributes().hasAttribute((Attribute)JujutsucraftModAttributes.ANIMATION_1.get())) {
                            var10000 = _livingEntity2.getAttribute((Attribute)JujutsucraftModAttributes.ANIMATION_1.get()).getBaseValue();
                            break label275;
                        }
                    }

                    var10000 = (double)0.0F;
                }

                label270: {
                    NUM1 = var10000;
                    if (entity instanceof LivingEntity) {
                        LivingEntity _livingEntity3 = (LivingEntity)entity;
                        if (_livingEntity3.getAttributes().hasAttribute((Attribute)JujutsucraftModAttributes.ANIMATION_2.get())) {
                            var10000 = _livingEntity3.getAttribute((Attribute)JujutsucraftModAttributes.ANIMATION_2.get()).getBaseValue();
                            break label270;
                        }
                    }

                    var10000 = (double)0.0F;
                }

                NUM2 = var10000;
                NUM1 += (double) (NUM1 <= (double) -50.0F && NUM1 >= (double) -100.0F ? 100 : 0);
                NUM3 = NUM1 >= (double) 0.0F ? NUM1 % (double) 100.0F : (double) 100.0F;
                sword = LogicSwordProcedure.execute(entity);
                STR1 = Math.random() > (double) 0.5F ? "right" : "left";
                if (NUM3 >= (double) 0.0F && NUM3 <= (double) 4.0F) {
                    if (sword && NUM2 > (double) 0.0F) {
                        if (NUM2 != (double) 1.0F && NUM2 != (double) 2.0F && NUM2 != (double) 3.0F) {
                            if (NUM2 == (double) 4.0F || NUM2 == (double) 5.0F || NUM2 == (double) 6.0F) {
                                STR1 = "left";
                            }
                        } else {
                            STR1 = "right";
                        }
                    }

                    if (NUM3 != (double) 0.0F && NUM3 != (double) 2.0F && NUM3 != (double) 3.0F) {
                        if (NUM3 == (double) 1.0F) {
                            if (sword) {
                                animation_name = "sword_to_right";
                            } else {
                                String var25 = Math.random() > (double) 0.75F ? "punch_" : "kick_";
                                animation_name = var25 + STR1;
                            }
                        } else if (NUM3 == (double) 4.0F) {
                            animation_name = sword ? "sword_overhead" : "punch_overhead";
                        }
                    } else if (NUM2 != (double) 0.0F && !(NUM2 >= (double) 100.0F)) {
                        if (sword) {
                            animation_name = Math.random() > 0.7 ? "sword_overhead" : "sword_to_" + STR1;
                        } else {
                            animation_name = "punch_" + STR1;
                        }
                    } else if (sword) {
                        animation_name = "sword_to_" + STR1;
                    } else {
                        String var24 = Math.random() > (double) 0.5F ? "kick_" : "punch_";
                        animation_name = var24 + STR1;
                    }
                } else if (NUM3 == (double) 20.0F) {
                    if (NUM1 == (double) 220.0F) {
                        animation_name = "red";
                    } else {
                        animation_name = "domain_expansion1";
                    }
                } else if (NUM1 < (double) 0.0F) {
                    if (NUM1 >= (double) -10.0F) {
                        if (NUM1 == (double) -1.0F) {
                            animation_name = "backstep";
                        } else if (NUM1 == (double) -2.0F) {
                            animation_name = "death";
                        } else if (NUM1 == (double) -3.0F) {
                            animation_name = "right_arm_up";
                        } else if (NUM1 == (double) -4.0F) {
                            animation_name = "both_arm_front";
                        } else if (NUM1 == (double) -5.0F) {
                            if (NUM2 == (double) 0.0F) {
                                animation_name = "sword_to_right";
                            } else if (NUM2 == (double) 1.0F) {
                                animation_name = "sword_to_left";
                            }
                        }

                        if (NUM1 == (double) -6.0F) {
                            animation_name = "kick_" + STR1;
                        } else if (NUM1 == (double) -7.0F) {
                            animation_name = "punch_" + STR1;
                        } else if (NUM1 == (double) -8.0F) {
                            if (NUM2 == (double) 0.0F) {
                                animation_name = "rotation";
                            } else if (NUM2 == (double) 1.0F) {
                                animation_name = "rotation2";
                            } else if (NUM2 == (double) 2.0F) {
                                animation_name = "ragnaraku2";
                            }
                        } else if (NUM1 == (double) -9.0F) {
                            animation_name = "guard";
                        } else if (NUM1 == (double) -10.0F) {
                            animation_name = "fall1";
                        }
                    } else if (NUM1 >= (double) -15.0F) {
                        animation_name = "dance" + Math.round(NUM1 + (double) 16.0F);
                    } else if (NUM1 >= (double) -20.0F) {
                        if (NUM1 == (double) -16.0F) {
                            animation_name = NUM2 == (double) 0.0F ? "simple_domain1" : "simple_domain2";
                        } else if (NUM1 == (double) -17.0F) {
                            animation_name = "clap";
                        } else if (NUM1 == (double) -18.0F) {
                            animation_name = "fly";
                        } else if (NUM1 == (double) -19.0F) {
                            if (NUM2 == (double) 0.0F) {
                                animation_name = "breath1";
                            } else if (NUM2 == (double) 1.0F) {
                                animation_name = "breath2";
                            }
                        } else if (NUM1 == (double) -20.0F) {
                            if (NUM2 == (double) 0.0F) {
                                animation_name = "step_back";
                            } else if (NUM2 == (double) 1.0F) {
                                animation_name = "step_right";
                            } else if (NUM2 == (double) 2.0F) {
                                animation_name = "step_left";
                            } else if (NUM2 == (double) 3.0F) {
                                animation_name = "step_front";
                            }
                        }
                    } else if (NUM1 >= (double) -25.0F && NUM1 == (double) -21.0F) {
                        animation_name = "invisibility";
                    }

                    if (NUM1 == (double) -49.0F) {
                        animation_name = "cancel";
                    }
                } else {
                    if (NUM1 == (double) 107.0F) {
                        animation_name = "open";
                    } else if (NUM1 == (double) 207.0F) {
                        animation_name = "red";
                    } else if (NUM1 == (double) 618.0F) {
                        animation_name = "ten_shadows_technique_mahoraga";
                    }

                    if (NUM1 == (double) 1706.0F) {
                        animation_name = "kick_flying";
                    } else if (NUM1 == (double) 1715.0F) {
                        animation_name = "wifi";
                    } else if (NUM1 == (double) 2015.0F) {
                        animation_name = "plus_ultra";
                    }
                }

                if (!animation_name.isEmpty()) {
                    if (world.isClientSide() && entity instanceof Player player2) {
                        net.mcreator.jujutsucraft.procedures.SetupAnimationsProcedure
                                .setAnimationClientside(player2, animation_name, true);
                    }
                }

                if (entity instanceof LivingEntity) {
                    LivingEntity _livingEntity6 = (LivingEntity)entity;
                    if (_livingEntity6.getAttributes().hasAttribute((Attribute)JujutsucraftModAttributes.ANIMATION_1.get())) {
                        _livingEntity6.getAttribute((Attribute)JujutsucraftModAttributes.ANIMATION_1.get()).setBaseValue((double)0.0F);
                    }
                }

                if (entity instanceof LivingEntity) {
                    LivingEntity _livingEntity7 = (LivingEntity)entity;
                    if (_livingEntity7.getAttributes().hasAttribute((Attribute)JujutsucraftModAttributes.ANIMATION_2.get())) {
                        _livingEntity7.getAttribute((Attribute)JujutsucraftModAttributes.ANIMATION_2.get()).setBaseValue((double)0.0F);
                    }
                }

                if (event != null && event.isCancelable()) {
                    event.setCanceled(true);
                }
            }


        }

    }
}


