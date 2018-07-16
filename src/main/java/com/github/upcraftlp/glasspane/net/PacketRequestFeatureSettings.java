package com.github.upcraftlp.glasspane.net;

import com.github.upcraftlp.glasspane.vanity.VanityPlayerInfo;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;

public class PacketRequestFeatureSettings implements IMessage {

    private ResourceLocation[] INFOS;

    public PacketRequestFeatureSettings() {
        //NO-OP
    }

    public PacketRequestFeatureSettings(VanityPlayerInfo playerInfo) {
        this();
        List<ResourceLocation> keys = playerInfo.getFeatures();
        INFOS = keys.toArray(new ResourceLocation[keys.size()]);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        INFOS = new ResourceLocation[buf.readInt()];
        for(int i = 0; i < INFOS.length; i++) {
            INFOS[i] = new ResourceLocation(ByteBufUtils.readUTF8String(buf));
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(INFOS.length);
        Arrays.stream(INFOS).forEach(key -> ByteBufUtils.writeUTF8String(buf, key.toString()));
    }

    @SideOnly(Side.CLIENT)
    public static class Handler implements IMessageHandler<PacketRequestFeatureSettings, PacketFeatureSettings> {

        @Override
        public PacketFeatureSettings onMessage(PacketRequestFeatureSettings message, MessageContext ctx) {
            //TODO set settings!


            return null;// new PacketFeatureSettings();
        }
    }
}
