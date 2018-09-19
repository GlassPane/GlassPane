package com.github.upcraftlp.glasspane.api.util;

import io.netty.util.internal.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.commons.lang3.Validate;

public class NameUtils {

    public static <T extends IForgeRegistryEntry<T>> T name(T t, String name) {
        Validate.isTrue(!StringUtil.isNullOrEmpty(name), "null or empty registry name!");
        t.setRegistryName(GameData.checkPrefix(name));
        return t;
    }

    public static Item name(Item item, String name) {
        Validate.isTrue(!StringUtil.isNullOrEmpty(name), "null or empty registry name!");
        item.setRegistryName(name);
        item.setTranslationKey(item.getRegistryName().getNamespace() + "." + item.getRegistryName().getPath());
        return item;
    }

    public static Block name(Block block, String name) {
        Validate.isTrue(!StringUtil.isNullOrEmpty(name), "null or empty registry name!");
        block.setRegistryName(name);
        block.setTranslationKey(block.getRegistryName().getNamespace() + "." + block.getRegistryName().getPath());
        return block;
    }

}
