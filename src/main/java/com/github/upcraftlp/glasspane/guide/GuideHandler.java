package com.github.upcraftlp.glasspane.guide;

import com.github.upcraftlp.glasspane.GlassPane;
import com.github.upcraftlp.glasspane.api.guide.IGuideBook;
import com.github.upcraftlp.glasspane.client.gui.GuiScreenGuide;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.commons.io.FileUtils;

import javax.annotation.Nullable;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = GlassPane.MODID, value = Side.CLIENT)
public class GuideHandler {

    //private static Map<ResourceLocation, ResourceLocation> LAST_PAGES = new HashMap<>(); //TODO implement guide "back" button?
    private static Map<ResourceLocation, ResourceLocation> PERSISTENT_PAGES = new HashMap<>();
    private static File SAVE_FILE;

    public static void openPage(IGuideBook guide, @Nullable ResourceLocation guidePage) {
        //if(mc.currentScreen instanceof GuiScreenGuide) {
        //    GuiScreenGuide guideScreen = (GuiScreenGuide) mc.currentScreen;
        //    LAST_PAGES.put(guideScreen.getBook().getGuideName(), guideScreen.getSelectedPage());
        //}
        Minecraft.getMinecraft().displayGuiScreen(new GuiScreenGuide(guide, guidePage));
        if(guidePage != null) PERSISTENT_PAGES.put(guide.getGuideName(), guidePage);
        else PERSISTENT_PAGES.remove(guide.getGuideName());
    }

    public static void openLastPage(IGuideBook guide) {
        openPage(guide, PERSISTENT_PAGES.get(guide.getGuideName()));
    }

    @SubscribeEvent
    public static void onDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        if(GlassPane.isDebugMode()) GlassPane.getDebugLogger().info("writing persistent mod data...");
        NBTTagList list = new NBTTagList();
        for(Map.Entry<ResourceLocation, ResourceLocation> entry : PERSISTENT_PAGES.entrySet()) {
            NBTTagCompound nbtEntry = new NBTTagCompound();
            nbtEntry.setString("guide", entry.getKey().toString());
            nbtEntry.setString("page", entry.getValue().toString());
            list.appendTag(nbtEntry);
        }
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("guidePages", list);
        File file = new File(SAVE_FILE.getAbsolutePath() + "_tmp");
        if(file.exists()) {
            try {
                FileUtils.forceDelete(file);
                file.createNewFile();
            } catch (IOException e) {
                GlassPane.getDebugLogger().error("unable to create temporary save file!", e);
            }
        }
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))){

            CompressedStreamTools.writeCompressed(nbt, outputStream);
            if(SAVE_FILE.exists()) SAVE_FILE.delete();
            file.renameTo(SAVE_FILE);
        } catch (IOException e) {
            GlassPane.getDebugLogger().error("unable to write persistent save file!", e);
        }
    }

    /**
     * internal use ONLY
     */
    public static void init(FMLPreInitializationEvent event) {
        //FIXME move to client persistent data!
        SAVE_FILE = new File(event.getModConfigurationDirectory(), "glasspanemods/persistent/glasspane_persistent.data");
        if(!SAVE_FILE.exists()) {
            try {
                FileUtils.forceMkdirParent(SAVE_FILE);
            } catch (IOException e) {
                GlassPane.getDebugLogger().error("unable to create persistent save file!", e);
            }
            return;
        }
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(SAVE_FILE))){
            NBTTagCompound data = CompressedStreamTools.readCompressed(inputStream);
            NBTTagList list = data.getTagList("guidePages", Constants.NBT.TAG_COMPOUND);
            PERSISTENT_PAGES.clear();
            for(int i = 0; i < list.tagCount(); i++) {
                NBTTagCompound guideData = list.getCompoundTagAt(i);
                ResourceLocation guide = new ResourceLocation(guideData.getString("guide"));
                ResourceLocation page = new ResourceLocation(guideData.getString("page"));
                PERSISTENT_PAGES.put(guide, page);
            }
        } catch (IOException e) {
            GlassPane.getDebugLogger().error("unable to parse persistent data!", e);
        }
    }
}
