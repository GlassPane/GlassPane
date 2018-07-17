package com.github.upcraftlp.glasspane.item;

import com.github.upcraftlp.glasspane.guide.GuideHandler;
import com.github.upcraftlp.glasspane.registry.GlassPaneGuideRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemGuide extends ItemBase {

    private final ResourceLocation guide;

    public ItemGuide(String name, ResourceLocation guide) {
        super(name);
        this.guide = guide;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if(worldIn.isRemote) {
            GuideHandler.openLastPage(GlassPaneGuideRegistry.getGuide(this.guide));
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
}
