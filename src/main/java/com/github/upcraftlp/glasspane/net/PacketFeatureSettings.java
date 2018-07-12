package com.github.upcraftlp.glasspane.net;

import com.github.upcraftlp.glasspane.vanity.CrystalBall;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketFeatureSettings implements IMessageHandler<PacketFeatureSettings, IMessage>, IMessage {

    private NBTTagList featureList;

    public PacketFeatureSettings() {
        //NO-OP
    }

    public PacketFeatureSettings(NBTTagList featureList) {
        this();
        this.featureList = featureList;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.featureList = new NBTTagList();
        for(int i = 0; i < buf.readInt(); i++) {
            this.featureList.appendTag(ByteBufUtils.readTag(buf));
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        int count = this.featureList.tagCount();
        buf.writeInt(count);
        for(int i = 0; i < count; i++) {
            ByteBufUtils.writeTag(buf, this.featureList.getCompoundTagAt(i));
        }
    }

    @Override
    public IMessage onMessage(PacketFeatureSettings message, MessageContext ctx) {
        CrystalBall.handleFeatureUpdates(message.featureList, ctx.getServerHandler().player);
        return null;
    }
}
