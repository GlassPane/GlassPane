package com.github.upcraftlp.glasspane.util.debug;

import com.github.upcraftlp.glasspane.GlassPane;
import com.github.upcraftlp.glasspane.api.util.ForgeUtils;
import com.google.common.collect.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.registries.*;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class RegistryDumper {

    private static final BiMap<ResourceLocation, ForgeRegistry<? extends IForgeRegistryEntry<?>>> REGISTRIES = ReflectionHelper.getPrivateValue(RegistryManager.class, RegistryManager.ACTIVE, "registries");

    private static File DATA_DIR = new File(ForgeUtils.MC_DIR, "logs/glasspane/registry");
    public static List<String> REGISTRY_NAMES = REGISTRIES.keySet().stream().map(ResourceLocation::toString).collect(Collectors.toList());

    static {
        ForgeUtils.createNewDirectory(DATA_DIR);
    }

    public static void dumpAllRegistries() {
        REGISTRIES.keySet().forEach(RegistryDumper::dumpRegistry);
    }

    public static void dumpRegistry(ResourceLocation registryName) {
        ForgeRegistry<? extends IForgeRegistryEntry<?>> registry = REGISTRIES.get(registryName);
        if(registry != null) {
            GlassPane.getDebugLogger().info("dumping active registry: {}", registryName);
            BufferedWriter writer = null;
            try {
                File dumpFile = new File(DATA_DIR, URLEncoder.encode("registry-dump_" + registryName.getNamespace() + "." + registryName.getPath() + ".md", "UTF-8"));
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dumpFile), StandardCharsets.UTF_8));

                writer.write("# Registry Dump of: " + registryName + "\n");
                writer.write("<br/>\n");

                writer.write("| ID | Resource Name |\n");
                writer.write("|---:|---------------|\n"); //colon on the right of the ID separator means right-aligned

                //do some sorting based on entry ID
                List<ResourceLocation> regNames = new ArrayList<>(registry.getKeys());
                regNames.sort(Comparator.comparingInt(registry::getID));

                for(ResourceLocation entry : regNames) {
                    writer.write("| " + registry.getID(entry) + " | " + entry.toString() + " |\n");
                }

                writer.write("<br/>\n");
                writer.write(registry.getKeys().size() + " entries in total.\n");

                writer.flush();
            }
            catch (Exception e) {
                GlassPane.getDebugLogger().error("caught exception trying to dump registry {}", registryName);
                GlassPane.getDebugLogger().error("unable to dump registry", e);
            }
            finally {

                IOUtils.closeQuietly(writer);
            }
        }
        else {
            GlassPane.getDebugLogger().warn("unable to dump invalid registry: {}", registryName);
        }
    }
}
