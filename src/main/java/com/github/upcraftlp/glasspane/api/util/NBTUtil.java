package com.github.upcraftlp.glasspane.api.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTUtil {

    public static NBTTagCompound getDefaultTagCompound(ItemStack stack) {
        return getDefaultTagCompound(stack, true);
    }

    public static NBTTagCompound getDefaultTagCompound(ItemStack stack, boolean createIfAbsent) {
        return getSubCompound(stack, "glasspane", createIfAbsent);
    }

    public static NBTTagCompound getSubCompound(ItemStack stack, String compoundName) {
        return getSubCompound(stack, compoundName, true);
    }

    public static NBTTagCompound getSubCompound(ItemStack stack, String compoundName, boolean createIfAbsent) {
        if(createIfAbsent) return stack.getOrCreateSubCompound(compoundName);
        else return stack.hasTagCompound() ? stack.getTagCompound().getCompoundTag(compoundName) : new NBTTagCompound();
    }

    public static void setDefaultTag(ItemStack stack, NBTTagCompound nbt) {
        setTagInfo(stack, nbt, "glasspane");
    }

    public static void setTagInfo(ItemStack stack, NBTTagCompound nbt, String compoundName) {
        stack.setTagInfo(compoundName, nbt);
    }

}
