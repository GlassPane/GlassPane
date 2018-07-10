package com.github.upcraftlp.glasspane.client.gui;

import net.minecraft.client.gui.GuiScreen;

public class GuiColorPicker extends GuiScreen {

    public GuiColorPicker(GuiScreen parentScreen) {

    }

    public interface GuiColorPickerCallback {

        void selectColor(int color);
    }
}
