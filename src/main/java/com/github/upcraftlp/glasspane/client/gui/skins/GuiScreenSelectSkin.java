package com.github.upcraftlp.glasspane.client.gui.skins;

import com.github.upcraftlp.glasspane.GlassPane;
import com.github.upcraftlp.glasspane.api.client.SkinnableMapping;
import com.github.upcraftlp.glasspane.client.ClientUtil;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GuiScreenSelectSkin extends GuiScreen {

    public static final int MARGIN_SIDE = 35;
    public static final int MARGIN_TOP = 63;
    public static final int SLOT_HEIGHT = 40;
    public final Int2IntMap indexMap = new Int2IntArrayMap();
    private Map<String, List<String>> validOptions;

    private final GuiScreen parentScreen;
    public int selectedIndex;
    private GuiListExtended list;

    public GuiScreenSelectSkin(GuiScreen screen) {
        this.parentScreen = screen;
        this.validOptions = SkinnableMapping.getValidOptions();
        NBTTagList list = ClientUtil.getPersistentData().getTagList("skins", Constants.NBT.TAG_STRING);
        int i = 0;
        for(@SuppressWarnings("unused") String id : validOptions.keySet()) {
            this.indexMap.put(i, SkinnableMapping.getSkinIdForRendering(new ResourceLocation(list.getStringTagAt(i++))));
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
        this.list.setShowSelectionBox(false);
    }

    @Override
    public void onGuiClosed() {
        int i = 0;
        NBTTagList list = new NBTTagList();
        for(Map.Entry<String, List<String>> e : this.validOptions.entrySet()) {
            list.appendTag(new NBTTagString(e.getKey() + ":" + e.getValue().get(this.indexMap.get(i++))));
        }
        ClientUtil.writePersistentData("skins", list);

        if(Minecraft.getMinecraft().getConnection() != null) {
            GlassPane.getLogger().error(ClientUtil.getPersistentData().getTagList("skins", Constants.NBT.TAG_STRING));  //FIXME remove debug
            //NetworkHandler.INSTANCE.sendToServer(new PacketUpdateServerSkins(nbt));
        }
        super.onGuiClosed();
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.list.handleMouseInput();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.list.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        this.list.mouseReleased(mouseX, mouseY, state);
    }
}
