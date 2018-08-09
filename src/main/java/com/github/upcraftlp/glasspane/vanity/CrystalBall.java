package com.github.upcraftlp.glasspane.vanity;

import com.github.upcraftlp.glasspane.GlassPane;
import com.github.upcraftlp.glasspane.api.util.ForgeUtils;
import com.github.upcraftlp.glasspane.api.vanity.VanityPlayerInfo;
import com.github.upcraftlp.glasspane.util.JsonUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = GlassPane.MODID)
public class CrystalBall {

    //TODO UUID lookup by username
    //TODO store externally
    private static final String VANITY_URL = "https://gist.githubusercontent.com/UpcraftLP/f2fd5c5e783e3fc23b85c5184fcd4488/raw/vanity_features.json";

    private static final Map<UUID, VanityPlayerInfo> VANITY_PLAYER_INFO = new HashMap<>();

    public static boolean canUseFeature(Entity entity, String feature) {
        return canUseFeature(entity, new ResourceLocation(feature));
    }

    public static boolean canUseFeature(Entity entity, ResourceLocation feature) {
        if(entity instanceof EntityPlayer) {
            VanityPlayerInfo info = VANITY_PLAYER_INFO.getOrDefault(entity.getUniqueID(), null);
            return info != null && info.hasFeature(feature);
        } else return feature.getPath().equalsIgnoreCase("none");
    }

    public static boolean hasVanityFeatures(EntityPlayer player) {
        return VANITY_PLAYER_INFO.containsKey(player.getUniqueID());
    }

    public static void updatePlayerInfo() {
        Thread t = new Thread(() -> {
            try {
                File f = new File(ForgeUtils.MOD_RESOURCES, "vanity_overrides.json");
                String json;
                if(f.exists() && !f.isDirectory()) {
                    GlassPane.getDebugLogger().info("vanity file override detected, skipping web request and loading from file!");
                    json = FileUtils.readFileToString(f, StandardCharsets.UTF_8);
                } else {
                    GlassPane.getLogger().info("fetching information from GitHub...");
                    json = IOUtils.toString(new URL(VANITY_URL), StandardCharsets.UTF_8);
                }
                VanityPlayerInfo[] playerInfo = JsonUtil.GSON.fromJson(json, VanityPlayerInfo[].class);
                synchronized(VANITY_PLAYER_INFO) {
                    VANITY_PLAYER_INFO.clear();
                    Arrays.stream(playerInfo).collect(Collectors.toList()).forEach(info -> VANITY_PLAYER_INFO.put(info.getUniqueID(), info));
                }
                GlassPane.getLogger().info("successfully loaded data!");
            } catch(IOException e) {
                GlassPane.getLogger().error("unable to update vanity information!", e);
            }
        });
        t.setName(GlassPane.MODNAME + "/Specials");
        t.start();
    }
}
