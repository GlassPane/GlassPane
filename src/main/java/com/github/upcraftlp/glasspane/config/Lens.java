package com.github.upcraftlp.glasspane.config;

import com.github.upcraftlp.glasspane.GlassPane;
import com.github.upcraftlp.glasspane.api.client.gui.EnumGuiBackgroundType;
import com.github.upcraftlp.glasspane.client.ClientUtil;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

@Config.LangKey("config.glasspane.general.title")
@Config(modid = GlassPane.MODID, name = "glasspanemods/GlassPane") //--> /config/glasspanemods/GlassPane.cfg
public class Lens {

    public static final Client client = new Client();

    public static boolean debugMode = false;

    public static class Client {

        @Config.Name("Gui Background Type")
        @Config.Comment("The background that should be used for GUIs that support dynamic backgrounds")
        public EnumGuiBackgroundType guiBackgroundType = EnumGuiBackgroundType.VANILLA;

        @Config.Name("Advanced Tooltips")
        @Config.Comment("Show advanced tooltip information?")
        public boolean showAdvancedTooltipInfo = true;

        @Config.Ignore
        public KeyBinding keyAdvandedTooltip = ClientUtil.createKeyBinding("tooltip", Keyboard.KEY_LSHIFT); //this has to be changed in the OPTIONS menu, not config screen. It's just here because it belongs here.
    }

    @Mod.EventBusSubscriber(modid = GlassPane.MODID)
    public static class Handler {

        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent event) {
            if(GlassPane.MODID.equals(event.getModID())) ConfigManager.sync(event.getModID(), Config.Type.INSTANCE);
        }
    }
}
