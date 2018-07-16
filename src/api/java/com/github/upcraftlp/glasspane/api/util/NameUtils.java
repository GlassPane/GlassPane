package com.github.upcraftlp.glasspane.api.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class NameUtils {

    public static <T extends IForgeRegistryEntry<T>> T name(T t, String name) {
        t.setRegistryName(GameData.checkPrefix(name));
        return t;
    }

    public static Item name(Item item, String name) {
        item.setRegistryName(name);
        item.setUnlocalizedName(item.getRegistryName().getResourceDomain() + "." + name);
        return item;
    }

    public static Block name(Block block, String name) {
        block.setRegistryName(name);
        block.setUnlocalizedName(block.getRegistryName().getResourceDomain() + "." + name);
        return block;
    }

    public static final ResourceLocation MISSING = new ResourceLocation("minecraft", "missingno");
}
