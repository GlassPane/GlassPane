package com.github.upcraftlp.glasspane.client;

import com.github.upcraftlp.glasspane.GlassPane;
import com.github.upcraftlp.glasspane.api.util.ForgeUtils;
import com.github.upcraftlp.glasspane.config.Lens;
import com.google.common.base.Preconditions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@SideOnly(Side.CLIENT)
public class ClientUtil {

    public static final File RESOURCES_DIR = ForgeUtils.createNewDirectory(new File(ForgeUtils.MC_DIR, "resources"));
    private static final File TEXTURES_DIR = ForgeUtils.createNewDirectory(new File(ForgeUtils.MOD_RESOURCES, "textures"));
    private static final File PERSISTENT_DATA = new File(ForgeUtils.MOD_RESOURCES, Minecraft.getMinecraft().getSession().getPlayerID() + ".data");
    private static NBTTagCompound dataCompound;

    private static NBTTagCompound loadData() {
        NBTTagCompound ret = null;
        if(!PERSISTENT_DATA.exists()) try {
            PERSISTENT_DATA.createNewFile();
        }
        catch(IOException e) {
            GlassPane.getLogger().error("unable to create persistent save file!", e);
        }
        else try (FileInputStream inputStream = new FileInputStream(PERSISTENT_DATA)){
            ret = CompressedStreamTools.readCompressed(inputStream);
        } catch(Exception e) {
            GlassPane.getLogger().error("unable to read persistent data!", e);
        }
        if(ret == null) ret = new NBTTagCompound();
        writePersistentDataInternal(ret, "lastModified", new NBTTagLong(new Date().getTime()));
        return ret;
    }

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(5);

    public static ITextComponent getKeyInfo(TextFormatting textColor, TextFormatting keyColor) {
        ITextComponent keyInfo = new TextComponentString(Lens.client.keyAdvandedTooltip.getDisplayName()).setStyle(new Style().setColor(keyColor));
        return new TextComponentTranslation("tooltip.glasspane.generic.advancedKeyInfo", keyInfo).setStyle(new Style().setColor(textColor));
    }

    public static KeyBinding createKeyBinding(String translationKey, int defaultKeyCode) {
        return createKeyBinding(translationKey, defaultKeyCode, "general");
    }

    public static KeyBinding createKeyBinding(String name, int defaultKeyCode, String category) {
        KeyBinding keyBinding = new KeyBinding("key.glasspane.category." + category + "." + name, defaultKeyCode, "key.glasspane.categories." + category);
        ClientRegistry.registerKeyBinding(keyBinding);
        return keyBinding;
    }

    /**
     * open's a mod's config if present, does nothing otherwise
     *
     * @param mod the mod instance or modid String to open the config for
     */
    public static void openConfig(Object mod) {
        Minecraft mc = Minecraft.getMinecraft();
        ModContainer modContainer = FMLCommonHandler.instance().findContainerFor(mod);
        IModGuiFactory guiFactory = FMLClientHandler.instance().getGuiFactoryFor(modContainer);
        if(guiFactory.hasConfigGui()) {
            GuiScreen configGui = guiFactory.createConfigGui(mc.currentScreen);
            mc.displayGuiScreen(configGui);
        }
    }

    public static void downloadTexture(String textureURL, String textureName, Consumer<ResourceLocation> onComplete) {
        Preconditions.checkArgument(StringUtils.isNullOrEmpty(textureURL));
        Preconditions.checkArgument(StringUtils.isNullOrEmpty(textureName));
        EXECUTOR_SERVICE.submit(() -> {
            File textureFile = new File(TEXTURES_DIR, textureName.replace(":", "/"));
            try {
                FileUtils.copyURLToFile(new URL(textureURL), textureFile);
                BufferedImage image = ImageIO.read(textureFile);
                Minecraft mc = Minecraft.getMinecraft();
                mc.addScheduledTask(() -> onComplete.accept(mc.getTextureManager().getDynamicTextureLocation(textureName, new DynamicTexture(image))));
            } catch(Exception e) {
                GlassPane.getLogger().error("unable to download resource \"{}\" to file \"{}\"!", StringUtils.isNullOrEmpty(textureURL) ? "<none>" : textureURL, StringUtils.isNullOrEmpty(textureName) ? "<none>" : textureName, e);
            }
        });
    }

    public static void writePersistentData(String key, NBTBase value) {
        writePersistentDataInternal(dataCompound, key, value);
    }

    private static void writePersistentDataInternal(NBTTagCompound nbt, String key, NBTBase value) {
        nbt.setTag(key, value);
        try (FileOutputStream outputStream = new FileOutputStream(PERSISTENT_DATA)){
            CompressedStreamTools.writeCompressed(nbt, outputStream);
        } catch(Exception e) {
            GlassPane.getLogger().error("unable to write persistent data!", e);
        }
    }

    public static NBTTagCompound getPersistentData() {
        if(dataCompound == null) dataCompound = loadData();
        return dataCompound.copy();
    }

}
