package com.github.upcraftlp.glasspane.item.armor;

import com.github.upcraftlp.glasspane.api.util.NameUtils;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.EnumFacing;

public class ItemArmorBase extends ItemArmor {

    protected static final EnumFacing DEFAULT_FACING = null;

    public ItemArmorBase(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(materialIn, renderIndexIn, equipmentSlotIn);
        NameUtils.name(this, name);
    }
}
