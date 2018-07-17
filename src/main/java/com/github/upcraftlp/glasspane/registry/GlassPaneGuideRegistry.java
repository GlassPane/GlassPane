package com.github.upcraftlp.glasspane.registry;

import com.github.upcraftlp.glasspane.GlassPane;
import com.github.upcraftlp.glasspane.api.guide.Guide;
import com.github.upcraftlp.glasspane.api.guide.GuideBook;
import com.github.upcraftlp.glasspane.api.util.ForgeUtils;
import com.github.upcraftlp.glasspane.config.Lens;
import com.github.upcraftlp.glasspane.util.JsonUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GlassPaneGuideRegistry {

    private static final Map<ResourceLocation, GuideBook> GUIDES = new HashMap<>();

    public static void initGuideBooks(FMLPreInitializationEvent event) {
        final ModContainer modContainer = ForgeUtils.getCurrentModContainer();
        Set<ASMDataTable.ASMData> asmData = event.getAsmData().getAll(Guide.class.getCanonicalName());
        for(ASMDataTable.ASMData entry : asmData) {
            try {
                String modid = (String) entry.getAnnotationInfo().getOrDefault("modid", "minecraft");
                String value = (String) entry.getAnnotationInfo().get("value");
                ResourceLocation guideName = new ResourceLocation(modid, value);
                ForgeUtils.setCurrentModContainer(modid);
                String targetClass = entry.getClassName();
                String targetField = entry.getObjectName();
                Field f = Class.forName(targetClass).getDeclaredField(targetField);
                if(f.getType() != GuideBook.class) {
                    GlassPane.getLogger().error("unable to inject guide {} into {}.{}: wrong type ({}), annotated field MUST be of type {}!", guideName, targetClass, targetField, f.getType().getCanonicalName(), GuideBook.class.getCanonicalName());
                    continue;
                }
                if((f.getModifiers() & Modifier.STATIC) != Modifier.STATIC) {
                    GlassPane.getLogger().error("unable to inject guide {} into {}.{}: not static!", guideName, targetClass, targetField);
                    continue;
                }
                if(!GUIDES.containsKey(guideName)) readGuideFromJson(guideName);
                EnumHelper.setFailsafeFieldValue(f, null, getGuide(guideName));

            } catch(Exception e) {
                GlassPane.getLogger().error("error while preparing class for automatic registration", e);
            }
        }
        ForgeUtils.setCurrentModContainer(modContainer);
    }

    public static void readGuideFromJson(ResourceLocation guideName) {
        String path = "/assets/" + guideName.getResourceDomain() + "/guides/" + guideName.getResourcePath() + "/index.json";
        try(InputStream stream = GlassPaneGuideRegistry.class.getResourceAsStream(path)) {
            if(stream != null) {
                GuideBook guide = JsonUtil.GSON.fromJson(new InputStreamReader(stream), GuideBook.class);
                guide.setGuideName(guideName);
                GUIDES.put(guideName, guide);
            }
            else throw new FileNotFoundException("guide index file not found: " + path);
        }
        catch (Exception e) {
            GlassPane.getLogger().error("unable to read guide {}: {}", guideName, e.getMessage());
            if(Lens.debugMode) GlassPane.getDebugLogger().error("full stacktrace:", e);
            GUIDES.remove(guideName);
        }
    }

    public static GuideBook getGuide(ResourceLocation guideName) {
        if(!GUIDES.containsKey(guideName)) GUIDES.put(guideName, new GuideBook(guideName));
        return GUIDES.get(guideName);
    }
}
