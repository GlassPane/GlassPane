package com.github.upcraftlp.glasspane.vanity;

import com.github.upcraftlp.glasspane.api.util.serialization.JsonPostProcessable;
import com.github.upcraftlp.glasspane.util.JsonUtil;
import com.google.gson.annotations.SerializedName;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.Arrays;
import java.util.UUID;

public class VanityPlayerInfo implements JsonPostProcessable {

    @SerializedName("id")
    private UUID uuid = new UUID(0, 0);
    @SerializedName("name")
    private String username = "unknown";
    @SerializedName("unlocked")
    private ResourceLocation[] features = new ResourceLocation[0];
    private transient GameProfile gameProfile;

    public VanityPlayerInfo() {
        //NO-OP
    }

    public GameProfile getGameProfile() {
        return gameProfile;
    }

    public String getUsername() {
        return username;
    }

    public boolean hasFeature(ResourceLocation feature) {
        return Arrays.asList(features).contains(feature);
    }

    @Override
    public String toString() {
        return JsonUtil.GSON.toJson(this);
    }

    @SuppressWarnings("ConstantConditions") //Playerlist#getPlayerByUUID() CAN in fact return null!
    public boolean hasPlayer() {
        return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(this.getUniqueID()) != null;
    }

    public UUID getUniqueID() {
        return uuid;
    }

    @Override
    public void jsonPostProcess() {
        GameProfile gp = new GameProfile(uuid, null);
        MinecraftSessionService sessionService = FMLCommonHandler.instance().getSide().isClient() ? Minecraft.getMinecraft().getSessionService() : FMLCommonHandler.instance().getMinecraftServerInstance().getMinecraftSessionService();
        this.gameProfile = sessionService.fillProfileProperties(gp, true);
        this.username = this.gameProfile.getName();
    }
}
