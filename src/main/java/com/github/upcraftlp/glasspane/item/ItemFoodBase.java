package com.github.upcraftlp.glasspane.item;

import com.github.upcraftlp.glasspane.api.item.IItemTooltipProvider;
import com.github.upcraftlp.glasspane.api.util.NameUtils;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class ItemFoodBase extends ItemFood implements IItemTooltipProvider {

    private boolean advancedTooltip;

    public ItemFoodBase(String name, int amount, float saturation, boolean isWolfFood) {
        super(amount, saturation, isWolfFood);
        NameUtils.name(this, name);
    }

    public ItemFoodBase(String name, int amount, boolean isWolfFood) {
        this(name, amount, 0.6F, isWolfFood);
    }

    public ItemFoodBase setHasAdvancedTooltip(boolean hasAdvancedTooltip) {
        this.advancedTooltip = hasAdvancedTooltip;
        return this;
    }

    @Override
    public boolean hasAdvancedTooltip(ItemStack stack) {
        return this.advancedTooltip;
    }
}
