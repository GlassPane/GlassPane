package com.github.upcraftlp.glasspane.api.util;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ForgeUtils {

    public static final ResourceLocation MISSING = new ResourceLocation("minecraft", "missingno");
    private static final Map<String, Boolean> LOADED_MODS = new ConcurrentHashMap<>();

    @Nullable
    public static ModContainer getModContainer(String modid) {
        return FMLCommonHandler.instance().findContainerFor(modid);
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

    public static void setCurrentModContainer(@Nullable ModContainer modContainer) {
        Loader.instance().setActiveModContainer(modContainer);
    }

    public static String getCurrentModID() {
        ModContainer container = getCurrentModContainer();
        return container != null ? container.getModId() : null;
    }

    public static boolean isModLoaded(String modid) {
        if(!LOADED_MODS.containsKey(modid)) LOADED_MODS.put(modid, Loader.isModLoaded(modid));
        return LOADED_MODS.get(modid);
    }

}
