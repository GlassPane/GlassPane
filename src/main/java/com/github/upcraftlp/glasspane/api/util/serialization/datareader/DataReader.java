package com.github.upcraftlp.glasspane.api.util.serialization.datareader;

import net.minecraft.util.ResourceLocation;

import java.io.InputStream;

/**
 * used to read data from mod resources, in compliance with MC1.13's /data/ structure separated from assets
 */
public interface DataReader<T> {

    default String getPath(ResourceLocation location, AssetType type) {
        return "/" + type.pathPrefix + "/" + location.getNamespace() + "/" + location.getPath();
    }

    T readData(InputStream in);

    enum AssetType {
        ASSETS("assets"),
        DATA("data");

        /**
         * the prefix for the asset path
         */
        public final String pathPrefix;

        AssetType(String pathPrefix) {
            this.pathPrefix = pathPrefix;
        }
    }
}
