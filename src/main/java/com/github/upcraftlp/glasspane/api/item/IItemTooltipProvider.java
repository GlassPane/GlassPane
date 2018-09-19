package com.github.upcraftlp.glasspane.api.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public interface IItemTooltipProvider {

    default boolean hasAdvancedTooltip(ItemStack stack) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    default void showTooltip(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flag) {}

    @SideOnly(Side.CLIENT)
    default void showAdvancedTooltip(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {}
}
