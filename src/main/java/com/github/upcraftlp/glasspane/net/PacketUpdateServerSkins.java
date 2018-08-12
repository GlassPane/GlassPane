package com.github.upcraftlp.glasspane.net;

import com.github.upcraftlp.glasspane.vanity.CrystalBall;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Arrays;

public class PacketUpdateServerSkins implements IMessage, IMessageHandler<PacketUpdateServerSkins, IMessage> {

    private String[] list;

    public PacketUpdateServerSkins() {
        //NO-OP
    }

    public PacketUpdateServerSkins(NBTTagList nbt) {
        this();
        this.list = new String[nbt.tagCount()];
        for(int i = 0; i < this.list.length; i++) this.list[i] = nbt.getStringTagAt(i);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.list = new String[buf.readInt()];
        for(int i = 0; i < this.list.length; i++) this.list[i] = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.list.length);
        Arrays.stream(this.list).forEach(s -> ByteBufUtils.writeUTF8String(buf, s));
    }

    @Override
    public IMessage onMessage(PacketUpdateServerSkins message, MessageContext ctx) {
        CrystalBall.setPlayerSettings(ctx.getServerHandler().player.getUniqueID(), Arrays.asList(message.list));
        return null;
    }
}
