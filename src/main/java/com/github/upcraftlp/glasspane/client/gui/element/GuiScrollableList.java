package com.github.upcraftlp.glasspane.client.gui.element;

import com.github.upcraftlp.glasspane.api.client.gui.GuiConstants;
import com.github.upcraftlp.glasspane.api.client.gui.IGuiElement;
import com.github.upcraftlp.glasspane.api.color.IColorPalette;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class GuiScrollableList extends Gui implements IGuiElement {

    //TODO make editable
    //TODO scroll indicator (also for dragging with the mouse)

    protected int TEXT_MARGIN = 4;
    protected int TEXT_HEIGHT = 2;
    protected int SCROLLBAR_WIDTH = 4;
    protected int x, y, width, height, currentIndex, maxIndex, maxLinesOnScreen;
    protected final List<String> textLines = Lists.newArrayList();
    protected static final Minecraft mc = Minecraft.getMinecraft();
    protected FontRenderer fontRenderer;
    protected int scrollFactor = 2;
    protected boolean isMouseDraggingScrollBar;
    protected int prevMouseY;
    protected IColorPalette colors;

    public GuiScrollableList(FontRenderer fontRenderer, IColorPalette colorPalette, int x, int y, int width, int height, String... text) {
        this.fontRenderer = fontRenderer;
        this.colors = colorPalette;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        currentIndex = 0;
        maxLinesOnScreen = (height - TEXT_MARGIN * 2) / (fontRenderer.FONT_HEIGHT + TEXT_HEIGHT);
        maxIndex = Math.max(0, textLines.size() - maxLinesOnScreen);
        Arrays.stream(text).forEachOrdered(this::addLines);
    }

    public void addLines(int marginSpaces, String... text) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < marginSpaces; i++) builder.append(" ");
        final String indent = builder.toString();
        int maxWidth = width - TEXT_MARGIN * 2 - fontRenderer.getStringWidth(indent) - SCROLLBAR_WIDTH;
        Arrays.stream(text).forEachOrdered(line -> fontRenderer.listFormattedStringToWidth(line, maxWidth).forEach(s -> textLines.add(indent + s)));
        maxIndex = Math.max(0, textLines.size() - maxLinesOnScreen);
        this.currentIndex = MathHelper.clamp(currentIndex, 0, maxIndex);
    }

    public void addLines(String... text) {
        addLines(0, text);
    }

    @Override
    public void handleMouseInput() {
        int amount = Mouse.getEventDWheel();
        if(amount != 0) {
            if (amount > 0) amount = -1;
            else amount = 1;
            scroll(amount);
        }
    }

    @Override
    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if(isMouseDraggingScrollBar) {
            int diff = mouseY - prevMouseY;

            int scrollbarHeight = (int) (((float) maxLinesOnScreen) / textLines.size() * height);
            int maxDiff = scrollbarHeight / (textLines.size() - maxLinesOnScreen);

            if(diff > maxDiff) {
                scroll(1);
            }
            else if(diff < -maxDiff) {
                scroll(-1);
            }
            prevMouseY = mouseY;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        if(this.isMouseDraggingScrollBar) this.isMouseDraggingScrollBar = false;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(mouseButton != GuiConstants.LEFT_MOUSE_BUTTON) return;
        int scrollbarX = x + width - SCROLLBAR_WIDTH;
        int scrollbarHeight = (int) (((float) maxLinesOnScreen) / textLines.size() * (height - 2));
        int scrollOffset = (int) (((float) currentIndex) / (textLines.size() - maxLinesOnScreen) * (height - scrollbarHeight));
        int scrollbarY = y + scrollOffset;
        if(mouseX >= scrollbarX && x <= scrollbarX + SCROLLBAR_WIDTH) {
            if(mouseY >= scrollbarY && mouseY <= scrollbarY + scrollbarHeight) {
                isMouseDraggingScrollBar = true;
                prevMouseY = mouseY;
            }
            else {
                //TODO insta-scroll
            }
        }
    }

    @Override
    public void drawElement() {
        drawRect(x - 2, y - 2, x + width + 2, y + height + 2, this.colors.getBorderColor());
        drawRect(x - 1, y - 1, x + width + 1, y + height + 1, this.colors.getFillColor());

        for(int i = currentIndex; i < Math.min(maxLinesOnScreen + currentIndex, textLines.size()); i++) {
            this.fontRenderer.drawString(textLines.get(i), x + TEXT_MARGIN, y + TEXT_MARGIN + (fontRenderer.FONT_HEIGHT + TEXT_HEIGHT) * (i - currentIndex), this.colors.getTextColor());
        }

        int scrollbarX = x + width - SCROLLBAR_WIDTH;
        int scrollbarHeight = (int) (((float) maxLinesOnScreen) / textLines.size() * (height - 2));
        int scrollOffset = (int) (((float) currentIndex) / (textLines.size() - maxLinesOnScreen) * (height - scrollbarHeight));
        int scrollbarY = y + scrollOffset;

        drawRect(scrollbarX, scrollbarY, scrollbarX + SCROLLBAR_WIDTH, scrollbarY + scrollbarHeight, this.colors.getAccentColor());
    }

    @Override
    public Rectangle getSize() {
        return null;
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        switch(keyCode) {
            case Keyboard.KEY_S:
            case Keyboard.KEY_DOWN:
                this.currentIndex = MathHelper.clamp(currentIndex + scrollFactor, 0, maxIndex);
                break;
            case Keyboard.KEY_W:
            case Keyboard.KEY_UP:
                this.currentIndex = MathHelper.clamp(currentIndex - scrollFactor, 0, maxIndex);
                break;
        }
    }

    protected void scroll(int rawAmount) {
        this.currentIndex = MathHelper.clamp(currentIndex + (rawAmount * scrollFactor), 0, maxIndex);
    }
}
