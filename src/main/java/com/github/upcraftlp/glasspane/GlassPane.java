package com.github.upcraftlp.glasspane;

import com.github.upcraftlp.glasspane.api.net.NetworkHandler;
import com.github.upcraftlp.glasspane.api.proxy.IProxy;
import com.github.upcraftlp.glasspane.api.util.logging.PrefixMessageFactory;
import com.github.upcraftlp.glasspane.client.ClientUtil;
import com.github.upcraftlp.glasspane.client.gui.GuiScreenInvalidSignature;
import com.github.upcraftlp.glasspane.config.Lens;
import com.github.upcraftlp.glasspane.net.PacketOpenGuide;
import com.github.upcraftlp.glasspane.net.PacketUpdateServerSkins;
import com.github.upcraftlp.glasspane.registry.GlassPaneAutomatedRegistry;
import com.github.upcraftlp.glasspane.registry.GlassPaneGuideRegistry;
import com.github.upcraftlp.glasspane.util.ModFingerprint;
import com.github.upcraftlp.glasspane.util.ModUpdateHandler;
import com.github.upcraftlp.glasspane.vanity.CrystalBall;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLModContainer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.github.upcraftlp.glasspane.GlassPane.*;

@Mod(
        modid = MODID,
        name = MODNAME,
        dependencies = DEPENDENCIES,
        acceptedMinecraftVersions = MCVERSIONS,
        certificateFingerprint = FINGERPRINT,
        version = VERSION,
        updateJSON = UPDATE_JSON
)
public class GlassPane {

    public static final String
            MODID = "glasspane",
            MODNAME = "GlassPane Framework",
            DEPENDENCIES = "required-after:forge",/*"after:jei@[4.10,);" +
                            "after:hwyla@[1.8.26,);" +
                            "",*/
            MCVERSIONS = "[1.12,1.13)",
            FINGERPRINT = "@FINGERPRINTKEY@",
            VERSION = "@VERSION@",
            UPDATE_JSON = "@UPDATEJSON@",
            CLIENT_PROXY = "com.github.upcraftlp.glasspane.proxy.ClientProxy",
            SERVER_PROXY = "com.github.upcraftlp.glasspane.proxy.ServerProxy";

    private static final Logger log = LogManager.getLogger(MODID, new PrefixMessageFactory(MODNAME));
    private static final Logger debugLogger = LogManager.getLogger(MODID + "_debug", new PrefixMessageFactory(MODNAME + " DEBUG"));
    private static final List<ModFingerprint> INVALID_FINGERPRINTS = new ArrayList<>();

    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
    public static IProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        GlassPaneAutomatedRegistry.registerDefaultPostProcessors(event.getSide());
        GlassPaneAutomatedRegistry.gatherAnnotatedClasses(event);
        GlassPaneGuideRegistry.initGuideBooks(event);
        NetworkHandler.INSTANCE.registerMessage(PacketOpenGuide.class, PacketOpenGuide.class, NetworkHandler.getNextPacketID(), Side.CLIENT);
        NetworkHandler.INSTANCE.registerMessage(PacketUpdateServerSkins.class, PacketUpdateServerSkins.class, NetworkHandler.getNextPacketID(), Side.SERVER);
        ModUpdateHandler.registerMod(MODID);
        proxy.preInit(event);
        CrystalBall.updatePlayerInfo();
        if(Lens.debugMode) debugLogger.info("Pre-Initialization complete!", new Object[0]);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        if(Lens.debugMode) debugLogger.info("Initialization complete!", new Object[0]);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        GlassPaneAutomatedRegistry.cleanup();
        if(Lens.debugMode) debugLogger.info("Post-Initialization complete!", new Object[0]);
    }

    @Mod.EventHandler
    public void serverAboutToStarting(FMLServerAboutToStartEvent event) {
        proxy.serverAboutToStart(event);
        if(Lens.debugMode) debugLogger.info("Server is going to start!", new Object[0]);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
        if(Lens.debugMode) debugLogger.info("Starting up the server!", new Object[0]);
    }

    @Mod.EventHandler
    public void serverStarted(FMLServerStartedEvent event) {
        proxy.serverStarted(event);
        if(Lens.debugMode) debugLogger.info("Server has started!", new Object[0]);
    }

    @Mod.EventHandler
    public void serverStopping(FMLServerStoppingEvent event) {
        proxy.serverStopping(event);
        if(Lens.debugMode) debugLogger.info("Server is going to stop!", new Object[0]);
    }

    @Mod.EventHandler
    public void serverStopped(FMLServerStoppedEvent event) {
        proxy.serverStopped(event);
        if(Lens.debugMode) debugLogger.info("Server has stopped!", new Object[0]);
    }

    @Mod.EventHandler
    public void handleModMessages(FMLInterModComms.IMCEvent event) {
        List<FMLInterModComms.IMCMessage> messages = event.getMessages();
        proxy.handleInterModMessages(messages);
        if(Lens.debugMode) debugLogger.info("Received {} IMC messages!", messages.size());
    }

    @Mod.EventHandler
    public void onLoadComplete(FMLLoadCompleteEvent event) { //this handles ALL mods with invalid fingerprint, so no need to handle the FMLFingerprintViolationEvent
        Loader.instance().getModList().stream().filter(container -> container instanceof FMLModContainer).map(container -> (FMLModContainer) container).filter(container -> ReflectionHelper.getPrivateValue(FMLModContainer.class, container, "fingerprintNotPresent")).forEachOrdered(container -> {
            Map<String, Object> descriptor = ReflectionHelper.getPrivateValue(FMLModContainer.class, (FMLModContainer) container, "descriptor");
            String expectedKey = (String) descriptor.get("certificateFingerprint");
            if(expectedKey != null) {
                File source = container.getSource();
                Set<String> fingerPrints = ReflectionHelper.getPrivateValue(FMLModContainer.class, (FMLModContainer) container, "sourceFingerprints");
                if(source.isDirectory() && Lens.debugMode) {
                    debugLogger.warn("{} has a mismatching fingerprint key!", source.getAbsolutePath());
                    debugLogger.warn("{} is a directory!", source.getAbsolutePath());
                }
                else {
                    log.warn("{} has a mismatching fingerprint key!", source.getAbsolutePath());
                    log.warn("Expected: {}", expectedKey);
                    StringBuilder res = new StringBuilder();
                    fingerPrints.forEach(res.append("\n   - ")::append);
                    log.warn("Got {} known keys: {}", fingerPrints.size(), res.toString());
                }
                synchronized (INVALID_FINGERPRINTS) {
                    INVALID_FINGERPRINTS.add(new ModFingerprint(source, expectedKey, ImmutableSet.copyOf(fingerPrints)));
                }
            }
        });
        if(!INVALID_FINGERPRINTS.isEmpty() && !ClientUtil.getPersistentData().getBoolean("confirmedModSignatures")) MinecraftForge.EVENT_BUS.register(new GuiScreenInvalidSignature(Lists.newArrayList(INVALID_FINGERPRINTS))); //copy the fingerprint list instead of handing it out
        log.info("Mod loading complete.");
    }

    public static Logger getLogger() {
        return log;
    }

    public static Logger getDebugLogger() {
        return debugLogger;
    }

}
