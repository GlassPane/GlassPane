package com.github.upcraftlp.glasspane.api.client.resources;

import com.github.upcraftlp.glasspane.GlassPane;
import com.github.upcraftlp.glasspane.config.Lens;
import net.minecraft.client.resources.FolderResourcePack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@SideOnly(Side.CLIENT)
public class DefaultFolderResourcePack extends FolderResourcePack {

    private final String name;
    private final int packFormat;

    public DefaultFolderResourcePack(File location, String name) {
        this(location, name, 3);
    }

    public DefaultFolderResourcePack(File location, String name, int packFormat) {
        super(location);
        this.name = name;
        this.packFormat = packFormat;
    }

    @Override
    public String getPackName() {
        return this.name;
    }

    @Override
    protected InputStream getInputStreamByName(String resourceName) throws IOException {
        try {
            return super.getInputStreamByName(resourceName);
        }
        catch (IOException exception) {
            if ("pack.mcmeta".equals(resourceName)) {
                if(Lens.debugMode) GlassPane.getDebugLogger().info("ResourcePack {} is missing a pack.mcmeta file, substituting a dummy one", this.name);
                return new ByteArrayInputStream(("{\n" +
                        " \"pack\": {\n"+
                        "   \"description\": \"dummy Folder Resourcepack for "+ this.name +"\",\n"+
                        "   \"pack_format\": "+ this.packFormat + "\n"+
                        "}\n" +
                        "}").getBytes(StandardCharsets.UTF_8));
            }
            else throw exception;
        }
    }

    @Override
    protected boolean hasResourceName(String name) {
        return super.hasResourceName(name) || "pack.mcmeta".equals(name);
    }
}
