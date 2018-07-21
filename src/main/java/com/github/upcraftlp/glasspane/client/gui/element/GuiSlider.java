package com.github.upcraftlp.glasspane.client.gui.element;

import com.github.upcraftlp.glasspane.api.client.gui.IGuiElement;
import com.github.upcraftlp.glasspane.api.color.IColorPalette;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.function.Consumer;

public class GuiSlider extends Gui implements IGuiElement {

    //TODO proper slider textures (opengl?)
    //TODO react to mouse click/drag
    //TODO draw gradient left to right instead top to bottom

    public final int componentId;
    private final Consumer<Float> callback;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final int sliderWidth;
    private int sliderColorStart;
    private int sliderColorEnd;
    private int backgroundColor;
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
        this.sliderWidth = this.width - 2;
        this.drawBackground = true;
        this.sliderColorStart = colors.getTextColor();
        this.sliderColorEnd = colors.getTextColor();
        this.backgroundColor = colors.getFillColor();
        this.cursorColor = colors.getAccentColor();
    }

    @Override
    public void drawElement() {
        if(this.shouldDrawBackground()) {
            drawRect(this.x, this.y, this.x + this.width, this.y + this.height, this.getBackgroundColor());
        }
        drawGradientRect(this.x - 1, this.y - 1, this.x + this.width + 1, this.y + this.height + 1, this.getSliderColorStart(), this.getSliderColorEnd());
        drawRect(this.x + (int) (this.sliderWidth * this.value) - 1, this.y + this.height / 2 - 1, this.x + (int) (this.sliderWidth * this.value) + 1, this.y + this.height / 2 + 1, this.getCursorColor());
    }

    @Override
    public Rectangle getSize() {
        return new Rectangle(x, y, width, height);
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value, boolean updateCallback) {
        this.value = value;
        if(updateCallback) this.callback.accept(value);
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

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
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
}
