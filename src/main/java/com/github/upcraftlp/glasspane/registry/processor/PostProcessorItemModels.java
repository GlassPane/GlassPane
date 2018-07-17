package com.github.upcraftlp.glasspane.registry.processor;

import com.github.upcraftlp.glasspane.api.registry.ICustomItemModelProvider;
import com.github.upcraftlp.glasspane.api.registry.RegistryPostProcessor;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistry;

public class PostProcessorItem extends RegistryPostProcessor<Item> {

    @Override
    public Class<Item> getType() {
        return Item.class;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void process(Item item, IForgeRegistry<Item> registry, Side side) {
        if(side.isClient()) {
            if(item instanceof ICustomItemModelProvider) ((ICustomItemModelProvider) item).initItemModel();
            else ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        }
    }
}
