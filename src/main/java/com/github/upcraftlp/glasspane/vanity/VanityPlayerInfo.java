package com.github.upcraftlp.glasspane.vanity;

import com.github.upcraftlp.glasspane.util.JsonUtil;
import com.google.gson.annotations.SerializedName;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class VanityPlayerInfo {

    public VanityPlayerInfo() {
        //NO-OP
    }

    private UUID uuid = new UUID(0, 0);

    @SerializedName("name")
    private String username = "unknown";

    private @Nullable String rank = null;

    @SerializedName("unlocked")
    private ResourceLocation[] features = new ResourceLocation[0];

    public UUID getUniqueID() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }

    @Nullable
    public String getRank() {
        return rank;
    }

    public List<ResourceLocation> getFeatures() {
        return Collections.unmodifiableList(Arrays.asList(features));
    }

    public boolean hasFeature(ResourceLocation feature) {
        return getFeatures().contains(feature);
    }

    @Override
    public String toString() {
        return JsonUtil.GSON.toJson(this, VanityPlayerInfo.class);
    }

    @SuppressWarnings("ConstantConditions") //Playerlist#getPlayerByUUID() CAN in fact return null!
    public boolean hasPlayer() {
        return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(this.getUniqueID()) != null;
    }
}
