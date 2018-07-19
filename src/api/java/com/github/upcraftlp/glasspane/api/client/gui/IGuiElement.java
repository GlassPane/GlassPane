package com.github.upcraftlp.glasspane.api.client.gui;

import java.awt.*;
import java.io.IOException;

public interface IGuiElement {

    void drawElement();
    Rectangle getSize();

    //keyboard
    default void handleKeyboardInput() throws IOException {}
    default void keyTyped(char typedChar, int keyCode) throws IOException {}

    //mouse
    default void handleMouseInput() throws IOException {}
    default void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {}
    default void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {}
    default void mouseReleased(int mouseX, int mouseY, int state) {}
}
