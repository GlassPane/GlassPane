package com.github.upcraftlp.glasspane.config;

import com.github.upcraftlp.glasspane.GlassPane;
import com.github.upcraftlp.glasspane.api.gui.EnumGuiBackgroundType;
import com.github.upcraftlp.glasspane.client.ClientUtil;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

@Config.LangKey("config.glasspane.general.title")
@Config(modid = GlassPane.MODID, name = "glasspanemods/GlassPane") //--> /config/glasspanemods/GlassPane.cfg
public class Lens {

    @SideOnly(Side.CLIENT)
    @Config.Name("Client Settings")
    @Config.Comment("These settigns affect the client ONLY")
    public static final Client client = new Client();

    @Config.Name("Updater Settings")
    public static final Update updater = new Update();

    @Config.Name("Debug Mode")
    @Config.Comment({"En/Disable global debug mode", "this is a global flag for all glasspane mods!"})
    public static boolean debugMode = false;

    @SideOnly(Side.CLIENT)
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

    public static class Update {

        @Config.Name("Show Available Updates")
        @Config.Comment("whether or not to show a notification about updates")
        public boolean showUpdates = true;

        @Config.Name("Show Experimental Builds")
        @Config.Comment("whether or not to show experimental builds that might be unstable")
        public boolean showBetaUpdates = true;
    }

    @Mod.EventBusSubscriber(modid = GlassPane.MODID)
    public static class Handler {

        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent event) {
            if(GlassPane.MODID.equals(event.getModID())) ConfigManager.sync(event.getModID(), Config.Type.INSTANCE);
        }
    }
}
