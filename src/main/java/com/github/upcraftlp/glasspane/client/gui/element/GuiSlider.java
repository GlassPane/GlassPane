package com.github.upcraftlp.glasspane.client.gui.element;

import com.github.upcraftlp.glasspane.api.client.gui.GuiConstants;
import com.github.upcraftlp.glasspane.api.client.gui.IGuiElement;
import com.github.upcraftlp.glasspane.api.color.IColorPalette;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.function.Consumer;

public class GuiSlider extends Gui implements IGuiElement {

    public final int componentId;
    private final Consumer<Float> callback;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private int borderColor;
    private int sliderColorStart;
    private int sliderColorEnd;
    private int cursorColor;
    private boolean drawBackground;
    private float value;

    public GuiSlider(int componentId, Consumer<Float> callback, int x, int y, int width, int height, IColorPalette colors) {
        this.componentId = componentId;
        this.callback = callback;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.drawBackground = true;
        this.sliderColorStart = colors.getTextColor();
        this.sliderColorEnd = colors.getTextColor();
        this.cursorColor = colors.getAccentColor();
        this.borderColor = colors.getBorderColor();
    }

    @Override
    public void drawElement() {
        if(this.shouldDrawBackground()) {
            drawRect(this.x - 1, this.y - 1, this.x + this.width + 1, this.y + this.height + 1, this.getBorderColor());

            GlStateManager.pushMatrix();
            GlStateManager.disableTexture2D();
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
            {
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder vertexBuffer = tessellator.getBuffer();

                vertexBuffer.setTranslation(this.x, this.y, 0);
                vertexBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
                {
                    Color start = new Color(this.getSliderColorStart());
                    Color end = new Color(this.getSliderColorEnd());
                    vertexBuffer.pos(0, 0, 0).color(start.getRed(), start.getGreen(), start.getBlue(), start.getAlpha()).endVertex();
                    vertexBuffer.pos(0, this.height, 0).color(start.getRed(), start.getGreen(), start.getBlue(), start.getAlpha()).endVertex();
                    vertexBuffer.pos(this.width, this.height, 0).color(end.getRed(), end.getGreen(), end.getBlue(), end.getAlpha()).endVertex();
                    vertexBuffer.pos(this.width, 0, 0).color(end.getRed(), end.getGreen(), end.getBlue(), end.getAlpha()).endVertex();
                }
                vertexBuffer.setTranslation(0, 0, 0);
                tessellator.draw();
            }
            GlStateManager.enableTexture2D();
            GlStateManager.popMatrix();
        }
        drawRect(this.x + (int) ((this.width - 1) * this.value), this.y - 2, this.x + (int) ((this.width - 1) * this.value) + 1, this.y + this.height + 2, this.getCursorColor());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(mouseButton == GuiConstants.LEFT_MOUSE_BUTTON) this.trackMouse(mouseX, mouseY);
    }

    private void trackMouse(int mouseX, int mouseY) {
        if(mouseX >= this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height) {
            this.setValue((mouseX - this.x) / (float) (this.width - 1));
        }
    }

    @Override
    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if(clickedMouseButton == GuiConstants.LEFT_MOUSE_BUTTON) this.trackMouse(mouseX, mouseY);
    }

    @Override
    public void handleMouseInput() {
        int wheel = Mouse.getEventDWheel();
        if(wheel < 0) {
            this.setValue(this.getValue() + 0.01F);
        }
        else if(wheel > 0) {
            this.setValue(this.getValue() - 0.01F);
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        switch(keyCode) {
            case Keyboard.KEY_UP:
            case Keyboard.KEY_W:
            case Keyboard.KEY_RIGHT:
            case Keyboard.KEY_D:
                this.setValue(this.getValue() + 0.05F);
                break;
            case Keyboard.KEY_DOWN:
            case Keyboard.KEY_S:
            case Keyboard.KEY_LEFT:
            case Keyboard.KEY_A:
                this.setValue(this.getValue() - 0.05F);
                break;
        }
    }

    @Override
    public Rectangle getSize() {
        return new Rectangle(x, y, width, height);
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value, boolean updateCallback) {
        this.value = MathHelper.clamp(value, 0.0F, 1.0F);
        if(updateCallback) this.callback.accept(this.value);
    }

    public void setValue(float value) {
        setValue(value, true);
    }

    public void setSliderColorStart(int color) {
        this.sliderColorStart = color;
    }

    public boolean shouldDrawBackground() {
        return drawBackground;
    }

    public void setDrawBackground(boolean drawBackground) {
        this.drawBackground = drawBackground;
    }

    public int getSliderColorStart() {
        return sliderColorStart;
    }

    public int getCursorColor() {
        return cursorColor;
    }

    public void setCursorColor(int cursorColor) {
        this.cursorColor = cursorColor;
    }

    public int getSliderColorEnd() {
        return sliderColorEnd;
    }

    public void setSliderColorEnd(int sliderColorEnd) {
        this.sliderColorEnd = sliderColorEnd;
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }
}
