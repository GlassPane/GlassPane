package com.github.upcraftlp.glasspane.client.gui;

import com.github.upcraftlp.glasspane.api.gui.IColorPalette;
import net.minecraft.client.gui.Gui;

public class GuiColorPicker extends Gui {

    private static final int BORDER_SIZE = 1;

    private final int componentId;
    private final IGuiColorPickerCallback callback;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final IColorPalette colors;

    public GuiColorPicker(int componentId, IGuiColorPickerCallback callback, int x, int y, int width, int height, IColorPalette colorPalette) {
        this.componentId = componentId;
        this.callback = callback;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        colors = colorPalette;
    }

    public void drawElement() {
        drawRect(this.x, this.y, this.x + this.width, this.y + this.height, this.colors.getBorderColor());
        drawRect(this.x + BORDER_SIZE, this.y + BORDER_SIZE, this.x + this.width - BORDER_SIZE * 2, this.y + this.height - BORDER_SIZE * 2, this.colors.getFillColor());
        //TODO actual color picker texture (generate one or use premade texture?)
    }

    public interface IGuiColorPickerCallback {

        void selectColor(int color);
    }
}
