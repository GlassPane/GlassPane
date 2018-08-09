package com.github.upcraftlp.glasspane.registry.processor;

import com.github.upcraftlp.glasspane.api.block.IHasItemBlock;
import com.github.upcraftlp.glasspane.api.registry.RegistryPostProcessor;
import com.github.upcraftlp.glasspane.registry.GlassPaneAutomatedRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistry;

public class PostProcessorBlockItems extends RegistryPostProcessor<Block> {

    @Override
    public Class<Block> getType() {
        return Block.class;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void process(Block block, IForgeRegistry<Block> registry, Side side) {
        if(!(block instanceof IHasItemBlock) || ((IHasItemBlock) block).createItemBlock()) {
            Item item = new ItemBlock(block).setRegistryName(block.getRegistryName());
            GlassPaneAutomatedRegistry.register(item, ForgeRegistries.ITEMS);
        }
    }
}
