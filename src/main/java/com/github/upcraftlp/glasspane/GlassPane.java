package com.github.upcraftlp.glasspane;

import com.github.upcraftlp.glasspane.api.net.NetworkHandler;
import com.github.upcraftlp.glasspane.api.proxy.IProxy;
import com.github.upcraftlp.glasspane.api.util.logging.PrefixMessageFactory;
import com.github.upcraftlp.glasspane.net.PacketFeatureSettings;
import com.github.upcraftlp.glasspane.net.PacketOpenGuide;
import com.github.upcraftlp.glasspane.registry.GlassPaneAutomatedRegistry;
import com.github.upcraftlp.glasspane.registry.GlassPaneGuideRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

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
            DEPENDENCIES = "",/*"after:jei@[4.10,);" +
                            "after:hwyla@[1.8.26,);" +
                            "",*/
            MCVERSIONS = "[1.12,1.13)",
            FINGERPRINT = "@FINGERPRINTKEY@",
            VERSION = "@VERSION@",
            UPDATE_JSON = "@UPDATEJSON@",
            CLIENT_PROXY = "com.github.upcraftlp.glasspane.proxy.ClientProxy",
            SERVER_PROXY = "com.github.upcraftlp.glasspane.proxy.ServerProxy";

    private static final Logger log = LogManager.getLogger(MODID, new PrefixMessageFactory(MODNAME));
    private static final Logger debugLogger = LogManager.getLogger(MODID, new PrefixMessageFactory(MODNAME + " DEBUG"));

    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
    public static IProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        GlassPaneAutomatedRegistry.registerDefaultPostProcessors(event.getSide());
        GlassPaneAutomatedRegistry.gatherAnnotatedClasses(event);
        GlassPaneGuideRegistry.initGuideBooks(event);
        NetworkHandler.INSTANCE.registerMessage(PacketFeatureSettings.class, PacketFeatureSettings.class, NetworkHandler.getNextPacketID(), Side.SERVER);
        NetworkHandler.INSTANCE.registerMessage(PacketOpenGuide.class, PacketOpenGuide.class, NetworkHandler.getNextPacketID(), Side.CLIENT);
        proxy.preInit(event);
        log.debug("Pre-Initialization complete!", new Object[0]);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        log.debug("Initialization complete!", new Object[0]);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        GlassPaneAutomatedRegistry.cleanup();
        log.debug("Post-Initialization complete!", new Object[0]);
    }

    @Mod.EventHandler
    public void serverAboutToStarting(FMLServerAboutToStartEvent event) {
        proxy.serverAboutToStart(event);
        log.debug("Server is going to start!", new Object[0]);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
        log.debug("Starting up the server!", new Object[0]);
    }

    @Mod.EventHandler
    public void serverStarted(FMLServerStartedEvent event) {
        proxy.serverStarted(event);
        log.debug("Server has started!", new Object[0]);
    }

    @Mod.EventHandler
    public void serverStopping(FMLServerStoppingEvent event) {
        proxy.serverStopping(event);
        log.debug("Server is going to stop!", new Object[0]);
    }

    @Mod.EventHandler
    public void serverStopped(FMLServerStoppedEvent event) {
        proxy.serverStopped(event);
        log.debug("Server has stopped!", new Object[0]);
    }

    @Mod.EventHandler
    public void handleModMessages(FMLInterModComms.IMCEvent event) {
        List<FMLInterModComms.IMCMessage> messages = event.getMessages();
        proxy.handleInterModMessages(messages);
        log.info("Received {} IMC messages!", messages.size());
    }

    public static Logger getLogger() {
        return log;
    }

    public static Logger getDebugLogger() {
        return debugLogger;
    }

}
