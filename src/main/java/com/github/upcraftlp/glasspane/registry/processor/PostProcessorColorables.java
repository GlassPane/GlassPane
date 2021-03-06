package com.github.upcraftlp.glasspane.registry.processor;

import com.github.upcraftlp.glasspane.GlassPane;
import com.github.upcraftlp.glasspane.api.block.IBlockColorProvider;
import com.github.upcraftlp.glasspane.api.item.IItemColorProvider;
import com.github.upcraftlp.glasspane.api.registry.RegistryPostProcessor;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.LinkedList;
import java.util.List;

public class PostProcessorColorables extends RegistryPostProcessor {

    private List<IBlockColorProvider> BLOCK_COLORS = new LinkedList<>();
    private List<IItemColorProvider> ITEM_COLORS = new LinkedList<>();

    @SubscribeEvent
    public void handleBlockColors(ColorHandlerEvent.Block event) {
        BLOCK_COLORS.forEach(colorProvider -> colorProvider.registerBlockColors(event));
        if(GlassPane.isDebugMode()) GlassPane.getDebugLogger().info("registered {} block color handlers!", BLOCK_COLORS.size());
        BLOCK_COLORS = null;
    }

    @SubscribeEvent
    public void handleItemColors(ColorHandlerEvent.Item event) {
        ITEM_COLORS.forEach(colorProvider -> colorProvider.registerItemColors(event));
        if(GlassPane.isDebugMode()) GlassPane.getDebugLogger().info("registered {} item color handlers!", ITEM_COLORS.size());
        ITEM_COLORS = null;
        MinecraftForge.EVENT_BUS.unregister(this); //This is safe to call because the block color event is fired first and this event subscriber is not used anymore.
    }

    @Override
    public Class getType() {
        return IForgeRegistryEntry.class;
    }

    @Override
    public boolean shouldProcess(Class type) {
        return type == Item.class || type == Block.class;
    }

    @Override
    public void process(IForgeRegistryEntry entry, IForgeRegistry registry, Side side) {
        if(entry instanceof IBlockColorProvider) {
            BLOCK_COLORS.add((IBlockColorProvider) entry);
        }
        if(entry instanceof IItemColorProvider) {
            ITEM_COLORS.add((IItemColorProvider) entry);
        }
    }
}
