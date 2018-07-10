package com.github.upcraftlp.glasspane.api.util;

import com.github.upcraftlp.glasspane.GlassPane;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTUtil {

    public static NBTTagCompound getModTagCompound(ItemStack stack, String modid) {
        if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        return stack.getSubCompound(modid);
    }

    public static NBTTagCompound getDefaultTagCompound(ItemStack stack) {
        return getModTagCompound(stack, GlassPane.MODID);
    }
}
