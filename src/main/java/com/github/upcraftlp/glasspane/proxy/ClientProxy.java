package com.github.upcraftlp.glasspane.proxy;

import com.github.upcraftlp.glasspane.api.client.resources.DefaultFolderResourcePack;
import com.github.upcraftlp.glasspane.api.event.RegisterRenderLayerEvent;
import com.github.upcraftlp.glasspane.api.proxy.IProxy;
import com.github.upcraftlp.glasspane.client.ClientUtil;
import com.github.upcraftlp.glasspane.guide.GuideHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
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
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        //players
        Minecraft.getMinecraft().getRenderManager().getSkinMap().values().forEach(ClientProxy::onRegisterLayers);

        //other living entities
        Minecraft.getMinecraft().getRenderManager().entityRenderMap.entrySet().stream().map(Map.Entry::getValue).forEach(ClientProxy::onRegisterLayers);
    }

    private static void onRegisterLayers(Render render) {
        if(render instanceof RenderLivingBase) MinecraftForge.EVENT_BUS.post(new RegisterRenderLayerEvent((RenderLivingBase) render));
    }

    @Override
    public int getSelectedSkin(ResourceLocation skin, EntityPlayer player) {
        return ClientUtil.getPersistentData().getCompoundTag("skins").getInteger(skin.getNamespace());
    }
}
