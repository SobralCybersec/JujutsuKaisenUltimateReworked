package com.jujutsu.jujutsucraftaddon.procedures.combat;

import net.mcreator.jujutsucraft.init.JujutsucraftModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class SlotEffectService {

    public static void playDecisionSound(LevelAccessor world, double x, double y, double z) {
        playSound(world, x, y, z, "entity.firework_rocket.blast", 5.0F, 2.0F);
        playSound(world, x, y, z, "entity.firework_rocket.blast_far", 5.0F, 2.0F);
        playSound(world, x, y, z, "block.anvil.place", 5.0F, 1.0F);
        playSound(world, x, y, z, "jujutsucraft:gacon", 5.0F, 2.0F);
    }

    public static void playJackpotSound(LevelAccessor world, double x, double y, double z) {
        if (world instanceof ServerLevel _level) {
            if (!_level.isClientSide()) {
                _level.playSound(null, BlockPos.containing(x, y, z), 
                    ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jujutsucraft:hakari1")), 
                    SoundSource.NEUTRAL, 5.0F, 1.0F);
            } else {
                _level.playLocalSound(x, y, z, 
                    ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jujutsucraft:hakari1")), 
                    SoundSource.NEUTRAL, 5.0F, 1.0F, false);
            }
        }
    }

    public static void spawnDecisionParticles(LevelAccessor world, double x, double y, double z) {
        if (world instanceof ServerLevel _level) {
            _level.sendParticles(ParticleTypes.END_ROD, x, y, z, 25, 1.0, 2.0, 1.0, 0.05);
            _level.sendParticles(ParticleTypes.CRIT, x, y, z, 25, 1.0, 2.0, 1.0, 0.05);
        }
    }

    public static void spawnJackpotParticles(LevelAccessor world, double x, double y, double z) {
        if (world instanceof ServerLevel _level) {
            _level.sendParticles(ParticleTypes.END_ROD, x, y, z, 10, 1.0, 1.0, 1.0, 0.5);
            _level.sendParticles(ParticleTypes.FIREWORK, x, y, z, 1, 1.0, 1.0, 1.0, 0.5);
        }
    }

    public static void spawnReachParticles(LevelAccessor world, double x, double y, double z, boolean isThunder) {
        if (!(world instanceof ServerLevel _level)) {
            return;
        }

        if (isThunder) {
            playSound(world, x, y, z, "jujutsucraft:electric_shock", 5.0F, 1.0F);
            _level.sendParticles(JujutsucraftModParticleTypes.PARTICLE_THUNDER_BLUE.get(), 
                x, y, z, 50, 2.0, 2.0, 2.0, 1.0);
        } else {
            playSound(world, x, y, z, "item.totem.use", 5.0F, 1.0F);
            _level.sendParticles(ParticleTypes.HAPPY_VILLAGER, 
                x, y, z, 50, 2.0, 2.0, 2.0, 1.0);
        }
    }

    private static void playSound(LevelAccessor world, double x, double y, double z, String sound, float volume, float pitch) {
        if (world instanceof Level _level) {
            if (!_level.isClientSide()) {
                _level.playSound(null, BlockPos.containing(x, y, z), 
                    ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(sound)), 
                    SoundSource.NEUTRAL, volume, pitch);
            } else {
                _level.playLocalSound(x, y, z, 
                    ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(sound)), 
                    SoundSource.NEUTRAL, volume, pitch, false);
            }
        }
    }
}
