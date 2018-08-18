package com.github.upcraftlp.glasspane.api.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTUtil {

    public static NBTTagCompound getDefaultTagCompound(ItemStack stack) {
        return getSubCompound(stack, "glasspane");
    }

    public static NBTTagCompound getSubCompound(ItemStack stack, String compoundName) {
        return stack.getOrCreateSubCompound(compoundName);
    }

    public static void setDefaultTag(ItemStack stack, NBTTagCompound nbt) {
        setTagInfo(stack, nbt, "glasspane");
    }

    public static void setTagInfo(ItemStack stack, NBTTagCompound nbt, String compoundName) {
        stack.setTagInfo(compoundName, nbt);
    }
}
