package com.jujutsu.jujutsucraftaddon.command.cursedtechnique;

import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import java.util.Map;
import com.jujutsu.jujutsucraftaddon.procedures.*;
import java.util.HashMap;

@Mod.EventBusSubscriber
public class ChangeCursedTechnique {

    @FunctionalInterface
    private interface CTProcedure {
        void execute(LevelAccessor world, CommandContext<CommandSourceStack> arguments);
    }

    private static final Map<String, CTProcedure> CT_PROCEDURES = new HashMap<>();

    static {
        CT_PROCEDURES.put("Maki", SetMakiProcedure::execute);
        CT_PROCEDURES.put("NonSorcerer", SetNonProcedure::execute);
        CT_PROCEDURES.put("Sukuna", SetSukunaProcedure::execute);
        CT_PROCEDURES.put("Gojo", SetGojoProcedure::execute);
        CT_PROCEDURES.put("Inumaki", SetInumakiProcedure::execute);
        CT_PROCEDURES.put("Jogo", SetJogoProcedure::execute);
        CT_PROCEDURES.put("Okkotsu", SetOkkotsuProcedure::execute);
        CT_PROCEDURES.put("Megumi", SetMegumiProcedure::execute);
        CT_PROCEDURES.put("Kashimo", SetKashimoProcedure::execute);
        CT_PROCEDURES.put("Dagon", SetDagonProcedure::execute);
        CT_PROCEDURES.put("Tsukumo", SetTsukumoProcedure::execute);
        CT_PROCEDURES.put("Choso", SetChosoProcedure::execute);
        CT_PROCEDURES.put("MeiMei", SetMeiMeiProcedure::execute);
        CT_PROCEDURES.put("Ishigori", SetIshigoriProcedure::execute);
        CT_PROCEDURES.put("Nanami", SetNanamiProcedure::execute);
        CT_PROCEDURES.put("Hanami", SetHanamiProcedure::execute);
        CT_PROCEDURES.put("Mahito", SetMahitoProcedure::execute);
        CT_PROCEDURES.put("Mahoraga", SetMahoragaProcedure::execute);
        CT_PROCEDURES.put("Takaba", SetTakabaProcedure::execute);
        CT_PROCEDURES.put("Geto", SetGetoProcedure::execute);
        CT_PROCEDURES.put("Naoya", SetNaoyaProcedure::execute);
        CT_PROCEDURES.put("Todo", SetTodoProcedure::execute);
        CT_PROCEDURES.put("Itadori", SetItadoriProcedure::execute);
        CT_PROCEDURES.put("Jinichi", SetJinichiProcedure::execute);
        CT_PROCEDURES.put("Kurourushi", SetKurourushiProcedure::execute);
        CT_PROCEDURES.put("Uraume", SetUraumeProcedure::execute);
        CT_PROCEDURES.put("Ogi", SetOgiProcedure::execute);
        CT_PROCEDURES.put("Higuruma", SetHigurumaProcedure::execute);
        CT_PROCEDURES.put("Angel", SetAngelProcedure::execute);
        CT_PROCEDURES.put("Hakari", SetHakariProcedure::execute);
        CT_PROCEDURES.put("Miguel", SetMiguelProcedure::execute);
        CT_PROCEDURES.put("Kusakabe", SetKusakabeProcedure::execute);
        CT_PROCEDURES.put("Chojuro", SetChojuroProcedure::execute);
        CT_PROCEDURES.put("Yaga", SetYagaProcedure::execute);
        CT_PROCEDURES.put("Kugisaki", SetNobaraProcedure::execute);
        CT_PROCEDURES.put("Junpei", SetJunpeiProcedure::execute);
        CT_PROCEDURES.put("Nishimiya", SetNishimiyaProcedure::execute);
        CT_PROCEDURES.put("Dhruv", SetDhruvProcedure::execute);
        CT_PROCEDURES.put("Yorozu", SetYorozuProcedure::execute);
        CT_PROCEDURES.put("Uro", SetUroProcedure::execute);
        CT_PROCEDURES.put("SmallpoxDeity", SetSmallDeityProcedure::execute);
        CT_PROCEDURES.put("Ino", SetInoProcedure::execute);
    }

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        var baseCommand = Commands.literal("jjkuChangeCursedTechnique").requires(s -> s.hasPermission(2));

        CT_PROCEDURES.forEach((ctName, procedure) ->
                baseCommand.then(Commands.literal(ctName)
                        .then(Commands.argument("Player", EntityArgument.player())
                                .executes(ctx -> {
                                    procedure.execute(ctx.getSource().getUnsidedLevel(), ctx);
                                    return 0;
                                })
                        )
                )
        );

        event.getDispatcher().register(baseCommand);
    }
}
