package com.github.upcraftlp.glasspane.guide;

import com.github.upcraftlp.glasspane.api.guide.IGuideCategory;
import com.github.upcraftlp.glasspane.api.guide.IGuidePage;
import com.github.upcraftlp.glasspane.api.util.ForgeUtils;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GuideCategoryImpl implements IGuideCategory {

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    @Nullable
    public ResourceLocation getIcon() {
        return icon;
    }

    @Override
    public List<IGuidePage> getPages() {
        return Collections.unmodifiableList(Arrays.asList(pages));
    }

    private ResourceLocation id = ForgeUtils.MISSING;

    @Nullable
    private ResourceLocation icon;

    /**
     * package-private because it's accessed in {@link GuideBookImpl#jsonPostProcess()}
     */
    GuidePageImpl[] pages = new GuidePageImpl[0];
}
