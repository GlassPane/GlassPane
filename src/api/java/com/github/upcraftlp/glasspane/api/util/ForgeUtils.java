package com.github.upcraftlp.glasspane.api.util;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

import javax.annotation.Nullable;

public class ForgeUtils {

    public static final ResourceLocation MISSING = new ResourceLocation("minecraft", "missingno");

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
}
