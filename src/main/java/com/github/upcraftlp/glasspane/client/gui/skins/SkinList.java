package com.github.upcraftlp.glasspane.client.gui.skins;

import com.github.upcraftlp.glasspane.api.client.color.DefaultColors;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraftforge.fml.client.config.GuiButtonExt;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class SkinList extends GuiListExtended {

    private final List<IGuiListEntry> entries = new ArrayList<>();
    private final GuiScreenSelectSkin gui;


    public SkinList(GuiScreenSelectSkin gui, Map<String, Set<String>> listEntries, Map<Integer, Integer> indexMap) {
        super(gui.mc, gui.width, gui.height, GuiScreenSelectSkin.MARGIN_TOP, gui.height - GuiScreenSelectSkin.SLOT_HEIGHT, GuiScreenSelectSkin.SLOT_HEIGHT);
        this.gui = gui;
        int i = 0;
        for(String s : listEntries.keySet()) {
            entries.add(new SkinListEntry(s, listEntries.get(s), indexMap.get(i++)));
        }
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
        private final String[] captions;
        private int selectedCaptionIndex;

        public SkinListEntry(String name, Set<String> buttonCaptions, int selectedCaptionIndex) {
            this.name = name;
            captions = buttonCaptions.toArray(new String[buttonCaptions.size()]);
            this.selectedCaptionIndex = selectedCaptionIndex;
            this.button = new GuiButtonExt(0, 0, 0, captions[selectedCaptionIndex]);
        }

        @Override
        public void updatePosition(int slotIndex, int x, int y, float partialTicks) {
        }

        @Override
        public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected, float partialTicks) {
            SkinList.this.mc.fontRenderer.drawString(this.name, SkinList.this.width / 8, y + slotHeight - SkinList.this.mc.fontRenderer.FONT_HEIGHT - 1, DefaultColors.FOREGROUND.WHITE);
            this.button.drawButton(SkinList.this.mc, mouseX, mouseY, partialTicks);
        }

        @Override
        public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY) {
            SkinList.this.gui.selectedIndex = slotIndex;
            if(this.button.mousePressed(SkinList.this.mc, mouseX, mouseY)) {
                if(++this.selectedCaptionIndex > this.captions.length - 1) this.selectedCaptionIndex = 0;
                this.button.displayString = captions[this.selectedCaptionIndex].toUpperCase(Locale.ROOT).replace("_", " ");
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
