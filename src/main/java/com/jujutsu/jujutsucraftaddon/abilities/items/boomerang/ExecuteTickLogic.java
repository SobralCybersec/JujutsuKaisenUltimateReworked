package com.jujutsu.jujutsucraftaddon.abilities.boomerang;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ExecuteTickLogic {
    private static final double SPEED = 1.5;
    private static final int MAX_DISTANCE = 20;
    private static final float EXPLOSION_STRENGTH = 2.5F;
    private static final double ARC_CURVE = 0.3;
    private static final double ROTATION_SPEED = 40.0;

    public void execute(BoomerangInstanceRecord instance, boolean returning, Runnable onReturn, Runnable onComplete) {
        ServerLevel level = (ServerLevel) instance.boomerang().level();
        Vec3 pos = instance.boomerang().position();

        instance.boomerang().setYRot(instance.boomerang().getYRot() + (float) ROTATION_SPEED);

        if (checkCollision(level, pos, instance)) {
            explode(level, pos);
            onReturn.run();
            returning = true;
        }

        if (instance.ticksTraveled() >= MAX_DISTANCE && !returning) {
            onReturn.run();
            returning = true;
        }

        if (!returning) {
            moveForward(instance);
        } else {
            moveToPlayer(instance);
            if (instance.boomerang().distanceTo(instance.player()) < 1.5) {
                returnItem(instance);
                onComplete.run();
            }
        }
    }

    private boolean checkCollision(ServerLevel level, Vec3 pos, BoomerangInstanceRecord instance) {
        return level.getEntities(null, new AABB(pos, pos).inflate(1)).stream()
                .anyMatch(e -> e instanceof LivingEntity && e != instance.player());
    }

    private void explode(ServerLevel level, Vec3 pos) {
        level.explode(null, pos.x, pos.y, pos.z, EXPLOSION_STRENGTH, ServerLevel.ExplosionInteraction.NONE);
    }

    private void moveForward(BoomerangInstanceRecord instance) {
        double arc = Math.sin(instance.ticksTraveled() * 0.2) * ARC_CURVE;
        Vec3 vel = instance.velocity();
        instance.boomerang().setDeltaMovement(new Vec3(vel.x + arc, vel.y, vel.z));
    }

    private void moveToPlayer(BoomerangInstanceRecord instance) {
        Vec3 dir = instance.player().position().add(0, instance.player().getEyeHeight(), 0)
                .subtract(instance.boomerang().position()).normalize().scale(SPEED);
        instance.boomerang().setDeltaMovement(dir);
    }

    private void returnItem(BoomerangInstanceRecord instance) {
        ItemStack item = instance.boomerang().getItem();
        if (instance.player().getMainHandItem().isEmpty()) {
            instance.player().setItemInHand(instance.player().getUsedItemHand(), item);
        } else {
            instance.player().addItem(item);
        }
    }
}
