package com.github.upcraftlp.glasspane.proxy;

import com.github.upcraftlp.glasspane.GlassPane;
import com.github.upcraftlp.glasspane.api.client.resources.DefaultFolderResourcePack;
import com.github.upcraftlp.glasspane.api.net.NetworkHandler;
import com.github.upcraftlp.glasspane.api.proxy.IProxy;
import com.github.upcraftlp.glasspane.client.ClientUtil;
import com.github.upcraftlp.glasspane.guide.GuideHandler;
import com.github.upcraftlp.glasspane.net.PacketRequestFeatureSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourcePack;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("unused")
@SideOnly(Side.CLIENT)
public class ClientProxy implements IProxy {

    @SuppressWarnings("unchecked")
    public ClientProxy() {
        //do this as early as possible to make MC aware of the resource pack!
        try {
            if(!ClientUtil.RESOURCES_DIR.exists()) FileUtils.forceMkdir(ClientUtil.RESOURCES_DIR);
            ((List<IResourcePack>) ReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "defaultResourcePacks", "field_110449_ao", "ap")).add(new DefaultFolderResourcePack(ClientUtil.RESOURCES_DIR, "GlassPane custom Resources"));
        }
        catch(IOException e) {
            GlassPane.getLogger().warn("unable to initialize the resources directory!", e);
        }
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        NetworkHandler.INSTANCE.registerMessage(PacketRequestFeatureSettings.Handler.class, PacketRequestFeatureSettings.class, NetworkHandler.getNextPacketID(), Side.CLIENT);
        GuideHandler.init(event);
    }
}
