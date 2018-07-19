package com.github.upcraftlp.glasspane.client;

import com.github.upcraftlp.glasspane.config.Lens;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientUtil {

    public static ITextComponent getKeyInfo(TextFormatting textColor, TextFormatting keyColor) {
        ITextComponent keyInfo = new TextComponentString(Lens.client.keyAdvandedTooltip.getDisplayName()).setStyle(new Style().setColor(keyColor));
        return new TextComponentTranslation("tooltip.glasspane.generic.advancedKeyInfo", keyInfo).setStyle(new Style().setColor(textColor));
    }

    public static KeyBinding createKeyBinding(String translationKey, int defaultKeyCode) {
        return createKeyBinding(translationKey, defaultKeyCode, "general");
    }

    public static KeyBinding createKeyBinding(String name, int defaultKeyCode, String category) {
        KeyBinding keyBinding = new KeyBinding("key.glasspane.category." + category + "." + name, defaultKeyCode, "key.glasspane.categories." + category);
        ClientRegistry.registerKeyBinding(keyBinding);
        return keyBinding;
    }

    /**
     * open's a mod's config if present, does nothing otherwise
     * @param mod the mod instance or modid String to open the config for
     */
    public static void openConfig(Object mod) {
        Minecraft mc = Minecraft.getMinecraft();
        ModContainer modContainer = FMLCommonHandler.instance().findContainerFor(mod);
        IModGuiFactory guiFactory = FMLClientHandler.instance().getGuiFactoryFor(modContainer);
        if(guiFactory.hasConfigGui()) {
            GuiScreen configGui = guiFactory.createConfigGui(mc.currentScreen);
            mc.displayGuiScreen(configGui);
        }
    }

}
