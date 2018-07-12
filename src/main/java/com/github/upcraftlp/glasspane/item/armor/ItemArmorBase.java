package com.github.upcraftlp.glasspane.item.armor;

import com.github.upcraftlp.glasspane.api.util.NameUtils;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.EnumFacing;

import java.util.Locale;

public class ItemArmorBase extends ItemArmor {

    protected static final EnumFacing DEFAULT_FACING = null;

    public ItemArmorBase(String name, ArmorMaterial materialIn, EntityEquipmentSlot equipmentSlotIn) {
        super(materialIn, 0, equipmentSlotIn);
        NameUtils.name(this, name + "_" + equipmentSlotIn.getName().toLowerCase(Locale.ROOT));
    }
}
