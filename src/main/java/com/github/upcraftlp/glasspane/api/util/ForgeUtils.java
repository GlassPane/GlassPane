package com.github.upcraftlp.glasspane.api.util;

import com.github.upcraftlp.glasspane.GlassPane;
import com.github.upcraftlp.glasspane.api.util.serialization.datareader.DataReader;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.apache.commons.io.FileUtils;

import javax.annotation.Nullable;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ForgeUtils {

    public static final ResourceLocation MISSING = new ResourceLocation("minecraft", "missingno");
    public static final File MC_DIR = ReflectionHelper.getPrivateValue(Loader.class, Loader.instance(), "minecraftDir");
    public static final File GLASSPANE_CONFIG_DIR = new File(MC_DIR, "config/glasspanemods");
    public static final File MOD_RESOURCES = createNewDirectory(new File(MC_DIR, "glasspane"));
    private static final Map<String, Boolean> LOADED_MODS = new ConcurrentHashMap<>();

    /**
     * creates a new directory if not existent, and returns it.
     */
    public static File createNewDirectory(File f) {
        if(!f.exists()) {
            try {
                FileUtils.forceMkdir(f);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;
    }

    public static String getCurrentModID() {
        ModContainer container = getCurrentModContainer();
        return container != null ? container.getModId() : null;
    }

    @Nullable
    public static ModContainer getCurrentModContainer() {
        return Loader.instance().activeModContainer();
    }

    public static void setCurrentModContainer(String modid) {
        ModContainer container = getCurrentModContainer();
        if(container == null || !container.getModId().equals(modid)) {
            setCurrentModContainer(getModContainer(modid));
        }
    }

    @Nullable
    public static ModContainer getModContainer(String modid) {
        return FMLCommonHandler.instance().findContainerFor(modid);
    }

    public static void setCurrentModContainer(@Nullable ModContainer modContainer) {
        Loader.instance().setActiveModContainer(modContainer);
    }

    public static boolean isModLoaded(String modid) {
        if(!LOADED_MODS.containsKey(modid)) LOADED_MODS.put(modid, Loader.isModLoaded(modid));
        return LOADED_MODS.get(modid);
    }

    public static <T> T readAssetData(ResourceLocation location, DataReader<T> reader, DataReader.AssetType type) {
        T ret;
        try {
            ret = reader.readData(getAssetInputStream(reader.getPath(location, type).toLowerCase(Locale.ROOT)));
        }
        catch (IOException e) {
            GlassPane.getDebugLogger().error("Exception reading data", e);
            ret = null;
        }
        return ret;
    }

    public static InputStream getAssetInputStream(String path) {
        return ForgeUtils.class.getResourceAsStream(path);
    }
}
