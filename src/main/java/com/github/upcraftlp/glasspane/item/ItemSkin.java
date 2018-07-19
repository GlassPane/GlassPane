package com.github.upcraftlp.glasspane.item;

import com.github.upcraftlp.glasspane.GlassPane;
import com.github.upcraftlp.glasspane.api.skin.IHasSkin;
import com.github.upcraftlp.glasspane.api.util.NBTUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ItemSkin extends ItemBase implements IHasSkin<ItemStack> {

    private static final ResourceLocation CUSTOM_SKIN = new ResourceLocation(GlassPane.MODID, "custom_skin");

    public ItemSkin(String name) {
        super(name);
        this.addPropertyOverride(CUSTOM_SKIN, this::getSkin);
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        super.onCreated(stack, worldIn, playerIn);
        this.setSkin(stack, GlassPane.proxy.getSelectedItemSkin(stack, playerIn));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ItemStack getDefaultInstance() {
        ItemStack ret = super.getDefaultInstance();
        this.setSkin(ret, GlassPane.proxy.getSelectedItemSkin(ret, Minecraft.getMinecraft().player));
        return ret;
    }

    @Override
    public void setSkin(ItemStack stack, int skin) {
        NBTUtil.getDefaultTagCompound(stack).setInteger(CUSTOM_SKIN.getResourcePath(), skin);
    }

    @Override
    public float getSkin(ItemStack stack, @Nullable World world, @Nullable Entity entity) {
        return NBTUtil.getDefaultTagCompound(stack).getInteger(CUSTOM_SKIN.getResourcePath());
    }
}
