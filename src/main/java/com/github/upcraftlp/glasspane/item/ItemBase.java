package com.github.upcraftlp.glasspane.item;

import com.github.upcraftlp.glasspane.api.util.NameUtils;
import com.github.upcraftlp.glasspane.client.ClientUtil;
import com.github.upcraftlp.glasspane.config.Lens;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBase extends Item {

    private boolean advancedTooltip;

    public ItemBase setHasAdvancedTooltip(boolean hasAdvancedTooltip) {
        this.advancedTooltip = hasAdvancedTooltip;
        return this;
    }

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
            if(flagIn.isAdvanced() || Keyboard.isKeyDown(Lens.client.keyAdvandedTooltip.getKeyCode())) this.showAdvancedTooltip(stack, worldIn, tooltip);
            else tooltip.add(ClientUtil.getKeyInfo(TextFormatting.GRAY, TextFormatting.AQUA).getFormattedText());
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @SideOnly(Side.CLIENT)
    public void showTooltip(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        //NO-OP
    }

    @SideOnly(Side.CLIENT)
    public void showAdvancedTooltip(ItemStack stack, @Nullable World world, List<String> tooltip) {
        //NO-OP
    }
}
