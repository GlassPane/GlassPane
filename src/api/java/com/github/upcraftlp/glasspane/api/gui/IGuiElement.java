package com.github.upcraftlp.glasspane.api.client.gui;

import javax.annotation.Nullable;
import java.awt.*;

public interface IGuiElement {

    void drawElement();
    Rectangle getSize();
    default void updateScreen() {}

    @Nullable
    default Object getChildComponentByID(int id) {
        return null;
    }

    //keyboard
    default void handleKeyboardInput() {}
    default void keyTyped(char typedChar, int keyCode) {}

    //mouse
    default void handleMouseInput() {}
    default void mouseClicked(int mouseX, int mouseY, int mouseButton) {}
    default void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {}
    default void mouseReleased(int mouseX, int mouseY, int state) {}
}
