package com.github.upcraftlp.glasspane.client.gui;

import com.github.upcraftlp.glasspane.api.gui.EnumGuiBackgroundType;
import com.github.upcraftlp.glasspane.api.gui.IGuiCustomizableBackground;
import com.github.upcraftlp.glasspane.config.Lens;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import javax.annotation.Nullable;

public class GuiScreenCustomBackground extends GuiScreen implements IGuiCustomizableBackground {

    private final Gui parentScreen;
    protected int x, y, width, height;

    public GuiScreenCustomBackground(@Nullable Gui parent) {
        ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        this.width = scaledResolution.getScaledWidth();
        this.height = scaledResolution.getScaledHeight();
        this.parentScreen = parent;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public EnumGuiBackgroundType getBackgroundType() {
        return Lens.client.guiBackgroundType;
    }

    @Override
    public void drawWorldBackground(int tint) {
        if (this.mc.world != null)
        {
            this.drawGradientRect(this.x, this.y, this.x + this.width, this.y + this.height, -1072689136, -804253680);
        }
        else
        {
            this.drawBackground(tint);
        }
    }

    @Override
    public void drawBackground(int tint) {
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        this.mc.getTextureManager().bindTexture(this.getBackgroundType().getTextureLocation());
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        float f = 32.0F;
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos(this.x, this.y + this.height, 0.0D).tex(0.0D, this.height / 32.0F + tint).color(64, 64, 64, 255).endVertex();
        bufferbuilder.pos(this.x + this.width, this.y + this.height, 0.0D).tex(this.width / 32.0F, this.height / 32.0F + tint).color(64, 64, 64, 255).endVertex();
        bufferbuilder.pos(this.x + this.width, this.y, 0.0D).tex(this.width / 32.0F, tint).color(64, 64, 64, 255).endVertex();
        bufferbuilder.pos(this.x, this.y, 0.0D).tex(0.0D, (double)tint).color(64, 64, 64, 255).endVertex();
        tessellator.draw();
    }

    public void setGuiSize(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.setGuiSize(width, height);
    }

    public void setGuiSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

}
