package com.github.upcraftlp.glasspane.item;

import com.github.upcraftlp.glasspane.GlassPane;
import com.github.upcraftlp.glasspane.vanity.SkinnableMapping;
import com.github.upcraftlp.glasspane.api.item.ICustomItemModelProvider;
import com.github.upcraftlp.glasspane.api.skin.IHasSkin;
import com.github.upcraftlp.glasspane.api.util.NBTUtil;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Locale;

public abstract class ItemSkin extends ItemBase implements IHasSkin<ItemStack>, ICustomItemModelProvider {

    protected static final ResourceLocation CUSTOM_SKIN = new ResourceLocation(GlassPane.MODID, "custom_skin");

    public ItemSkin(String name) {
        super(name);
        this.addPropertyOverride(CUSTOM_SKIN, (stack, worldIn, entityIn) -> SkinnableMapping.getSkinIdForRendering(this.getSkin(stack, worldIn, entityIn)));
    }

    public void setSelectedSkin(ItemStack stack, EntityPlayer player) {
        ResourceLocation skin = GlassPane.proxy.getSelectedSkin(this.getSkinID(), player);
        this.setSkin(stack, skin);
        if(skin != null) {
            NBTTagList lore = new NBTTagList();
            lore.appendTag(new NBTTagString("created by: " + player.getDisplayNameString()));
            lore.appendTag(new NBTTagString("skin: " + skin.getPath().toUpperCase(Locale.ROOT).replace("_", " ")));
            stack.getOrCreateSubCompound("display").setTag("Lore", lore);
        }
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        this.setSelectedSkin(stack, playerIn);
    }

    @Override
    public void setSkin(ItemStack stack, ResourceLocation skin) {
        NBTTagCompound nbt = NBTUtil.getDefaultTagCompound(stack);
        if(skin != null) nbt.setString(CUSTOM_SKIN.getPath(), skin.getPath());
        else nbt.removeTag(CUSTOM_SKIN.getPath());
        if(!nbt.isEmpty()) NBTUtil.setDefaultTag(stack, nbt);
        else if(stack.hasTagCompound()) stack.getTagCompound().removeTag(GlassPane.MODID);
    }

    @Nullable
    @Override
    public ResourceLocation getSkin(ItemStack stack, @Nullable World world, @Nullable Entity entity) {
        NBTTagCompound nbt = stack.getSubCompound(GlassPane.MODID);
        if(nbt != null) {
            String loc = nbt.getString(CUSTOM_SKIN.getPath());
            if(!loc.isEmpty()) return new ResourceLocation(this.getSkinID(), loc);
        }
        return null;
    }

    public abstract ModelResourceLocation getCustomModelName(String variant);
    public abstract String[] getCustomModelNames();

    @Override
    public void initItemModel() {
        ModelResourceLocation[] names = Arrays.stream(this.getCustomModelNames()).map(this::getCustomModelName).toArray(ModelResourceLocation[]::new);
        ModelResourceLocation[] newNames = Arrays.copyOf(names, names.length + 1);
        ModelResourceLocation baseItem = new ModelResourceLocation(this.getRegistryName(), "inventory");
        newNames[newNames.length - 1] = baseItem;
        ModelBakery.registerItemVariants(this, newNames);
        ModelLoader.setCustomMeshDefinition(this, stack -> {
            NBTTagCompound nbt = stack.getSubCompound(GlassPane.MODID);
            if(nbt != null && nbt.hasKey(CUSTOM_SKIN.getPath(), Constants.NBT.TAG_STRING)) {
                String variant = nbt.getString(CUSTOM_SKIN.getPath());
                return getCustomModelName(new ResourceLocation(variant).getPath());
            }
            return baseItem;
        });
    }
}
