package com.github.upcraftlp.glasspane.api.guide;

import com.github.upcraftlp.glasspane.api.util.NameUtils;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class GuidePage {

    public ResourceLocation getId() {
        return id;
    }

    @Nullable
    public ResourceLocation getIcon() {
        return icon;
    }

    private ResourceLocation id = NameUtils.MISSING;

    @Nullable
    private ResourceLocation icon;
}
