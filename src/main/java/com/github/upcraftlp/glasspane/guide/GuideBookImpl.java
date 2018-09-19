package com.github.upcraftlp.glasspane.guide;

import com.github.upcraftlp.glasspane.GlassPane;
import com.github.upcraftlp.glasspane.api.guide.IGuideBook;
import com.github.upcraftlp.glasspane.api.guide.IGuideCategory;
import com.github.upcraftlp.glasspane.api.guide.IGuidePage;
import com.github.upcraftlp.glasspane.api.util.serialization.JsonPostProcessable;
import com.github.upcraftlp.glasspane.registry.GlassPaneGuideRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class GuideBookImpl implements IGuideBook, JsonPostProcessable {

    private ResourceLocation id;

    public GuideBookImpl(ResourceLocation guideName) {
        this.id = guideName;
    }

    @Override
    public ResourceLocation getGuideName() {
        return id;
    }

    private GuideCategoryImpl[] categories = new GuideCategoryImpl[0];

    @Override
    public List<IGuideCategory> getCategories() {
        return Collections.unmodifiableList(Arrays.asList(categories));
    }

    @Override
    public void jsonPostProcess() {
        Arrays.stream(this.categories).forEach(category -> {
            if(GlassPane.isDebugMode()) GlassPane.getDebugLogger().info("parsing category: {}, pages: {}", category.getId(), category.pages.length);
            for(int i = 0; i < category.pages.length; i++) {
                IGuidePage page = category.pages[i];
                //TODO parse page!
                ResourceLocation pageID = page.getId();
                String path = "/assets/" + this.id.getNamespace() + "/guides/" + this.id.getPath() + "/" + Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode().toLowerCase(Locale.ROOT) + "/" + pageID.getNamespace() + "/" + pageID.getPath() + ".md";
                try(InputStream inputStream = GlassPaneGuideRegistry.class.getResourceAsStream(path)) {
                    page.readPage(inputStream);
                }
                catch(Exception e) {
                    GlassPane.getLogger().error("unable to parse page " + pageID + "@" + this.id + ", substituting empty page!", e);
                    category.pages[i] = new GuidePageImpl();
                }
            }
        });
    }
}
