package com.github.upcraftlp.glasspane.api.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

import javax.annotation.Nullable;
import java.util.List;

public interface IProxy {

    default void preInit(FMLPreInitializationEvent event) {

    }

    default void init(FMLInitializationEvent event) {

    }

    default void postInit(FMLPostInitializationEvent event) {

    }

    default void serverAboutToStart(FMLServerAboutToStartEvent event) {

    }

    default void serverStarting(FMLServerStartingEvent event) {

    }

    default void serverStarted(FMLServerStartedEvent event) {

    }

    default void serverStopping(FMLServerStoppingEvent event) {

    }

    default void serverStopped(FMLServerStoppedEvent event) {

    }

    default void handleInterModMessages(List<FMLInterModComms.IMCMessage> messages) {

    }

    @Nullable
    default ResourceLocation getSelectedSkin(String skin, EntityPlayer player) {
        return null;
    }
}
