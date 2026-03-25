package com.jujutsu.jujutsucraftaddon.mixins;

import com.jujutsu.jujutsucraftaddon.procedures.TechniqueExecutor;
import com.jujutsu.jujutsucraftaddon.procedures.TechniqueRegistry;
import com.jujutsu.jujutsucraftaddon.procedures.TechniqueValidator;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.StartCursedTechniqueProcedure;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = StartCursedTechniqueProcedure.class, priority = -10000)
public abstract class StartCursedTechniqueTest {

    @Inject(at = @At("HEAD"), method = "execute", remap = false, cancellable = true)
    private static void execute(LevelAccessor world, double x, double y, double z, Entity entity, CallbackInfo ci) {
        if (entity == null) return;
        ci.cancel();

        boolean isCreative = TechniqueValidator.isCreativeMode(entity);
        String techniqueName = TechniqueValidator.getTechniqueName(entity);
        double cost = TechniqueValidator.getTechniqueCost(entity);

        if (!TechniqueValidator.canUse(entity, cost, isCreative)) return;

        TechniqueRegistry.TechniqueConfig config = TechniqueRegistry.getByName(techniqueName);
        if (config == null) {
            double t1 = entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                    .orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCurseTechnique;
            double s1 = entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                    .orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerSelectCurseTechnique;
            config = new TechniqueRegistry.TechniqueConfig((int)t1, (int)s1, Math.max(cost / 2.0, 20.0));
        }

        if (!isCreative) TechniqueValidator.consumeCursePower(entity, cost);
        TechniqueExecutor.execute(world, entity, config.t1(), config.s1(), config.tick(), isCreative);
    }
}