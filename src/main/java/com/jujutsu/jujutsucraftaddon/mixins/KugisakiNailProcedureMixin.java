package com.jujutsu.jujutsucraftaddon.mixins;

import net.mcreator.jujutsucraft.entity.BulletNailEntity;
import net.mcreator.jujutsucraft.init.JujutsucraftModEntities;
import net.mcreator.jujutsucraft.init.JujutsucraftModItems;
import net.mcreator.jujutsucraft.procedures.DamageFixProcedure;
import net.mcreator.jujutsucraft.procedures.KugisakiNailProcedure;
import net.mcreator.jujutsucraft.procedures.RotateEntityProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = KugisakiNailProcedure.class, priority = -10000)
public abstract class KugisakiNailProcedureMixin {

    /**
     * @author Satushi
     * @reason Buffs Nails and make them don't cost always every shot
     */

    @Inject(at = @At("HEAD"), method = "execute", remap = false, cancellable = true)
    private static void execute(LevelAccessor world, double x, double y, double z, Entity entity, CallbackInfo ci) {
        ci.cancel();
        if (entity != null) {
            double x_pos = 0.0;
            double y_pos = 0.0;
            double z_pos = 0.0;

            ItemStack handItem;
            if (entity instanceof LivingEntity) {
                LivingEntity _livEnt = (LivingEntity) entity;
                _livEnt.swing(InteractionHand.MAIN_HAND, true);
                handItem = _livEnt.getMainHandItem();
            } else {
                handItem = ItemStack.EMPTY;
            }

            Level projectileLevel;
            if (handItem.getItem() == JujutsucraftModItems.HAMMER.get()) {
                if (world instanceof Level) {
                    projectileLevel = (Level) world;
                    if (!projectileLevel.isClientSide()) {
                        projectileLevel.playSound((Player) null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.place")), SoundSource.NEUTRAL, 0.5F, 1.5F);
                    } else {
                        projectileLevel.playLocalSound(x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.place")), SoundSource.NEUTRAL, 0.5F, 1.5F, false);
                    }
                }

                if (Math.random() < 0.01) {
                    if (entity instanceof Player) {
                        Player _player = (Player) entity;
                        ItemStack _stktoremove = new ItemStack((ItemLike) JujutsucraftModItems.NAIL.get());
                        _player.getInventory().clearOrCountMatchingItems((p) -> {
                            return _stktoremove.getItem() == p.getItem();
                        }, 1, _player.inventoryMenu.getCraftSlots());
                    }
                }
            }

            LivingEntity target = getMobTarget(entity);
            if (target != null) {
                x_pos = target.getX();
                y_pos = target.getY() + (double) target.getBbHeight() * 0.5;
                z_pos = target.getZ();
                RotateEntityProcedure.execute(x_pos, y_pos, z_pos, entity);
            }

            entity.getPersistentData().putDouble("Damage", 0.5);
            DamageFixProcedure.execute(entity);
            Entity _shootFrom = entity;
            projectileLevel = _shootFrom.level();
            if (!projectileLevel.isClientSide()) {
                AbstractArrow _entityToSpawn = new BulletNailEntity((EntityType) JujutsucraftModEntities.BULLET_NAIL.get(), projectileLevel);
                _entityToSpawn.setOwner(entity);
                _entityToSpawn.setBaseDamage((float) entity.getPersistentData().getDouble("Damage"));
                _entityToSpawn.setKnockback(0);
                _entityToSpawn.setSilent(true);
                _entityToSpawn.setCritArrow(true);
                _entityToSpawn.pickup = AbstractArrow.Pickup.ALLOWED;
                _entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
                _entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 3.0F, 0.0F);
                projectileLevel.addFreshEntity(_entityToSpawn);
            }

            entity.getPersistentData().putDouble("skill", 0.0);
        }
    }

    private static LivingEntity getMobTarget(Entity entity) {
        if (entity instanceof Mob) {
            return ((Mob) entity).getTarget();
        }
        return null;
    }
}