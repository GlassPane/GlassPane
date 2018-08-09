package com.github.upcraftlp.glasspane.util;

import com.github.upcraftlp.glasspane.GlassPane;
import com.github.upcraftlp.glasspane.api.event.factory.GlassPaneEventFactory;
import com.github.upcraftlp.glasspane.api.util.ForgeUtils;
import com.github.upcraftlp.glasspane.config.Lens;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ModUpdateHandler {

    private static final Set<String> MODS_TO_CHECK = new HashSet<>();

    public static void registerMod(String modid) {
        if(ForgeUtils.isModLoaded(modid)) MODS_TO_CHECK.add(modid);
        else GlassPane.getLogger().warn("Tried to register unloaded mod {} for glasspane update notifications!", modid);
    }

    private static Map<ModContainer, ForgeVersion.CheckResult> logMessages() {
        if(!Lens.updater.showUpdates) {
            if(Lens.debugMode) GlassPane.getDebugLogger().info("aborting version check becasue it was disabled");
            return Collections.emptyMap();
        }
        Map<ModContainer, ForgeVersion.CheckResult> outdated = new HashMap<>();
        MODS_TO_CHECK.forEach(modid -> {
            ModContainer mc = FMLCommonHandler.instance().findContainerFor(modid);
            ForgeVersion.CheckResult result = ForgeVersion.getResult(mc);
            switch(result.status) {
                case BETA_OUTDATED:
                    if(!Lens.updater.showBetaUpdates) break;
                case OUTDATED:
                    outdated.put(mc, result);
                    break;
                case PENDING:
                case FAILED:
                    GlassPane.getDebugLogger().warn("unable to get update for {}, status: {}", mc.getModId(), result.status);
                    break;
                case AHEAD:
                case UP_TO_DATE:
                    if(Lens.debugMode) GlassPane.getDebugLogger().info("found status {} for mod {}", result.status, mc.getModId());
            }
        });
        outdated.entrySet().removeIf(entry -> GlassPaneEventFactory.onModOutdated(entry.getKey(), entry.getValue())); //fire events and remove entries if event was canceled
        if(!outdated.isEmpty()) {
            GlassPane.getLogger().warn("Outdated mods detected!");
            outdated.forEach((container, result) -> {
                StringBuilder listBuilder = new StringBuilder();
                result.changes.forEach((version, changelog) -> listBuilder.append("\t").append(version).append(":\n\t\t").append(changelog).append("\n"));
                GlassPane.getLogger().info("ModID: {}\nCurrent Version: {}\nLatest Version: {}", container.getModId(), container.getVersion(), result.target);
                GlassPane.getLogger().info("Changes:\n{}", listBuilder.toString());
            });
            GlassPane.getLogger().info("this will be logged again on next restart, however you can disable it in the mod's config if you wish");
        }
        return outdated;
    }

    @Mod.EventBusSubscriber(modid = GlassPane.MODID, value = Side.CLIENT)
    public static class HandlerClient {
        @SubscribeEvent
        public static void onClientJoinWorld(EntityJoinWorldEvent event) {
            if(event.getEntity() instanceof EntityPlayerSP && event.getEntity() == Minecraft.getMinecraft().player) {
                MinecraftForge.EVENT_BUS.unregister(HandlerClient.class);
                Map<ModContainer, ForgeVersion.CheckResult> outdated = logMessages();
                EntityPlayerSP player = (EntityPlayerSP) event.getEntity();

                //TODO display FANCY message(s) on the player's screen!

                outdated.forEach((container, result) -> {
                    ITextComponent oldVersion = new TextComponentString(container.getVersion()).setStyle(new Style().setColor(TextFormatting.DARK_RED));
                    ITextComponent newVersion = new TextComponentString(String.valueOf(result.target)).setStyle(new Style().setColor(TextFormatting.DARK_GREEN));
                    ITextComponent name = new TextComponentString(container.getName()).setStyle(new Style().setColor(TextFormatting.DARK_GREEN));
                    ITextComponent nameComponent = new TextComponentTranslation("message.glasspane.modupdate", name);
                    ITextComponent versionsComponent = new TextComponentTranslation("message.glasspane.modupdate.versions", oldVersion, newVersion);
                    ITextComponent changelogComponent = new TextComponentString("");
                    if(!result.changes.isEmpty()) {
                        ITextComponent changelog = new TextComponentString("");
                        Style styleVersions = new Style().setBold(true).setColor(TextFormatting.AQUA);
                        Style styleLines = new Style().setItalic(true).setColor(TextFormatting.GRAY);

                        //TODO changelog GUI list!
                        result.changes.forEach((version, changes) -> {
                            changelog.appendSibling(new TextComponentString("* " + version + ":").setStyle(styleVersions));
                            Arrays.stream(changes.split("\n")).forEachOrdered(s -> changelog.appendText("\n  ").appendSibling(new TextComponentString(s).setStyle(styleLines)));
                            changelog.appendText("\n");
                        });
                        try {
                            String changelogHTML = "data:text/html" + URLEncoder.encode(",<pre>" + changelog.getUnformattedText().replace("\n", "<br/>") + "</pre>", "UTF-8");
                            changelogComponent.appendSibling(new TextComponentTranslation("message.glasspane.modupdate.changelog").setStyle(new Style().setColor(TextFormatting.GRAY).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, changelog)).setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, changelogHTML))));
                        } catch(UnsupportedEncodingException e) {
                            e.printStackTrace(); //should never happen
                        }
                    }
                    if(result.url != null) {
                        changelogComponent.appendText(" ").appendSibling(new TextComponentTranslation("message.glasspane.modupdate.download").setStyle(new Style().setColor(TextFormatting.AQUA).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString(result.url).setStyle(new Style().setColor(TextFormatting.AQUA).setItalic(true)))).setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, result.url))));
                    }
                    ITextComponent finalComponent = new TextComponentTranslation("message.glasspane.modupdate.format", nameComponent, versionsComponent, changelogComponent).appendText("\n");
                    player.sendMessage(finalComponent);
                });
            }
        }
    }

    @Mod.EventBusSubscriber(modid = GlassPane.MODID, value = Side.SERVER)
    public static class HandlerServer {
        @SubscribeEvent
        public static void playerLogin(PlayerEvent.PlayerLoggedInEvent event) {
            MinecraftForge.EVENT_BUS.unregister(HandlerServer.class);
            logMessages();
        }
    }
}
