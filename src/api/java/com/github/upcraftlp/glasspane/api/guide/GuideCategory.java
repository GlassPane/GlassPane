package com.github.upcraftlp.glasspane.api.guide;

import com.github.upcraftlp.glasspane.api.util.NameUtils;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GuideCategory {

    public ResourceLocation getId() {
        return id;
    }

    @Nullable
    public ResourceLocation getIcon() {
        return icon;
    }

    public List<GuidePage> getPages() {
        return Collections.unmodifiableList(Arrays.asList(pages));
    }

    private ResourceLocation id = NameUtils.MISSING;

    @Nullable
    private ResourceLocation icon;

    /**
     * package-private because it's accessed in {@link GuideBook#jsonPostProcess()}
     */
    GuidePage[] pages = new GuidePage[0];
}
