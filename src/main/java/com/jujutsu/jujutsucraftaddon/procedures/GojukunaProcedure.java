package com.jujutsu.jujutsucraftaddon.procedures;

import com.jujutsu.jujutsucraftaddon.network.JujutsucraftaddonModVariables;
import com.jujutsu.jujutsucraftaddon.world.inventory.*;
import io.netty.buffer.Unpooled;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.network.NetworkHooks;

public class GojukunaProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null)
            return;
        if ((entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables())).PlayerCurseTechnique == 2) {
            if (((entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).Clans).equals("Gojo")) {
                if (entity instanceof LivingEntity && ((LivingEntity) entity).hasEffect(JujutsucraftModMobEffects.SIX_EYES.get())) {
                    handleGojoPath(entity, x, y, z);
                }
            }
        } else if ((entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables())).PlayerCurseTechnique2 == 1) {
            if (((entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).Clans).equals("Sukuna")) {
                if ((((JujutsucraftModVariables.PlayerVariables) entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, (Direction) null).orElse(new JujutsucraftModVariables.PlayerVariables())).BodyItem.getCount() >= 3.0)) {
                    handleSukunaPath(entity, world, x, y, z);
                }
            }
        }
    }

    private static void handleGojoPath(Entity entity, double x, double y, double z) {
        int gh = (entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).GH;
        if (entity instanceof ServerPlayer _ent) {
            BlockPos _bpos = BlockPos.containing(x, y, z);
            openGojoStoryScreen(_ent, _bpos, gh);
        }
    }

    private static void handleSukunaPath(Entity entity, LevelAccessor world, double x, double y, double z) {
        int sh = (entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).SH;
        boolean hasBathRitual = false;
        if (entity instanceof ServerPlayer _plr && _plr.level() instanceof ServerLevel) {
            hasBathRitual = _plr.getAdvancements().getOrStartProgress(_plr.server.getAdvancements().getAdvancement(new ResourceLocation("jujutsucraftaddon:bath_ritual"))).isDone();
        }
        if (entity instanceof ServerPlayer _ent) {
            BlockPos _bpos = BlockPos.containing(x, y, z);
            openSukunaStoryScreen(_ent, _bpos, sh, hasBathRitual);
        }
    }

    private static void openGojoStoryScreen(ServerPlayer player, BlockPos pos, int gh) {
        if (gh == 0) {
            NetworkHooks.openScreen(player, new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.literal("StoryModeGojo1");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player p) {
                    return new StoryModeGojo1Menu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
                }
            }, pos);
        } else if (gh == 1) {
            NetworkHooks.openScreen(player, new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.literal("StoryModeGojo2");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player p) {
                    return new StoryModeGojo2Menu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
                }
            }, pos);
        } else if (gh == 2) {
            NetworkHooks.openScreen(player, new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.literal("StoryModeGojo3");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player p) {
                    return new StoryModeGojo3Menu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
                }
            }, pos);
        } else if (gh == 3) {
            NetworkHooks.openScreen(player, new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.literal("StoryModeGojo4");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player p) {
                    return new StoryModeGojo4Menu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
                }
            }, pos);
        } else if (gh == 4) {
            NetworkHooks.openScreen(player, new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.literal("StoryModeGojo6");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player p) {
                    return new StoryModeGojo6Menu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
                }
            }, pos);
        }
    }

    private static void openSukunaStoryScreen(ServerPlayer player, BlockPos pos, int sh, boolean hasBathRitual) {
        if (sh == 0) {
            NetworkHooks.openScreen(player, new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.literal("StoryModeSukuna1");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player p) {
                    return new StoryModeSukuna1Menu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
                }
            }, pos);
        } else if (sh == 1) {
            NetworkHooks.openScreen(player, new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.literal("StoryModeSukuna2");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player p) {
                    return new StoryModeSukuna2Menu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
                }
            }, pos);
        } else if (sh == 2) {
            NetworkHooks.openScreen(player, new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.literal("StoryModeSukuna3");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player p) {
                    return new StoryModeSukuna3Menu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
                }
            }, pos);
        } else if (sh == 3) {
            NetworkHooks.openScreen(player, new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.literal("StoryModeSukuna4");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player p) {
                    return new StoryModeSukuna4Menu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
                }
            }, pos);
        } else if (sh == 4 && hasBathRitual) {
            NetworkHooks.openScreen(player, new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.literal("StoryModeSukuna5");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player p) {
                    return new StoryModeSukuna5Menu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
                }
            }, pos);
        } else if (sh == 5 && hasBathRitual) {
            NetworkHooks.openScreen(player, new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.literal("StoryModeSukuna6");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player p) {
                    return new StoryModeSukuna6Menu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
                }
            }, pos);
        } else if (sh == 6 && hasBathRitual) {
            NetworkHooks.openScreen(player, new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.literal("StoryModeSukuna7");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player p) {
                    return new StoryModeSukuna7Menu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
                }
            }, pos);
        }
    }
}