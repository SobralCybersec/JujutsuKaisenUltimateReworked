package com.jujutsu.jujutsucraftaddon.mixins;

import com.jujutsu.jujutsucraftaddon.init.mod.JujutsucraftaddonModParticleTypes;
import com.jujutsu.jujutsucraftaddon.procedures.EffectsBlackFlashProcedure;
import net.mcreator.jujutsucraft.init.JujutsucraftModParticleTypes;
import net.mcreator.jujutsucraft.procedures.AIBlackFlashProcedure;
import net.mcreator.jujutsucraft.procedures.BlockDestroyAllDirectionProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = AIBlackFlashProcedure.class, priority = -10000)
public abstract class AIBlackFlashProcedureMixin {
    /**
     * @author Satushi
     * @reason Changes Black Flash Entity Particles
     */

    @Inject(at = @At("HEAD"), method = "execute", remap = false, cancellable = true)
    private static void execute(LevelAccessor world, double x, double y, double z, Entity entity, CallbackInfo ci) {
        ci.cancel();

        if (entity != null) {
            double rad = (double)0.0F;
            double range = (double)0.0F;
            double x_pos = (double)0.0F;
            double y_pos = (double)0.0F;
            double z_pos = (double)0.0F;
            double soundPitch = (double)0.0F;
            double rad_now = (double)0.0F;
            double num1 = (double)0.0F;
            double num2 = (double)0.0F;
            double num3 = (double)0.0F;
            double pitch = (double)0.0F;
            double loop_num = (double)0.0F;
            EffectsBlackFlashProcedure.execute(world, x, y, z, entity);
            entity.getPersistentData().putDouble("cnt1", entity.getPersistentData().getDouble("cnt1") + (double)1.0F);
            if (entity.getPersistentData().getDouble("cnt1") < (double)10.0F) {
                if (entity.getPersistentData().getDouble("cnt1") == (double)1.0F) {
                    soundPitch = (double)0.75F;

                    for(int index0 = 0; index0 < 3; ++index0) {
                        if (world instanceof Level) {
                            Level _level = (Level)world;
                            if (!_level.isClientSide()) {
                                _level.playSound((Player)null, BlockPos.containing(x, y, z), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jujutsucraft:electric_shock")), SoundSource.NEUTRAL, 1.0F, (float)soundPitch);
                            } else {
                                _level.playLocalSound(x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jujutsucraft:electric_shock")), SoundSource.NEUTRAL, 1.0F, (float)soundPitch, false);
                            }
                        }

                        soundPitch += (double)0.25F;
                    }

                    if (world instanceof ServerLevel) {
                        ServerLevel _level = (ServerLevel)world;
                        _level.sendParticles(ParticleTypes.FLASH, x, y + (double)1.0F, z, 10, (double)0.5F, (double)0.5F, (double)0.5F, (double)0.0F);
                    }
                }

                if (world instanceof ServerLevel) {
                    ServerLevel _level = (ServerLevel)world;
                    _level.sendParticles((SimpleParticleType)JujutsucraftModParticleTypes.PARTICLE_BLACK_FLASH_1.get(), x, y + (double)1.0F, z, 3, (double)0.5F, (double)1.0F, (double)0.5F, (double)0.0F);
                }
            } else if (entity.getPersistentData().getDouble("cnt1") < (double)17.0F) {
                if (entity.getPersistentData().getDouble("cnt1") == (double)10.0F) {
                    soundPitch = (double)0.75F;

                    for(int index1 = 0; index1 < 3; ++index1) {
                        if (world instanceof Level) {
                            Level _level = (Level)world;
                            if (!_level.isClientSide()) {
                                _level.playSound((Player)null, BlockPos.containing(x, y, z), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.lightning_bolt.thunder")), SoundSource.NEUTRAL, 1.0F, (float)soundPitch);
                            } else {
                                _level.playLocalSound(x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.lightning_bolt.thunder")), SoundSource.NEUTRAL, 1.0F, (float)soundPitch, false);
                            }
                        }

                        if (world instanceof Level) {
                            Level _level = (Level)world;
                            if (!_level.isClientSide()) {
                                _level.playSound((Player)null, BlockPos.containing(x, y, z), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.lightning_bolt.impact")), SoundSource.NEUTRAL, 1.0F, (float)soundPitch);
                            } else {
                                _level.playLocalSound(x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.lightning_bolt.impact")), SoundSource.NEUTRAL, 1.0F, (float)soundPitch, false);
                            }
                        }

                        soundPitch += (double)0.25F;
                    }

                    entity.getPersistentData().putDouble("BlockRange", (double)20.0F);
                    entity.getPersistentData().putDouble("BlockDamage", (double)5F);
                    BlockDestroyAllDirectionProcedure.execute(world, x, y, z, entity);
                }

                loop_num = (double)5.0F + entity.getPersistentData().getDouble("cnt1") * (double)2.0F;

                for(int index2 = 0; index2 < 2; ++index2) {
                    rad_now = Math.toRadians(Math.random() * (double)360.0F);
                    pitch = Math.toRadians((double)0.0F);
                    num1 = (Math.random() - (double)0.5F) * 1.6;
                    num2 = (Math.random() - (double)0.5F) * 1.6;
                    num3 = (Math.random() - (double)0.5F) * 1.6;
                    entity.getPersistentData().putDouble("x_pos", x);
                    entity.getPersistentData().putDouble("y_pos", y + (double)1.0F);
                    entity.getPersistentData().putDouble("z_pos", z);
                    entity.getPersistentData().putDouble("cnt2", (double)0.0F);

                    for(int index3 = 0; index3 < (int)loop_num; ++index3) {
                        x_pos = entity.getPersistentData().getDouble("x_pos") + Math.cos(rad_now) * (Math.cos(pitch) + Math.abs(Math.sin(rad_now) * Math.sin(pitch))) * (entity.getPersistentData().getDouble("cnt2") + (double)1.0F);
                        y_pos = entity.getPersistentData().getDouble("y_pos") + Math.sin(pitch) * (entity.getPersistentData().getDouble("cnt2") + (double)1.0F) * (double)-1.0F;
                        z_pos = entity.getPersistentData().getDouble("z_pos") + Math.sin(rad_now) * (Math.cos(pitch) + Math.abs(Math.cos(rad_now) * Math.sin(pitch))) * (entity.getPersistentData().getDouble("cnt2") + (double)1.0F);
                        if (world instanceof ServerLevel) {
                            ServerLevel _level = (ServerLevel)world;
                            _level.sendParticles((SimpleParticleType)JujutsucraftModParticleTypes.PARTICLE_BLACK_FLASH_1.get(), x_pos, y_pos, z_pos, 1, 0.1, 0.1, 0.1, (double)0.0F);
                        }

                        if (world.getBlockState(BlockPos.containing(x_pos, y_pos, z_pos)).canOcclude()) {
                            if (!world.getBlockState(BlockPos.containing(x_pos + (double)1.0F, y_pos + (double)0.0F, z_pos + (double)0.0F)).canOcclude() || !world.getBlockState(BlockPos.containing(x_pos + (double)-1.0F, y_pos + (double)0.0F, z_pos + (double)0.0F)).canOcclude() || !world.getBlockState(BlockPos.containing(x_pos + (double)0.0F, y_pos + (double)1.0F, z_pos + (double)0.0F)).canOcclude() || !world.getBlockState(BlockPos.containing(x_pos + (double)0.0F, y_pos + (double)-1.0F, z_pos + (double)0.0F)).canOcclude() || !world.getBlockState(BlockPos.containing(x_pos + (double)0.0F, y_pos + (double)0.0F, z_pos + (double)1.0F)).canOcclude() || !world.getBlockState(BlockPos.containing(x_pos + (double)0.0F, y_pos + (double)0.0F, z_pos + (double)-1.0F)).canOcclude()) {
                                world.levelEvent(2001, BlockPos.containing(x_pos, y_pos, z_pos), Block.getId(world.getBlockState(BlockPos.containing(x_pos, y_pos, z_pos))));
                            }

                            if (world instanceof ServerLevel) {
                                ServerLevel _level = (ServerLevel)world;
                                _level.sendParticles(ParticleTypes.EXPLOSION, x_pos, y_pos, z_pos, 5, (double)0.25F, (double)0.25F, (double)0.25F, (double)0.0F);
                            }
                        }

                        entity.getPersistentData().putDouble("cnt2", entity.getPersistentData().getDouble("cnt2") + (double)0.25F);
                        if (entity.getPersistentData().getDouble("cnt2") % (double)4.0F < (double)2.0F) {
                            entity.getPersistentData().putDouble("x_pos", entity.getPersistentData().getDouble("x_pos") + num1);
                            entity.getPersistentData().putDouble("y_pos", entity.getPersistentData().getDouble("y_pos") + num2);
                            entity.getPersistentData().putDouble("z_pos", entity.getPersistentData().getDouble("z_pos") + num3);
                        } else {
                            entity.getPersistentData().putDouble("x_pos", entity.getPersistentData().getDouble("x_pos") - num1);
                            entity.getPersistentData().putDouble("y_pos", entity.getPersistentData().getDouble("y_pos") - num2);
                            entity.getPersistentData().putDouble("z_pos", entity.getPersistentData().getDouble("z_pos") - num3);
                        }
                    }
                }
            } else if (entity.getPersistentData().getDouble("cnt1") > (double)20.0F) {
                x_pos = x + Math.random() * (double)16.0F - (double)8.0F;
                z_pos = z + Math.random() * (double)16.0F - (double)8.0F;
                if (world instanceof ServerLevel) {
                    ServerLevel _level = (ServerLevel)world;
                    _level.sendParticles((SimpleParticleType)JujutsucraftModParticleTypes.PARTICLE_BLACK_FLASH_1.get(), x_pos, y, z_pos, 40, 0.1, (double)2.0F, 0.1, 0.1);
                }

                if (world instanceof Level) {
                    Level _level = (Level)world;
                    if (!_level.isClientSide()) {
                        _level.playSound((Player)null, BlockPos.containing(x_pos, y, z_pos), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jujutsucraft:electric_shock")), SoundSource.NEUTRAL, 0.25F, 1.25F);
                    } else {
                        _level.playLocalSound(x_pos, y, z_pos, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jujutsucraft:electric_shock")), SoundSource.NEUTRAL, 0.25F, 1.25F, false);
                    }
                }

                if (entity.getPersistentData().getDouble("cnt1") > (double)25.0F && !entity.level().isClientSide()) {
                    entity.discard();
                }
            }

        }

      /*  if (entity != null) {
            double rad = 0.0;
            double range = 0.0;
            double x_pos = 0.0;
            double y_pos = 0.0;
            double z_pos = 0.0;
            double soundPitch = 0.0;
            double rad_now = 0.0;
            double num1 = 0.0;
            double num2 = 0.0;
            double num3 = 0.0;
            double pitch = 0.0;
            double loop_num = 0.0;
            entity.getPersistentData().putDouble("cnt1", entity.getPersistentData().getDouble("cnt1") + 1.0);
            int index2;
            Level _level8;
            ServerLevel _level;
            EffectsBlackFlashProcedure.execute(world, x, y, z, entity);
            if (entity.getPersistentData().getDouble("cnt1") < 10.0) {
                if (entity.getPersistentData().getDouble("cnt1") == 1.0) {

                }

                if (world instanceof ServerLevel) {
                    _level = (ServerLevel) world;
                    _level.sendParticles(JujutsucraftModParticleTypes.PARTICLE_BLACK_FLASH_1.get(), x, y + 1.0, z, 3, 0.5, 1.0, 0.5, 0.0);
                }

            } else if (entity.getPersistentData().getDouble("cnt1") < 17.0) {
                if (entity.getPersistentData().getDouble("cnt1") == 10.0) {
                    soundPitch = 0.75;

                    for (index2 = 0; index2 < 3; ++index2) {
                        if (world instanceof Level _level3) {
                            if (!_level3.isClientSide()) {
                                _level3.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.lightning_bolt.thunder")), SoundSource.NEUTRAL, 1.0F, (float) soundPitch);
                            } else {
                                _level3.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.lightning_bolt.thunder")), SoundSource.NEUTRAL, 1.0F, (float) soundPitch, false);
                            }
                        }

                        if (world instanceof Level _level4) {
                            if (!_level4.isClientSide()) {
                                _level4.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.lightning_bolt.impact")), SoundSource.NEUTRAL, 1.0F, (float) soundPitch);
                            } else {
                                _level4.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.lightning_bolt.impact")), SoundSource.NEUTRAL, 1.0F, (float) soundPitch, false);
                            }
                        }

                        soundPitch += 0.25;
                    }

                    entity.getPersistentData().putDouble("BlockRange", 20.0);
                    entity.getPersistentData().putDouble("BlockDamage", 5.0);
                    BlockDestroyAllDirectionProcedure.execute(world, x, y, z, entity);
                }

                loop_num = 5.0 + entity.getPersistentData().getDouble("cnt1") * 2.0;

                for (index2 = 0; index2 < 2; ++index2) {
                    rad_now = Math.toRadians(Math.random() * 360.0);
                    pitch = Math.toRadians(0.0);
                    num1 = (Math.random() - 0.5) * 1.6;
                    num2 = (Math.random() - 0.5) * 1.6;
                    num3 = (Math.random() - 0.5) * 1.6;
                    entity.getPersistentData().putDouble("x_pos", x);
                    entity.getPersistentData().putDouble("y_pos", y + 1.0);
                    entity.getPersistentData().putDouble("z_pos", z);
                    entity.getPersistentData().putDouble("cnt2", 0.0);

                    for (int index3 = 0; index3 < (int) loop_num; ++index3) {
                        x_pos = entity.getPersistentData().getDouble("x_pos") + Math.cos(rad_now) * (Math.cos(pitch) + Math.abs(Math.sin(rad_now) * Math.sin(pitch))) * (entity.getPersistentData().getDouble("cnt2") + 1.0);
                        y_pos = entity.getPersistentData().getDouble("y_pos") + Math.sin(pitch) * (entity.getPersistentData().getDouble("cnt2") + 1.0) * -1.0;
                        z_pos = entity.getPersistentData().getDouble("z_pos") + Math.sin(rad_now) * (Math.cos(pitch) + Math.abs(Math.cos(rad_now) * Math.sin(pitch))) * (entity.getPersistentData().getDouble("cnt2") + 1.0);
                        if (world instanceof ServerLevel) {
                            _level = (ServerLevel) world;
                            if (!ModList.get().isLoaded("jjkueffects")) {
                                if (Math.random() < (1) / ((float) 10)) {
                                    _level.sendParticles(JujutsucraftModParticleTypes.PARTICLE_BLACK_FLASH_1.get(), x_pos, y_pos, z_pos, 1, 0.1, 0.1, 0.1, 0.0);
                                }
                                if (Math.random() < (1) / ((float) 20)) {
                                    _level.sendParticles(JujutsucraftaddonModParticleTypes.KOKUSEN_3.get(), x_pos + Mth.nextInt(RandomSource.create(), -5, 5), y_pos + Mth.nextInt(RandomSource.create(), 1, 10), z_pos + Mth.nextInt(RandomSource.create(), -5, 5), 1, 0.1, 0.1, 0.1, 0.0);
                                }
                            }
                            if (Math.random() < (1) / ((float) 20)) {
                                _level.sendParticles(JujutsucraftaddonModParticleTypes.KOKUSEN_5.get(),  x_pos + Mth.nextInt(RandomSource.create(), -5, 5), y_pos + Mth.nextInt(RandomSource.create(), 1, 10), z_pos + Mth.nextInt(RandomSource.create(), -5, 5), 1, 0.1, 0.1, 0.1, 0.0);
                            }
                        }


//                        if (world instanceof ServerLevel) {
//                            _level = (ServerLevel)world;
//                            DustParticleOptions dustOptions = new DustParticleOptions(new Vector3f(0.6F, 0.0F, 0.0F), 2.0F);
//
//                            // Send particles at specified position with spread
//                            _level.sendParticles(dustOptions, x_pos, y_pos, z_pos, 5, 0.25, 0.25, 0.25, 0.0);
//                        }
//
//                        if (world instanceof ServerLevel) {
//                            _level = (ServerLevel)world;
//                            DustParticleOptions dustOptions = new DustParticleOptions(new Vector3f(0.0F, 0.0F, 0.0F), 2.0F);
//
//                            // Send particles at specified position with spread
//                            _level.sendParticles(dustOptions, x_pos, y_pos, z_pos, 5, 0.25, 0.25, 0.25, 0.0);
//                        }


                        if (world.getBlockState(BlockPos.containing(x_pos, y_pos, z_pos)).canOcclude()) {
                            if (!world.getBlockState(BlockPos.containing(x_pos + 1.0, y_pos + 0.0, z_pos + 0.0)).canOcclude() || !world.getBlockState(BlockPos.containing(x_pos + -1.0, y_pos + 0.0, z_pos + 0.0)).canOcclude() || !world.getBlockState(BlockPos.containing(x_pos + 0.0, y_pos + 1.0, z_pos + 0.0)).canOcclude() || !world.getBlockState(BlockPos.containing(x_pos + 0.0, y_pos + -1.0, z_pos + 0.0)).canOcclude() || !world.getBlockState(BlockPos.containing(x_pos + 0.0, y_pos + 0.0, z_pos + 1.0)).canOcclude() || !world.getBlockState(BlockPos.containing(x_pos + 0.0, y_pos + 0.0, z_pos + -1.0)).canOcclude()) {
                                world.levelEvent(2001, BlockPos.containing(x_pos, y_pos, z_pos), Block.getId(world.getBlockState(BlockPos.containing(x_pos, y_pos, z_pos))));
                            }
                            if (!ModList.get().isLoaded("jjkueffects")) {
                                if (world instanceof ServerLevel) {
                                    _level = (ServerLevel) world;
                                    _level.sendParticles(JujutsucraftModParticleTypes.PARTICLE_BLACK_FLASH_1.get(), x_pos, y_pos, z_pos, 5, 0.25, 0.25, 0.25, 0.0);
//                                _level.sendParticles(JujutsucraftaddonModParticleTypes.KOKUSEN_3.get(), x_pos, y_pos, z_pos, 5, 0.25, 0.25, 0.25, 0.0);
//                                _level.sendParticles(JujutsucraftaddonModParticleTypes.KOKUSEN_4.get(), x_pos, y_pos, z_pos, 5, 0.25, 0.25, 0.25, 0.0);
//                                _level.sendParticles(JujutsucraftaddonModParticleTypes.KOKUSEN_5.get(), x_pos, y_pos, z_pos, 5, 0.25, 0.25, 0.25, 0.0);
                                }
                            }

                        }

                        entity.getPersistentData().putDouble("cnt2", entity.getPersistentData().getDouble("cnt2") + 0.25);
                        if (entity.getPersistentData().getDouble("cnt2") % 4.0 < 2.0) {
                            entity.getPersistentData().putDouble("x_pos", entity.getPersistentData().getDouble("x_pos") + num1);
                            entity.getPersistentData().putDouble("y_pos", entity.getPersistentData().getDouble("y_pos") + num2);
                            entity.getPersistentData().putDouble("z_pos", entity.getPersistentData().getDouble("z_pos") + num3);
                        } else {
                            entity.getPersistentData().putDouble("x_pos", entity.getPersistentData().getDouble("x_pos") - num1);
                            entity.getPersistentData().putDouble("y_pos", entity.getPersistentData().getDouble("y_pos") - num2);
                            entity.getPersistentData().putDouble("z_pos", entity.getPersistentData().getDouble("z_pos") - num3);
                        }
                    }
                }
            } else if (entity.getPersistentData().getDouble("cnt1") > 20.0) {
                x_pos = x + Math.random() * 16.0 - 8.0;
                y_pos = y;
                z_pos = z + Math.random() * 16.0 - 8.0;



                if (entity.getPersistentData().getDouble("cnt1") > 25.0 && !entity.level().isClientSide()) {
                    entity.discard();
                }
            }

        }*/
    }
}
