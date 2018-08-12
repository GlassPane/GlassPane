package com.github.upcraftlp.glasspane.proxy;

import com.github.upcraftlp.glasspane.api.client.resources.DefaultFolderResourcePack;
import com.github.upcraftlp.glasspane.api.event.factory.GlassPaneClientEventFactory;
import com.github.upcraftlp.glasspane.api.proxy.IProxy;
import com.github.upcraftlp.glasspane.client.ClientUtil;
import com.github.upcraftlp.glasspane.guide.GuideHandler;
import com.github.upcraftlp.glasspane.vanity.CrystalBall;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
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
        Minecraft.getMinecraft().getRenderManager().getSkinMap().values().forEach(GlassPaneClientEventFactory::onRegisterLayers);

        //other living entities
        Minecraft.getMinecraft().getRenderManager().entityRenderMap.entrySet().stream().map(Map.Entry::getValue).forEach(GlassPaneClientEventFactory::onRegisterLayers);
    }

    @Nullable
    @Override
    public ResourceLocation getSelectedSkin(String skinID, EntityPlayer player) {
        if(player != Minecraft.getMinecraft().player) return CrystalBall.getSelectedFeature(skinID, player);
        NBTTagList tagList = ClientUtil.getPersistentData().getTagList("skins", Constants.NBT.TAG_STRING);
        for(int i = 0; i < tagList.tagCount(); i++) {
            ResourceLocation rl = new ResourceLocation(tagList.getStringTagAt(i));
            if(rl.getNamespace().equals(skinID)) return rl.getPath().equals("none") ? null : rl;
        }
        return null;
    }
}
