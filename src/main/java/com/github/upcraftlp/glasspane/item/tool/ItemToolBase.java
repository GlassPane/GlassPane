package com.github.upcraftlp.glasspane.item.tool;

import com.github.upcraftlp.glasspane.api.client.IItemTooltipProvider;
import com.github.upcraftlp.glasspane.api.util.NameUtils;
import net.minecraft.item.ItemTool;

import java.util.Collections;

public class ItemToolBase extends ItemTool implements IItemTooltipProvider {

    public ItemToolBase(String name, float attackDamageIn, float attackSpeedIn, ToolMaterial materialIn) {
        super(attackDamageIn, attackSpeedIn, materialIn, Collections.emptySet());
        NameUtils.name(this, name);
    }

    public ItemToolBase(String name, ToolMaterial materialIn) {
        this(name, 0.0F, 0.0F, materialIn);
    }

    public ItemToolBase setAxeLevel(int harvestLevel) {
        this.setHarvestLevel("axe", harvestLevel);
        return this;
    }

    public ItemToolBase setPickaxeLevel(int harvestLevel) {
        this.setHarvestLevel("pickaxe", harvestLevel);
        return this;
    }

    public ItemToolBase setShovelLevel(int harvestLevel) {
        this.setHarvestLevel("shovel", harvestLevel);
        return this;
    }

}
