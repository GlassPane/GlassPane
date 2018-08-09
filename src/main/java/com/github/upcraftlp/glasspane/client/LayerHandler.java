package com.github.upcraftlp.glasspane.client;

import com.github.upcraftlp.glasspane.GlassPane;
import com.github.upcraftlp.glasspane.api.event.client.RegisterRenderLayerEvent;
import com.github.upcraftlp.glasspane.client.render.layer.LayerCapeCustom;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerCape;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = GlassPane.MODID, value = Side.CLIENT)
public class LayerHandler {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onRegisterLayers(RegisterRenderLayerEvent event) {
        event.getLayers().removeIf(layer -> layer instanceof LayerCape || layer instanceof LayerElytra);
        RenderLivingBase render = event.getRender();
        if(render instanceof RenderPlayer) event.addRenderLayer(new LayerCapeCustom((RenderPlayer) render));
        MinecraftForge.EVENT_BUS.unregister(LayerHandler.class);
    }

}
