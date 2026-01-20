package com.jujutsu.jujutsucraftaddon.techniques.hr;

import com.jujutsu.jujutsucraftaddon.mixins.OptionsAccessor;
import com.jujutsu.jujutsucraftaddon.network.JujutsucraftaddonModVariables;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = "jujutsucraftaddon", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public abstract class TojiHRClassRenderer {

    private static final Set<LivingEntity> glowSet = new HashSet<>();
    private static int tickCounter = 0;

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void eventHandler(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player != null){
            tickCounter++;
            if (tickCounter < 10) return;
            tickCounter = 0;


            OptionsAccessor accessor = (OptionsAccessor) Minecraft.getInstance().options;
            JujutsucraftaddonModVariables.PlayerVariables playerVariables = player.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                    .orElse(new JujutsucraftaddonModVariables.PlayerVariables());

            if (player.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                    .orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCurseTechnique == -1) {

                float renderDistance = accessor.getRenderDistance().get();
                AABB playerBoundingBox = player.getBoundingBox().inflate(renderDistance);

                if (playerVariables.HRVision) {
                    updateEntityGlowState(mc, playerBoundingBox, true);
                }

                if (!playerVariables.HRVision) {
                    updateEntityGlowState(mc, playerBoundingBox, false);
                }
            }
        }

    }

    private static void updateEntityGlowState(Minecraft mc, AABB playerBoundingBox, boolean shouldGlow) {
        mc.level.getEntitiesOfClass(LivingEntity.class, playerBoundingBox, e -> e != mc.player)
                .forEach(entity -> {
                    String tagname = "glowing";
                    boolean currentlyGlowing = entity.getPersistentData().getBoolean(tagname);
                    if (currentlyGlowing != shouldGlow) {
                        entity.getPersistentData().putBoolean(tagname, shouldGlow);
                        if (shouldGlow) {
                            entity.setCustomNameVisible(true);
                            if (!entity.hasCustomName()) {
                                entity.setCustomName(Component.literal(entity.getName().getString()));
                            }
                            glowSet.add(entity);
                        }

                        if (!shouldGlow) {
                            entity.setCustomNameVisible(false);
                            glowSet.remove(entity);
                        }
                    }
                });

        mc.level.getEntitiesOfClass(Projectile.class, playerBoundingBox)
                .forEach(entity -> {
                    boolean currentlyGlowing = entity.getPersistentData().getBoolean("glowing");
                    if (currentlyGlowing != shouldGlow) {
                        entity.getPersistentData().putBoolean("glowing", shouldGlow);
                    }
                });

        mc.level.getEntitiesOfClass(Arrow.class, playerBoundingBox)
                .forEach(entity -> {
                    boolean currentlyGlowing = entity.getPersistentData().getBoolean("glowing");
                    if (currentlyGlowing != shouldGlow) {
                        entity.getPersistentData().putBoolean("glowing", shouldGlow);
                    }
                });
    }
}
