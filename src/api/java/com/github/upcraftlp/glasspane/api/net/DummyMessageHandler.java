package com.github.upcraftlp.glasspane.api.net;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import javax.annotation.Nullable;

/**
 * @author jamieswhiteshirt
 * @see "https://github.com/JamiesWhiteShirt/demolitions/blob/4ea3135fa3b42dd089b6fc893e8f588d9f7e05d1/src/main/java/com/jamieswhiteshirt/demolitions/common/network/messagehandler/DummyMessageHandler.java"
 */
public class DummyMessageHandler<T extends IMessage> implements IMessageHandler<T, T> {

    @Nullable
    @Override
    public T onMessage(T message, MessageContext messageContext) {
        throw new UnsupportedOperationException("This message handler should never have been called");
    }

    public static <T extends IMessage> DummyMessageHandler<T> newInstance() {
        return new DummyMessageHandler<>();
    }
}
