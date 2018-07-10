package com.github.upcraftlp.glasspane.api.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

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

    default int getSelectedItemSkin(ItemStack stack, EntityPlayer player) {
        return 0; //TODO implement in proxy classes!
    }
}