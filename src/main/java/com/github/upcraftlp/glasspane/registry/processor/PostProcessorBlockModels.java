package com.github.upcraftlp.glasspane.registry.processor;

import com.github.upcraftlp.glasspane.api.block.ICustomBlockModelProvider;
import com.github.upcraftlp.glasspane.api.item.ICustomItemModelProvider;
import com.github.upcraftlp.glasspane.api.registry.RegistryPostProcessor;
import net.minecraft.block.Block;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistry;

public class PostProcessorBlockModels extends RegistryPostProcessor<Block> {

    @Override
    public Class<Block> getType() {
        return Block.class;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void process(Block block, IForgeRegistry<Block> registry, Side side) {
        if(side.isClient()) {
            if(block instanceof ICustomBlockModelProvider) ((ICustomBlockModelProvider) block).initBlockModel();
            if(block instanceof ICustomItemModelProvider) ((ICustomItemModelProvider) block).initItemModel();
        }
    }
}
