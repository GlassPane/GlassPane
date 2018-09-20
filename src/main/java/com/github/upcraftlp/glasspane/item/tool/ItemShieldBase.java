package com.github.upcraftlp.glasspane.item.tool;

import com.github.upcraftlp.glasspane.api.util.NameUtils;
import com.google.common.annotations.Beta;
import net.minecraft.item.ItemShield;
import org.apache.commons.lang3.NotImplementedException;

@Beta
public class ItemShieldBase extends ItemShield {

    //TODO shield class

    public ItemShieldBase(String name) {
        NameUtils.name(this, name);
        throw new NotImplementedException("shields are to be finished");
    }
}
