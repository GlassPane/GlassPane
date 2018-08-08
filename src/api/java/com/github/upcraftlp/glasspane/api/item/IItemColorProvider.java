package com.github.upcraftlp.glasspane.api.client;

import net.minecraftforge.client.event.ColorHandlerEvent;

public interface IItemColorProvider {

    void registerItemColors(ColorHandlerEvent.Item event);
}
