package com.github.upcraftlp.glasspane.api.guide;

import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.List;

public interface IGuideCategory {

    ResourceLocation getId();

    @Nullable
    ResourceLocation getIcon();

    List<IGuidePage> getPages();
}
