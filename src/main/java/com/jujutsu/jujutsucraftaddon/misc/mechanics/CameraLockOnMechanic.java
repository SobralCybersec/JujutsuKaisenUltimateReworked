package com.jujutsu.jujutsucraftaddon.misc.mechanics;

import com.jujutsu.jujutsucraftaddon.network.JujutsucraftaddonModVariables;
import com.jujutsu.jujutsucraftaddon.procedures.LockOnCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = "jujutsucraftaddon", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class CameraLockOnMechanic {
    private static final float SMOOTH_FACTOR = 0.05f;

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                LockOnCapability capability = mc.player.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                        .orElse(new JujutsucraftaddonModVariables.PlayerVariables()).lockOnCapability;

                if (capability != null && capability.getTargetEntityId() != -1) {
                    Entity target = mc.level.getEntity(capability.getTargetEntityId());
                    if (target != null) {
                        double dx = target.getX() - mc.player.getX();
                        double dy = (target.getEyeY() - mc.player.getEyeY()); // Eye height for proper pitch
                        double dz = target.getZ() - mc.player.getZ();

                        // Calculate yaw (horizontal rotation)
                        float targetYaw = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90F;
                        float currentYaw = mc.player.getYRot();
                        float newYaw = Mth.rotLerp(SMOOTH_FACTOR, currentYaw, targetYaw); // Smooth yaw rotation

                        // Calculate pitch (vertical rotation)
                        float targetPitch = (float) -Math.toDegrees(Math.atan2(dy, Math.sqrt(dx * dx + dz * dz)));
                        float currentPitch = mc.player.getXRot();
                        float newPitch = Mth.rotLerp(SMOOTH_FACTOR, currentPitch, targetPitch); // Smooth pitch rotation

                        // Apply the rotations
                        mc.player.setYRot(newYaw);
                        mc.player.setXRot(newPitch);
                    }
                }
            }

        }
    }
}
