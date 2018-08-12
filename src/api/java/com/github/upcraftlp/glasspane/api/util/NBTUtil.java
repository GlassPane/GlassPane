package com.github.upcraftlp.glasspane.api.util;

import com.github.upcraftlp.glasspane.GlassPane;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTUtil {

    public static NBTTagCompound getDefaultTagCompound(ItemStack stack) {
        return getSubCompound(stack, GlassPane.MODID);
    }

    public static NBTTagCompound getSubCompound(ItemStack stack, String compoundName) {
        return stack.getOrCreateSubCompound(compoundName);
    }

    public static void setDefaultTag(ItemStack stack, NBTTagCompound nbt) {
        setTagInfo(stack, nbt, GlassPane.MODID);
    }

    public static void setTagInfo(ItemStack stack, NBTTagCompound nbt, String compoundName) {
        stack.setTagInfo(compoundName, nbt);
    }
}
