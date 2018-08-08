package com.github.upcraftlp.glasspane.api.client;

import net.minecraftforge.client.event.ColorHandlerEvent;

public interface IBlockColorProvider {

    void registerBlockColors(ColorHandlerEvent.Block event);
}
