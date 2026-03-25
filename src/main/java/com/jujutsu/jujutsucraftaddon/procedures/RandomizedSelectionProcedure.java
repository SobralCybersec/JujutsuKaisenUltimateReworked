package com.jujutsu.jujutsucraftaddon.procedures;

import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

import java.util.List;
import java.util.Map;

public class RandomizedSelectionProcedure {

    private static final Map<Integer, List<String>> TECHNIQUE_ITEMS = Map.ofEntries(
        Map.entry(-1, List.of("jujutsucraft:glasses_maki_chestplate", "jujutsucraft:glasses_maki_leggings")),
        Map.entry(1,  List.of("jujutsucraft:clothes_zenin_boots", "jujutsucraft:clothes_zenin_leggings")),
        Map.entry(2,  List.of("jujutsucraft:uniform_gojo_chestplate", "jujutsucraft:uniform_gojo_leggings", "jujutsucraft:uniform_gojo_boots")),
        Map.entry(3,  List.of("jujutsucraft:uniform_normal_chestplate", "jujutsucraft:uniform_normal_leggings", "jujutsucraft:uniform_normal_helmet")),
        Map.entry(4,  List.of("jujutsucraft:clothes_jogo_helmet", "jujutsucraft:clothes_jogo_chestplate")),
        Map.entry(5,  List.of("jujutsucraft:uniform_normal_leggings", "jujutsucraft:uniform_okkotsu_chestplate", "jujutsucraft:uniform_normal_boots")),
        Map.entry(6,  List.of("jujutsucraft:uniform_normal_chestplate", "jujutsucraft:uniform_normal_leggings", "jujutsucraft:uniform_normal_boots")),
        Map.entry(7,  List.of("jujutsucraft:clothes_kashimo_hajime_helmet", "jujutsucraft:clothes_kashimo_hajime_chestplate", "jujutsucraft:clothes_kashimo_hajime_leggings")),
        Map.entry(8,  List.of("jujutsucraft:clothes_dagon_chestplate", "jujutsucraft:clothes_dagon_helmet")),
        Map.entry(9,  List.of("jujutsucraft:uniform_normal_chestplate", "jujutsucraft:uniform_normal_leggings", "jujutsucraft:uniform_normal_boots")),
        Map.entry(10, List.of("jujutsucraft:clothes_choso_chestplate", "jujutsucraft:clothes_choso_helmet")),
        Map.entry(11, List.of("jujutsucraft:uniform_normal_chestplate", "jujutsucraft:uniform_normal_leggings", "jujutsucraft:uniform_normal_boots", "jujutsucraft:mei_mei_axe")),
        Map.entry(12, List.of("jujutsucraft:clothes_ishigori_chestplate", "jujutsucraft:clothes_ishigori_helmet", "jujutsucraft:clothes_ishigori_leggings")),
        Map.entry(13, List.of("jujutsucraft:clothes_nanami_chestplate", "jujutsucraft:clothes_nanami_helmet", "jujutsucraft:clothes_nanami_leggings")),
        Map.entry(14, List.of("jujutsucraft:hanami_eye", "jujutsucraft:hanami_hand")),
        Map.entry(15, List.of("jujutsucraft:mahito_hand_1", "jujutsucraft:mahito_hand_2")),
        Map.entry(16, List.of("jujutsucraft:mahoraga_wheel_helmet", "jujutsucraft:sword_of_extermination", "jujutsucraft:clothes_zenin_2_leggings")),
        Map.entry(17, List.of("jujutsucraft:costume_takaba_chestplate", "jujutsucraft:costume_takaba_boots", "jujutsucraft:costume_takaba_leggings")),
        Map.entry(18, List.of("jujutsucraft:clothes_geto_helmet", "jujutsucraft:clothes_geto_leggings", "jujutsucraft:clothes_geto_boots", "jujutsucraft:clothes_geto_chestplate")),
        Map.entry(19, List.of("jujutsucraft:clothes_zenin_leggings", "jujutsucraft:clothes_zenin_chestplate", "jujutsucraft:clothes_zenin_boots")),
        Map.entry(20, List.of("jujutsucraft:clothes_todo_aoi_leggings", "jujutsucraft:pendant_todo_aoi", "jujutsucraft:muscular_body_chestplate")),
        Map.entry(21, List.of("jujutsucraft:uniform_itadori_leggings", "jujutsucraft:uniform_itadori_chestplate", "jujutsucraft:uniform_normal_boots")),
        Map.entry(22, List.of("jujutsucraft:muscular_body_chestplate", "jujutsucraft:clothes_zenin_leggings")),
        Map.entry(23, List.of("jujutsucraft:fester_life_blade", "jujutsucraft:kurourushi_body_leggings", "jujutsucraft:kurourushi_body_chestplate", "jujutsucraft:kurourushi_body_helmet")),
        Map.entry(24, List.of("jujutsucraft:clothes_uraume_leggings", "jujutsucraft:clothes_uraume_boots", "jujutsucraft:clothes_uraume_chestplate", "jujutsucraft:clothes_uraume_helmet")),
        Map.entry(26, List.of("jujutsucraft:sword_ogi", "jujutsucraft:clothes_zenin_leggings", "jujutsucraft:clothes_zenin_chestplate", "jujutsucraft:clothes_zenin_boots")),
        Map.entry(27, List.of("jujutsucraft:gavel", "jujutsucraft:uniform_zenin_leggings", "jujutsucraft:uniform_higuruma_chestplate")),
        Map.entry(28, List.of("jujutsucraft:clothes_angel_chestplate", "jujutsucraft:clothes_angel_helmet")),
        Map.entry(29, List.of("jujutsucraft:clothes_hakari_kinji_helmet", "jujutsucraft:clothes_hakari_kinji_chestplate", "jujutsucraft:clothes_hakari_kinji_leggings")),
        Map.entry(30, List.of("jujutsucraft:black_rope", "jujutsucraft:clothes_miguel_helmet", "jujutsucraft:clothes_miguel_chestplate", "jujutsucraft:clothes_miguel_boots", "jujutsucraft:clothes_miguel_leggings")),
        Map.entry(31, List.of("jujutsucraft:sword_kusakabe", "jujutsucraft:clothes_kusakabe_chestplate", "jujutsucraft:clothes_kusakabe_leggings")),
        Map.entry(34, List.of("jujutsucraft:hammer", "jujutsucraft:nail 64")),
        Map.entry(35, List.of("jujutsucraft:broom")),
        Map.entry(36, List.of("jujutsucraft:broom", "jujutsucraft:clothes_nishimiya_helmet")),
        Map.entry(37, List.of("jujutsucraft:broom")),
        Map.entry(38, List.of("jujutsucraft:clothes_uro_helmet", "jujutsucraft:clothes_uro_chestplate")),
        Map.entry(39, List.of("jujutsucraft:clothes_yorozu_chestplate")),
        Map.entry(40, List.of("jujutsucraft:clothes_ino_takuma_1_helmet", "jujutsucraft:clothes_ino_takuma_1_chestplate", "jujutsucraft:clothes_ino_takuma_2_helmet", "jujutsucraft:clothes_ino_takuma_1_leggings", "jujutsucraft:clothes_ino_takuma_1_boots"))
    );

    public static void execute(Entity entity) {
        if (entity == null) return;

        int technique = (int) entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .orElse(new JujutsucraftModVariables.PlayerVariables())
                .PlayerCurseTechnique2;

        List<String> items = TECHNIQUE_ITEMS.get(technique);
        if (items == null) return;

        items.forEach(item -> runCommand(entity, "give @s " + item));
    }

    private static void runCommand(Entity entity, String command) {
        if (entity.level().isClientSide() || entity.getServer() == null) return;
        entity.getServer().getCommands().performPrefixedCommand(
                new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(),
                        entity.level() instanceof ServerLevel sl ? sl : null, 4,
                        entity.getName().getString(), entity.getDisplayName(), entity.level().getServer(), entity),
                command);
    }
}