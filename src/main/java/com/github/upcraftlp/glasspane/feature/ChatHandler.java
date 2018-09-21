package com.github.upcraftlp.glasspane.feature;

import com.github.upcraftlp.glasspane.GlassPane;
import com.github.upcraftlp.glasspane.config.Lens;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.*;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.eventhandler.*;

import java.util.regex.Pattern;

@Mod.EventBusSubscriber(modid = GlassPane.MODID)
public class ChatHandler {

    private static final Pattern COLOR_REGEX = Pattern.compile("((^|[^\\u0026])(\\u0026\\u0026)*)\\u0026([0-9a-fA-Fk-oK-OrR])");

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onChat(ServerChatEvent event) {
        if(Lens.colorChat) {
            String username = event.getUsername();
            MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
            if(Lens.colorOps && !server.isSinglePlayer() && server.getPlayerList().getOppedPlayers().getPermissionLevel(event.getPlayer().getGameProfile()) > 0) username = TextFormatting.DARK_RED.toString() + username;
            event.setComponent(new TextComponentTranslation("chat.type.text", username, ForgeHooks.newChatWithLinks(COLOR_REGEX.matcher(event.getMessage()).replaceAll("$1\u00A7$4").replace("\u0026\u0026", "\u0026"))));
        }
    }
}
