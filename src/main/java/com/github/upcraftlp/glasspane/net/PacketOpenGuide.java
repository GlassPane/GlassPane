package com.github.upcraftlp.glasspane.net;

import com.github.upcraftlp.glasspane.guide.GuideHandler;
import com.github.upcraftlp.glasspane.registry.GlassPaneGuideRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketOpenGuide implements IMessage, IMessageHandler<PacketOpenGuide, IMessage>  {

    private ResourceLocation page;
    private ResourceLocation guide;

    @Override
    public void fromBytes(ByteBuf buf) {
        this.guide = new ResourceLocation(ByteBufUtils.readUTF8String(buf));
        this.page = new ResourceLocation(ByteBufUtils.readUTF8String(buf));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.guide.toString());
        ByteBufUtils.writeUTF8String(buf, this.page.toString());
    }

    @Override
    public IMessage onMessage(PacketOpenGuide message, MessageContext ctx) {
        GuideHandler.openPage(GlassPaneGuideRegistry.getGuide(message.guide), message.page);
        return null;
    }
}
