package com.jujutsu.jujutsucraftaddon.procedures;

import com.jujutsu.jujutsucraftaddon.configuration.TechniqueRatesConfiguration;
import net.mcreator.jujutsucraft.procedures.*;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class TestProcedureeProcedure {

    @FunctionalInterface
    private interface CTExecutor {
        void execute(LevelAccessor world, double x, double y, double z, Player entity, HashMap guistate);
    }

    private static final Map<String, CTExecutor> CT_EXECUTORS = new HashMap<>();
    private static final NavigableMap<Double, String> CT_RANGES = new TreeMap<>();

    static {
        // Register all CT executors
        CT_EXECUTORS.put("SUKUNA", SelectSukunaProcedure::execute);
        CT_EXECUTORS.put("GOJO", SelectGojoProcedure::execute);
        CT_EXECUTORS.put("OGI", SelectOgiProcedure::execute);
        CT_EXECUTORS.put("MAKI", SelectMakiProcedure::execute);
        CT_EXECUTORS.put("DHRUV", SelectDhruvLakdawallaProcedure::execute);
        CT_EXECUTORS.put("MAHORAGA", SelectMahoragaProcedure::execute);
        CT_EXECUTORS.put("TSUKUMO", SelectTsukumoProcedure::execute);
        CT_EXECUTORS.put("YAGA", SelectYagaProcedure::execute);
        CT_EXECUTORS.put("KUROURUSHI", SelectKurourushiProcedure::execute);
        CT_EXECUTORS.put("ISHIGORI", SelectIshigoriProcedure::execute);
        CT_EXECUTORS.put("NISHIMIYA", SelectNishimiyaProcedure::execute);
        CT_EXECUTORS.put("TODO", SelectTodoProcedure::execute);
        CT_EXECUTORS.put("DAGON", SelectDagonProcedure::execute);
        CT_EXECUTORS.put("MIGUEL", SelectMiguelProcedure::execute);
        CT_EXECUTORS.put("OKKOTSU", SelectOkkotsuProcedure::execute);
        CT_EXECUTORS.put("MEIMEI", SelectMeiMeiProcedure::execute);
        CT_EXECUTORS.put("YOROZU", SelectYorozuProcedure::execute);
        CT_EXECUTORS.put("URAUME", SelectUraumeProcedure::execute);
        CT_EXECUTORS.put("GETO", SelectGetoProcedure::execute);
        CT_EXECUTORS.put("CHOSO", SelectChosoProcedure::execute);
        CT_EXECUTORS.put("TAKAKOURO", SelectTakakoUroProcedure::execute);
        CT_EXECUTORS.put("ITADORI", SelectItadoriProcedure::execute);
        CT_EXECUTORS.put("KUSAKABE", SelectKusakabeProcedure::execute);
        CT_EXECUTORS.put("JUNPE", SelectJunpeProcedure::execute);
        CT_EXECUTORS.put("JOGO", SelectJogoProcedure::execute);
        CT_EXECUTORS.put("TAKABA", SelectTakabaProcedure::execute);
        CT_EXECUTORS.put("HIGURUMA", SelectHigurumaProcedure::execute);
        CT_EXECUTORS.put("ANGEL", SelectAngelProcedure::execute);
        CT_EXECUTORS.put("HAKARI", SelectHakariProcedure::execute);
        CT_EXECUTORS.put("MAHITO", SelectMahitoProcedure::execute);
        CT_EXECUTORS.put("CHOJURO", SelectChojuroProcedure::execute);
        CT_EXECUTORS.put("NANAMI", SelectNanamiProcedure::execute);
        CT_EXECUTORS.put("FUSHIGURO", SelectFushiguroProcedure::execute);
        CT_EXECUTORS.put("SMALLPOXDEITY", SelectSmallpoxDeityProcedure::execute);
        CT_EXECUTORS.put("INUMAKI", SelectInumakiProcedure::execute);
        CT_EXECUTORS.put("KUGISAKI", SelectKugisakiProcedure::execute);
        CT_EXECUTORS.put("HANAMI", SelectHanamiProcedure::execute);
        CT_EXECUTORS.put("KASHIMO", SelectKashimoProcedure::execute);
        CT_EXECUTORS.put("JINICHI", SelectJinichiProcedure::execute);
        CT_EXECUTORS.put("NAOYA", SelectNaoyaProcedure::execute);
    }

    private static void calculateCTRanges() {
        CT_RANGES.clear();
        double cumulative = 0.0;

        cumulative = addRange("SUKUNA", TechniqueRatesConfiguration.SUKUNA.get(), cumulative);
        cumulative = addRange("GOJO", TechniqueRatesConfiguration.GOJO.get(), cumulative);
        cumulative = addRange("OGI", TechniqueRatesConfiguration.OGI.get(), cumulative);
        cumulative = addRange("MAKI", TechniqueRatesConfiguration.MAKI.get(), cumulative);
        cumulative = addRange("DHRUV", TechniqueRatesConfiguration.DHRUV.get(), cumulative);
        cumulative = addRange("MAHORAGA", TechniqueRatesConfiguration.MAHORAGA.get(), cumulative);
        cumulative = addRange("TSUKUMO", TechniqueRatesConfiguration.TSUKUMO.get(), cumulative);
        cumulative = addRange("YAGA", TechniqueRatesConfiguration.YAGA.get(), cumulative);
        cumulative = addRange("KUROURUSHI", TechniqueRatesConfiguration.KUROURUSHI.get(), cumulative);
        cumulative = addRange("ISHIGORI", TechniqueRatesConfiguration.ISHIGORI.get(), cumulative);
        cumulative = addRange("NISHIMIYA", TechniqueRatesConfiguration.NISHIMIYA.get(), cumulative);
        cumulative = addRange("TODO", TechniqueRatesConfiguration.TODO.get(), cumulative);
        cumulative = addRange("DAGON", TechniqueRatesConfiguration.DAGON.get(), cumulative);
        cumulative = addRange("MIGUEL", TechniqueRatesConfiguration.MIGUEL.get(), cumulative);
        cumulative = addRange("OKKOTSU", TechniqueRatesConfiguration.OKKOTSU.get(), cumulative);
        cumulative = addRange("MEIMEI", TechniqueRatesConfiguration.MEIMEI.get(), cumulative);
        cumulative = addRange("YOROZU", TechniqueRatesConfiguration.YOROZU.get(), cumulative);
        cumulative = addRange("URAUME", TechniqueRatesConfiguration.URAUME.get(), cumulative);
        cumulative = addRange("GETO", TechniqueRatesConfiguration.GETO.get(), cumulative);
        cumulative = addRange("CHOSO", TechniqueRatesConfiguration.CHOSO.get(), cumulative);
        cumulative = addRange("TAKAKOURO", TechniqueRatesConfiguration.TAKAKOURO.get(), cumulative);
        cumulative = addRange("ITADORI", TechniqueRatesConfiguration.ITADORI.get(), cumulative);
        cumulative = addRange("KUSAKABE", TechniqueRatesConfiguration.KUSAKABE.get(), cumulative);
        cumulative = addRange("JUNPE", TechniqueRatesConfiguration.JUNPE.get(), cumulative);
        cumulative = addRange("JOGO", TechniqueRatesConfiguration.JOGO.get(), cumulative);
        cumulative = addRange("TAKABA", TechniqueRatesConfiguration.TAKABA.get(), cumulative);
        cumulative = addRange("HIGURUMA", TechniqueRatesConfiguration.HIGURUMA.get(), cumulative);
        cumulative = addRange("ANGEL", TechniqueRatesConfiguration.ANGEL.get(), cumulative);
        cumulative = addRange("HAKARI", TechniqueRatesConfiguration.HAKARI.get(), cumulative);
        cumulative = addRange("MAHITO", TechniqueRatesConfiguration.MAHITO.get(), cumulative);
        cumulative = addRange("CHOJURO", TechniqueRatesConfiguration.CHOJURO.get(), cumulative);
        cumulative = addRange("NANAMI", TechniqueRatesConfiguration.NANAMI.get(), cumulative);
        cumulative = addRange("FUSHIGURO", TechniqueRatesConfiguration.FUSHIGURO.get(), cumulative);
        cumulative = addRange("SMALLPOXDEITY", TechniqueRatesConfiguration.SMALLPOXDEITY.get(), cumulative);
        cumulative = addRange("INUMAKI", TechniqueRatesConfiguration.INUMAKI.get(), cumulative);
        cumulative = addRange("KUGISAKI", TechniqueRatesConfiguration.KUGISAKI.get(), cumulative);
        cumulative = addRange("HANAMI", TechniqueRatesConfiguration.HANAMI.get(), cumulative);
        cumulative = addRange("KASHIMO", TechniqueRatesConfiguration.KASHIMO.get(), cumulative);
        cumulative = addRange("JINICHI", TechniqueRatesConfiguration.JINICHI.get(), cumulative);
        cumulative = addRange("NAOYA", TechniqueRatesConfiguration.NAOYA.get(), cumulative);
    }

    private static double addRange(String ct, double weight, double cumulative) {
        CT_RANGES.put(cumulative + weight, ct);
        return cumulative + weight;
    }

    private static String selectRandomCT() {
        double total = CT_RANGES.lastKey();
        double random = RandomSource.create().nextDouble() * total;
        return CT_RANGES.ceilingEntry(random).getValue();
    }

    public static void execute(LevelAccessor world, double x, double y, double z, Player entity, HashMap guistate) {
        if (entity == null || guistate == null) return;

        calculateCTRanges();
        String selectedCT = selectRandomCT();

        CTExecutor executor = CT_EXECUTORS.get(selectedCT);
        if (executor != null) {
            executor.execute(world, x, y, z, entity, guistate);
        }
    }
}
