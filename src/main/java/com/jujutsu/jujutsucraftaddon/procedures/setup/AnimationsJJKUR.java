package com.jujutsu.jujutsucraftaddon.procedures.setup;

import com.jujutsu.jujutsucraftaddon.init.mod.JujutsucraftaddonModMobEffects;
import com.jujutsu.jujutsucraftaddon.network.JujutsucraftaddonModVariables;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nullable;

public class AnimationsJJKUR {

    public static void playAnimationsJJKUR(@Nullable Event event, LevelAccessor world, DamageSource damagesource, Entity entity) {
        if (damagesource.is(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("jujutsucraft:start_animation"))) && entity instanceof Player) {
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
                // Ab Animations
                if (((LivingEntity) entiry_a).hasEffect(JujutsucraftaddonModMobEffects.ANIM_1.get())) {
                    player = (AbstractClientPlayer) entiry_a;
                    animation = (ModifierLayer) PlayerAnimationAccess.getPlayerAssociatedData(player).get(new ResourceLocation("jujutsucraftaddon", "player_animation"));
                    if (animation != null) {
                        animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation("jujutsucraftaddon", "ab1player"))));
                    }
                    if (event != null && event.isCancelable()) {
                        event.setCanceled(true);
                    } else if (event != null && event.hasResult()) {
                        event.setResult(Event.Result.DENY);
                    }

                } else if (((LivingEntity) entiry_a).hasEffect(JujutsucraftaddonModMobEffects.ANIM_2.get())) {
                    player = (AbstractClientPlayer) entiry_a;
                    animation = (ModifierLayer) PlayerAnimationAccess.getPlayerAssociatedData(player).get(new ResourceLocation("jujutsucraftaddon", "player_animation"));
                    if (animation != null) {
                        animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation("jujutsucraftaddon", "ab2player"))));
                    }
                    if (event != null && event.isCancelable()) {
                        event.setCanceled(true);
                    } else if (event != null && event.hasResult()) {
                        event.setResult(Event.Result.DENY);
                    }

                } else if (((LivingEntity) entiry_a).hasEffect(JujutsucraftaddonModMobEffects.MURASAKI_EFFECT.get()) && (!(((LivingEntity) entiry_a).hasEffect(JujutsucraftaddonModMobEffects.SOKA_MONA.get())))) {
                    player = (AbstractClientPlayer) entiry_a;
                    animation = (ModifierLayer) PlayerAnimationAccess.getPlayerAssociatedData(player).get(new ResourceLocation("jujutsucraftaddon", "player_animation"));
                    if (animation != null) {
                        animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation("jujutsucraftaddon", "murasaki"))));
                    }
                } else if (((LivingEntity) entiry_a).hasEffect(JujutsucraftaddonModMobEffects.WORLD_GOJO.get())) {
                    if ((entiry_a.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).OutputLevel >= 4) {
                        player = (AbstractClientPlayer) entiry_a;
                        animation = (ModifierLayer) PlayerAnimationAccess.getPlayerAssociatedData(player).get(new ResourceLocation("jujutsucraftaddon", "player_animation"));
                        if (animation != null) {
                            animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation("jujutsucraftaddon", "murasaki2"))));
                        }
                    }

                } else if (((LivingEntity) entiry_a).hasEffect(JujutsucraftaddonModMobEffects.ANIM_3.get())) {
                    player = (AbstractClientPlayer) entiry_a;
                    animation = (ModifierLayer) PlayerAnimationAccess.getPlayerAssociatedData(player).get(new ResourceLocation("jujutsucraftaddon", "player_animation"));
                    if (animation != null) {
                        animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation("jujutsucraftaddon", "ab3player"))));
                    }
                    if (event != null && event.isCancelable()) {
                        event.setCanceled(true);
                    } else if (event != null && event.hasResult()) {
                        event.setResult(Event.Result.DENY);
                    }

                } else if (((LivingEntity) entiry_a).hasEffect(JujutsucraftaddonModMobEffects.ANIM_4.get())) {
                    player = (AbstractClientPlayer) entiry_a;
                    animation = (ModifierLayer) PlayerAnimationAccess.getPlayerAssociatedData(player).get(new ResourceLocation("jujutsucraftaddon", "player_animation"));
                    if (animation != null) {
                        animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation("jujutsucraftaddon", "ab4player"))));
                    }
                    if (event != null && event.isCancelable()) {
                        event.setCanceled(true);
                    } else if (event != null && event.hasResult()) {
                        event.setResult(Event.Result.DENY);
                    }

                } else if (((LivingEntity) entiry_a).hasEffect(JujutsucraftaddonModMobEffects.DODGE.get())) {
                    player = (AbstractClientPlayer) entiry_a;
                    animation = (ModifierLayer) PlayerAnimationAccess.getPlayerAssociatedData(player).get(new ResourceLocation("jujutsucraftaddon", "player_animation"));
                    if (animation != null && !animation.isActive()) {
                        animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation("jujutsucraftaddon", ("dodge" + Mth.nextInt(RandomSource.create(), 1, 21))))));
                    }
                    if (event != null && event.isCancelable()) {
                        event.setCanceled(true);
                    } else if (event != null && event.hasResult()) {
                        event.setResult(Event.Result.DENY);
                    }

                } else if (((LivingEntity) entiry_a).hasEffect(JujutsucraftaddonModMobEffects.JACKPOT.get())) {
                    player = (AbstractClientPlayer) entiry_a;
                    animation = (ModifierLayer) PlayerAnimationAccess.getPlayerAssociatedData(player).get(new ResourceLocation("jujutsucraftaddon", "player_animation"));
                    if (animation != null && !animation.isActive()) {
                        animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation("jujutsucraftaddon", "tucadonca"))));
                    }
                    if (event != null && event.isCancelable()) {
                        event.setCanceled(true);
                    } else if (event != null && event.hasResult()) {
                        event.setResult(Event.Result.DENY);
                    }

                } else if (((LivingEntity) entiry_a).hasEffect(JujutsucraftaddonModMobEffects.SOKA_MONA.get())) {
                    player = (AbstractClientPlayer) entiry_a;
                    animation = (ModifierLayer) PlayerAnimationAccess.getPlayerAssociatedData(player).get(new ResourceLocation("jujutsucraftaddon", "player_animation"));
                    if (animation != null && !animation.isActive()) {
                        animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation("jujutsucraftaddon", "soka"))));
                    }
                    if (event != null && event.isCancelable()) {
                        event.setCanceled(true);
                    } else if (event != null && event.hasResult()) {
                        event.setResult(Event.Result.DENY);
                    }

                } else if (((LivingEntity) entiry_a).hasEffect(JujutsucraftaddonModMobEffects.COUNTER.get())) {
                    player = (AbstractClientPlayer) entiry_a;
                    animation = (ModifierLayer) PlayerAnimationAccess.getPlayerAssociatedData(player).get(new ResourceLocation("jujutsucraftaddon", "player_animation"));
                    if (animation != null && !animation.isActive()) {
                        animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation("jujutsucraftaddon", ("counterhr" + Mth.nextInt(RandomSource.create(), 1, 7))))));
                    }
                    if (event != null && event.isCancelable()) {
                        event.setCanceled(true);
                    } else if (event != null && event.hasResult()) {
                        event.setResult(Event.Result.DENY);
                    }

                } else if (((LivingEntity) entiry_a).hasEffect(JujutsucraftaddonModMobEffects.ANIMATION_HEIAN.get())) {
                    player = (AbstractClientPlayer) entiry_a;
                    animation = (ModifierLayer) PlayerAnimationAccess.getPlayerAssociatedData(player).get(new ResourceLocation("jujutsucraftaddon", "player_animation"));
                    if (animation != null && !animation.isActive()) {
                        animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation("jujutsucraftaddon", "heianform"))));
                    }
                    if (event != null && event.isCancelable()) {
                        event.setCanceled(true);
                    } else if (event != null && event.hasResult()) {
                        event.setResult(Event.Result.DENY);
                    }

                } else if (((LivingEntity) entiry_a).hasEffect(JujutsucraftaddonModMobEffects.WORLD_CUT.get())) {
                    player = (AbstractClientPlayer) entiry_a;
                    animation = (ModifierLayer) PlayerAnimationAccess.getPlayerAssociatedData(player).get(new ResourceLocation("jujutsucraftaddon", "player_animation"));
                    if ((entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).Moveset == 2
                            || (entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).Moveset == 3) {
                        if ((entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).OutputLevel <= 3) {
                            animation = (ModifierLayer) PlayerAnimationAccess.getPlayerAssociatedData(player).get(new ResourceLocation("jujutsucraftaddon", "player_animation"));
                            if (animation != null && !animation.isActive()) {
                                animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation("jujutsucraftaddon", "rapiddismantle"))));
                            }
                            return;
                        } else {


                            animation = (ModifierLayer) PlayerAnimationAccess.getPlayerAssociatedData(player).get(new ResourceLocation("jujutsucraftaddon", "player_animation"));
                            if (animation != null && !animation.isActive()) {
                                animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation("jujutsucraftaddon", "maximum3"))));
                            }
                            return;

                        }

                    } else if ((entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).Moveset == 1) {
                        if (world.isClientSide()) {
                            animation = (ModifierLayer) PlayerAnimationAccess.getPlayerAssociatedData(player).get(new ResourceLocation("jujutsucraftaddon", "player_animation"));
                            if (animation != null && !animation.isActive()) {
                                animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation("jujutsucraftaddon", "spiderwebanim"))));
                            }
                            return;
                        }

                    } else if ((entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).Moveset == 4) {
                        if (world.isClientSide()) {
                            animation = (ModifierLayer) PlayerAnimationAccess.getPlayerAssociatedData(player).get(new ResourceLocation("jujutsucraftaddon", "player_animation"));
                            if (animation != null && !animation.isActive()) {
                                animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation("jujutsucraftaddon", ("worldslash" + Mth.nextInt(RandomSource.create(), 1, 4))))));
                            }
                            return;
                        }
                    }

                    if (event != null && event.isCancelable()) {
                        event.setCanceled(true);
                    } else if (event != null && event.hasResult()) {
                        event.setResult(Event.Result.DENY);
                    }

                } else if (((LivingEntity) entiry_a).hasEffect(JujutsucraftaddonModMobEffects.ANIMATION_TWO.get())) {
                    player = (AbstractClientPlayer) entiry_a;
                    animation = (ModifierLayer) PlayerAnimationAccess.getPlayerAssociatedData(player).get(new ResourceLocation("jujutsucraftaddon", "player_animation"));
                    if ((entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).AnimationYuzuki >= 1
                            && (entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).AnimationYuzuki < 20) {
                        if (world.isClientSide()) {
                            animation = (ModifierLayer) PlayerAnimationAccess.getPlayerAssociatedData(player).get(new ResourceLocation("jujutsucraftaddon", "player_animation"));
                            if (animation != null && !animation.isActive()) {
                                animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation("jujutsucraftaddon", "swordnpc"))));
                            }
                        }


                        {
                            double _setval = (entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).AnimationYuzuki + 1;
                            entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                capability.AnimationYuzuki = _setval;
                                capability.syncPlayerVariables(entity);
                            });
                        }
                    } else if ((entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).AnimationYuzuki >= 20) {
                        {
                            double _setval = 0;
                            entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                capability.AnimationYuzuki = _setval;
                                capability.syncPlayerVariables(entity);
                            });
                        }
                    }
                    if ((entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).AnimationDefense == 1) {
                        if (world.isClientSide()) {
                            if (entity instanceof AbstractClientPlayer) {
                                animation = (ModifierLayer) PlayerAnimationAccess.getPlayerAssociatedData(player).get(new ResourceLocation("jujutsucraftaddon", "player_animation"));
                                if (animation != null) {
                                    animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation("jujutsucraftaddon", "defensesword4"))));
                                }
                            }
                        }

                        {
                            double _setval = 0;
                            entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                capability.AnimationDefense = _setval;
                                capability.syncPlayerVariables(entity);
                            });
                        }
                    } else if ((entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).AnimationDefense == 2) {
                        if (world.isClientSide()) {

                            animation = (ModifierLayer) PlayerAnimationAccess.getPlayerAssociatedData(player).get(new ResourceLocation("jujutsucraftaddon", "player_animation"));
                            if (animation != null) {
                                animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation("jujutsucraftaddon", "defensesword3"))));
                            }

                        }

                        {
                            double _setval = 0;
                            entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                capability.AnimationDefense = _setval;
                                capability.syncPlayerVariables(entity);
                            });
                        }
                    } else if ((entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).AnimationDefense == 3) {
                        if (world.isClientSide()) {
                            if (entity instanceof AbstractClientPlayer) {
                                animation = (ModifierLayer) PlayerAnimationAccess.getPlayerAssociatedData(player).get(new ResourceLocation("jujutsucraftaddon", "player_animation"));
                                if (animation != null) {
                                    animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation("jujutsucraftaddon", "defensesword2"))));
                                }
                            }
                        }

                        {
                            double _setval = 0;
                            entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                capability.AnimationDefense = _setval;
                                capability.syncPlayerVariables(entity);
                            });
                        }
                    }
                    if ((entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).AnimationDefense >= 4
                            && (entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).AnimationDefense < 24) {
                        if (world.isClientSide()) {
                            if (entity instanceof AbstractClientPlayer) {
                                animation = (ModifierLayer) PlayerAnimationAccess.getPlayerAssociatedData(player).get(new ResourceLocation("jujutsucraftaddon", "player_animation"));
                                if (animation != null && !animation.isActive()) {
                                    animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation("jujutsucraftaddon", "red"))));
                                }
                            }
                        }

                        {
                            double _setval = (entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).AnimationDefense + 1;
                            entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                capability.AnimationDefense = _setval;
                                capability.syncPlayerVariables(entity);
                            });
                        }
                    } else if ((entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).AnimationDefense == 24) {
                        {
                            double _setval = 0;
                            entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                capability.AnimationDefense = _setval;
                                capability.syncPlayerVariables(entity);
                            });
                        }
                    } else if ((entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).AnimationDefense >= 106
                            && (entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).AnimationDefense < 116) {
                        if (world.isClientSide()) {
                            if (entity instanceof AbstractClientPlayer) {
                                animation = (ModifierLayer) PlayerAnimationAccess.getPlayerAssociatedData(player).get(new ResourceLocation("jujutsucraftaddon", "player_animation"));
                                if (animation != null && !animation.isActive()) {
                                    animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation("jujutsucraftaddon", "cleaveweb"))));
                                }
                            }
                        }

                        {
                            double _setval = (entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).AnimationDefense + 1;
                            entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                capability.AnimationDefense = _setval;
                                capability.syncPlayerVariables(entity);
                            });
                        }
                    } else if ((entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).AnimationDefense == 116) {
                        {
                            double _setval = 0;
                            entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                capability.AnimationDefense = _setval;
                                capability.syncPlayerVariables(entity);
                            });
                        }
                    }
                    if ((entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).AnimationDefense >= 99
                            && (entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).AnimationDefense < 109) {
                        if (world.isClientSide()) {
                            if (entity instanceof AbstractClientPlayer) {
                                animation = (ModifierLayer) PlayerAnimationAccess.getPlayerAssociatedData(player).get(new ResourceLocation("jujutsucraftaddon", "player_animation"));
                                if (animation != null && !animation.isActive()) {
                                    animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation("jujutsucraftaddon", "barragekick"))));
                                }
                            }
                        }

                        {
                            double _setval = (entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).AnimationDefense + 1;
                            entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                capability.AnimationDefense = _setval;
                                capability.syncPlayerVariables(entity);
                            });
                        }
                    } else if ((entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).AnimationDefense == 109) {
                        {
                            double _setval = 0;
                            entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                                capability.AnimationDefense = _setval;
                                capability.syncPlayerVariables(entity);
                            });
                        }
                    }

                    if (event != null && event.isCancelable()) {
                        event.setCanceled(true);
                    } else if (event != null && event.hasResult()) {
                        event.setResult(Event.Result.DENY);
                    }

                }
            }
        }

    }
}
