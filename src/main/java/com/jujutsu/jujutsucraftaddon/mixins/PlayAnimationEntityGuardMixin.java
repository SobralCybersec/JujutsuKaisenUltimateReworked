package com.jujutsu.jujutsucraftaddon.mixins;

import net.mcreator.jujutsucraft.init.JujutsucraftModAttributes;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.mcreator.jujutsucraft.procedures.LogicSwordProcedure;
import net.mcreator.jujutsucraft.procedures.PlayAnimationEntityGuardProcedure;
import net.mcreator.jujutsucraft.procedures.PlayAnimationProcedure;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib.animatable.GeoEntity;

@Mixin(value = PlayAnimationEntityGuardProcedure.class, priority = -10000)
public abstract class PlayAnimationEntityGuardMixin {

    /**
     * @author Satushi
     * @reason Changes playing Animations
     */

    @Inject(at = @At("HEAD"), method = "execute", remap = false, cancellable = true)
    private static void execute(LevelAccessor world, Entity entity, Entity entityiterator, CallbackInfo ci) {
        ci.cancel();

        if (entity != null && entityiterator != null) {
            boolean sword = false;
            if (entityiterator instanceof LivingEntity) {
                LivingEntity _livEnt0 = (LivingEntity)entityiterator;
                if (_livEnt0.hasEffect((MobEffect)JujutsucraftModMobEffects.CURSED_TECHNIQUE.get())) {
                    return;
                }
            }

            if (entityiterator.getPersistentData().getDouble("skill") == (double)0.0F) {
                sword = LogicSwordProcedure.execute(entityiterator);
                if (sword && entity.getPersistentData().getDouble("skill") != (double)0.0F && !entity.getPersistentData().getBoolean("attack")) {
                    int var10000;
                    label72: {
                        if (entityiterator instanceof LivingEntity) {
                            LivingEntity _livEnt = (LivingEntity)entityiterator;
                            if (_livEnt.hasEffect((MobEffect)JujutsucraftModMobEffects.NEUTRALIZATION.get())) {
                                var10000 = _livEnt.getEffect((MobEffect)JujutsucraftModMobEffects.NEUTRALIZATION.get()).getAmplifier();
                                break label72;
                            }
                        }

                        var10000 = 0;
                    }

                    if (var10000 > 10) {
                        sword = false;
                    }
                }

                if (!(entityiterator instanceof Player) && !(entityiterator instanceof GeoEntity)) {
                    if (entityiterator instanceof LivingEntity) {
                        LivingEntity _entity = (LivingEntity)entityiterator;
                        _entity.swing(InteractionHand.MAIN_HAND, true);
                    }
                } else {
                    if (entityiterator instanceof Player && entityiterator instanceof LivingEntity) {
                        LivingEntity _entity = (LivingEntity)entityiterator;
                        _entity.swing(InteractionHand.MAIN_HAND, true);
                    }

                    if (sword) {
                        if (entityiterator instanceof LivingEntity) {
                            LivingEntity _livingEntity9 = (LivingEntity)entityiterator;
                            if (_livingEntity9.getAttributes().hasAttribute((Attribute)JujutsucraftModAttributes.ANIMATION_1.get())) {
                                _livingEntity9.getAttribute((Attribute)JujutsucraftModAttributes.ANIMATION_1.get()).setBaseValue((double)-5.0F);
                            }
                        }

                        if (entityiterator instanceof LivingEntity) {
                            LivingEntity _livingEntity11 = (LivingEntity)entityiterator;
                            if (_livingEntity11.getAttributes().hasAttribute((Attribute)JujutsucraftModAttributes.ANIMATION_2.get())) {
                                _livingEntity11.getAttribute((Attribute)JujutsucraftModAttributes.ANIMATION_2.get()).setBaseValue((double)Mth.nextInt(RandomSource.create(), 0, 1));
                            }
                        }
                    } else {
                        if (entityiterator instanceof LivingEntity) {
                            LivingEntity _livingEntity12 = (LivingEntity)entityiterator;
                            if (_livingEntity12.getAttributes().hasAttribute((Attribute)JujutsucraftModAttributes.ANIMATION_1.get())) {
                                _livingEntity12.getAttribute((Attribute)JujutsucraftModAttributes.ANIMATION_1.get()).setBaseValue((double)-9.0F);
                            }
                        }

                        if (entityiterator instanceof LivingEntity) {
                            LivingEntity _livingEntity13 = (LivingEntity)entityiterator;
                            if (_livingEntity13.getAttributes().hasAttribute((Attribute)JujutsucraftModAttributes.ANIMATION_2.get())) {
                                _livingEntity13.getAttribute((Attribute)JujutsucraftModAttributes.ANIMATION_2.get()).setBaseValue((double)0.0F);
                            }
                        }
                    }

                    PlayAnimationProcedure.execute(world, entityiterator);
                }
            }

        }

       /* boolean sword = false;
        if (entityiterator != null) {
            Entity entity_a = null;
            entity_a = entityiterator;
            if (entity_a instanceof LivingEntity) {
                LivingEntity _livEnt0 = (LivingEntity)entity_a;
                if (_livEnt0.hasEffect((MobEffect)JujutsucraftModMobEffects.CURSED_TECHNIQUE.get())) {
                    return;
                }
            }

            if (entity_a.getPersistentData().getDouble("skill") == 0.0) {
                sword = LogicSwordProcedure.execute(entity_a);
                LivingEntity _livingEntity8;
                if (!(entity_a instanceof Player) && !(entity_a instanceof GeoEntity)) {
                    if (entity_a instanceof LivingEntity) {
                        _livingEntity8 = (LivingEntity)entity_a;
                        _livingEntity8.swing(InteractionHand.MAIN_HAND, true);
                    }
                } else {
                    if (entity_a instanceof Player && entity_a instanceof LivingEntity) {
                        _livingEntity8 = (LivingEntity)entity_a;
                        _livingEntity8.swing(InteractionHand.MAIN_HAND, true);
                    }

                    if (sword) {
                        if (entity_a instanceof LivingEntity) {
                            _livingEntity8 = (LivingEntity)entity_a;
                            if (_livingEntity8.getAttributes().hasAttribute((Attribute)JujutsucraftModAttributes.ANIMATION_1.get())) {
                                _livingEntity8.getAttribute((Attribute)JujutsucraftModAttributes.ANIMATION_1.get()).setBaseValue(100.0);
                            }
                        }

                        if (entity_a instanceof LivingEntity) {
                            _livingEntity8 = (LivingEntity)entity_a;
                            if (_livingEntity8.getAttributes().hasAttribute((Attribute)JujutsucraftModAttributes.ANIMATION_2.get())) {
                                _livingEntity8.getAttribute((Attribute)JujutsucraftModAttributes.ANIMATION_2.get()).setBaseValue((double) Mth.nextInt(RandomSource.create(), 0, 100));
                            }
                        }
                    } else if (entity_a instanceof LivingEntity) {
                        _livingEntity8 = (LivingEntity)entity_a;
                        if (_livingEntity8.getAttributes().hasAttribute((Attribute)JujutsucraftModAttributes.ANIMATION_1.get())) {
                            _livingEntity8.getAttribute((Attribute)JujutsucraftModAttributes.ANIMATION_1.get()).setBaseValue(-9.0);
                        }
                    }

                    PlayAnimationProcedure.execute(world, entity_a);
                }
            }

        }*/
    }
}
