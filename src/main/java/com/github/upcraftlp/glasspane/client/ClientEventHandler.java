package com.github.upcraftlp.glasspane.client;

import com.github.upcraftlp.glasspane.GlassPane;
import com.github.upcraftlp.glasspane.api.color.DefaultColors;
import com.github.upcraftlp.glasspane.config.Lens;
import com.github.upcraftlp.glasspane.item.ItemBase;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.input.Keyboard;

@Mod.EventBusSubscriber(modid = GlassPane.MODID, value = Side.CLIENT)
public class ClientEventHandler {

    private static boolean shouldRenderBackgroundColor = false;

    @SubscribeEvent
    public static void preRenderTooltip(RenderTooltipEvent.Pre event) {
        Item item = event.getStack().getItem();
        shouldRenderBackgroundColor = item instanceof ItemBase && ((ItemBase) item).hasAdvancedTooltip(event.getStack()) && Lens.client.showAdvancedTooltipInfo && (Minecraft.getMinecraft().gameSettings.advancedItemTooltips || Keyboard.isKeyDown(Lens.client.keyAdvandedTooltip.getKeyCode()));
    }

    @SubscribeEvent
    public static void renderTooltipBackground(RenderTooltipEvent.Color event) {
        if(shouldRenderBackgroundColor) {
            //TODO how to indicate debug tooltip?
            event.setBorderStart(DefaultColors.DISCORD_BLUE);
            event.setBorderEnd(DefaultColors.DISCORD_BLUE);
        }
    }

    @SubscribeEvent
    public static void postRenderTooltipBackground(RenderTooltipEvent.PostBackground event) {
        if(shouldRenderBackgroundColor) shouldRenderBackgroundColor = false;
    }

}
