package com.github.upcraftlp.glasspane.client;

import com.github.upcraftlp.glasspane.GlassPane;
import com.github.upcraftlp.glasspane.api.client.color.DefaultColors;
import com.github.upcraftlp.glasspane.api.item.IItemTooltipProvider;
import com.github.upcraftlp.glasspane.api.net.NetworkHandler;
import com.github.upcraftlp.glasspane.client.gui.skins.GuiScreenSelectSkin;
import com.github.upcraftlp.glasspane.config.Lens;
import com.github.upcraftlp.glasspane.net.PacketUpdateServerSkins;
import com.github.upcraftlp.glasspane.vanity.CrystalBall;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.input.Keyboard;

@Mod.EventBusSubscriber(modid = GlassPane.MODID, value = Side.CLIENT)
public class ClientEventHandler {

    private static final KeyBinding KEY_SKIN_GUI = ClientUtil.createKeyBinding("skinnable", Keyboard.KEY_L);
    private static final int BUTTON_SKIN_GUI_ID = 954;
    private static boolean shouldTooltipRenderBackgroundColor = false;

    @SubscribeEvent
    public static void preRenderTooltip(RenderTooltipEvent.Pre event) {
        Item item = event.getStack().getItem();
        shouldTooltipRenderBackgroundColor = item instanceof IItemTooltipProvider && ((IItemTooltipProvider) item).hasAdvancedTooltip(event.getStack()) && Lens.client.showAdvancedTooltipInfo && (Minecraft.getMinecraft().gameSettings.advancedItemTooltips || Keyboard.isKeyDown(ClientUtil.KEY_ADVANDED_TOOLTIP.getKeyCode()));
    }

    @SubscribeEvent
    public static void renderTooltipBackground(RenderTooltipEvent.Color event) {
        if(shouldTooltipRenderBackgroundColor) {
            //TODO how to indicate debug tooltip?
            event.setBorderStart(DefaultColors.DISCORD_BLUE);
            event.setBorderEnd(DefaultColors.DISCORD_BLUE);
        }
    }

    @SubscribeEvent
    public static void postRenderTooltipBackground(RenderTooltipEvent.PostBackground event) {
        if(shouldTooltipRenderBackgroundColor) shouldTooltipRenderBackgroundColor = false;
    }

    @SubscribeEvent
    public static void onPressKey(GuiScreenEvent.KeyboardInputEvent.Post event) {
        if(Minecraft.getMinecraft().currentScreen instanceof GuiIngameMenu && featuresEnabled() && Keyboard.isKeyDown(KEY_SKIN_GUI.getKeyCode())) { //unpacking the keycode is NEEDED here!
            Minecraft.getMinecraft().displayGuiScreen(new GuiScreenSelectSkin(Minecraft.getMinecraft().currentScreen));
        }
    }

    @SubscribeEvent
    public static void onRenderGui(GuiScreenEvent.DrawScreenEvent.Post event) {
        if(event.getGui() instanceof GuiIngameMenu && featuresEnabled()) {
            String toDraw = TextFormatting.YELLOW + I18n.format("gui.glasspane.skinnable.overlay", KEY_SKIN_GUI.getDisplayName());
            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
            int width = fontRenderer.getStringWidth(toDraw);
            fontRenderer.drawStringWithShadow(toDraw, event.getGui().width - width - 4, event.getGui().height - fontRenderer.FONT_HEIGHT - 4, DefaultColors.FOREGROUND.YELLOW);
        }
    }

    private static boolean featuresEnabled() {
        return CrystalBall.hasVanityFeatures(Minecraft.getMinecraft().getSession().getProfile().getId());
    }

    @SubscribeEvent
    public static void onRenderGui(GuiScreenEvent.InitGuiEvent.Post event) {
        if(event.getGui() instanceof GuiCustomizeSkin && featuresEnabled()) {
            GuiButton b = null;
            for(GuiButton button : event.getButtonList()) {
                if(button.id == 200) {
                    b = button;
                    break;
                }
            }
            if(b != null) { //just in case someone tampers with that screen
                event.getButtonList().add(new GuiButtonExt(BUTTON_SKIN_GUI_ID, b.x, b.y, b.width, b.height, I18n.format("gui.glasspane.skinnable.button")));
                b.y += 24;
            }
        }
    }

    @SubscribeEvent
    public static void onPressButton(GuiScreenEvent.ActionPerformedEvent.Post event) {
        if(Minecraft.getMinecraft().currentScreen instanceof GuiCustomizeSkin && featuresEnabled() && event.getButton().id == BUTTON_SKIN_GUI_ID) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiScreenSelectSkin(event.getGui()));
        }
    }

    @SubscribeEvent
    public static void onServerJoin(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        NetworkHandler.INSTANCE.sendToServer(new PacketUpdateServerSkins(ClientUtil.getPersistentData().getTagList("skins", Constants.NBT.TAG_STRING)));
    }
}
