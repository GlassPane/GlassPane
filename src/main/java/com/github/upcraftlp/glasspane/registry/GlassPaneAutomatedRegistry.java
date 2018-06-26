package com.github.upcraftlp.glasspane.registry;

import com.github.upcraftlp.glasspane.GlassPane;
import com.github.upcraftlp.glasspane.api.registry.AutoRegistry;
import com.github.upcraftlp.glasspane.api.registry.RegistryPostProcessor;
import com.github.upcraftlp.glasspane.api.util.ForgeUtils;
import com.github.upcraftlp.glasspane.registry.processor.PostProcessorBlock;
import com.github.upcraftlp.glasspane.registry.processor.PostProcessorItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;


@Mod.EventBusSubscriber(modid = GlassPane.MODID)
public class GlassPaneAutomatedRegistry {

    private static List<Class> REGISTRY_CLASSES = new ArrayList<>();

    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public static <T extends IForgeRegistryEntry<T>> void onRegister(RegistryEvent.Register event) {
        ModContainer currentModContainer = Loader.instance().activeModContainer();
        IForgeRegistry<T> registry = event.getRegistry();
        GlassPane.getLogger().debug("beginning registry of type {}...", registry.getRegistrySuperType().getSimpleName());
        for(Class clazz : REGISTRY_CLASSES) {
            AutoRegistry annotation = AutoRegistry.class.cast(clazz.getAnnotation(AutoRegistry.class));
            String modid = annotation.value();
            ForgeUtils.setCurrentModContainer(modid);
            for(Field f : clazz.getDeclaredFields()) {
                if(Modifier.isStatic(f.getModifiers()) && registry.getRegistrySuperType().isAssignableFrom(f.getType())) {
                    try {
                        T entry = (T) f.get(null);
                        if(entry.getRegistryName() == null) {
                            GlassPane.getLogger().warn("No registry name set for {}:{}, substituting field name", clazz.getName(), f.getName());
                            entry.setRegistryName(new ResourceLocation(modid, f.getName().toLowerCase(Locale.ROOT)));
                        }
                        register(entry, registry);
                    } catch(IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        ForgeUtils.setCurrentModContainer(currentModContainer);
        GlassPane.getLogger().debug("ModContainer restored!");
    }

    @SuppressWarnings("unchecked")
    public static <T extends IForgeRegistryEntry<T>> void register(T entry, IForgeRegistry<T> registry) {
        registry.register(entry);
        for(RegistryPostProcessor<T> processor : RegistryPostProcessor.getPostProcessors(registry.getRegistrySuperType())) {
            processor.process(entry, registry, FMLCommonHandler.instance().getSide());
        }
    }

    public static void registerDefaultPostProcessors() {
        RegistryPostProcessor.registerPostProcessor(new PostProcessorBlock());
        RegistryPostProcessor.registerPostProcessor(new PostProcessorItem());
    }

    public static void gatherAnnotatedClasses(FMLPreInitializationEvent event) {
        final ModContainer modContainer = ForgeUtils.getCurrentModContainer();
        Set<ASMDataTable.ASMData> asmData = event.getAsmData().getAll(AutoRegistry.class.getCanonicalName());
        asmData.forEach(data -> {
            try {
                String modid = (String) data.getAnnotationInfo().get("value");
                ForgeUtils.setCurrentModContainer(modid);
                Class clazz = Class.forName(data.getClassName());
                REGISTRY_CLASSES.add(clazz);
            } catch(Exception e) {
                GlassPane.getLogger().error("error while preparing class for automatic registration", e);
            }


        });
        ForgeUtils.setCurrentModContainer(modContainer);
    }
}
