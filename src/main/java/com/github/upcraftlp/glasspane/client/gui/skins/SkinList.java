package com.github.upcraftlp.glasspane.client.gui.skins;

import com.github.upcraftlp.glasspane.api.client.color.DefaultColors;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraftforge.fml.client.config.GuiButtonExt;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SkinList extends GuiListExtended {

    private final List<IGuiListEntry> entries = new ArrayList<>();
    private final GuiScreenSelectSkin gui;


    public SkinList(GuiScreenSelectSkin gui, Map<String, List<String>> listEntries, Map<Integer, Integer> indexMap) {
        super(gui.mc, gui.width, gui.height, GuiScreenSelectSkin.MARGIN_TOP, gui.height - GuiScreenSelectSkin.SLOT_HEIGHT, GuiScreenSelectSkin.SLOT_HEIGHT);
        this.gui = gui;
        int i = 0;
        for(String s : listEntries.keySet()) entries.add(new SkinListEntry(s, listEntries.get(s), indexMap.get(i++)));
    }

    @Override
    protected int getScrollBarX() {
        return this.width - GuiScreenSelectSkin.MARGIN_SIDE * 2 + 3;
    }

    @Override
    public int getListWidth() {
        return this.width - GuiScreenSelectSkin.MARGIN_SIDE * 4;
    }

    @Override
    public IGuiListEntry getListEntry(int index) {
        return this.entries.get(index);
    }

    @Override
    protected boolean isSelected(int slotIndex) {
        return this.gui.selectedIndex == slotIndex;
    }

    @Override
    protected int getSize() {
        return this.entries.size();
    }

    private class SkinListEntry implements IGuiListEntry {

        private final GuiButton button;
        private final String name;
        private final List<String> captions;
        private int selectedCaptionIndex;

        public SkinListEntry(String name, List<String> buttonCaptions, int selectedCaptionIndex) {
            this.name = name;
            captions = buttonCaptions;
            this.selectedCaptionIndex = selectedCaptionIndex;
            this.button = new GuiButtonExt(0, GuiScreenSelectSkin.MARGIN_SIDE, 0, captions.get(this.selectedCaptionIndex).toUpperCase(Locale.ROOT).replace("_", " "));
        }

        @Override
        public void updatePosition(int slotIndex, int x, int y, float partialTicks) {}

        @Override
        public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected, float partialTicks) {
            SkinList.this.mc.fontRenderer.drawString(this.name, x + listWidth / 8, y + (slotHeight - SkinList.this.mc.fontRenderer.FONT_HEIGHT) / 2, DefaultColors.FOREGROUND.WHITE);
            this.button.y = y + (slotHeight - this.button.height) / 2;
            this.button.x = x + listWidth - this.button.width - 15;
            this.button.drawButton(SkinList.this.mc, mouseX, mouseY, partialTicks);
        }

        @Override
        public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY) {
            SkinList.this.gui.selectedIndex = slotIndex;
            if(this.button.mousePressed(SkinList.this.mc, mouseX, mouseY)) {
                if(++this.selectedCaptionIndex > this.captions.size() - 1) this.selectedCaptionIndex = 0;
                this.button.displayString = this.captions.get(this.selectedCaptionIndex).toUpperCase(Locale.ROOT).replace("_", " ");
                SkinList.this.gui.indexMap.put(slotIndex, this.selectedCaptionIndex);
                return true;
            }
            return false;
        }

        @Override
        public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
            this.button.mouseReleased(mouseX, mouseY);
        }
    }
}
