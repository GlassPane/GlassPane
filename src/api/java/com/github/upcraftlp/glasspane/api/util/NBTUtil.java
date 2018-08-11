package com.github.upcraftlp.glasspane.api.util;

import com.github.upcraftlp.glasspane.GlassPane;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTUtil {

    public static NBTTagCompound getDefaultTagCompound(ItemStack stack) {
        return stack.getOrCreateSubCompound(GlassPane.MODID);
    }
}
