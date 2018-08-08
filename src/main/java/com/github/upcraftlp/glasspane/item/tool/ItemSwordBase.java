package com.github.upcraftlp.glasspane.item.tool;

import com.github.upcraftlp.glasspane.api.item.IItemTooltipProvider;
import com.github.upcraftlp.glasspane.api.util.NameUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemSwordBase extends ItemSword implements IItemTooltipProvider {

    private boolean advancedTooltip;

    public ItemSwordBase(String name, ToolMaterial material) {
        super(material);
        NameUtils.name(this, name);
    }

    public ItemSwordBase setHasAdvancedTooltip(boolean hasAdvancedTooltip) {
        this.advancedTooltip = hasAdvancedTooltip;
        return this;
    }

    @Override
    public boolean hasAdvancedTooltip(ItemStack stack) {
        return this.advancedTooltip;
    }
}
