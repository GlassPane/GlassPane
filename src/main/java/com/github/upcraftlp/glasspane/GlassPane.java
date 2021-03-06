package com.github.upcraftlp.glasspane;

import com.github.upcraftlp.glasspane.api.net.NetworkHandler;
import com.github.upcraftlp.glasspane.api.proxy.IProxy;
import com.github.upcraftlp.glasspane.api.util.logging.PrefixMessageFactory;
import com.github.upcraftlp.glasspane.command.CommandGPDebug;
import com.github.upcraftlp.glasspane.config.Lens;
import com.github.upcraftlp.glasspane.net.*;
import com.github.upcraftlp.glasspane.registry.*;
import com.github.upcraftlp.glasspane.util.*;
import com.github.upcraftlp.glasspane.vanity.CrystalBall;
import com.google.common.collect.ImmutableSet;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.*;
import org.apache.logging.log4j.*;

import java.io.File;
import java.util.*;

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

    private static final boolean debugMode = false; //global debug flag that overrides the config value if set.

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
    public static IProxy proxy = null;

    public static boolean isDebugMode() {
        return debugMode || Lens.debugMode;
    }

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
        if(isDebugMode()) debugLogger.info("Pre-Initialization complete!", new Object[0]);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        if(isDebugMode()) debugLogger.info("Initialization complete!", new Object[0]);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        GlassPaneAutomatedRegistry.cleanup();
        if(isDebugMode()) debugLogger.info("Post-Initialization complete!", new Object[0]);
    }

    @Mod.EventHandler
    public void serverAboutToStarting(FMLServerAboutToStartEvent event) {
        proxy.serverAboutToStart(event);
        if(isDebugMode()) debugLogger.info("Server is going to start!", new Object[0]);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
        if(isDebugMode()) {
            event.registerServerCommand(new CommandGPDebug());
            debugLogger.info("Starting up the server!", new Object[0]);
        }
    }

    @Mod.EventHandler
    public void serverStarted(FMLServerStartedEvent event) {
        proxy.serverStarted(event);
        if(isDebugMode()) debugLogger.info("Server has started!", new Object[0]);
    }

    @Mod.EventHandler
    public void serverStopping(FMLServerStoppingEvent event) {
        proxy.serverStopping(event);
        if(isDebugMode()) debugLogger.info("Server is going to stop!", new Object[0]);
    }

    @Mod.EventHandler
    public void serverStopped(FMLServerStoppedEvent event) {
        proxy.serverStopped(event);
        if(isDebugMode()) debugLogger.info("Server has stopped!", new Object[0]);
    }

    @Mod.EventHandler
    public void handleModMessages(FMLInterModComms.IMCEvent event) {
        List<FMLInterModComms.IMCMessage> messages = event.getMessages();
        proxy.handleInterModMessages(messages);
        if(isDebugMode()) debugLogger.info("Received {} IMC messages!", messages.size());
    }

    public static List<ModFingerprint> getInvalidFingerprints() {
        return INVALID_FINGERPRINTS;
    }

    @Mod.EventHandler
    public void onLoadComplete(FMLLoadCompleteEvent event) { //this handles ALL mods with invalid fingerprint, so no need to handle the FMLFingerprintViolationEvent
        Loader.instance().getModList().stream().filter(container -> container instanceof FMLModContainer).map(container -> (FMLModContainer) container).filter(container -> ReflectionHelper.getPrivateValue(FMLModContainer.class, container, "fingerprintNotPresent")).forEachOrdered(container -> {
            Map<String, Object> descriptor = ReflectionHelper.getPrivateValue(FMLModContainer.class, (FMLModContainer) container, "descriptor");
            String expectedKey = (String) descriptor.get("certificateFingerprint");
            if(expectedKey != null) {
                File source = container.getSource();
                Set<String> fingerPrints = ReflectionHelper.getPrivateValue(FMLModContainer.class, (FMLModContainer) container, "sourceFingerprints");
                if(source.isDirectory() && isDebugMode()) {
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
        proxy.loadComplete(event);
        log.info("Mod loading complete.");
    }

    public static Logger getLogger() {
        return log;
    }

    public static Logger getDebugLogger() {
        return debugLogger;
    }

}
