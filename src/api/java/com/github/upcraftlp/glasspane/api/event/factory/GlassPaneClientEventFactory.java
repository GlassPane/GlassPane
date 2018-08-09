package com.github.upcraftlp.glasspane.api.event.factory;

import com.github.upcraftlp.glasspane.api.event.client.RegisterRenderLayerEvent;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GlassPaneClientEventFactory {

    public static void onRegisterLayers(Render render) {
        if(render instanceof RenderLivingBase) MinecraftForge.EVENT_BUS.post(new RegisterRenderLayerEvent((RenderLivingBase) render));
    }
}
