package com.github.upcraftlp.glasspane.item.tool;

import com.github.upcraftlp.glasspane.api.item.IItemTooltipProvider;
import com.github.upcraftlp.glasspane.api.util.NameUtils;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;

public class ItemHoeBase extends ItemHoe implements IItemTooltipProvider {

    private boolean advancedTooltip;

    public ItemHoeBase(String name, ToolMaterial material) {
        super(material);
        NameUtils.name(this, name);
    }

    public ItemHoeBase setHasAdvancedTooltip(boolean hasAdvancedTooltip) {
        this.advancedTooltip = hasAdvancedTooltip;
        return this;
    }

    @Override
    public boolean hasAdvancedTooltip(ItemStack stack) {
        return this.advancedTooltip;
    }
}
