package com.github.upcraftlp.glasspane.api.guide;

import com.github.upcraftlp.glasspane.GlassPane;
import com.github.upcraftlp.glasspane.api.util.serialization.JsonPostProcessable;
import com.github.upcraftlp.glasspane.registry.GlassPaneGuideRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class GuideBook implements JsonPostProcessable {

    private transient ResourceLocation id;

    public GuideBook(ResourceLocation guideName) {
        this.id = guideName;
    }

    public ResourceLocation getGuideName() {
        return id;
    }

    private GuideCategory[] categories = new GuideCategory[0];

    public List<GuideCategory> getCategories() {
        return Collections.unmodifiableList(Arrays.asList(categories));
    }

    public void setGuideName(ResourceLocation guideName) {
        this.id = guideName;
    }

    @Override
    public void jsonPostProcess() {
        Arrays.stream(this.categories).forEach(category -> {
            for(int i = 0; i < category.pages.length; i++) {
                GuidePage page = category.pages[i];
                //TODO parse page!
                ResourceLocation pageID = page.getId();
                String path = "/assets/" + this.id.getResourceDomain() + "/guides/" + this.id.getResourcePath() + "/" + Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode().toLowerCase(Locale.ROOT) + "/" + pageID.getResourceDomain() + "/" + pageID.getResourcePath() + ".md";
                try(InputStream inputStream = GlassPaneGuideRegistry.class.getResourceAsStream(path)) {
                    page.read(inputStream);
                }
                catch(Exception e) {
                    GlassPane.getLogger().error("unable to parse page " + pageID + "@" + this.id + ", substituting empty page!", e);
                    category.pages[i] = new GuidePage();
                }
            }
        });
    }
}
