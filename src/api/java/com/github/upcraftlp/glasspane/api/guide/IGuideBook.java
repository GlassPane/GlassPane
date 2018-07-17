package com.github.upcraftlp.glasspane.api.guide;

import net.minecraft.util.ResourceLocation;

import java.util.List;

public interface IGuideBook {

    ResourceLocation getGuideName();

    List<IGuideCategory> getCategories();
}
