package com.github.upcraftlp.glasspane.client.gui.element;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.List;

public class GuiScrollableList {

    //TODO make editable
    //TODO scroll indicator (also for dragging with the mouse)

    protected int TEXT_MARGIN = 4;
    protected int TEXT_HEIGHT = 2;
    protected int SCROLLBAR_WIDTH = 4;
    protected int x, y, width, height, currentIndex, maxIndex, maxLinesOnScreen;
    protected final List<String> textLines = Lists.newArrayList();
    protected static final Minecraft mc = Minecraft.getMinecraft();
    protected FontRenderer fontRenderer = mc.fontRenderer;
    protected static final int WHITE = 0xFFFFFFFF;
    protected int scrollFactor = 2;
    private int timePressed = 0;
    protected boolean isMouseDraggingScrollBar;
    protected int prevMouseY;

    public GuiScrollableList(int x, int y, int width, int height, List<String> text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        text.forEach(s -> addLines(0, s));

        currentIndex = 0;
        maxLinesOnScreen = (height - TEXT_MARGIN * 2) / (fontRenderer.FONT_HEIGHT + TEXT_HEIGHT);

        maxIndex = Math.max(0, textLines.size() - maxLinesOnScreen);
    }

    public void addLines(int marginSpaces, String... text) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < marginSpaces; i++) builder.append(" ");
        final String indent = builder.toString();
        int maxWidth = width - TEXT_MARGIN * 2 - fontRenderer.getStringWidth(indent) - SCROLLBAR_WIDTH;
        for (String line : text) {
            fontRenderer.listFormattedStringToWidth(line, maxWidth).forEach(s -> textLines.add(indent + s));
        }
        maxIndex = Math.max(0, textLines.size() - maxLinesOnScreen);
        this.currentIndex = MathHelper.clamp(currentIndex, 0, maxIndex);
    }

    public void handleMouseInput() {
        int amount = Mouse.getEventDWheel();
        if(amount != 0) {
            if (amount > 0) amount = -1;
            else amount = 1;
            scroll(amount);
        }
    }

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

    public void mouseReleased(int mouseX, int mouseY, int state) {
        if(this.isMouseDraggingScrollBar) this.isMouseDraggingScrollBar = false;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        int scrollbarX = x + width - SCROLLBAR_WIDTH;
        int scrollbarHeight = (int) (((float) maxLinesOnScreen) / textLines.size() * height);
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

    public void keyTyped(char typedChar, int keyCode) {
        int scrollAmount = 0;
        if(keyCode == Keyboard.KEY_DOWN) scrollAmount = 1;
        else if(keyCode == Keyboard.KEY_UP) scrollAmount = -1;
        scroll(scrollAmount);
    }

    protected void scroll(int rawAmount) {
        this.currentIndex = MathHelper.clamp(currentIndex + (rawAmount * scrollFactor), 0, maxIndex);
    }

    public void draw() {
        //drawRect(x, y, x + width, y + height, -6250336);
        //drawRect(x + 1, y + 1, x + width - 1, y + height - 1, -16777216);

        for(int i = currentIndex; i < Math.min(maxLinesOnScreen + currentIndex, textLines.size()); i++) {
            fontRenderer.drawString(textLines.get(i), x + TEXT_MARGIN, y + TEXT_MARGIN + (fontRenderer.FONT_HEIGHT + TEXT_HEIGHT) * (i - currentIndex), WHITE);
        }

        int scrollbarHeight = (int) (((float) maxLinesOnScreen) / textLines.size() * height);
        int scrollOffset = (int) (((float) currentIndex) / (textLines.size() - maxLinesOnScreen) * (height - scrollbarHeight));

        //drawRect(x + width - SCROLLBAR_WIDTH, y + scrollOffset, x + width, y + scrollbarHeight + scrollOffset, Color.GRAY.getRGB());
    }

    public void update() {
        boolean key_down = Keyboard.isKeyDown(Keyboard.KEY_DOWN);
        if(key_down || Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            if(timePressed > 10) {
                if(key_down) scroll(1);
                else scroll(-1);
            }
            timePressed++;
        }
        else timePressed = 0;
    }
}
