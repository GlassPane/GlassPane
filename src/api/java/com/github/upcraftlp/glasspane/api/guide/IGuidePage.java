package com.github.upcraftlp.glasspane.api.guide;

import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.io.InputStream;

public interface IGuidePage {
    ResourceLocation getId();

    @Nullable
    ResourceLocation getIcon();

    boolean isLoaded();

    void readPage(InputStream inputStream);
}
