package com.github.upcraftlp.glasspane.item;

import com.github.upcraftlp.glasspane.api.client.IItemTooltipProvider;
import com.github.upcraftlp.glasspane.api.util.NameUtils;
import com.github.upcraftlp.glasspane.client.ClientUtil;
import com.github.upcraftlp.glasspane.config.Lens;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBase extends Item implements IItemTooltipProvider {

    private boolean advancedTooltip;

    public ItemBase setHasAdvancedTooltip(boolean hasAdvancedTooltip) {
        this.advancedTooltip = hasAdvancedTooltip;
        return this;
    }

    @Override
    public boolean hasAdvancedTooltip(ItemStack stack) {
        return this.advancedTooltip;
    }

    protected final EnumFacing DEFAULT_FACING = null;

    public ItemBase(String name) {
        NameUtils.name(this, name);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        this.showTooltip(stack, worldIn, tooltip, flagIn);
        if(this.hasAdvancedTooltip(stack) && Lens.client.showAdvancedTooltipInfo) {
            if(flagIn.isAdvanced() || Keyboard.isKeyDown(Lens.client.keyAdvandedTooltip.getKeyCode())) this.showAdvancedTooltip(stack, worldIn, tooltip, flagIn);
            else tooltip.add(ClientUtil.getKeyInfo(TextFormatting.GRAY, TextFormatting.AQUA).getFormattedText());
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

}
