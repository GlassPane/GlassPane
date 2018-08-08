package com.github.upcraftlp.glasspane.api.event;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * subscribe to this class using the {@link MinecraftForge#EVENT_BUS}
 */
@SuppressWarnings("unused")
@Cancelable
public class ModOutdatedEvent extends Event {

    private final ForgeVersion.CheckResult result;
    private final ModContainer mc;

    public ModOutdatedEvent(ModContainer mod, ForgeVersion.CheckResult result) {
        this.result = result;
        this.mc = mod;
    }

    public ForgeVersion.CheckResult getVersionChecksResult() {
        return this.result;
    }

    public ModContainer getActiveModContainer() {
        return this.mc;
    }
}
