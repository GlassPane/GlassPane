package com.github.upcraftlp.glasspane.api.net;

import com.github.upcraftlp.glasspane.GlassPane;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

import javax.annotation.Nullable;

public class NetworkHandler {

    private static int packetID;

    public static SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(GlassPane.MODID);

    /**
     * a message handler to be substituted for side-onl handlers,
     * will crash the game if receiving a message!
     */
    public static DummyMessageHandler DUMMY_HANDLER = new DummyMessageHandler();

    /**
     * gets a new packet ID to use and increments it internally
     */
    public static int getNextPacketID() {
        return packetID++;
    }

    /**
     * @author jamieswhiteshirt
     * @see "https://github.com/JamiesWhiteShirt/demolitions/blob/4ea3135fa3b42dd089b6fc893e8f588d9f7e05d1/src/main/java/com/jamieswhiteshirt/demolitions/common/network/messagehandler/DummyMessageHandler.java"
     */
    private static class DummyMessageHandler<T extends IMessage> implements IMessageHandler<T, T> {

        @Nullable
        @Override
        public T onMessage(T message, MessageContext messageContext) {
            throw new UnsupportedOperationException("This message handler should not have been called!");
        }
    }
}
