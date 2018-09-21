package com.github.upcraftlp.glasspane.vanity;

import com.google.common.base.Preconditions;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.*;

import java.util.*;
import java.util.stream.Collectors;

@SideOnly(Side.CLIENT)
public class SkinnableMapping {

    private static final Map<String, List<String>> ENTRIES = new TreeMap<>();
    private static final Map<ResourceLocation, Integer> REGISTERED_SKINS = new HashMap<>();

    public static void addMapping(ResourceLocation skin, int skinID) {
        Preconditions.checkArgument(skinID > 0, "skin ID must be greater than zero!");
        REGISTERED_SKINS.put(skin, skinID);
        ENTRIES.computeIfAbsent(skin.getNamespace(), key -> new ArrayList<>()).add(skin.getPath());
    }

    public static int getSkinIdForRendering(ResourceLocation skin) {
        return REGISTERED_SKINS.getOrDefault(skin, 0);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, List<String>> getValidOptions() {
        Map ret = new TreeMap();
        UUID playerID = Minecraft.getMinecraft().getSession().getProfile().getId();
        ENTRIES.forEach((id, set) -> {
            List<String> filtered = set.stream().filter(s -> CrystalBall.canUseFeature(playerID, new ResourceLocation(id, s))).collect(Collectors.toList());
            if(!filtered.isEmpty()) {
                filtered.add(0, "none");
                ret.put(id, filtered);
            }
        });
        return ret;
    }
}
