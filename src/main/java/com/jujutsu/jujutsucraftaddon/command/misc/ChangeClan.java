package com.jujutsu.jujutsucraftaddon.command.misc;

import com.jujutsu.jujutsucraftaddon.procedures.*;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber
public class ChangeClan {

    @FunctionalInterface
    private interface ClanProcedure {
        void execute(CommandContext<CommandSourceStack> arguments);
    }

    private static final Map<String, ClanProcedure> CLAN_PROCEDURES = Map.ofEntries(
            Map.entry("Geto", ClanGetoProcedure::execute),
            Map.entry("Gojo", ClanGojoProcedure::execute),
            Map.entry("Itadori", ClanItadoriProcedure::execute),
            Map.entry("Sukuna", ClanSukunaProcedure::execute),
            Map.entry("Fushiguro", ClanFushiguroProcedure::execute),
            Map.entry("Kamo", ClanKamoProcedure::execute),
            Map.entry("Tsukumo", ClanTsukumoProcedure::execute),
            Map.entry("Zenin", ClanZeninProcedure::execute),
            Map.entry("RejectedZenin", ClanRejectedZeninProcedure::execute),
            Map.entry("Kashimo", ClanKashimoProcedure::execute),
            Map.entry("Okkotsu", ClanOkkotsuProcedure::execute),
            Map.entry("Kenjaku", ClanKenjakuProcedure::execute),
            Map.entry("Hakari", ClanHakariProcedure::execute),
            Map.entry("Inumaki", ClanInumakiProcedure::execute),
            Map.entry("Fujiwara", ClanFujiwaraProcedure::execute),
            Map.entry("Abe", ClanAbeProcedure::execute),
            Map.entry("Sugawara", ClanSugawaraProcedure::execute),
            Map.entry("Higuruma", ClanHigurumaProcedure::execute),
            Map.entry("Uraume", ClanUraumeProcedure::execute),
            Map.entry("Sung", ClanSungProcedure::execute),
            Map.entry("Zaraki", ClanZarakiProcedure::execute),
            Map.entry("Wukong", ClanWukongProcedure::execute),
            Map.entry("Majima", ClanMajimaProcedure::execute),
            Map.entry("Kiryu", ClanKiryuProcedure::execute)
    );

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        var baseCommand = Commands.literal("jjkuClan").requires(s -> s.hasPermission(2));

        CLAN_PROCEDURES.forEach((clanName, procedure) ->
                baseCommand.then(Commands.literal(clanName)
                        .then(Commands.argument("Player", EntityArgument.player())
                                .executes(ctx -> {
                                    procedure.execute(ctx);
                                    return 0;
                                })
                        )
                )
        );

        event.getDispatcher().register(baseCommand);
    }
}

