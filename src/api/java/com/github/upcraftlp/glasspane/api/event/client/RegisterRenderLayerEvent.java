package com.github.upcraftlp.glasspane.api.event.client;

import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public class RegisterRenderLayerEvent extends Event {

    private final RenderLivingBase render;
    private final List<LayerRenderer> layers;

    @SuppressWarnings("unchecked")
    public RegisterRenderLayerEvent(RenderLivingBase render) {
        this.render = render;
        this.layers = ReflectionHelper.getPrivateValue(RenderLivingBase.class, render, "field_177097_h", "h", "layerRenderers");
    }

    public void addRenderLayer(LayerRenderer layer) {
        layers.add(layer);
    }

    public RenderLivingBase getRender() {
        return render;
    }

    public List<LayerRenderer> getLayers() {
        return layers;
    }
}
