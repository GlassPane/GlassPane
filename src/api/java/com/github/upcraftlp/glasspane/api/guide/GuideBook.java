package com.github.upcraftlp.glasspane.api.guide;

import net.minecraft.util.ResourceLocation;

public class GuideBook {

    private transient ResourceLocation guideName;

    public GuideBook(ResourceLocation guideName) {
        this.guideName = guideName;
    }

    public ResourceLocation getGuideName() {
        return guideName;
    }

    private GuideCategory[] categories = new GuideCategory[0];

    public GuideCategory[] getCategories() {
        return categories;
    }

    public void setGuideName(ResourceLocation guideName) {
        this.guideName = guideName;
    }
}
