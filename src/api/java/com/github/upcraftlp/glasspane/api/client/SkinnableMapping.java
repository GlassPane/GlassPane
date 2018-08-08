package com.github.upcraftlp.glasspane.api.client;

import com.google.common.base.Preconditions;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class SkinnableMapping {

    private static final Map<String, Set<String>> ENTRIES = new TreeMap<>();
    private static final Map<ResourceLocation, Integer> REGISTERED_SKINS = new HashMap<>();

    //TODO GUI for skin selection!
    public static void addMapping(ResourceLocation skin, int skinID) {
        Preconditions.checkArgument(skinID > 0, "skin ID must be greater than zero!");
        REGISTERED_SKINS.put(skin, skinID);
        if(!ENTRIES.containsKey(skin.getNamespace())) ENTRIES.put(skin.getNamespace(), new TreeSet<>());
        ENTRIES.get(skin.getNamespace()).add(skin.getPath());
    }

    public static int getSkinIdForRendering(ResourceLocation skin) {
        return REGISTERED_SKINS.getOrDefault(skin, 0);
    }

    public static Map<String, Set<String>> getValidOptions() {
        return Collections.unmodifiableMap(ENTRIES);
    }
}
