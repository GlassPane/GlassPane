package com.github.upcraftlp.glasspane.vanity;

import com.github.upcraftlp.glasspane.GlassPane;
import com.github.upcraftlp.glasspane.api.net.NetworkHandler;
import com.github.upcraftlp.glasspane.net.PacketRequestFeatureSettings;
import com.github.upcraftlp.glasspane.util.JsonUtil;
import com.google.gson.reflect.TypeToken;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = GlassPane.MODID)
public class CrystalBall {

    //TODO UUID lookup by username
    //TODO store externally
    private static final String VANITY_URL = "https://gist.githubusercontent.com/UpcraftLP/f2fd5c5e783e3fc23b85c5184fcd4488/raw/vanity_features.json";

    private static final Map<UUID, VanityPlayerInfo> VANITY_PLAYER_INFO = new HashMap<>();

    public static boolean canUseFeature(Entity entity, ResourceLocation feature) {
        VanityPlayerInfo info = VANITY_PLAYER_INFO.getOrDefault(entity.getUniqueID(), null);
        return info != null && info.hasFeature(feature);
    }

    public static boolean isFeatureEnabled(Entity entity, String feature) {
        //TODO query features from server!
        return false;
    }

    public static boolean canUseFeature(Entity entity, String feature) {
        return canUseFeature(entity, new ResourceLocation(feature));
    }

    public static boolean hasVanityFeatures(EntityPlayer player) {
        return VANITY_PLAYER_INFO.containsKey(player.getUniqueID());
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if(event.player instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) event.player;
            VANITY_PLAYER_INFO.clear();
            try {
                String json = IOUtils.toString(new URL(VANITY_URL), StandardCharsets.UTF_8);
                Type type = new TypeToken<List<VanityPlayerInfo>>(){}.getType();
                List<VanityPlayerInfo> playerInfo = JsonUtil.GSON.fromJson(json, type);
                playerInfo.stream().filter(VanityPlayerInfo::hasPlayer).collect(Collectors.toList()).forEach(info -> VANITY_PLAYER_INFO.put(info.getUniqueID(), info));
            } catch(IOException e) {
                GlassPane.getLogger().error("unable to update vanity information!", e);
            }
            if(hasVanityFeatures(player)) {
                NetworkHandler.INSTANCE.sendTo(new PacketRequestFeatureSettings(VANITY_PLAYER_INFO.get(player.getUniqueID())), player);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        VANITY_PLAYER_INFO.remove(event.player.getUniqueID());
    }

    /**
     * used to update the feature state on the client or server
     */
    public static void handleFeatureUpdates(NBTTagList list, EntityPlayer player) {
        boolean forceUpdate = FMLCommonHandler.instance().getSide().isClient(); //always let the server override client settings

    }

}
