package com.jujutsu.jujutsucraftaddon.procedures;

import java.util.HashMap;
import java.util.Map;

public class TechniqueRegistry {

    private static final Map<String, TechniqueConfig> TECHNIQUES = new HashMap<>();

    public record TechniqueConfig(int t1, int s1, double tick) {}

    public static void register(String name, int t1, int s1, double tick) {
        TECHNIQUES.put(name, new TechniqueConfig(t1, s1, tick));
    }

    public static TechniqueConfig getByName(String name) {
        return TECHNIQUES.get(name);
    }

    public static void clear() {
        TECHNIQUES.clear();
    }
}
