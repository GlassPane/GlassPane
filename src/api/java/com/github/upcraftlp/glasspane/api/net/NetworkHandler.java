package com.github.upcraftlp.glasspane.api.net;

import com.github.upcraftlp.glasspane.GlassPane;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class NetworkHandler {

    private static int packetID;

    public static SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(GlassPane.MODID);

    /**
     * gets a new packet ID to use and increments it internally
     */
    public static int getNextPacketID() {
        return packetID++;
    }
}
