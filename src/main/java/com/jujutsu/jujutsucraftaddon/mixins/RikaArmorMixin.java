package com.jujutsu.jujutsucraftaddon.mixins;

import com.google.common.collect.Iterables;
import com.jujutsu.jujutsucraftaddon.network.JujutsucraftaddonModVariables;
import net.mcreator.jujutsucraft.item.RikaBodyItem;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.KurourushiBodyHelmetTickEventProcedure;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RikaBodyItem.Helmet.class, priority = -10000)
public class RikaArmorMixin {


    private static final long MESSAGE_COOLDOWN = 6000;
    private static final long BUFF_COOLDOWN = 7200;

    @Inject(method = "inventoryTick", at = @At("HEAD"), remap = false, cancellable = true)
    private void rikaArmorTickMixin(ItemStack itemstack, Level level, Entity entity, int slot, boolean selected, CallbackInfo ci) {
        ci.cancel();

        if (!(entity instanceof Player player)) return;

        // Server side only
        if (level.isClientSide()) return;

        var addonVars = player.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY).orElse(null);
        var baseVars = player.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY).orElse(null);

        if (addonVars == null || baseVars == null) return;

        if ("Okkotsu".equals(addonVars.Clans) && baseVars.PlayerCurseTechnique2 == -1) {

            long gameTime = level.getGameTime();

            if (gameTime % BUFF_COOLDOWN == 0) {

                int amplifier = 0;

                if (player.hasEffect(MobEffects.DAMAGE_BOOST)) {
                    amplifier = player.getEffect(MobEffects.DAMAGE_BOOST).getAmplifier();
                }

                player.addEffect(new MobEffectInstance(
                        MobEffects.DAMAGE_BOOST,
                        1200,
                        amplifier * 2,
                        false,
                        false
                ));
            }

            if (gameTime % MESSAGE_COOLDOWN == 0) {

                player.displayClientMessage(
                        Component.literal("Your Family Blood runs inside of your heart, to protect the one you need"),
                        false
                );
            }

            if ((addonVars.RikaAwakening == 0) && player.getHealth() <= 20.0F) {

                player.addEffect(new MobEffectInstance(
                        MobEffects.HEAL,
                        40,
                        255,
                        false,
                        false
                ));

                player.displayClientMessage(
                        Component.literal("Don't lose now"),
                        false
                );

                addonVars.RikaAwakening = 1;
                addonVars.syncPlayerVariables(player);
            }

            return;
        }

        if (Iterables.contains(player.getArmorSlots(), itemstack)) {
            KurourushiBodyHelmetTickEventProcedure.execute(entity);
        }
    }
}
