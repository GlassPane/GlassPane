package com.github.upcraftlp.glasspane.config;

import com.github.upcraftlp.glasspane.GlassPane;
import com.github.upcraftlp.glasspane.api.gui.EnumGuiBackgroundType;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config.LangKey("config.glasspane.general.title")
@Config(modid = GlassPane.MODID, name = "GlassPane")
public class GlassPaneConfig {

    public static final Client client = new Client();

    public static class Client {

        @Config.Name("Gui Background Type")
        @Config.Comment("The background that should be used for GUIs that support dynamic backgrounds")
        public EnumGuiBackgroundType guiBackgroundType = EnumGuiBackgroundType.VANILLA;
    }

    @Mod.EventBusSubscriber(modid = GlassPane.MODID)
    public static class Handler {

        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent event) {
            if(GlassPane.MODID.equals(event.getModID())) ConfigManager.sync(event.getModID(), Config.Type.INSTANCE);
        }
    }
}
