package com.github.upcraftlp.glasspane.guide;

import com.github.upcraftlp.glasspane.GlassPane;
import com.github.upcraftlp.glasspane.api.guide.IGuidePage;
import com.github.upcraftlp.glasspane.api.util.ForgeUtils;
import com.github.upcraftlp.glasspane.config.Lens;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class GuidePageImpl implements IGuidePage {

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    @Nullable
    public ResourceLocation getIcon() {
        return icon;
    }

    private ResourceLocation id = ForgeUtils.MISSING;

    private transient boolean loaded = false;

    @Nullable
    private ResourceLocation icon;

    @Override
    public boolean isLoaded() {
        return this.loaded;
    }

    @Override
    public void readPage(InputStream inputStream) {
        try {
            String rawPageText = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            if(Lens.debugMode) GlassPane.getDebugLogger().info("loaded page: {}\nPAGE START\n{}\nPAGE END", this.id, rawPageText);
            this.loaded = true;
        } catch(IOException e) {
            GlassPane.getLogger().error("unable to read page" + this.id, e);
            this.loaded = false;
        }
    }
}
