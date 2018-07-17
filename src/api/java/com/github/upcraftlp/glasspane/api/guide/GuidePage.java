package com.github.upcraftlp.glasspane.api.guide;

import com.github.upcraftlp.glasspane.GlassPane;
import com.github.upcraftlp.glasspane.api.util.NameUtils;
import com.github.upcraftlp.glasspane.config.Lens;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class GuidePage {

    public ResourceLocation getId() {
        return id;
    }

    @Nullable
    public ResourceLocation getIcon() {
        return icon;
    }

    private ResourceLocation id = NameUtils.MISSING;

    private transient boolean loaded = false;

    @Nullable
    private ResourceLocation icon;

    public boolean isLoaded() {
        return this.loaded;
    }

    public void read(InputStream inputStream) {
        try {
            String rawPageText = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            if(Lens.debugMode) GlassPane.getDebugLogger().info("----------------------------------------------------------------------\nloaded page: {}\n{}\n----------------------------------------------------------------------", this.id, rawPageText);
            this.loaded = true;
        } catch(IOException e) {
            GlassPane.getLogger().error("unable to read page" + this.id, e);
            this.loaded = false;
        }
    }
}
