package com.github.upcraftlp.glasspane.client.gui;

import com.github.upcraftlp.glasspane.api.gui.EnumGuiBackgroundType;
import com.github.upcraftlp.glasspane.api.gui.IGuiCustomizableBackground;
import com.github.upcraftlp.glasspane.api.guide.IGuideBook;
import com.github.upcraftlp.glasspane.config.Lens;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Objects;

public class GuiScreenGuide extends GuiScreen implements IGuiCustomizableBackground {

    private final IGuideBook book;
    private final ResourceLocation selectedPage;

    public GuiScreenGuide(IGuideBook book, @Nullable ResourceLocation selectedPage) {
        this.book = book;
        this.selectedPage = selectedPage;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        //TODO debug!
        drawCenteredString(fontRenderer, this.book.getGuideName().toString(), width / 2, 14, this.getBackgroundType().colors.getAccentColor());
        drawCenteredString(fontRenderer, Objects.toString(this.selectedPage), width / 2, 14 + fontRenderer.FONT_HEIGHT + 2, this.getBackgroundType().colors.getTextColor());
    }

    public EnumGuiBackgroundType getBackgroundType() {
        return Lens.client.guiBackgroundType;
    }

    @Override
    public void drawWorldBackground(int tint) {
        EnumGuiBackgroundType background = this.getBackgroundType();
        if (this.mc.world != null) {
            this.drawGradientRect(0, 0, this.width, this.height, background.worldBackgroundColorStart, background.worldBackgroundColorEnd);
        }
        else this.drawBackground(tint);
        drawRect(10, 10, width - 10, height - 10, background.colors.getBorderColor());
        drawRect(12, 12, width - 12, height - 12, background.colors.getFillColor());
        //TODO draw custom texture!
    }

    @Override
    public void drawBackground(int tint) {
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        this.mc.getTextureManager().bindTexture(this.getBackgroundType().backgroundTexture);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos(0.0D, (double)this.height, 0.0D).tex(0.0D, (double)((float)this.height / 32.0F + (float)tint)).color(64, 64, 64, 255).endVertex();
        bufferbuilder.pos((double)this.width, (double)this.height, 0.0D).tex((double)((float)this.width / 32.0F), (double)((float)this.height / 32.0F + (float)tint)).color(64, 64, 64, 255).endVertex();
        bufferbuilder.pos((double)this.width, 0.0D, 0.0D).tex((double)((float)this.width / 32.0F), (double)tint).color(64, 64, 64, 255).endVertex();
        bufferbuilder.pos(0.0D, 0.0D, 0.0D).tex(0.0D, (double)tint).color(64, 64, 64, 255).endVertex();
        tessellator.draw();
    }
}
