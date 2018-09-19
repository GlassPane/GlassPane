package com.github.upcraftlp.glasspane.client.gui;

import com.github.upcraftlp.glasspane.GlassPane;
import com.github.upcraftlp.glasspane.api.client.color.DefaultPalettes;
import com.github.upcraftlp.glasspane.api.client.color.IColorPalette;
import com.github.upcraftlp.glasspane.client.ClientUtil;
import com.github.upcraftlp.glasspane.client.gui.element.GuiScrollableList;
import com.github.upcraftlp.glasspane.util.ModFingerprint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.CoreModManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author UpcraftLP
 */
@SideOnly(Side.CLIENT)
public final class GuiScreenInvalidSignature extends GuiScreen {

    private static final int MARGIN_SIDE = 15;
    private static final int MARGIN_TOP_BOTTOM = 15;
    private static final int BUTTON_HEIGHT = 20;
    private static final IColorPalette colors = DefaultPalettes.VANILLA;
    private final List<ModFingerprint> fingerprints;
    private GuiScrollableList textList;

    @SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
    public void onGuiOpen(GuiOpenEvent event) {
        event.setGui(this);
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    public GuiScreenInvalidSignature(List<ModFingerprint> fingerprints) {
        this.fingerprints = fingerprints;
        Collections.sort(this.fingerprints);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        this.textList.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.textList.keyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        this.textList.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.textList.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.textList.updateScreen();
    }

    @Override
    public void handleKeyboardInput() throws IOException {
        super.handleKeyboardInput();
        this.textList.handleKeyboardInput();
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        this.textList = new GuiScrollableList(this.fontRenderer, colors, MARGIN_SIDE, MARGIN_TOP_BOTTOM + (fontRenderer.FONT_HEIGHT + 5) * 2, (width - MARGIN_SIDE * 2), height - (MARGIN_TOP_BOTTOM + fontRenderer.FONT_HEIGHT + 3) * 2 - BUTTON_HEIGHT - 12);
        int buttonCount = 3;
        final int BUTTON_WIDTH = (width - (buttonCount + 1) * MARGIN_SIDE) / buttonCount;
        this.addButton(new GuiButtonExt(0, MARGIN_SIDE, height - MARGIN_TOP_BOTTOM - BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT, I18n.format("menu.quit")));
        this.addButton(new GuiButtonExt(1, MARGIN_SIDE + MARGIN_SIDE + BUTTON_WIDTH, height - MARGIN_TOP_BOTTOM - BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT, I18n.format("fml.button.open.mods.folder")));
        this.addButton(new GuiButtonExt(2, MARGIN_SIDE + (MARGIN_SIDE + BUTTON_WIDTH) * 2, height - MARGIN_TOP_BOTTOM - BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT, I18n.format("gui.glasspane.violation.continue")));

        Collections.sort(fingerprints);
        fingerprints.forEach(fingerprint -> {
            textList.addLines(0, I18n.format("gui.glasspane.violation.file", fingerprint.getSource().getAbsolutePath()));
            textList.addLines(2, I18n.format("gui.glasspane.violation.hash", fingerprint.getMd5Hash()));
            textList.addLines(2, I18n.format("gui.glasspane.violation.expectedKey", TextFormatting.GRAY.toString() + TextFormatting.ITALIC.toString() + String.valueOf(fingerprint.getExpectedKey()) + TextFormatting.RESET.toString()));
            textList.addLines(2, I18n.format("gui.glasspane.violation.validKeys", fingerprint.getFingerPrints().size()));
            if(!fingerprint.getFingerPrints().isEmpty()) fingerprint.getFingerPrints().forEach(s -> textList.addLines(4, s));
            else textList.addLines(4, TextFormatting.DARK_GRAY.toString() + TextFormatting.ITALIC.toString() + "<NONE>");
            textList.addLines(0, "");
        });
        if(GlassPane.isDebugMode()) {
            textList.addLines(0, "");
            textList.addLines(0, I18n.format("gui.glasspane.violation.coremods"));
            if(CoreModManager.getTransformers().isEmpty()) textList.addLines(2, TextFormatting.DARK_GRAY.toString() + TextFormatting.ITALIC.toString() + "<NONE>");
            else CoreModManager.getTransformers().keySet().forEach(coreModName -> textList.addLines(2,coreModName));
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        textList.handleMouseInput();
    }

    @Override
    public void confirmClicked(boolean result, int id) {
        if(result) {
            mc.displayGuiScreen(null);
            ClientUtil.writePersistentData("confirmedModSignatures", new NBTTagByte((byte) 1));
        }
        else mc.displayGuiScreen(this);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch(button.id) {
            case 0:
                mc.shutdown();
                break;
            case 1:
                File modsDir = new File(Minecraft.getMinecraft().gameDir, "mods");
                Desktop.getDesktop().open(modsDir);
                break;
            case 2:
                Minecraft.getMinecraft().displayGuiScreen(new GuiYesNo(this, I18n.format("gui.glasspane.violation.confirm.title"), I18n.format("gui.glasspane.violation.confirm.text"), button.id));
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        //title
        GlStateManager.pushMatrix();
        float scale = 2.0F;
        GlStateManager.scale(scale, scale, scale);
        this.drawCenteredString(fontRenderer,  I18n.format("gui.glasspane.violation.header"), (int) (width / (2 * scale)), (int) (MARGIN_TOP_BOTTOM / scale) + 3, 0xFFFFFFFF);
        GlStateManager.popMatrix();
        this.textList.drawElement();
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
}
