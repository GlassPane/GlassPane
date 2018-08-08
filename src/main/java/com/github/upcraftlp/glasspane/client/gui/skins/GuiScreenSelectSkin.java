package com.github.upcraftlp.glasspane.client.gui.skins;

import com.github.upcraftlp.glasspane.api.client.SkinnableMapping;
import com.github.upcraftlp.glasspane.client.ClientUtil;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTTagCompound;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class GuiScreenSelectSkin extends GuiScreen {

    public static final int MARGIN_SIDE = 15;
    public static final int MARGIN_TOP = 15;
    public static final int MARGIN_BOTTOM = 50;
    public static final int SLOT_HEIGHT = 26;
    public final Int2IntMap indexMap = new Int2IntArrayMap();
    private Map<String, Set<String>> validOptions;

    private final GuiScreen parentScreen;
    public int selectedIndex;
    private GuiListExtended list;

    public GuiScreenSelectSkin(GuiScreen screen) {
        this.parentScreen = screen;
        this.validOptions = SkinnableMapping.getValidOptions();
        NBTTagCompound nbt = ClientUtil.getPersistentData().getCompoundTag("skins");
        int i = 0;
        for(String id : validOptions.keySet()) {
            this.indexMap.put(i++, nbt.getInteger(id));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.list.drawScreen(mouseX, mouseY, partialTicks);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if(keyCode == 1) this.mc.displayGuiScreen(this.parentScreen);
        else super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void initGui() {
        super.initGui();
        this.list = new SkinList(this, validOptions, this.indexMap);
    }

    @Override
    public void onGuiClosed() {
        int i = 0;
        NBTTagCompound nbt = new NBTTagCompound();
        for(String id : this.validOptions.keySet()) {
            nbt.setInteger(id, this.indexMap.get(i++));
        }
        ClientUtil.writePersistentData("skins", nbt);

        //TODO update server!
        super.onGuiClosed();
    }
}
