package com.github.upcraftlp.glasspane.proxy;

import com.github.upcraftlp.glasspane.api.client.SkinnableMapping;
import com.github.upcraftlp.glasspane.api.client.resources.DefaultFolderResourcePack;
import com.github.upcraftlp.glasspane.api.event.factory.GlassPaneClientEventFactory;
import com.github.upcraftlp.glasspane.api.proxy.IProxy;
import com.github.upcraftlp.glasspane.client.ClientUtil;
import com.github.upcraftlp.glasspane.client.render.layer.LayerCapeCustom;
import com.github.upcraftlp.glasspane.guide.GuideHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@SideOnly(Side.CLIENT)
public class ClientProxy implements IProxy {

    @SuppressWarnings("unchecked")
    public ClientProxy() {
        //do this as early as possible to make MC aware of the resource pack!
        ((List<IResourcePack>) ReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "defaultResourcePacks", "field_110449_ao", "ap")).add(new DefaultFolderResourcePack(ClientUtil.RESOURCES_DIR, "GlassPane custom Resources"));
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        GuideHandler.init(event);
        SkinnableMapping.addMapping(LayerCapeCustom.GLASSPANE_CAPE_0, 1);
        SkinnableMapping.addMapping(LayerCapeCustom.GLASSPANE_CAPE_1, 2);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        //players
        Minecraft.getMinecraft().getRenderManager().getSkinMap().values().forEach(GlassPaneClientEventFactory::onRegisterLayers);

        //other living entities
        Minecraft.getMinecraft().getRenderManager().entityRenderMap.entrySet().stream().map(Map.Entry::getValue).forEach(GlassPaneClientEventFactory::onRegisterLayers);
    }

    @Override
    public int getSelectedSkin(String skinID, EntityPlayer player) {
        return ClientUtil.getPersistentData().getCompoundTag("skins").getInteger(skinID);
    }
}
