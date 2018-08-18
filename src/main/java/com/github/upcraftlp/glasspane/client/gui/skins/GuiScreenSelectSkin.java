package com.github.upcraftlp.glasspane.client.gui.skins;

import com.github.upcraftlp.glasspane.vanity.SkinnableMapping;
import com.github.upcraftlp.glasspane.api.net.NetworkHandler;
import com.github.upcraftlp.glasspane.client.ClientUtil;
import com.github.upcraftlp.glasspane.net.PacketUpdateServerSkins;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.client.config.GuiButtonExt;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuiScreenSelectSkin extends GuiScreen {

    public static final int MARGIN_SIDE = 35;
    public static final int MARGIN_TOP = 63;
    public static final int SLOT_HEIGHT = 40;

    @Nullable
    private final GuiScreen parentScreen;
    public int selectedIndex;
    private Map<String, List<String>> validOptions;
    public final Map<String, String> indexMap = new HashMap<>();
    private GuiListExtended list;

    public GuiScreenSelectSkin(@Nullable GuiScreen screen) {
        this.parentScreen = screen;
        this.validOptions = SkinnableMapping.getValidOptions();
        NBTTagList list = ClientUtil.getPersistentData().getTagList("skins", Constants.NBT.TAG_STRING);
        for(int i = 0; i < list.tagCount(); i++) {
            ResourceLocation rl = new ResourceLocation(list.getStringTagAt(i));
            this.indexMap.put(rl.getNamespace(), rl.getPath());
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
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.list.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        this.list.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void initGui() {
        super.initGui();
        this.list = new SkinList(this, validOptions, this.indexMap);
        this.list.setShowSelectionBox(false);
        this.buttonList.add(new GuiButtonExt(0, this.width / 2 - 100, this.height - 35, I18n.format("gui.done")));
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.list.handleMouseInput();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if(button.id == 0) {
            mc.displayGuiScreen(this.parentScreen);
        }
    }

    @Override
    public void onGuiClosed() {
        NBTTagList list = new NBTTagList();
        for(String s : this.validOptions.keySet()) {
            list.appendTag(new NBTTagString(s + ":" + this.indexMap.get(s)));
        }
        ClientUtil.writePersistentData("skins", list);
        if(Minecraft.getMinecraft().getConnection() != null) {
            NetworkHandler.INSTANCE.sendToServer(new PacketUpdateServerSkins(list));
        }
        super.onGuiClosed();
    }
}
