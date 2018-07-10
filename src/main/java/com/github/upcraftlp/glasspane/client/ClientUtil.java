package com.github.upcraftlp.glasspane.client;

import com.github.upcraftlp.glasspane.config.Lens;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.registry.ClientRegistry;

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

}
