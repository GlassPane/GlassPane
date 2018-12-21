package com.github.upcraftlp.glasspane.api.event.factory;

import com.github.upcraftlp.glasspane.api.event.ModOutdatedEvent;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.ModContainer;

public class GlassPaneEventFactory {

    public static boolean onModOutdated(ModContainer mod, ForgeVersion.CheckResult checkResult) {
        return MinecraftForge.EVENT_BUS.post(new ModOutdatedEvent(mod, checkResult));
    }

}
