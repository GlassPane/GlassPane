package com.github.upcraftlp.glasspane.api.vanity;

import com.github.upcraftlp.glasspane.util.JsonUtil;
import com.google.gson.annotations.SerializedName;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.Arrays;
import java.util.UUID;

public class VanityPlayerInfo {

    @SerializedName("id")
    private UUID uuid = new UUID(0, 0);
    @SerializedName("name")
    private String username = "unknown";
    @SerializedName("unlocked")
    private ResourceLocation[] features = new ResourceLocation[0];

    public VanityPlayerInfo() {
        //NO-OP
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
        return (FMLCommonHandler.instance().getSide().isClient() && Minecraft.getMinecraft().getSession().getProfile().getId().equals(this.getUniqueID())) || FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(this.getUniqueID()) != null;
    }

    public UUID getUniqueID() {
        return uuid;
    }
}
