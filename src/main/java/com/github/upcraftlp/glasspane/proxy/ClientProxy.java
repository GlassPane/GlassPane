package com.github.upcraftlp.glasspane.proxy;

import com.github.upcraftlp.glasspane.api.net.NetworkHandler;
import com.github.upcraftlp.glasspane.api.proxy.IProxy;
import com.github.upcraftlp.glasspane.guide.GuideHandler;
import com.github.upcraftlp.glasspane.net.PacketRequestFeatureSettings;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("unused")
@SideOnly(Side.CLIENT)
public class ClientProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        NetworkHandler.INSTANCE.registerMessage(PacketRequestFeatureSettings.Handler.class, PacketRequestFeatureSettings.class, NetworkHandler.getNextPacketID(), Side.CLIENT);
        GuideHandler.init(event);
    }
}
