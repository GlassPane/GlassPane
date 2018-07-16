package com.github.upcraftlp.glasspane.api.guide;

import com.github.upcraftlp.glasspane.api.util.NameUtils;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class GuideCategory {

    public ResourceLocation getId() {
        return id;
    }

    @Nullable
    public ResourceLocation getIcon() {
        return icon;
    }

    public GuidePage[] getPages() {
        return pages;
    }

    private ResourceLocation id = NameUtils.MISSING;

    @Nullable
    private ResourceLocation icon;

    private GuidePage[] pages = new GuidePage[0];
}
