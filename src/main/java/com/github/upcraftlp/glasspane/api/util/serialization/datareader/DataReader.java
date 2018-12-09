package com.github.upcraftlp.glasspane.api.util.serialization.datareader;

import com.github.upcraftlp.glasspane.GlassPane;
import net.minecraft.util.ResourceLocation;

import java.io.InputStream;

/**
 * used to read data from mod resources, in compliance with MC1.13's /data/ structure separated from assets
 */
public interface DataReader<T> {

    default String getPath(ResourceLocation location, AssetType type) {
        String path = "/" + type.pathPrefix + "/" + location.getNamespace() + "/" + location.getPath();
        if(type.isLocalized()) {
            String languageCode = GlassPane.proxy.getCurrentLanguageCode();
            int extension = path.lastIndexOf('.');
            if(extension != -1) {
                path = path.substring(0, extension) + "_" + languageCode + path.substring(extension);
            }
            else path += languageCode;
        }
        return path;
    }

    T readData(InputStream in);

    enum AssetType {
        ASSETS("assets", true),
        DATA("data", false);

        /**
         * the prefix for the asset path
         */
        public final String pathPrefix;
        private final boolean localized;

        AssetType(String pathPrefix, boolean isLocalized) {
            this.pathPrefix = pathPrefix;
            this.localized = isLocalized;
        }

        public boolean isLocalized() {
            return localized;
        }
    }
}
